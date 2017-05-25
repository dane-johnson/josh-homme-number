(ns josh-homme-number.search-test
  (:require [expectations :refer :all]
            [josh-homme-number.search :refer :all]
            [josh-homme-number.scraper :refer :all]))

(defn mock-fetch
  "Mock api call"
  [_]
  {})
(defn mock-get-artists
  "Mock artist scraper"
  [_]
  '(["Arthur Dent" "ad.com"]))
(defn mock-get-bands
  "Mock band scraper"
  [_]
  '(["Life, the Universe, and Everything" "ltuae.com"]))
(defn in-context
  {:expectations-options :in-context}
  [work]
  (let [work-ns (str (.ns (:the-var (meta work))))]
    (if (= work-ns "josh-homme-number.search-test")
      (with-redefs [fetch-url mock-fetch
                    get-artists mock-get-artists
                    get-bands mock-get-bands]
        (work))
      (work))))

(expect (more-> "Arthur Dent" first
                future? second
                '("Life, the Universe, and Everything" "Arthur Dent") last)
        (first (next-tier ["Arthur Dent" (future (fetch-url "")) (list)])))

(expect true? (fuzzy-match "Arthur Dent" "Arhur Rent"))
(expect false? (fuzzy-match "Arthur Dent" "Ford Prefect"))
