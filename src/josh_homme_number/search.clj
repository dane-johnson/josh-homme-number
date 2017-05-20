(ns josh-homme-number.search
  (:require [josh-homme-number.scraper :refer :all])
  (:gen-class))


(defrecord Node [name links])

(declare artist-node band-node) ;; Declare two constructors

(defn artist-node
  "Makes a node that represents an artist. Links will be delayed calls
  to create band nodes"
  [[name url]]
  (->Node name
          (let [artist-page (fetch-url url)
                band-tuples (get-bands artist-page)]
            (map #(delay (band-node %)) band-tuples))))

(defn band-node
  "Makes a node that represents a band. Links will be evaluated
  immediately, so this will make as many http requests as is neccesary
  to create nodes for every artist in the band"
  [[name url]]
  (->Node name
          (let [band-page (fetch-url url)
                artist-tuples (get-artists band-page)]
            (map artist-node artist-tuples))))


(def visited (atom #{}))

(defn search
  "Searches for a given artist, up to a given max depth"
  []
  )

