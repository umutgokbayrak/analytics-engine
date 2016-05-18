(ns analytics.services.properties
  (:require [analytics.core :refer :all]
            [analytics.utils :as util])
  (:import  [java.util Date])
  (:gen-class))


(defn add-property-context!
  [op-id op-type site-id user-id session-id
   page event property-name property-value]
    (db-create-property-context!
      {:op_id op-id
       :op_type op-type
       :site_id site-id
       :user_id user-id
       :session_id session-id
       :page page
       :event event
       :property_name property-name
       :property_value property-value
       :created_at (Date.)}))
