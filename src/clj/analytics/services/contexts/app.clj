(ns analytics.services.contexts.app
  (:require [analytics.core :refer :all]
            [analytics.utils :as util])
  (:gen-class))


(defn add-app-context!
  [op-id op-type site-id user-id session-id
   page event app-name app-version]
    (db-create-app-context!
      {:op_id op-id
       :op_type op-type
       :site_id site-id
       :user_id user-id
       :session_id session-id
       :page page
       :event event
       :app_name app-name
       :app_version app-version
       :created_at (java.util.Date.)}))

