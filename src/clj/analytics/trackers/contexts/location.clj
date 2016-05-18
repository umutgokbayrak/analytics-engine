(ns analytics.trackers.contexts.location
  (:use [analytics.services.contexts.location])
  (:require [analytics.core :refer :all]
            [analytics.utils :as util]
            [analytics.env :as env])
  (:gen-class))


(defmethod track-context :location [p-context op-id data]
  (if (env/enabled? :location.context.saved)
    (let [context (second p-context)
          loc_data (util/ip2geo (:ip (:context data)))]

      ;; TODO: loc_data burada degil, api icerisinde ve istek geldigi anda alinmali

      (add-location-context!
        op-id
        (:type data)
        (:site-id data)
        (or (:user-id data) (:anonymous-id data))
        (:session-id data)
        (:page data)
        (:event data)
        (or (:latitude context) (:latitude loc_data) "")
        (or (:longitude context) (:longitude loc_data) "")
        (or (:country_code context) (:country-code loc_data) "")
        (or (:city context) (:city loc_data) "")
        (or (:postal_code context) (:postal-code loc_data) "")
        (or (:organization context) (:org loc_data) "")))))
