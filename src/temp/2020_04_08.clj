(ns temp.2020-04-08
  (:require [crux.api :as crux]
            [clojure.java.io :as io]
            [next.jdbc :as jdbc]
            ))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; How to setup Crux with embedded storage

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

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; How to setup Crux with PG backend

;; I've got this DB setup:
;; postgresql://localhost/heimdall?user=heimdall&password=f7886e6385e2579c9c60bfcc8c064b8a4ff6fc5b
;; jdbc:postgresql://localhost/heimdall?user=heimdall&password=f7886e6385e2579c9c60bfcc8c064b8a4ff6fc5b

;; First, are we able to connect at all?

(def pg-connection-str "jdbc:postgresql://localhost/heimdall?user=heimdall&password=f7886e6385e2579c9c60bfcc8c064b8a4ff6fc5b")

(comment
  (let [datasource (jdbc/get-datasource pg-connection-str)]
    (with-open [conn (jdbc/get-connection datasource)]
      (jdbc/execute! conn ["SELECT 90 * 980;"])
      ))

  ;; Using the jdbcUrl
  (def crux-pg-conf {:crux.node/topology '[crux.jdbc/topology]
                     :crux.jdbc/dbtype "postgresql"
                     :crux.jdbc/dbname "heimdall"
                     :crux.jdbc/jdbcUrl pg-connection-str})

  ;; Using jdbc properties
  (def crux-pg-conf {:crux.node/topology '[crux.jdbc/topology]
                     :crux.jdbc/dbtype "postgresql"
                     :crux.jdbc/user "heimdall"
                     :crux.jdbc/dbname "heimdall"
                     :crux.jdbc/host "localhost"
                     :crux.jdbc/password "f7886e6385e2579c9c60bfcc8c064b8a4ff6fc5b"
                     ;; :crux.jdbc/jdbcUrl pg-connection-str
                     })

  (def node ^crux.api.ICruxAPI (crux/start-node crux-pg-conf))
  (.close node)

  (crux/submit-tx
   node
   [[:crux.tx/put
     {:crux.db/id :dbpedia.resource/Pablo-Picasso
      :name "Pablo P."
      :last-name "Picasso"}]])
  ;; works
  ;; Perhaps we don't need any more?


  )
