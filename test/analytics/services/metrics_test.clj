(ns analytics.services.metrics-test
  (:require [clojure.test :refer :all]
            [analytics.utils :as util]
            [analytics.services.metrics :as metrics]))


(deftest get-session-test
  (testing "FIXME, I fail."
    (is (some? (metrics/get-metric
                 {:context "session"
                  :date-prefix "2016"
                  :dim-name1 "site-id"
                  :dim-name2 "channel"
                  :dim-value1 "2423486b-1e8d-417b-bcd9-fb183c6eec9c"
                  :dim-value2 "fn"})))))

