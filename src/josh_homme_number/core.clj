(ns josh-homme-number.core
  (:require [josh-homme-number.tcp :refer [server-loop]])
  (:gen-class))

;; main function
(defn -main
  [& args]
  (let [port (Integer/parseInt (or (first args) "9000"))]
    (println "Server running on port" port)
    (server-loop port)))
