(ns eu.teod.heimdall-test
  (:require [clojure.test :refer :all]
            [eu.teod.heimdall :refer :all]
            ))

(deftest load-namespaces
  (testing "Load the system and try not to crash!"
    (require '[eu.teod.heimdall.handler]
             '[eu.teod.heimdall.server])
    (is :ok)))
