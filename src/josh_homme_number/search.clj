(ns josh-homme-number.search
  (:require [josh-homme-number.scraper :refer :all])
  (:gen-class))

(defprotocol OnPath
  (depth [_]))

(defrecord Node [name path links]
  OnPath
  (depth [_] ((comp #(quot % 2) count) path)))

(declare artist-node band-node) ;; Declare two constructors

;; A delayed future will allow a delay to be defined wrapping a future, so
;; we may delay http calls until there is a chance we will need to make
;; them, yet make them in a seperate thread.
(defn delayed-future
  [f]
  (delay (future f)))

(defn deref-delayed-future
  [df]
  (deref (deref df)))

(defn artist-node
  "Makes a node that represents an artist. Links will be delayed calls
  to create band nodes"
  [[name url] path]
  (->Node name path
          (let [artist-page (fetch-url url)
                band-tuples (get-bands artist-page)]
            (map #(delayed-future (band-node % (cons name path)))
                 band-tuples))))

(defn band-node
  "Makes a node that represents a band. Links will be evaluated
  immediately, so this will make as many http requests as is neccesary
  to create nodes for every artist in the band"
  [[name url] path]
  (->Node name
          (let [band-page (fetch-url url)
                artist-tuples (get-artists band-page)]
            (map #(artist-node artist-tuples (cons name path))))))

(defn next-artist-tier
  "Gets the next layer under this as a seq"
  [node]
  (->> (deref-delayed-future (:links node))
       (reduce #(concat %1 (:links %2)) (list))))

(defn search 
  "Searches through the artist tree to a specifed max depth, and returns 
  a path to get back to Homme from the artist, or nil if not found"
  [root artist max-depth]
  (loop [queue (conj clojure.lang.PersistentQueue/EMPTY root)]
    (let [curr (peek queue)
          queue (pop queue)]
      (cond
        (= artist (:name curr)) (:path curr)
        (> (.depth curr) max-depth) nil
        :else
        (let [next-artists (next-artist-tier curr)]
          (when ((comp not >) (inc (.depth curr)) max-depth)
            (doseq [next next-artists]
              (map force (:links next))))
          (recur (apply conj queue next-artists)))))))
