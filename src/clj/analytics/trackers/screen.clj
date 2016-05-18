(ns analytics.trackers.screen
  (:use [analytics.services.ops])
  (:require [analytics.core :refer :all]
            [analytics.env :as env]
            [analytics.trackers.core :as tc]
            [analytics.trackers.contexts.properties :as tp]
            [analytics.utils :as util])
  (:gen-class))

(defmethod track-op "screen" [data]
  (tc/user-session-prep data)

  ;; Track metrics
  (tc/track-metrics :screen data)

  ;; Track Screen
  (if (env/enabled? :screen.op.saved)
    (let [op-id
          (add-op!
            "screen"
            (:site-id data)
            (or (:user-id data) (:anonymous-id data))
            (:session-id data)
            (or (:hash-code data) (util/uuid))
            (:channel data)
            (or (:page data) (:name data) (:screen data))
            nil)]
      (tp/track-properties op-id data)
      op-id)
    -1))
