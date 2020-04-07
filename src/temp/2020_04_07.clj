(ns temp.2020-04-07
  (:require [eu.teod.heimdall :as heimdall]
            [clojure.edn]))

(comment
  ;; How does environ work?

  (require '[environ.core])

  environ.core/env

  (clojure.repl/source environ.core/env)

  ;; Should work. Now, over HTTP? Dunno. Let's just write some JSON?

  ;; Hmm. HTTP Client?
  )

(comment
  (require '[clj-http.client :as http])

  (http/head eu.teod.heimdall/deploy-url-test {:insecure? true})

  (http/get eu.teod.heimdall/deploy-url-test {:insecure? true})
  )

(comment
  (require '[clj-http.client :as http])

  (-> (http/get (str heimdall/local-url ,,,))
      :body)
  ;; => "Hello, world!"

  (-> (http/get (str heimdall/local-url "/admin/env")
                {:headers {:x-admin-token (str eu.teod.heimdall.handler/admin-token)}
                 })
      :body
      clojure.edn/read-string
      :env
      keys
      )

  (-> (http/get (str heimdall/deploy-url-test "/admin/env")
                {:headers {:x-admin-token (str eu.teod.heimdall.handler/admin-token)}
                 :insecure? true})
      :body
      clojure.edn/read-string
      :env
      keys
      )

  (str eu.teod.heimdall.handler/admin-token)
  )
