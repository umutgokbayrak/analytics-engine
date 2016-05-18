(ns analytics.services.contexts.campaign
  (:require [analytics.core :refer :all]
            [analytics.utils :as util])
  (:gen-class))


(defn add-campaign-context!
  [op-id op-type site-id user-id session-id
   page event campaign-name source medium
   term content]
    (db-create-campaign-context!
      {:op_id op-id
       :op_type op-type
       :site_id site-id
       :user_id user-id
       :session_id session-id
       :page page
       :event event
       :campaign_name campaign-name
       :campaign_source source
       :campaign_medium medium
       :campaign_term term
       :campaign_content content
       :created_at (java.util.Date.)}))
