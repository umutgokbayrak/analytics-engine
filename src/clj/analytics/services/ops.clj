(ns analytics.services.ops
  (:require [analytics.core :refer :all]
            [analytics.utils :as util])
  (:import  [java.util Date])
  (:gen-class))


(defn add-op!
  [op-type site-id user-id session-id
   hash-code channel page event]
  (:generated_key
    (db-create-op!
      {:op_type op-type
       :site_id site-id
       :user_id user-id
       :session_id session-id
       :hash_code hash-code
       :channel channel
       :page page
       :event event
       :created_at (Date.)})))
