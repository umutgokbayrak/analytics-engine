(ns analytics.services.users
  (:require [analytics.core :refer :all]
            [analytics.utils :as util])
  (:import  [java.util Date])
  (:gen-class))


(defn get-user
  [site-id user-id]
  (db-get-user
    {:user_id user-id
     :site_id site-id}))


(defn add-user!
  [anonymous-id p-user-id site-id channel]
  (let [user-id (or p-user-id anonymous-id)]
    (db-create-user!
      {:anonymous_id anonymous-id
       :user_id user-id
       :site_id site-id
       :channel channel
       :created_at (Date.)})))

(defn insert-user-if-needed
  [anonymous-id user-id site-id channel]
  (let [user (get-user site-id user-id)]
    (if (nil? user)
      (add-user!
        anonymous-id
        user-id
        site-id
        channel))))


(defn delete-user-by-id!
  [site-id user-id]
  (db-delete-user!
    {:user_id user-id
     :site_id site-id}))


(defn delete-user-by-anon-id!
  [anonymous-id]
  (db-delete-user-by-anon-id!
    {:anonymous_id anonymous-id}))


(defn identify-user! [anonymous-id user-id]
  (db-identify-user!
    {:user_id user-id
     :anonymous_id anonymous-id}))
