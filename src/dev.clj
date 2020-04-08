(ns dev
  (:require
   [clojure.java.io :as io]

   [integrant.core :as ig]
   [integrant.repl :refer [clear halt go init prep reset]]
   [integrant.repl.state :refer [config system]]

   ;; system
   [eu.teod.heimdall.server]
   [eu.teod.heimdall.handler]
   [eu.teod.heimdall.config]
   ))

;; (defn go [])

;; TODO
;; Adopt to "dev usage"
;; Does currently not use dev setup at all!

(defn read-config []
  (ig/read-string (slurp (io/resource "eu/teod/heimdall/config.edn"))))

(integrant.repl/set-prep! #'eu.teod.heimdall.config/dev-conf)

(comment
  (read-config)
  )

(comment
  ;; How does environ work?
  )
