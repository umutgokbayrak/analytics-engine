(ns analytics.trackers.contexts.network
  (:use [analytics.services.contexts.network])
  (:require [analytics.core :refer :all]
            [analytics.env :as env])
  (:gen-class))


(defmethod track-context :network [p-context op-id data]
  (if (env/enabled? :network.context.saved)
    (let [context (second p-context)]
      (add-network-context!
        op-id
        (:type data)
        (:site-id data)
        (or (:user-id data) (:anonymous-id data))
        (:session-id data)
        (:page data)
        (:event data)
        (:carrier context)
        (:isp context)
        (:bluetooth context)
        (:cellular context)
        (:wifi context)))))
