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

;; What keys do we get?
(comment
  (def env-keys '(:awt-toolkit :clojure-libfile :clojure-version :database-url :db-database :db-host :db-password :db-port :db-username :file-encoding :file-separator :heimdall-port :heimdall-port-5000-tcp :heimdall-port-5000-tcp-addr :heimdall-port-5000-tcp-port :heimdall-port-5000-tcp-proto :heimdall-service-host :heimdall-service-port :home :hostname :iterapp-build-number :iterapp-deployment-id :iterapp-git-sha :java-awt-graphicsenv :java-awt-printerjob :java-base-url :java-class-path :java-class-version :java-home :java-io-tmpdir :java-library-path :java-runtime-name :java-runtime-version :java-specification-name :java-specification-vendor :java-specification-version :java-url-version :java-vendor :java-vendor-url :java-vendor-url-bug :java-vendor-version :java-version :java-version-date :java-vm-compressedoopsmode :java-vm-info :java-vm-name :java-vm-specification-name :java-vm-specification-vendor :java-vm-specification-version :java-vm-vendor :java-vm-version :jdk-debug :kubernetes-port :kubernetes-port-443-tcp :kubernetes-port-443-tcp-addr :kubernetes-port-443-tcp-port :kubernetes-port-443-tcp-proto :kubernetes-service-host :kubernetes-service-port :kubernetes-service-port-https :lang :line-separator :os-arch :os-name :os-version :path :path-separator :pgdatabase :pghost :pgpassword :pgport :pguser :port :pwd :shlvl :sun-arch-data-model :sun-boot-library-path :sun-cpu-endian :sun-cpu-isalist :sun-io-unicode-encoding :sun-java-command :sun-java-launcher :sun-jnu-encoding :sun-management-compiler :sun-os-patch-level :user-dir :user-home :user-language :user-name :user-timezone))

  env-keys

  (def db-keys #{:db-database
                 :db-host
                 :db-password
                 :db-port
                 :db-username})

  (-> (http/get (str heimdall/deploy-url-test "/admin/env")
                {:headers {:x-admin-token (str eu.teod.heimdall.handler/admin-token)}
                 :insecure? true})
      :body
      clojure.edn/read-string
      :env
      (select-keys db-keys)
      )
  ;; => {:db-username "apps-heimdall-test-655e87",
  ;;     :db-port "5432",
  ;;     :db-host "localhost",
  ;;     :db-password "F21ikTH0tE6G2Iw",
  ;;     :db-database "apps-heimdall-test-655e87"}

  (-> (http/get (str heimdall/deploy-url-test "/admin/env")
                {:headers {:x-admin-token (str eu.teod.heimdall.handler/admin-token)}
                 :insecure? true})
      :body
      clojure.edn/read-string
      :env
      :database-url
      )
  "postgres://apps-heimdall-test-655e87:F21ikTH0tE6G2Iw@localhost:5432/apps-heimdall-test-655e87"

  (sort env-keys)
  )

