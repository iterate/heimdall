(ns temp.2020-04-07
  (:require [eu.teod.heimdall :as heimdall]
            [eu.teod.heimdall.handler]
            [clojure.edn]
            [clj-http.client :as http]
            ))

(comment
  (-> (http/get (str heimdall/local-url ,,,))
      :body)
  ;; => "Hello, world!"

  (-> (http/get (str heimdall/deploy-url-test "/admin/env")
                {:headers {:x-admin-token (str eu.teod.heimdall.handler/admin-token)}
                 :insecure? true})
      :body
      clojure.edn/read-string
      :env
      (select-keys eu.teod.heimdall.config/required-pg-env-keys)
      )

  (str eu.teod.heimdall.handler/admin-token)
  )

;; What keys do we get?
(comment
  (def db-keys #{:db-database
                 :db-host
                 :db-password
                 :db-port
                 :db-username})

  (-> (http/get (str heimdall/deploy-url-test "/admin/env")
                {:headers {:x-admin-token (str eu.teod.heimdall.handler/admin-token)}
                 :insecure? true})
      :body
      clojure.edn/read-string
      :env
      (select-keys db-keys)
      )
  ;; => {:db-username "apps-heimdall-test-655e87",
  ;;     :db-port "5432",
  ;;     :db-host "localhost",
  ;;     :db-password "F21ikTH0tE6G2Iw",
  ;;     :db-database "apps-heimdall-test-655e87"}

  (-> (http/get (str heimdall/deploy-url-test "/admin/env")
                {:headers {:x-admin-token (str eu.teod.heimdall.handler/admin-token)}
                 :insecure? true})
      :body
      clojure.edn/read-string
      :env
      :database-url
      )
  "postgres://apps-heimdall-test-655e87:F21ikTH0tE6G2Iw@localhost:5432/apps-heimdall-test-655e87"

  (sort env-keys)
  )

