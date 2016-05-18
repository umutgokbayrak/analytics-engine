(ns analytics.handler
  (:require [compojure.core :refer [defroutes routes]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.content-type :refer [wrap-content-type]]
            [ring.middleware.not-modified :refer [wrap-not-modified]]
            [ring.middleware.file-info :refer [wrap-file-info]]
            [hiccup.middleware :refer [wrap-base-url]]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [analytics.routes.api :refer [api-routes]])
  (:gen-class))

(defn init []
  (println "Analytics is starting"))

(defn destroy []
  (println "Analytics is shutting down"))

(defroutes app-routes
;;  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (-> (routes api-routes app-routes)
      (handler/site)
      (wrap-base-url)
      (wrap-resource "public")
      (wrap-content-type)
      (wrap-not-modified)))
