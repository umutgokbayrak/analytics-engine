(ns analytics.services.sites
  (:require [analytics.core :refer :all]
            [analytics.utils :as util])
  (:import  [java.util Date])
  (:gen-class))

(defn num-of-sites []
  (db-count-sites))


(defn add-site!
  [p-site-id site-name description domain]
  (let [site-id (or p-site-id (util/uuid))
        date (Date.)]
    (db-create-site!
      {:site_id site-id
       :site_name site-name
       :site_description description
       :created_at date})
    (db-create-site-domain!
      {:site_id site-id
       :domain_name domain
       :created_at date})))


(defn delete-site-by-id!
  [site-id]
  ;; site-domain will automatically deleted because of the cascade
  (db-delete-site!
    {:site_id site-id}))

