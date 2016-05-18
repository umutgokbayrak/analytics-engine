(ns analytics.core-test
  (:require [clojure.test :refer :all]
            [analytics.utils :as util]
            [analytics.api.core :refer :all]))

(def data
  {:anonymous-id (util/uuid)
   :type "event"
   :site-id "AN-2423486b"
   :user-id (str "hebelek" (util/uuid) "@pismail.com")
   :session-id (util/uuid) ;; "6088eec0-2336-47bc-afee-845e58cf0336"
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
             :timezone "3" ;; var hrs = -(new Date().getTimezoneOffset() / 60)
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

(deftest track-test
  (track data))

;; (deftest identify-test
;;   (track data-identify))

;; (track-test)
;; (identify-test)

; (keys (:properties data))
