(ns josh-homme-number.tcp
  (:require [clojure.java.io :as io]
            [josh-homme-number.util :refer [root print-path]]
            [josh-homme-number.search :refer [search]])
  (gen-class))

(defn handle-request
  "Handles a single request for an artist"
  [conn]
  (binding [*in* (io/reader conn)
            *out* (io/writer conn)]
    (print-path (search (root) (read-line) 2)))
  (.close conn))

(defn server-loop
  "This will serve connections to the root over TCP"
  [port]
  (let [server (java.net.ServerSocket. port)]
    (loop []
      (-> (.accept server) (#(future (handle-request %))))
      (recur))))
