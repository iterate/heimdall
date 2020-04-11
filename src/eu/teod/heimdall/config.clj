(ns eu.teod.heimdall.config
  (:require [eu.teod.heimdall :as heimdall]
            [integrant.core :as ig]
            [clojure.java.io :as io]
            [environ.core :refer [env]]
            [clojure.set :as set]))

(def ^:private conf-path
  "Shared conf"
  "eu/teod/heimdall/config.edn")

(def ^:private dev-override-conf-path
  "Dev-only conf. Overrides shared."
  "eu/teod/heimdall/config.dev.edn")

(def ^:private prod-override-conf-path
  "Prod-only conf. Overrides shared."
  "eu/teod/heimdall/config.prod.edn")

(def ^:private required-crux-conf-env-keys
  #{:crux.jdbc/dbname :crux.jdbc/host :crux.jdbc/password :crux.jdbc/port :crux.jdbc/user})

(def ^:private required-pg-env-keys
  #{:db-database :db-host :db-password :db-port :db-username})

(defn ^:private env->crux-conf
  "Create a Crux-compatible conf from Iterapp env vars"
  [raw-env]
  (let [crux-conf (-> raw-env
                      (select-keys required-pg-env-keys)
                      (set/rename-keys {:db-database :crux.jdbc/dbname
                                        :db-host     :crux.jdbc/host
                                        :db-password :crux.jdbc/password
                                        :db-port     :crux.jdbc/port
                                        :db-username :crux.jdbc/user}))]
    crux-conf))

(defn ^:private validate-crux-env-conf!
  "Crash unless we got a valid conf"
  [conf]
  (assert (set/subset? (set (keys conf))
                       required-crux-conf-env-keys))
  conf)

(defn ^:private override-env-vars
  "Selects the env vars we need and inserts them into the right place in the configuration"
  ([conf]
   (override-env-vars conf env))
  ([conf env]
   (let [server-env-conf (if (contains? env :port)
                           {:port (Long/parseLong (:port env))})
         db-env-conf (env->crux-conf env)]
     (validate-crux-env-conf! db-env-conf)
     (-> conf
         (update ::heimdall/server (fn [conf]
                                     (merge conf server-env-conf)))
         (update ::heimdall/db (fn [conf]
                                 (merge conf db-env-conf)))))))

(comment
  (prod-conf)

  (dev-conf)

  )

(defn dev-conf []
  (prn ::dev-conf)
  (-> {}
      (merge (ig/read-string (slurp (io/resource conf-path))))
      (merge (ig/read-string (slurp (io/resource dev-override-conf-path))))))

(defn prod-conf []
  (prn ::prod-conf)
  (-> {}
      (merge (ig/read-string (slurp (io/resource conf-path))))
      (merge (ig/read-string (slurp (io/resource prod-override-conf-path))))
      override-env-vars))

(comment
  (prod-conf)

  (ig/read-string (slurp (io/resource conf-path)))
  (ig/read-string (slurp (io/resource prod-override-conf-path)))
  )

(comment
  (env->crux-conf env)
  (-> env
      env->crux-conf
      validate-crux-conf!)

  (-> {:db-username "apps-heimdall-test-655e87",
       :db-port "5432",
       :db-host "localhost",
       :db-password "F21ikTH0tE6G2Iw",
       :db-database "apps-heimdall-test-655e87"}
      env->crux-conf
      validate-crux-conf!
      )
  )
