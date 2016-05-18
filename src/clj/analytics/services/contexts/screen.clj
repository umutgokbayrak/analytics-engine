(ns analytics.services.contexts.screen
  (:require [analytics.core :refer :all]
            [analytics.utils :as util])
  (:gen-class))

(defn add-screen-context!
  [op-id op-type site-id user-id session-id
   page event height width]
    (db-create-screen-context!
      {:op_id op-id
       :op_type op-type
       :site_id site-id
       :user_id user-id
       :session_id session-id
       :page page
       :event event
       :screen_height height
       :screen_width width
       :screen_height_width (str height "x" width)
       :created_at (java.util.Date.)}))
