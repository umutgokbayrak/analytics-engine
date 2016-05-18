(defproject org.clojars.umutgokbayrak/analytics-engine "0.1.0"
  :description "General purpose analytics engine in Clojure"
  :url "https://github.com/umutgokbayrak/analytics-engine"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [environ "1.0.2"]
                 [org.slf4j/slf4j-log4j12 "1.7.9"]
                 [com.h2database/h2 "1.4.191"]
                 [mysql/mysql-connector-java "5.1.38"]
                 [conman "0.4.9"]
                 [org.clojure/core.async "0.2.374"]
                 [de.bertschneider/clj-geoip "0.2"]
                 [digest "1.4.4"]
                 [compojure "1.5.0"]
                 [org.clojure/data.json "0.2.6"]]
  :jvm-opts ["-server" "-Djava.net.preferIPv4Stack=true"])
