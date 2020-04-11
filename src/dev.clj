(ns dev
  (:require
   [integrant.core :as ig]
   [integrant.repl :refer [clear halt go init prep reset]]
   [integrant.repl.state :refer [config system]]

   ;; system
   [eu.teod.heimdall.system]
   [eu.teod.heimdall.config]
   [eu.teod.heimdall :as heimdall]
   ))

(defonce loader (atom #'eu.teod.heimdall.config/dev-conf))

(defn set-dev! []
  (reset! loader #'eu.teod.heimdall.config/dev-conf))

(defn set-prod! []
  (reset! loader #'eu.teod.heimdall.config/prod-conf))

(integrant.repl/set-prep! (fn [] (@loader)))
