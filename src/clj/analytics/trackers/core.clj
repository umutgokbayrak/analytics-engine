(ns analytics.trackers.core
  (:require [analytics.core :refer :all]
            [analytics.services.users :as users]
            [analytics.services.ops :as ops]
            [analytics.services.sessions :as sessions]
            [analytics.services.metrics :as m]
            [analytics.utils :as util]
            [analytics.env :as env])
  (:gen-class))

(defn track-metrics
  "Tracks all sorts of metrics"
  [context data]
  (doseq [calc (env/metrics context)]
    (m/+! (name context) data calc)))

(defn user-session-prep
  "Creates a user or a session if needed"
  [data]
  ;; Creating a user record if needed
  (users/insert-user-if-needed
    (:anonymous-id data)
    (or (:user-id data) (:anonymous-id data))
    (:site-id data)
    (:channel data))

  ;; Creating a session record if needed, pinging elsewise
  (let [session-id (:session-id data)
         session (sessions/get-session session-id)]
    (if (nil? session)
      (do
        (println "Creating new session")
        ;; adding new session
        (sessions/add-session!
          session-id
          (:site-id data)
          (:user-id data)
          (:channel data)
          (:ip (:context data))
          (:locale (:context data)))

        ;; record this op as new session (land)
        (if (env/enabled? :session.op.saved)
          (ops/add-op!
            "session"
            (:site-id data)
            (or (:user-id data) (:anonymous-id data))
            (:session-id data)
            (or (:hash-code data) (util/uuid))
            (:channel data)
            (:page data)
            nil)))
      (do
        (println "Pinging the session")
        (sessions/ping-session!
          session-id))))

  ;; Track metrics
  (track-metrics :user data)
  (track-metrics :session data))
