(ns eu.teod.heimdall
  (:gen-class)
  (:require [ring.adapter.jetty]
            [integrant.core :as ig]
            ))

(comment
  (remove-ns (-> *ns* str symbol))
  )
;; Note:
;;
;;    ring.adapter.jetty/run-jetty
;;
;; Starts a server that can then be started and stopped

(defn handler [request]
  (prn ::handler)
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "Hello World22"})


;; (defmethod ig/init-key ::handler [_ opts]
;;   (prn ::init)
;;   #'handler)

(defonce server (atom nil))

(defn stop! []
  (when @server
    (.stop @server)
    (reset! server nil)))

(defn restart!
  ([]
   (restart! {}))
  ([opts]
   (let [opts (merge {:port 3000
                      :join? false}
                     opts)]
     (stop!)
     (reset! server (ring.adapter.jetty/run-jetty #'handler opts)))))

(comment
  (restart!)
  (stop!)

  )

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (restart! {:port (or (some-> "PORT" System/getenv Long/parseLong)
                       5000)
             :join? true}))
