(ns heimdall.handler
  (:require [integrant.core :as ig]))

(defn app [req]
  {:status  200
   :headers {"Content-Type" "text/plain"}
   :body    "Hello World"})

(defmethod ig/init-key :heimdall/handler
  [_ {}]
  (fn [req]
    (app req)))
