(ns temp.2020-04-11)

;; How to load PG? We need to get this:

{:crux.node/topology '[crux.jdbc/topology]
 :crux.jdbc/dbtype "postgresql"
 :crux.jdbc/user "heimdall"
 :crux.jdbc/dbname "heimdall"
 :crux.jdbc/host "localhost"
 :crux.jdbc/password "f7886e6385e2579c9c60bfcc8c064b8a4ff6fc5b"
 ;; :crux.jdbc/jdbcUrl pg-connection-str
 }

;; from env vars.

;; then we need to put it into the config map in the right way.
