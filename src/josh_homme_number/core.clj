(ns josh-homme-number.core
  (:require [josh-homme-number [scraper :refer :all]
                               [search :refer :all]])
  (:gen-class))


(def ^:dynamic *base-url*
  "http://www.allmusic.com/artist/josh-homme-mn0000828897")
(def ^:dynamic *base-artist*
  "Josh Homme")
(defn root
  "Returns the root node, using *base-artist* and *base-url*"
  []
  [*base-artist* (future (fetch-url *base-url*))])

;; CLI outputs
(defn print-path
  [path]
  (letfn [(print-prefix [artist band]
            (printf "%s with %s who was in " band artist))]
    (do
      (print (first path) "was in ")
      (loop [[band artist & others :as path] (rest path)]
        (if (> (count path) 2)
          (do
            (print-prefix artist band)
            (recur others))
          (printf "%s with %s.\n" band artist))))))

;; main function
(defn -main
  []
  (do
    (println "Looking for Kurt Cobain")
    (let [path (search (root) "Kurt Cobain" 2)]
      (if ((comp not nil?) path)
        (print-path path)
        (println "Could not find a connection.")))))
