(ns eu.teod.heimdall.util)

;; Java wrappers

(defn new-uuid []
  (java.util.UUID/randomUUID))

(defn str->uuid [s]
  (try
    (java.util.UUID/fromString s)
    (catch IllegalArgumentException e
      nil)))

(comment
  (str->uuid (str (new-uuid)))
  ;; => #uuid "6d6b2d51-1879-4a88-94af-4afeb0eca9eb"

  (str->uuid "crap")
  ;; => nil
  )
