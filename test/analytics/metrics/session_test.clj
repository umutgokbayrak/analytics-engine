(ns analytics.metrics.session-test
  (:require [clojure.test :refer :all]
            [analytics.utils :as util]
            [analytics.services.metrics :as metr]))


(deftest get-session-test
  (testing "FIXME, I fail."
    (is (= 1 (metr/get-session
               {:context "session"
                :date-prefix "2016"
                :dim-name1 "site-id"
                :dim-name2 "channel"
                :dim-value1 "2423486b-1e8d-417b-bcd9-fb183c6eec9c"
                :dim-value2 "fn"})))))
