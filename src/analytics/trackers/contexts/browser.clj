(ns analytics.trackers.contexts.browser
  (:use [analytics.services.contexts.browser])
  (:require [analytics.core :refer :all]
            [analytics.env :as env])
  (:gen-class))


(defmethod track-context :browser [p-context op-id data]
  (if (env/enabled? :browser.context.saved)
    (let [context (second p-context)]
      (add-browser-context!
        op-id
        (:type data)
        (:site-id data)
        (or (:user-id data) (:anonymous-id data))
        (:session-id data)
        (:page data)
        (:event data)
        (:name context)
        (:version context)
        (:major context)))))
