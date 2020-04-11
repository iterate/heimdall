(ns eu.teod.heimdall.main
  (:require [integrant.core :as ig]
            [clojure.java.io :as io]
            [clojure.pprint :refer [pprint]]
            [eu.teod.heimdall.config :as config]))

(defn -main [& args]
  ;; Load Integrant handlers
  (require '[eu.teod.heimdall.system])

  ;; Init system
  (let [config (config/prod-conf)]
    (pprint config)
    ;; (ig/init config)
    )

  ;; TODO handle shutdown?
  )
