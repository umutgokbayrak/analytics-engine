(ns analytics.config.db
  (:gen-class))

(def db-map
  {:dev  {}
   :test {}
   :prod {:database-name (System/getenv "DB_NAME")
          :database-user (System/getenv "DB_USER")
          :database-pass (System/getenv "DB_PASS")
          :database-host (System/getenv "DB_HOST")
          :database-post (System/getenv "DB_PORT")}})
