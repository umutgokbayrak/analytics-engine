(ns analytics.utils
  (:require [cljs-uuid-utils.core :as uuid]
            [clojure.string :as s]
            [goog.net.cookies :as cks]
            [goog.userAgent :as gua]
            [analytics.parsers.useragent :as ua]))

(def ua-parser (ua/UAParser.))

(defn clj->json
  "Converts edn to json string"
  [ds]
  (.stringify js/JSON (clj->js ds)))


(defn json->clj
  "Converts json to edn"
  [ds]
  (js->clj ds :keywordize-keys true))


(defn str->clj
  "Converts json as string to edn"
  [ds]
  (if (exists? ds)
    (js->clj (.parse js/JSON ds) :keywordize-keys true)))


(defn set-cookie!
  "Sets the cookie either session or persistent"
  [_type _name _value]
  (case _type
    :session
      (.set goog.net.cookies _name _value -1)
    :persistent
      (let [hostname (.-hostname (.-location js/document))
            one-year (* 365 24 60 60)]
        (.set goog.net.cookies _name _value one-year "/" hostname))))

(defn get-cookie
  "Returns the cookie value by name"
  [_name]
  (.get goog.net.cookies _name))


(defn has-storage?
  "Checks to see if the client supports session or localStores"
  []
  (exists? js/Storage))


(defn get-data
  "Gets the data from sessionStore, localStore or cookie"
  [k persist?]
  (or (if (and has-storage? persist?) (aget js/localStorage k))
      (if (and has-storage? (not persist?)) (aget js/sessionStorage k))
      (get-cookie k)))


(defn set-data!
  "Sets the data to sessionStore, localStore or cookie"
  [k v persist?]
  (or
    (if has-storage?
      (if persist?
        (aset js/localStorage k v)
        (aset js/sessionStorage k v))
      (set-cookie!
        (if persist? :persistent :session)
        k v))))


(defn get-hostname-from-url
  "Gets only the domain name from a specified url"
  [url]
  (let [a (.createElement js/document "a")]
    (aset a "href" url)
    (.-hostname a)))


(defn parse-analytics-cookie
  "Parses the Google Analytics cookie and returns the campaign params as map"
  []
  (try
    (if-let [cookie (get-cookie "__utmz")]
      (let [z (s/split cookie #".")
            y (s/split (nth z 4) #"|")
            keywords (map #(keyword (first (s/split % #"="))) y)
            values (map #(second (s/split % #"=")) y)]
        (zipmap keywords values))
      {})
    (catch js/Error e (.log js/console e))))


(defn get-os []
  "Get OS details"
  (json->clj (.getOS ua-parser)))


(defn get-browser
  "Get browser details"
  []
  (json->clj (.getBrowser ua-parser)))


(defn get-device
  "Get device details for mobile"
  []
  (json->clj (.getDevice ua-parser)))


(defn mobile?
  "Checks if user is on a mobile device or not"
  []
  (.-MOBILE goog.userAgent))
