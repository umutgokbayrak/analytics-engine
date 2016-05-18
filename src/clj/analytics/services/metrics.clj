(ns analytics.services.metrics
  (:require [analytics.core :refer :all]
            [analytics.env :as env]
            [analytics.services.utils.metrics :as m-util]
            [digest :as digest])
  (:import  [java.util Date]
            [java.text SimpleDateFormat])
  (:gen-class))

(defn- str2num
  "Converts a string to md5 hex and then to a biginteger"
  [s]
  (BigInteger. (digest/md5 s) 16))


(defn- date-prefixes
  "Reads date-prefix preferences and returns as an array"
  []
  (let [date (Date.)
        all-time (if (env/enabled? :all.time.prefix.saved)
                   [""]
                   nil)
        yearly (if (env/enabled? :yearly.prefix.saved)
                 (.format (SimpleDateFormat. "yyy") date)
                 nil)
        monthly (if (env/enabled? :monthly.prefix.saved)
                 (.format (SimpleDateFormat. "yyy MM") date)
                 nil)
        daily (if (env/enabled? :daily.prefix.saved)
                 (.format (SimpleDateFormat. "yyy MM dd") date)
                 nil)
        hourly (if (env/enabled? :hourly.prefix.saved)
                 (.format (SimpleDateFormat. "yyy MM dd HH") date)
                 nil)]
    (remove nil? [all-time yearly monthly daily hourly])))

(defn get-metric
  "Retrieves a metric row"
  [{:keys
    [context metric-type date-prefix dim-name1
     dim-name2 dim-name3 dim-name4 dim-value1
     dim-value2 dim-value3 dim-value4]}]

  (db-get-metric
        {:context context
         :metric_type (or metric-type "inc")
         :date_prefix date-prefix
         :dim_name
         (str2num
           (str (or dim-name1 "")
                (or dim-name2 "")
                (or dim-name3 "")
                (or dim-name4 "")))
         :dim_value
         (str2num
           (str (or dim-value1 "")
                (or dim-value2 "")
                (or dim-value3 "")
                (or dim-value4 "")))}))


(defn- increment!
  "Increment a metric value. Creates a new one if needed"
  [{:keys
   [context metric-type dim-name1 dim-name2
    dim-name3 dim-name4 dim-value1 dim-value2
    dim-value3 dim-value4 incr-val]}]
  (let [date-prefixes (date-prefixes)
        p-metric-type (or metric-type "inc")
        p-incr-val (or incr-val 1)]
    (doseq [date-prefix date-prefixes]
      (try
        (let [metric
              (get-metric
                {:context context
                 :metric-type p-metric-type
                 :date-prefix date-prefix
                 :dim-name1 dim-name1
                 :dim-name2 dim-name2
                 :dim-name3 dim-name3
                 :dim-name4 dim-name4
                 :dim-value1 dim-value1
                 :dim-value2 dim-value2
                 :dim-value3 dim-value3
                 :dim-value4 dim-value4})]
          (if (nil? metric)
            (do
              ;; Creating new metric row.
              (db-create-metric!
                {:context context
                 :metric_type p-metric-type
                 :date_prefix date-prefix
                 :dim_name
                 (str2num
                   (str (or dim-name1 "")
                        (or dim-name2 "")
                        (or dim-name3 "")
                        (or dim-name4 "")))
                 :dim_name1 dim-name1
                 :dim_name2 dim-name2
                 :dim_name3 dim-name3
                 :dim_name4 dim-name4
                 :dim_value
                 (str2num
                   (str (or dim-value1 "")
                        (or dim-value2 "")
                        (or dim-value3 "")
                        (or dim-value4 "")))
                 :dim_value1 dim-value1
                 :dim_value2 dim-value2
                 :dim_value3 dim-value3
                 :dim_value4 dim-value4
                 :metric_value p-incr-val
                 :updated_at (Date.)}))
            (do
              ;; Incrementing current metric row
              (db-increment-metric!
                {:metric_id (:id metric)
                 :incr_value p-incr-val}))))
        (catch Exception e (println (.getMessage e) "val2:" dim-value2))))))


(defn +!
  "Increments the metric automating the date prefixes"
  [context data dimensions-map]

  (if (true? (some #(= :properties %) dimensions-map))
    (let [props (:properties data)
          prop-keys (keys props)]
      (doseq [k prop-keys]
        (let [properties-index (.indexOf dimensions-map :properties)
              new-keyword (keyword (str "prop--" (name k)))
              new-dimensions-map (assoc dimensions-map properties-index new-keyword)]
          (increment! (m-util/get-map context data new-dimensions-map)))))
    (increment! (m-util/get-map context data dimensions-map))))

