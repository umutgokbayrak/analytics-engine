(ns analytics.trackers.core
  (:require [analytics.utils :as util]
            [analytics.env :as env]))


(defn get-user
  "Returns the user id or generates a new one"
  []
  (util/get-data "user-id" true))


(defn get-session
  "Returns the session id or generates a new one"
  []
  (let [session-id (util/get-data "session-id" false)]
    (if (nil? session-id)
      (let [new-session-id (cljs.core/random-uuid)]
        (util/set-data! "session-id" new-session-id false))
      session-id)))


(defn get-anonymous-id
  "Returns the anonymous id or generates a new one"
  []
  (let [anon-id (util/get-data "anonymous-id" true)]
    (if (nil? anon-id)
      (let [new-anon-id (cljs.core/random-uuid)]
        (util/set-data! "anonymous-id" new-anon-id true))
      anon-id)))


(defn get-page-url
  "Get the page pathname + search and hash"
  []
  (str (.-pathname js/location)
       (or (.-search js/location) "")
       (or (.-hash js/location) "")))


(defn- get-page-context
  "Gets the page context"
  []
  (let [context
        {:url (get-page-url)
         :title (.-title js/document)}
        referrer (.-referrer js/document)
        referrer-context
        (if (not= referrer "")
          {:url referrer
           :domain (util/get-hostname-from-url referrer)
           :type ""} ;; TODO: implement this
          {})]
    (assoc context :referrer referrer-context)))


(defn- assoc-devices
  "Assoc the Device context if exists"
  [contexts]
  (let [device (util/get-device)]
    (if (and (> (count device) 0) (exists? (:model device)))
      (assoc contexts :device device)
      contexts)))


(defn- assoc-campaign
  "Assoc the campaign key if a campaign exists"
  [contexts]
  (let [campaign (util/parse-analytics-cookie)]
    (if (> (count campaign) 0)
      (assoc contexts
        :campaign
        {:name (:utmccn campaign)
         :source (:utmcsr campaign)
         :medium (:utmcmd campaign)
         :term (:utmctr campaign)
         :content (:utmcct campaign)})
      contexts)))


(defn get-contexts
  "Gets the context from session/local store or cookie
  and stores it when needed."
  []
  (let [contexts
        (assoc-devices
          (assoc-campaign
            (or (util/str->clj (util/get-data "contexts" false))
                {:app {:name (env/js :app-name)
                       :version (env/js :app-version)}
                 :locale (.-language js/navigator)
                 :os (util/get-os)
                 :browser (util/get-browser)
                 :screen {:height (.-height js/screen)
                          :width (.-width js/screen)}
                 :timezone (- 0 (/ (.getTimezoneOffset (js/Date.)) 60))
                 })))]

    ;; save the data for later use
    (util/set-data! "contexts" (util/clj->json contexts) false)

    ;; assoc the page context and return
    (assoc contexts :page (get-page-context))))


(defn get-site-id
  "Returns the site id from localStore or url param"
  []
  (let [site-id (util/get-data "site-id" true)]
    (if (nil? site-id)
      (let [src (.-src (.getElementById js/document "_ae"))
            pos (.indexOf src ".js?s=")
            site-id (.substr src (+ pos 6))]
        (util/set-data! "site-id" site-id true)
        site-id)
      site-id)))


(defn get-channel
  "Returns the channel"
  []
  (let [channel (if (util/mobile?) "mweb" "web")]
    (util/set-data! "channel" channel false)
    channel))


(defn run []
  (get-site-id)
  (get-contexts)
  (get-session)
  (get-anonymous-id)
  (get-channel)
  (.log js/console "Analytics engine initialized"))
