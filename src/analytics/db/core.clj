(ns analytics.db.core
  (:require [clojure.java.jdbc :as jdbc]
            [conman.core :as conman]
            [analytics.env :as env])
  (:import [java.sql BatchUpdateException PreparedStatement])
  (:gen-class))


;; used by conman for hugsql connections
(def db (atom nil))
(def db-host (env/db :database-host))
(def db-name (env/db :database-name))

(defn mysql? []
  (and (some? db-host) (some? db-name)))


(if (nil? @db)
  (let [db-port (env/db :database-port)
        db-user (env/db :database-user)
        db-pass (env/db :database-pass)
        db-url (if (and (some? db-host) (some? db-name))
                 (str "jdbc:mysql://" db-host ":" db-port "/"
                      db-name "?user=" db-user "&password=" db-pass
                      "&useUnicode=yes&characterEncoding=utf8&useSSL=false")
                 "jdbc:h2:file:./analytics.db")]

    ;; initialize database
    (println "Initializing databases")
    (if (mysql?)
      (reset! db (conman/connect!
                   {:init-size  1
                    :min-idle   1
                    :max-idle   4
                    :max-active 32
                    :jdbc-url   db-url}))
      (reset! db (conman/connect!
                   {:datasource
                    (doto (org.h2.jdbcx.JdbcDataSource.)
                      (.setURL db-url)
                      (.setUser "sa")
                      (.setPassword ""))})))

    ;; bind the sql scripts
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
        :subname (str "//" db-host "/" db-name)
        :user (env/db :database-user)
        :useSSL false
        :password (env/db :database-pass)}})


(defn- create-tables-if-needed []
  (try
    (db-count-sites)
    (println "Tables exist.")
    (catch Exception e
      (do
        (println "Creating tables.")
        (jdbc/db-do-commands @db (env/db-init-scripts))))))


;; if h2 mode is on, we shall create the tables as well
(if (not (mysql?))
  (create-tables-if-needed))

