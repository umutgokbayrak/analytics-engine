(ns analytics.services.contexts.network
  (:require [analytics.core :refer :all]
            [analytics.utils :as util])
  (:gen-class))

(defn add-network-context!
  [op-id op-type site-id user-id session-id
   page event carrier isp bluetooth cellular wifi]
    (db-create-network-context!
      {:op_id op-id
       :op_type op-type
       :site_id site-id
       :user_id user-id
       :session_id session-id
       :page page
       :event event
       :network_carrier carrier
       :network_isp (if (true? isp) 1 0)
       :network_bluetooth (if (true? bluetooth) 1 0)
       :network_cellular (if (true? cellular) 1 0)
       :network_wifi (if (true? wifi) 1 0)
       :created_at (java.util.Date.)}))
