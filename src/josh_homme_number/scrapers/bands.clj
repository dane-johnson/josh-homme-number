(ns josh-homme-number.scrapers.bands
  (gen-class))

(def scrapers
  "Available scrapers: allmusic"
  {:allmusic (fn [node]
               [(first (get node :content))
                (get-in node [:attrs :href])])})
