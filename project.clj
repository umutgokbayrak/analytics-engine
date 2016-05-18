<<<<<<< HEAD
(defproject org.clojars.umutgokbayrak/analytics-engine "0.1.0"
=======
(defproject org.clojars.umutgokbayrak/analytics-engine "1.0"
>>>>>>> 2da17280865eb9169ab38d131b93e94c318288a2
  :description "General purpose analytics engine in Clojure"
  :url "https://github.com/umutgokbayrak/analytics-engine"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
<<<<<<< HEAD
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
=======

  :dependencies [; clojure dependencies
                  [org.clojure/clojure "1.8.0"]
                  [migratus "0.8.13"]
                  [environ "1.0.2"]
                  [org.slf4j/slf4j-log4j12 "1.7.9"]
                  [mysql/mysql-connector-java "5.1.38"]
                  [conman "0.4.9"]
                  [org.clojure/core.async "0.2.374"]
                  [de.bertschneider/clj-geoip "0.2"]
                  [digest "1.4.4"]
                  [compojure "1.5.0"]
                  [ring-server "0.4.0"]
                  [hiccup "1.0.5"]
                  [org.clojure/data.json "0.2.6"]

                  ; clojurescript dependencies
                  [org.clojure/clojurescript "1.8.51"]
                  [prismatic/dommy "1.1.0"]
                  [com.lucasbradstreet/cljs-uuid-utils "1.0.2"]]
  :plugins [[migratus-lein "0.2.6"]
            [lein-cljsbuild "1.1.3"]
            [lein-ring "0.9.7"]]
  :ring {:handler analytics.handler/app
         :init analytics.handler/init
         :destroy analytics.handler/destroy}
  :source-paths ["src/clj"]
  :main ^:skip-aot analytics.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}
             :production
             {:ring
              {:open-browser? false, :stacktraces? false, :auto-reload? false}}
             :dev
             {:dependencies [[ring-mock "0.1.5"] [ring/ring-devel "1.3.1"]]}}
  :resource-paths ["resources"]
  :hooks [leiningen.cljsbuild]
  :uberjar-name "analytics.jar"
  :jvm-opts ["-server"
             "-Xmx2g"
             "-XX:-OmitStackTraceInFastThrow"
             "-Djava.net.preferIPv4Stack=true"]
  :cljsbuild
  {:builds
   [{:id "dev"
     :compiler
     {:output-to "resources/public/js/analytics.js"
      :optimizations :whitespace
      :externs ["externs/raphael.ext.js"]
      :pretty-print true
      :libs ["analytics.parsers.useragent"]
      :warnings {:single-segment-namespace false}}
     :source-paths ["src/cljs"]
     :jar true}]})
>>>>>>> 2da17280865eb9169ab38d131b93e94c318288a2
