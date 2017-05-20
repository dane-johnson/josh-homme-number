(ns josh-homme-number.scraper
  (:require [net.cgrand.enlive-html :refer [html-resource select]]
            [josh-homme-number.scrapers [artists :as artists] [bands :as bands]])
  (gen-class))

(def ^:dynamic *artist-scraper* (:allmusic artists/scrapers))
(def ^:dynamic *band-scraper* (:allmusic bands/scrapers))

(defn fetch-url
  "Gets the html content of a url"
  [url]
  (html-resource (java.net.URL. url)))

(defn alist->content-url-map
  "Converts a list of links to a map of content to urls using the specified scraper"
  [scraper alist]
  (reduce #(conj %1 (scraper %2))
          (list) alist))

(defn scrape-alist
  "Scrapes a page for a specific list of links"
  [scraper selector page]
  (->> (select page [selector :a])
       (alist->content-url-map scraper)))

(def get-bands
  "Scrapes the page for bands"
  (partial scrape-alist *band-scraper* :div.member-of))
(def get-artists
  "Scrapes the page for artists"
  (partial scrape-alist *artist-scraper* :div.group-members))
