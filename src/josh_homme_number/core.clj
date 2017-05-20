(ns josh-homme-number.core
  (:require [josh-homme-number [scraper :refer :all] [search :refer :all]])
  (:gen-class))


(def ^:dynamic *base-url*
  "http://www.allmusic.com/artist/josh-homme-mn0000828897")

(def found-artists (atom #{"Josh Homme"}))
(def found-bands (atom #{}))

(defn -main
  []
  (do
    (println "Getting all 2nd order persons")
    (->> *base-url*
         (fetch-url)
         (get-bands)
         (map #(fetch-url (second %)))
         (map get-artists))))
