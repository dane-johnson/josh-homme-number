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

(defmacro mock-api
  [& args]
  `(with-redefs [fetch-url mock-fetch
                 get-artists mock-get-artists
                 get-bands mock-get-bands]
     ~@args))

(expect '(["Arthur Dent" "ad.com"
           ("Life, the Universe, and Everything" "Arthur Dent")])
        (mock-api (next-tier ["Arthur Dent" "ad.com" (list)])))
