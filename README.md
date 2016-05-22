# analytics-engine

This is a general purpose Clojure analytics library designed to
help you build your next analytics backed application.

# Installation

Add this to your [Leiningen](https://github.com/technomancy/leiningen) :dependencies:

[![Clojars Project](https://img.shields.io/clojars/v/org.clojars.umutgokbayrak/analytics-engine.svg)](https://clojars.org/org.clojars.umutgokbayrak/analytics-engine)


To use the engine you first need to download the newest version of the free GeoIP data files. To do so you can use the download script [UpdateGeoIpFiles.sh](https://github.com/bertschneider/clj-geoip/blob/master/scripts/UpdateGeoIpFiles.sh) provided from [clj-geoip](https://github.com/bertschneider/clj-geoip) project. It simply downloads the newest archives and extracts them into the resources folder.

MaxMind provides new versions of the data files on a monthly basis. So it's a good idea to run the script every now and then.

Viola! Basic setup is done. Engine will run with H2 database without any configuration. If you want to run it with MySQL you need to provide config parameters. Check config namespace and override those .clj files in your project.

Check [analytics-template project](https://github.com/umutgokbayrak/analytics-template) in order to get started and see how it works in real life.


## Usage


```bash
(ns test
  (:require [analytics.utils :as util]
            	 [analytics.api.core :refer :all]))

(def data
  {:anonymous-id (util/uuid)
   :type "event"    ;; possible values: event/page/screen
   :site-id "TEST-ID"
   :user-id (str "hebelek@pismail.com")
   :session-id (util/uuid)
   :channel "fn"
   :page "Sample"
   :event "Button Clicked"
   :context {:app {:name "Initech Global"
                   :version "1.0"}
             :campaign {:name "TPS Innovation Newsletter"
                        :source "Newsletter"
                        :medium "email"
                        :term "tps records"
                        :content "image link"}
             :device {:id "B5372DB0-C21E-11E4-8DFC-AA07A5B093DB"
                      :manufacturer "Apple"
                      :model "iPhone7,2"
                      :name "maguro"
                      :type "ios"}
             :locale "nl-NL"
             :location {:city "San Francisco"
                        :country "United States"
                        :latitude 40.2964197
                        :longitude -76.9411617}
             :ip "8.8.8.8"
             :network {:bluetooth false
                       :carrier "T-Mobile NL"
                       :cellular true
                       :wifi true}
             :os {:name "iPhone OS"
                  :version "8.1.3"}
             :browser {:name "Safari"
                       :version "9.1"
                       :major "9"}
             :page {:url "/academy/"
                    :referrer {:type "refferrer"
                               :url "http://pismail.com/index"
                               :domain "pismail.com"}
                    :title "Academy Page Title"}
             :screen {:height 768
                      :width 1024}
             :user-agent "Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1"
             :timezone "3"
             :hebelek {:test "1"}}
   :properties {:plan "Pro Annual"
                :account-type "Facebook"
                :variation "blue signup button"}
   })

(def data-identify
  {:anonymous-id "4b3a21a8-fc7f-4c61-859d-c9c9f2deaf38"
   :type "identify"
   :site-id "2423486b-1e8d-417b-bcd9-fb183c6eec9c"
   :user-id "hebelek555@pismail.com"
   :channel "fn"
   :traits {:address "Yeni bir adres"
            :age 39
            :email "umutgokbayrak@yahoo.com"
            :gender "M"
            :spouse "Seryal"
            :children "Melissa"}
   })

;; To track data run this
  (track data)

;; To identify a user run this
(track data-identify)
```

## License
See license file

Copyright © 2016 Umut Gokbayrak
