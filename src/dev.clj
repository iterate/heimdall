(ns dev
  (:require
   [integrant.core :as ig]
   [integrant.repl :refer [clear halt go init prep reset]]
   [integrant.repl.state :refer [config system]]

   ;; system
   [eu.teod.heimdall.system]
   [eu.teod.heimdall.config]
   ))

(integrant.repl/set-prep! #'eu.teod.heimdall.config/dev-conf)

(require '[eu.teod.heimdall.system])

(comment
  (read-config)
  )

(comment
  ;; How does environ work?
  )
