(ns analytics.services.contexts.device
  (:require [analytics.core :refer :all]
            [analytics.utils :as util])
  (:gen-class))


(defn add-device-context!
  [op-id op-type site-id user-id session-id
   page event device-id manufacturer model
   device-name]
    (db-create-device-context!
      {:op_id op-id
       :op_type op-type
       :site_id site-id
       :user_id user-id
       :session_id session-id
       :page page
       :event event
       :device_id device-id
       :device_manufacturer manufacturer
       :device_model model
       :device_name device-name
       :created_at (java.util.Date.)}))
