(ns analytics.routes.api
  (:require [compojure.core :refer :all]
            [clojure.data.json :as json]
            [analytics.api.core :refer :all]
            [analytics.services.sites :as sites]
            [analytics.services.ops :as ops]
            [analytics.utils :as util]))

(defn api [json-data req]
  (if (some? json-data)
    (try
      (let [ip  (or (get-in req [:headers "x-forwarded-for"]) (:remote-addr req))
            data-clj (json/read-str json-data :key-fn keyword)
            site-id (:site-id data-clj)
            user-id (or (:user-id data-clj) (:anonymous-id data-clj))
            session-id (:session-id data-clj)
            hash-code (:hash-code data-clj)]
        (if (and
              (sites/site-exists? site-id) ;; site must exist
              (false? (ops/hash-exists? user-id session-id hash-code))) ;; request must be unique
          (let [loc_data (util/ip2geo ip)
                data (assoc-in (assoc-in data-clj [:context :location] loc_data) [:context :ip] ip)]
            ;; If you need enrichments, this is the place to go
            (println data)
            (track data))))
      (catch Exception e (.printStackTrace e))))

  ;; TODO: return an empty image (in memory)
  "ok")

(defroutes api-routes
  (GET "/api" [data :as req] (api data req)))
