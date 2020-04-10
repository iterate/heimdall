(ns temp.2020-04-10
  (:require [eu.teod.heimdall :as heimdall]
            [crux.api :as crux]))

(comment
  (require '[integrant.repl.state :refer [system]])

  (keys system)
  (type (system ::heimdall/db))

  (require '[crux.api :as crux])

  ;; what ids are there?

  (defn db []
    (-> system ::heimdall/db crux/db))

  (crux/q (db)
          {:find '[id]
           :where [['_ :crux.db/id 'id]]})
  ;; => #{[:dbpedia.resource/Pablo-Picasso]}

  ;; works

  )
