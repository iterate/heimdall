(ns eu.teod.heimdall.db
  (:require [crux.api :as crux]
            [clojure.java.io :as io]))

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
