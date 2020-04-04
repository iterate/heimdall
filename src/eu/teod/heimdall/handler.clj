(ns eu.teod.heimdall.handler
  (:require [integrant.core :as ig]))

(defn handler [request]
  (prn ::handler)
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "Hello World22"})

(defmethod ig/init-key :eu.teod.heimdall/handler [_ opts]
  (prn ::init)
  #'handler)
