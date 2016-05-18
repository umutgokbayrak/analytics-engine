(ns analytics
  (:require [analytics.trackers.core :as tc]
            [analytics.utils :as util]
            [analytics.env :as env]))


(defn- get-base-data [type_ page event props traits]

  ;; TODO: assoc page hash code for protection against repetitive calls.
  (let [user-id (tc/get-user)
        base {:anonymous-id (tc/get-anonymous-id)
              :type type_
              :site-id (tc/get-site-id)
              :session-id (tc/get-session)
              :channel (tc/get-channel)
              :page (or page (tc/get-page-url))
              :context (tc/get-contexts)
              :properties (or (util/json->clj props) {})
              :traits (or (util/json->clj traits) {})}
        base (if (some? event)
               (assoc base :event event)
               base)]
    (if (some? user-id)
      (assoc base :user-id user-id)
      base)))


(defn send-data [data]
  (let [encoded (js/encodeURIComponent (util/clj->json data))
        url (str (env/js :api-url) "?data=" encoded "&r=" (.random js/Math))
        img-id "__analytics-img"
        img (.getElementById js/document img-id)]
    (if (nil? img)
      (let [img-obj (js/Image.)]
        (aset img-obj "id" img-id)
        (.setAttribute img-obj "style" "display:none;width:1px;height:1px;")
        (aset img-obj "src" url)
        (.appendChild js/document.body img-obj))
      (aset img "src" url))))


(defn ^:export page [_page properties]
  (let [data (get-base-data "page" _page nil properties nil)]

    (send-data data)
    (.log js/console (util/clj->json data))))


(defn ^:export event [_event properties _page]
  (let [data (get-base-data "event" _page _event properties nil)]
    (send-data data)
    (.log js/console (util/clj->json data))))


(defn ^:export identify [user traits]
  ;; store the data to local storage or cookie for later use
  (util/set-data! "user-id" user true)

  (let [data (get-base-data "identify" nil nil nil traits)]
    (send-data data)
    (.log js/console (util/clj->json data))))


(defn ^:export start [properties _page]
  (let [data (get-base-data "start" _page nil properties nil)]
    (send-data data)
    (.log js/console (util/clj->json data))))


(defn ^:export stop [properties _page]
  (let [data (get-base-data "stop" _page nil properties nil)]
    (send-data data)
    (.log js/console (util/clj->json data))))

(tc/run)
