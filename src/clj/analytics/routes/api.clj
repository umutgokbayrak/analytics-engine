(ns analytics.routes.api
  (:require [compojure.core :refer :all]
            [clojure.data.json :as json]
            [analytics.api.core :refer :all]
            [analytics.utils :as util]))

(defn home []
  "Nothing here")

(defn api [json-data req]
  (if (some? json-data)
    (let [ip  (or (get-in req [:headers "x-forwarded-for"]) (:remote-addr req))
          data-clj (json/read-str json-data :key-fn keyword)
          loc_data (util/ip2geo ip)
          data (assoc-in (assoc-in data-clj [:context :location] loc_data) [:context :ip] ip)]
      ;; If you need enrichments, this is the place to go
      (println data)
      (track data)))

  ;; TODO: return an empty image (in memory)
  "ok")

(defroutes api-routes
  (GET "/" [] (home))
  (GET "/api" [data :as req] (api data req)))
