(ns analytics.services.utils.metrics
  (require [analytics.utils :as util])
  (:gen-class))

(defn get-value [node data]
  (if (.startsWith (name node) "prop--")
    (get (:properties data)
         (keyword (subs (name node) 6)))
    (case node
      :site-id
      (:site-id data)
      :timezone
      (:timezone (:context data))
      :screen
      (str
        (:width (:screen (:context data)))
        "x"
        (:height (:screen (:context data))))
      :referrer-domain
      (:domain (:referrer (:context (:page data))))
      :referrer-type
      (:type (:referrer (:context (:page data))))
      :os-name
      (:name (:os (:context data)))
      :os-version
      (:version (:os (:context data)))
      :carrier
      (:carrier (:network (:context data)))
      :bluetooth
      (:bluetooth (:network (:context data)))
      :wifi
      (:wifi (:network (:context data)))
      :cellular
      (:cellular (:network (:context data)))
      :city
      (:city (:location (:context data)))
      :country
      (:country (:location (:context data)))
      :locale
      (:locale (:context data))
      :manufacturer
      (:manufacturer (:device (:context data)))
      :model
      (:model (:device (:context data)))
      :channel
      (:channel data)
      :campaign-name
      (:name (:campaign (:context data)))
      :campaign-source
      (:source (:campaign (:context data)))
      :campaign-medium
      (:medium (:campaign (:context data)))
      :campaign-term
      (:term (:campaign (:context data)))
      :campaign-content
      (:content (:campaign (:context data)))
      :user-id
      (:user-id data)
      :event
      (:event data)
      :page
      (:page data)
      :app-name
      (:name (:app (:context data)))
      :app-version
      (:version (:app (:context data)))
      :browser-name
      (:name (:browser (:context data)))
      :browser-version
      (:version (:browser (:context data)))
      :browser-major
      (:major (:browser (:context data)))
      (throw (Exception. (str "get-value with: " node))))))


(defn get-map [context data dimensions]
  (let [incr-map {:context context}]
    (reduce
      (fn [incr-map dimension]
        (try
          (let [dim-value (get-value (second dimension) data)
                dim-name (name (second dimension))
                index (+ (first dimension) 1)]
            (assoc
              (assoc incr-map
                (keyword (str "dim-name" index))
                dim-name)
              (keyword (str "dim-value" index))
              dim-value))
          (catch Exception e (println (.getMessage e))))
        )
      {:context context}
      (map-indexed (fn [idx itm] [idx itm]) dimensions))))
