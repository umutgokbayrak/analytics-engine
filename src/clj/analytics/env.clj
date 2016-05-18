(ns analytics.env
  (:use [clojure.java.io])
  (:gen-class))


(def env-var (or (System/getenv "ENV") "dev"))
(def db-map (load-file (.getFile (resource "analytics/config/db.edn"))))
(def metrics-map (load-file (.getFile (resource "analytics/config/metrics.edn"))))
(def analytics-map (load-file (.getFile (resource "analytics/config/analytics.edn"))))

(defn db [k]
  (get (get db-map (keyword env-var)) k))

(defn metrics [k]
  (get metrics-map k))

(defn analytics [k]
  (get analytics-map k))


(defn enabled? [k]
  (or (get analytics-map k) false))

