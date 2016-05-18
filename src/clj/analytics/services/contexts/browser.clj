(ns analytics.services.contexts.browser
  (:require [analytics.core :refer :all]
            [analytics.utils :as util])
  (:gen-class))

(defn add-browser-context!
  [op-id op-type site-id user-id session-id
   page event browser-name browser-version
   browser-major]
    (db-create-browser-context!
      {:op_id op-id
       :op_type op-type
       :site_id site-id
       :user_id user-id
       :session_id session-id
       :page page
       :event event
       :browser_name browser-name
       :browser_version browser-version
       :browser_major browser-major
       :created_at (java.util.Date.)}))
