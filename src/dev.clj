(ns dev
  (:require
   [clojure.java.io :as io]

   [integrant.core :as ig]
   [integrant.repl :refer [clear halt go init prep reset]]
   [integrant.repl.state :refer [config system]]

   ))

;; (defn go [])

(defn read-config []
  (ig/read-string (slurp (io/resource "eu/teod/heimdall/config.edn"))))

(integrant.repl/set-prep! #'read-config)

(comment
  (read-config)
  )
