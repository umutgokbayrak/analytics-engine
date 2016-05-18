(ns analytics.services.user-traits
  (:require [analytics.core :refer :all]
            [analytics.utils :as util])
  (:import  [java.util Date])
  (:gen-class))

(defn- create-user-traits!
  [site-id user-id trait-map]
  (db-create-user-traits!
    {:site_id site-id
     :user_id user-id
     :address (:address trait-map)
     :city (:city trait-map)
     :country_code (:country_code trait-map)
     :postal_code (:postal_code trait-map)
     :state (:state trait-map)
     :age (:age trait-map)
     :avatar_url (:avatar_url trait-map)
     :birthday (:birthday trait-map)
     :description (:description trait-map)
     :email (:email trait-map)
     :full_name (:full_name trait-map)
     :gender (:gender trait-map)
     :phone (:phone trait-map)
     :title (:title trait-map)
     :username (:username trait-map)
     :created_at (Date.)}))


(defn- update-user-traits!
  [site-id user-id orig trait-map]
  (db-update-user-traits!
    {:site_id site-id
     :user_id user-id
     :address (or (:address trait-map) (:address orig))
     :city (or (:city trait-map) (:city orig))
     :country_code (or (:country_code trait-map) (:country_code orig))
     :postal_code (or (:postal_code trait-map) (:postal_code orig))
     :state (or (:state trait-map) (:state orig))
     :age (or (:age trait-map) (:age orig))
     :avatar_url (or (:avatar_url trait-map) (:avatar_url orig))
     :birthday (or (:birthday trait-map) (:birthday orig))
     :description (or (:description trait-map) (:description orig))
     :email (or (:email trait-map) (:email orig))
     :full_name (or (:full_name trait-map) (:full_name orig))
     :gender (or (:gender trait-map) (:gender orig))
     :phone (or (:phone trait-map) (:phone orig))
     :title (or (:title trait-map) (:title orig))
     :username (or (:username trait-map) (:username orig))
     :updated_at (Date.)}))


(defn- save-custom-traits!
  [site-id user-id custom-traits]
  (let [names (map #(name %) (keys custom-traits))]
    (if (> (count names) 0)
      (doseq [n names]
        (let [custom_trait (db-get-custom-user-trait
                    {:site_id site-id
                     :user_id user-id
                     :trait_name n})]
          (if (nil? custom_trait)
            (db-create-custom-user-trait!
              {:site_id site-id
               :user_id user-id
               :trait_name n
               :trait_value (str (get custom-traits (keyword n)))
               :created_at (Date.)})
            (db-update-custom-user-trait!
              {:site_id site-id
               :user_id user-id
               :trait_name n
               :trait_value (str (get custom-traits (keyword n)))
               :updated_at (Date.)})))))))


(defn save-user-traits!
  [site-id user-id trait-map]
  (let [orig-traits (db-get-user-traits {:site_id site-id :user_id user-id})
        custom-traits
        (dissoc trait-map
                :site_id :user_id :address :city
                :country_code :postal_code :state
                :age :avatar_url :birthday :description
                :email :full_name :gender :phone
                :title :username :updated_at :created_at)]
    (if (nil? orig-traits)
      (create-user-traits!
        site-id user-id
        trait-map)
      (update-user-traits!
        site-id user-id
        orig-traits
        trait-map))
    (save-custom-traits! site-id user-id custom-traits)))

;; (save-user-traits!
;;   "914b36e4-a10d-4e13-bf35-df888bfcb1fa"
;;   "53a65860-c9e9-45e4-ab51-397f19ea784c"
;;   {:address "Hebelek2 Address"
;;    :login_count 222
;;    :duration 5432
;;    :segments "Abidik2"})
