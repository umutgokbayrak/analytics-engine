(ns analytics.trackers.contexts.os
  (:use [analytics.services.contexts.os])
  (:require [analytics.core :refer :all]
            [analytics.env :as env])
  (:gen-class))


(defmethod track-context :os [p-context op-id data]
  (if (env/enabled? :os.context.saved)
    (let [context (second p-context)]
      (add-os-context!
        op-id
        (:type data)
        (:site-id data)
        (or (:user-id data) (:anonymous-id data))
        (:session-id data)
        (:page data)
        (:event data)
        (:name context)
        (:version context)))))
