(ns analytics.services.contexts.page
  (:require [analytics.core :refer :all]
            [analytics.utils :as util])
  (:gen-class))


(defn add-page-context!
  [op-id op-type site-id user-id session-id
   page event referrer-type referrer-url
   referrer-domain page-title page-url]
    (db-create-page-context!
      {:op_id op-id
       :op_type op-type
       :site_id site-id
       :user_id user-id
       :session_id session-id
       :page page
       :event event
       :referrer_type referrer-type
       :referrer_url referrer-url
       :referrer_domain referrer-domain
       :page_title page-title
       :page_url page-url
       :created_at (java.util.Date.)}))
