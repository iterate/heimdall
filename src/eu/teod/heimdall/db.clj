(ns eu.teod.heimdall.db
  (:require [crux.api :as crux]
            [clojure.java.io :as io]
            [integrant.core :as ig]))

(def crux-dev-setup {:crux.node/topology '[crux.standalone/topology
                                           crux.kv.rocksdb/kv-store]
                     :crux.kv/db-dir (str (io/file "db"))})

(defn close-node [crux-node]
  (.close crux-node))

(comment
  ;; We want to control resources with Integrant
  ;; ... and we want that usual dev + user setup.

  (def node ^crux.api.ICruxAPI (crux/start-node crux-dev-setup))

  (crux/submit-tx
   node
   [[:crux.tx/put
     {:crux.db/id :dbpedia.resource/Pablo-Picasso
      :name "Pablo"
      :last-name "Picasso"}]])

  (crux/q (crux/db node)
          '{:find [e fn ln]
            :where [[e :name fn]
                    [e :last-name ln]]})
  ;; => #{[:dbpedia.resource/Pablo-Picasso "Pablo" "Picasso"]}
  )

;; Need integrant methods

(defmethod ig/init-key :eu.teod.heimdall/db [_ {:keys [db-dir-path] :as opts}]
  (prn ::init)
  (let [opts (merge (select-keys opts [:crux.node/topology])
                    {:crux.kv/db-dir (str (io/file db-dir-path))})]
    (crux/start-node opts)))

(defmethod ig/halt-key! :eu.teod.heimdall/db [_ node]
  (.close node))

;; as-of now, these only work for the in-memory option. We'd like to use a full
;; set of connection options, possibly loaded from environment variables.

;; But first, we need to see if we're able to connect.

(comment
  (System/getenv "PATH")

  (require '[environ.core :refer [env]])

  env
  )

