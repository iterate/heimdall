(ns eu.teod.heimdall.handler
  (:require [integrant.core :as ig]
            [clojure.pprint :refer [pprint]]
            [environ.core :refer [env]]))

(defn handler [request]
  (prn ::handler)
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body
   #_
   (str
    "<pre>"
    (with-out-str (pprint env))
    "</pre>")
   "Hello, world!"})

(defmethod ig/init-key :eu.teod.heimdall/handler [_ opts]
  (prn ::init)
  #'handler)
