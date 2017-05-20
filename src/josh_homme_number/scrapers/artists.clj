(ns josh-homme-number.scrapers.artists
  (gen-class))

(def scrapers
  "Available scrapers: allmusic"
  {:allmusic (fn [node]
               [(get-in node [:attrs :title])
                (str "http://allmusic.com" (get-in node [:attrs :href]))])})
