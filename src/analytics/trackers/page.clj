(ns analytics.trackers.page
  (:use [analytics.services.ops])
  (:require [analytics.core :refer :all]
            [analytics.env :as env]
            [analytics.trackers.core :as tc]
            [analytics.trackers.contexts.properties :as tp]
            [analytics.utils :as util])
  (:gen-class))


(defmethod track-op "page" [data]
  (tc/user-session-prep data)

  ;; Track metrics
  (tc/track-metrics :page data)

  ;; Track Page
  (if (env/enabled? :page.op.saved)
    (let [op-id
          (add-op!
            "page"
            (:site-id data)
            (or (:user-id data) (:anonymous-id data))
            (:session-id data)
            (or (:hash-code data) (util/uuid))
            (:channel data)
            (:page data)
            nil)]
      (tp/track-properties op-id data)
      op-id)
    -1))
