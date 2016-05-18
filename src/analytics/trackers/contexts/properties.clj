(ns analytics.trackers.contexts.properties
  (:use [analytics.services.properties])
  (:require [analytics.core :refer :all]
            [analytics.env :as env])
  (:gen-class))

(defn track-properties [op-id data]
  (if (env/enabled? :properties.saved)
    (let [props (:properties data)
          names (keys props)]
      (doseq [n names]
        (add-property-context!
          op-id
          (:type data)
          (:site-id data)
          (:user-id data)
          (:session-id data)
          (:page data)
          (:event data)
          (name n)
          (get props n))))))
