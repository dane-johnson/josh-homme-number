(ns josh-homme-number.search
  (:require [josh-homme-number.scraper :refer :all])
  (:gen-class))

(def next-order
  "Gets the next order of artists after a given artist"
  (memoize
   ;; Artists collaborate with the same people a lot, no need to
   ;; Be repeating http requests
   (fn         
     [url]
     (let [bands (get-bands (fetch-url url))]
       (map #(hash-map (first %) (get-artists (fetch-url (second %)))) bands)))))

;; REPL helpers
(def joshs-url josh-homme-number.core/*base-url*)
