(ns analytics.services.contexts.location
  (:require [analytics.core :refer :all]
            [analytics.utils :as util])
  (:gen-class))


(defn add-location-context!
  [op-id op-type site-id user-id session-id
   page event latitude longitude country-code
   city postal-code organization]
    (db-create-location-context!
      {:op_id op-id
       :op_type op-type
       :site_id site-id
       :user_id user-id
       :session_id session-id
       :page page
       :event event
       :loc_latitude latitude
       :loc_longitude longitude
       :loc_country_code country-code
       :loc_city city
       :loc_postal_code postal-code
       :loc_organization organization
       :created_at (java.util.Date.)}))
