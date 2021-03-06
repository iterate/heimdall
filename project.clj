(defproject heimdall "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [duct/core "0.8.0"]
                 [duct/module.logging "0.5.0"]
                 [duct/hikaricp-component "0.1.2"]
                 [duct/module.sql "0.6.0"]
                 [duct/server.http.jetty "0.2.1"]
                 [org.postgresql/postgresql "42.2.14"]
                 [seancorfield/next.jdbc "1.1.569"]]
  :plugins [[duct/lein-duct "0.12.1"]]
  :main ^:skip-aot heimdall.main
  :resource-paths ["resources" "target/resources"]
  :prep-tasks     ["javac" "compile" ["run" ":duct/compiler"]]
  :middleware     [lein-duct.plugin/middleware]
  :profiles
  {:dev  [:project/dev :profiles/dev]
   :repl {:prep-tasks   ^:replace ["javac" "compile"]
          :repl-options {:init-ns user}}
   :uberjar {:aot :all}
   :profiles/dev {}
   :project/dev  {:source-paths   ["dev/src"]
                  :resource-paths ["dev/resources"]
                  :dependencies   [[integrant/repl "0.3.1"]
                                   [eftest "0.5.9"]
                                   [hawk "0.2.11"]]}})
