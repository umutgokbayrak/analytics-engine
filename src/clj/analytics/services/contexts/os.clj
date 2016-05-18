(ns analytics.services.contexts.os
  (:require [analytics.core :refer :all]
            [analytics.utils :as util])
  (:gen-class))


(defn add-os-context!
  [op-id op-type site-id user-id session-id
   page event os-name os-version]
    (db-create-os-context!
      {:op_id op-id
       :op_type op-type
       :site_id site-id
       :user_id user-id
       :session_id session-id
       :page page
       :event event
       :os_name os-name
       :os_version os-version
       :created_at (java.util.Date.)}))
