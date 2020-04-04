(ns eu.teod.heimdall.main
  (:require [integrant.core :as ig]
            [clojure.java.io :as io]
            [clojure.pprint :refer [pprint]]))

(defn read-env-var-opts []
  (cond-> {}
    (some? (System/getenv "PORT")) (assoc :port (-> (System/getenv "PORT") Long/parseLong))
    ))

(defn read-integrant-opts []
  (ig/read-string (slurp (io/resource "eu/teod/heimdall/config.edn"))))

(defn -main [& args]
  ;; Load Integrant handlers
  (require '[eu.teod.heimdall.server]
           '[eu.teod.heimdall.handler])

  ;; Init system
  (let [config (merge-with merge
                           (read-integrant-opts)
                           (read-env-var-opts))]
    (pprint config)
    (ig/init config)
    )

  ;; TODO handle shutdown?
  )
