(ns eu.teod.heimdall.handler
  (:require [integrant.core :as ig]
            [clojure.pprint :refer [pprint]]
            [environ.core :refer [env]]
            [eu.teod.heimdall.util :as util]
            [clojure.ed|]))

(def admin-token
  #uuid "612e9390-540b-4e3c-8314-c7375bb33080")

(defonce last-req (atom nil))

(defn authorized? [req]
  (let [token-str (get-in req [:headers "x-admin-token"])]
    (and (some? token-str)
         (= admin-token (util/str->uuid token-str)))))

(comment
  (authorized? @last-req)

  (authorized? (assoc-in @last-req [:headers "x-admin-token"] "123"))
  )

(defn admin-env [req]
  (if (authorized? req)
    {:status 200
     :headers {"Content-Type" "application/edn"}
     :body (pr-str {:env env})}
    {:status 403
     :headers {"Content-Type" "text/html"}
     :body "No."}))

(comment
  (admin-env @last-req)
  (:headers @last-req)
  )


(defn handler [req]
  (reset! last-req req)
  (cond
    (= (:uri req) "/admin/env")
    #__ (do (prn :route ::admin-env)
            (#'admin-env req))
    :else
    #__ {:status 200
         :headers {"Content-Type" "text/html"}
         :body "Hello, world!!!"}))

(comment
  (handler @last-req)
  )

(defmethod ig/init-key :eu.teod.heimdall/handler [_ opts]
  (prn ::init)
  #'handler)
