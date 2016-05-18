(ns analytics.trackers.start
  (:use [analytics.services.ops])
  (:require [analytics.core :refer :all]
            [analytics.env :as env]
            [analytics.trackers.core :as tc]
            [analytics.trackers.contexts.properties :as tp]
            [analytics.utils :as util])
  (:gen-class))


(defmethod track-op "start" [data]
  (tc/user-session-prep data)

  ;; Track metrics
  (tc/track-metrics :start data)

  ;; Track Event
  (if (env/enabled? :start.op.saved)
    (let [op-id
          (add-op!
            "start"
            (:site-id data)
            (or (:user-id data) (:anonymous-id data))
            (:session-id data)
            (:asset-id data)
            (:channel data)
            (or (:page data) (:name data) (:screen data))
            (:event data))]
      (tp/track-properties op-id data)
      op-id)
    -1))
