(ns analytics.core
  (:require [migratus.core :as migratus]
            [clojure.java.jdbc :as jdbc]
            [conman.core :as conman]
            [analytics.env :as env]
;;             [analytics.handler :as handler]
            [ring.server.standalone :refer :all])
  (:import [java.sql BatchUpdateException PreparedStatement])
  (:gen-class))

;; used by conman for hugsql connections
(def db (atom nil))

(if (nil? @db)
  (let [db-host (env/db :database-host)
        db-port (env/db :database-port)
        db-name (env/db :database-name)
        db-user (env/db :database-user)
        db-pass (env/db :database-pass)
        db-url (str "jdbc:mysql://" db-host ":" db-port "/"
                    db-name "?user=" db-user "&password=" db-pass
                    "&useUnicode=yes&characterEncoding=utf8&useSSL=false")]
    (println "Initializing databases")
    (reset! db (conman/connect!
                {:init-size  1
                 :min-idle   1
                 :max-idle   4
                 :max-active 32
                 :jdbc-url   db-url}))
    (conman/bind-connection
      @db
      "sql/users.sql"
      "sql/sessions.sql"
      "sql/contexts.sql"
      "sql/ops.sql"
      "sql/sites.sql"
      "sql/metrics.sql")))

(defn get-db []
  @db)

;; basic protocol extends
(defn to-date [sql-date]
  (-> sql-date (.getTime) (java.util.Date.)))

(extend-protocol jdbc/IResultSetReadColumn
  java.sql.Date
  (result-set-read-column [v _ _] (to-date v))

  java.sql.Timestamp
  (result-set-read-column [v _ _] (to-date v)))

(extend-type java.util.Date
  jdbc/ISQLParameter
  (set-parameter [v ^PreparedStatement stmt idx]
    (.setTimestamp stmt idx (java.sql.Timestamp. (.getTime v)))))


;; used by migratus, which is run via leiningen
(def conn-edn
  {:store :database
   :migration-dir "migrations"
   :db {:classname "com.mysql.jdbc.Driver"
        :subprotocol "mysql"
        :subname (str "//" (env/db :database-host) "/" (env/db :database-name))
        :user (env/db :database-user)
        :useSSL false
        :password (env/db :database-pass)}})

;; multi methods to use when tracking
(defmulti track-op
  (fn[data] (:type data)))

(defmethod track-op :default [_])

(defmulti track-context
  (fn [context op-id data] (first context)))

(defmethod track-context :default [_ _ _])


;; main method to run via leiningen
(defn start-app [& args]
  (println "Starting app...")
;;   (serve {:handler handler/app
;;           :init handler/init
;;           :destroy handler/destroy}))
  )

(defn -main [& args]
  (cond
    (some #{"migrate"} args)
      (migratus/migrate conn-edn)
    (some #{"rollback"} args)
      (migratus/rollback conn-edn)
    :else
    (start-app args)))
