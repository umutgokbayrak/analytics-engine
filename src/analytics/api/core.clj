(ns analytics.api.core
  (:use [analytics.core]
        [analytics.trackers.event]
        [analytics.trackers.identify] [analytics.trackers.page]
        [analytics.trackers.screen] [analytics.trackers.start]
        [analytics.trackers.stop] [analytics.trackers.contexts.app]
        [analytics.trackers.contexts.campaign]
        [analytics.trackers.contexts.device]
        [analytics.trackers.contexts.location]
        [analytics.trackers.contexts.network]
        [analytics.trackers.contexts.os]
        [analytics.trackers.contexts.screen]
        [analytics.trackers.contexts.browser]
        [analytics.trackers.contexts.page])
  (:require [analytics.utils :as util])
  (:import [java.util Date])
  (:gen-class))


(defn- op-valid? [data]
  ;; do security checks
  (println "Performing security checks")
  ;; site-id, anonymous-id, channel, ip are crucial
  ;; at least context node must be present
  ;; TODO: implement this
  true)

(defn track
  "Tracks data with multimethods"
  [data]
  (if (op-valid? data)
    (let [op-id (track-op data)
          contexts (filter
                     (fn [[k v]]
                       (map? v))
                     (:context data))]
      (if (> op-id 0)
        (doseq [context contexts]
          (track-context context op-id data))))
    (println "Not a valid data")))
