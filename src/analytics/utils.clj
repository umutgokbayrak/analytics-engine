(ns analytics.utils
  (:use [de.bertschneider.clj-geoip.core])
  (:import [java.util UUID Calendar])
  (:gen-class))


(defn uuid []
  (.toString (UUID/randomUUID)))


(defn diff-in-secs [cal1 cal2]
  (/ (- (.getTimeInMillis cal1) (.getTimeInMillis cal2)) (* 1000.0)))


(defn secs-until-date [compare-date]
  (if (some? compare-date)
    (let [now (java.util.Date.)]
      (let [compare-cal (Calendar/getInstance)
            now-cal (Calendar/getInstance)]
        (.setTime now-cal now)
        (.setTime compare-cal compare-date)
        (diff-in-secs now-cal compare-cal)))
    0))


(defn ip2geo [ip]
  (let [mls (multi-lookup-service)]
    (lookup mls ip)))


(defmacro profile
  [descr & body]
  `(let [start# (.getTime (js/Date.))
         ret# (do ~@body)]
    (print (str ~descr ": " (- (.getTime (js/Date.)) start#) " msecs"))
    ret#))
