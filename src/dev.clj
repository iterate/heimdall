(ns dev
  (:require
   [clojure.java.io :as io]

   [integrant.core :as ig]
   [integrant.repl :refer [clear halt go init prep reset]]
   [integrant.repl.state :refer [config system]]

   ;; system
   [eu.teod.heimdall.server]
   [eu.teod.heimdall.handler]
   ))

;; (defn go [])

(defn read-config []
  (ig/read-string (slurp (io/resource "eu/teod/heimdall/config.edn"))))

(integrant.repl/set-prep! #'read-config)

(comment
  (read-config)
  )

(comment
  ;; How does environ work?
  )
