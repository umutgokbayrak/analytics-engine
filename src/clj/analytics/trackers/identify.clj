(ns analytics.trackers.identify
  (:require [analytics.core :refer :all]
            [analytics.env :as env]
            [analytics.services.users :as users]
            [analytics.services.ops :as ops]
            [analytics.services.user-traits :as traits]
            [analytics.utils :as util])
  (:gen-class))


(defmethod track-op "identify" [data]
  ;; Identifying user in users
  (users/identify-user!
    (:anonymous-id data)
    (:user-id data))

  ;; Now saving the user traits
  (let [traits-map (:traits data)]
    (traits/save-user-traits!
      (:site-id data)
      (:user-id data)
      traits-map))

  ;; if session-id exists save it as an op
  (if (env/enabled? :identify.op.saved)
    (if (:session-id data)
      (let [op-id (ops/add-op!
                    "identify"
                    (:site-id data)
                    (or (:user-id data) (:anonymous-id data))
                    (:session-id data)
                    (or (:hash-code data) (util/uuid))
                    (:channel data)
                    (:page data)
                    nil)]
        op-id)
      -1)
    -1))
