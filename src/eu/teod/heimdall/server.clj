(ns eu.teod.heimdall.server
  (:require [integrant.core :as ig]
            [ring.adapter.jetty]))

;; Define integrant methods for starting and stopping the jetty adapter

;; Interesting inputs to the jetty server:
;;
;;   :port :: which port to bind
;;   :handler :: function to call on requests

(defmethod ig/init-key :eu.teod.heimdall/server
  [_ {:keys [handler port]}]
  (prn ::init 'port port)
  (let [conf {:port port
              :join? false}]
    (ring.adapter.jetty/run-jetty handler conf)))

(defmethod ig/halt-key! :eu.teod.heimdall/server
  [_ server]
  (prn ::stop)
  (.stop server))
