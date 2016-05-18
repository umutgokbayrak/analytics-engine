(ns analytics.trackers.stop
  (:use [analytics.services.ops])
  (:require [analytics.core :refer :all]
            [analytics.env :as env]
            [analytics.trackers.core :as tc]
            [analytics.trackers.contexts.properties :as tp]
            [analytics.utils :as util])
  (:gen-class))


(defmethod track-op "stop" [data]
  (tc/user-session-prep data)

  ;; Track Event
  (if (env/enabled? :stop.op.saved)
    (let [op-id
          (add-op!
            "stop"
            (:site-id data)
            (or (:user-id data) (:anonymous-id data))
            (:session-id data)
            (:asset-id data)
            (:channel data)
            (or (:page data) (:name data) (:screen data))
            (:event data))]

      ;; Track Properties
      (tp/track-properties op-id data)

      ;; Track metrics and get duration from from asset-id
      (tc/track-metrics :stop (assoc data :op-id op-id))

      op-id)
    -1))
