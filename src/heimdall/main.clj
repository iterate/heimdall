(ns heimdall.main
  (:gen-class)
  (:require [duct.core :as duct]))

(duct/load-hierarchy)

(defn -main [& args]
  (prn ::main-start)
  (let [keys     (or (duct/parse-keys args) [:duct/daemon])
        profiles [:duct.profile/prod]]
    (-> (duct/resource "heimdall/config.edn")
        (duct/read-config)
        (duct/exec-config profiles keys)))
  (prn ::main-done))
