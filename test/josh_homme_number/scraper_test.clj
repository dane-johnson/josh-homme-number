(ns josh-homme-number.scraper-test
  (:require [expectations :refer :all]
            [josh-homme-number.scraper :refer :all]))

(defn make-artist-link
  [name url]
  {:tag :a :attrs {:href url :title name}})
(defn make-band-link
  [name url]
  {:tag :a :content (list name) :attrs {:href url}})
(defn make-mock-page
  [class content]
  (list {:tag :div :attrs {:class class} :content content}))

(def mock-artist-page
  (make-mock-page "member-of"
                  (list
                   (make-band-link "Life, the Universe, and Everything" "http://ltuae.com")
                   (make-band-link "The Resturant at the end of the Universe" "http://trateotu.com"))))
(def mock-band-page
  (make-mock-page "group-members"
                  (list
                   (make-artist-link "Arthur Dent" "/artist/ad")
                   (make-artist-link "Ford Prefect" "/artist/fp"))))

(expect (hash-set ["Life, the Universe, and Everything" "http://ltuae.com"]
                  ["The Resturant at the end of the Universe" "http://trateotu.com"])
        (set (get-bands mock-artist-page)))
(expect (hash-set ["Arthur Dent" "http://allmusic.com/artist/ad"]
                  ["Ford Prefect" "http://allmusic.com/artist/fp"])
        (set (get-artists mock-band-page)))
