(ns josh-homme-number.search
  (:require [clj-fuzzy.metrics :refer [dice]]
            [josh-homme-number.scraper :refer :all])
  (:gen-class))


(def ^:dynamic *match-threshold* 0.5)

(defn fuzzy-match
  "Tests if two strings match within a certain degree, *match-threshold*"
  [a b]
  (> (dice a b) *match-threshold*))

(defn depth
  "Computes how many degrees are represented by a path"
  [path]
  (quot (count path) 2))

(defn next-tier
  "Gets the next tier of artists after the current one"
  [[name page path]]
  (->> (get-bands @page)
       (reduce (fn [s [band-name band-url]]
                 (->> (get-artists (fetch-url band-url))
                      ;; Push the new path into the vector
                      (map #(conj % (conj path name band-name)))
                      ;; Replace the URL with a future of the resolved
                      ;; page
                      (map #(update % 1 (fn fetch-in-future [url]
                                          (future (fetch-url url)))))
                      (concat s)))
               (list))))

(defn search
  "Depth first search to find a specific artist. Will return nil if
  max-depth is reached without finding the artist, otherwise will return
  a path of bands and artists to get back to root"
  [root artist max-depth]
  (loop [q (conj (clojure.lang.PersistentQueue/EMPTY) (conj root (list)))]
    (let [[name _ path :as v] (peek q)]
      (cond
        (nil? v) nil
        (fuzzy-match name artist) (cons name path)
        (= (depth path) max-depth) (recur (pop q))
        :else (recur (apply conj (pop q) (next-tier v)))))))
