(ns eu.teod.heimdall
  (:require [ring.adapter.jetty])
  (:gen-class))

(comment
  (remove-ns (-> *ns* str symbol))
  )
;; Note:
;;
;;    ring.adapter.jetty/run-jetty
;;
;; Starts a server that can then be started and stopped

(defn handler [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "Hello World22"})

(defonce server (atom nil))

(defn stop! []
  (when @server
    (.stop @server)
    (reset! server nil)))

(defn restart!
  ([]
   (start! {}))
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
  (println "Hello, World!"))
