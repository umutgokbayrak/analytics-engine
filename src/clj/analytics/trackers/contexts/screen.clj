(ns analytics.trackers.contexts.screen
  (:use [analytics.services.contexts.screen])
  (:require [analytics.core :refer :all]
            [analytics.env :as env])
  (:gen-class))


(defmethod track-context :screen [p-context op-id data]
  (if (env/enabled? :screen.context.saved)
    (let [context (second p-context)]
      (add-screen-context!
        op-id
        (:type data)
        (:site-id data)
        (or (:user-id data) (:anonymous-id data))
        (:session-id data)
        (:page data)
        (:event data)
        (:height context)
        (:width context)))))
