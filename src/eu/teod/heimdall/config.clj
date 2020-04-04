(ns eu.teod.heimdall.config
  (:require [eu.teod.heimdall :as heimdall]
            [integrant.core :as ig]
            [clojure.java.io :as io]))

;; Load config from here!
;; Differentiate between dev and prod conf

;; Idea: 1 shared conf, and dev/prod "jacking" into that.

(def ^:private
  conf-path "eu/teod/heimdall/config.edn")

(def ^:private
  dev-override-conf-path "eu/teod/heimdall/config.dev.edn")

(def ^:private
  prod-override-conf-path "eu/teod/heimdall/config.prod.edn")

(defn override-env-vars [conf]
  (cond-> conf
    (some? (System/getenv "PORT"))
    #_x (assoc-in [::heimdall/server :port] (-> (System/getenv "PORT") Long/parseLong))
    ;; TODO assoc in postgreSQL path
    ))

(defn dev-conf []
  (-> {}
      (merge (ig/read-string (slurp (io/resource conf-path))))
      (merge (ig/read-string (slurp (io/resource dev-override-conf-path))))))

(comment
  (dev-conf)
  )

(defn prod-conf []
  (-> {}
      (merge (ig/read-string (slurp (io/resource conf-path))))
      (merge (ig/read-string (slurp (io/resource prod-override-conf-path))))
      override-env-vars))

(comment
  (prod-conf)
  )
