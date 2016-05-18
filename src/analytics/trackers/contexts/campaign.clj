(ns analytics.trackers.contexts.campaign
  (:use [analytics.services.contexts.campaign])
  (:require [analytics.core :refer :all]
            [analytics.env :as env])
  (:gen-class))


(defmethod track-context :campaign [p-context op-id data]
  (if (env/enabled? :campaign.context.saved)
    (let [context (second p-context)]
      (add-campaign-context!
        op-id
        (:type data)
        (:site-id data)
        (or (:user-id data) (:anonymous-id data))
        (:session-id data)
        (:page data)
        (:event data)
        (:name context)
        (:source context)
        (:medium context)
        (:term context)
        (:content context)))))
