(ns analytics.config.metrics
  (:gen-class))

(def metrics-map
  {:session
   [[:site-id]
    [:site-id :channel]
    [:site-id :app-name]
    [:site-id :app-version]
    [:site-id :locale]
    [:site-id :city]
    [:site-id :country]
    [:site-id :carrier]
    [:site-id :bluetooth]
    [:site-id :wifi]
    [:site-id :cellular]
    [:site-id :os-name]
    [:site-id :os-name :os-version]
    [:site-id :referrer-domain]
    [:site-id :referrer-type]
    [:site-id :referrer-type :referrer-domain]
    [:site-id :campaign-name]
    [:site-id :campaign-source]
    [:site-id :campaign-medium]
    [:site-id :campaign-term]
    [:site-id :campaign-content]
    [:site-id :campaign-source :campaign-medium]
    [:site-id :campaign-source :campaign-medium :campaign-name]
    [:site-id :manufacturer]
    [:site-id :model]
    [:site-id :screen]
    [:site-id :timezone]
    [:site-id :properties]
    [:site-id :browser-name]
    [:site-id :browser-name :browser-version]
    [:site-id :browser-name :browser-major]]
   :event
   [[:site-id :event]
    [:site-id :event :channel]
    [:site-id :event :page]
    [:site-id :event :app-name]
    [:site-id :event :campaign-name]
    [:site-id :event :properties]
    [:site-id :event :browser-name]
    [:site-id :event :browser-name :browser-version]
    [:site-id :event :browser-name :browser-major]]
   :page
   [[:site-id :page]
    [:site-id :page :channel]
    [:site-id :page :app-name]
    [:site-id :page :campaign-name]
    [:site-id :page :properties]]
   :screen
   [[:site-id :page]
    [:site-id :page :channel]
    [:site-id :page :app-name]
    [:site-id :page :campaign-name]
    [:site-id :page :properties]]
   :start []
   :stop
   [
     ;; duration per channel

     ;; duration per site

     ;; duration per app

     ;; duration per user

     ;; duration per session

     ;; duration per campaign

     ;; duration per device

     ;; duration per locale

     ;; duration per location

     ;; duration per network

     ;; duration per os

     ;; duration per referrer

     ;; duration per screen

     ;; duration per timezone

     ;; duration per property
     ]
   :user
   [[:site-id]
    [:site-id :channel]
    [:site-id :app-name]
    [:site-id :app-version]
    [:site-id :locale]
    [:site-id :city]
    [:site-id :country]
    [:site-id :carrier]
    [:site-id :bluetooth]
    [:site-id :wifi]
    [:site-id :cellular]
    [:site-id :os-name]
    [:site-id :os-name :os-version]
    [:site-id :referrer-domain]
    [:site-id :referrer-type]
    [:site-id :referrer-type :referrer-domain]
    [:site-id :campaign-name]
    [:site-id :campaign-source]
    [:site-id :campaign-medium]
    [:site-id :campaign-term]
    [:site-id :campaign-content]
    [:site-id :campaign-source :campaign-medium]
    [:site-id :campaign-source :campaign-medium :campaign-name]
    [:site-id :manufacturer]
    [:site-id :model]
    [:site-id :screen]
    [:site-id :timezone]
    [:site-id :properties]]
   })
