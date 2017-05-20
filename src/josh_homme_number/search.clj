(ns josh-homme-number.search
  (:require [josh-homme-number.scraper :refer :all])
  (:gen-class))

(defrecord Node [artist links])
(defrecord Link [band nodes])

(def root ())

(def visited (atom #{}))

(defn search
  "Searches for the specified artist and returns the shortest path to
  her or nil if she is not found"
  [node artist max-depth]
  (cond
    (= (second node) artist) (list) ;; We've found her, back up the line!
    (or (zero? (dec max-depth)) (@visited artist)) nil ;; No dice
    :else (do
            (swap! visited conj artist)
            (let))))

;; REPL helpers
(def joshs-url josh-homme-number.core/*base-url*)
