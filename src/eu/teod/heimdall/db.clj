(ns eu.teod.heimdall.db
  (:require [crux.api :as crux]
            [clojure.java.io :as io]
            [integrant.core :as ig]))


(defmethod ig/init-key :eu.teod.heimdall/db [_ {:keys [db-dir-path] :as opts}]
  (prn ::init 'db)
  (crux/start-node opts))

(defmethod ig/halt-key! :eu.teod.heimdall/db [_ node]
  (prn ::close 'db)
  (.close node))
