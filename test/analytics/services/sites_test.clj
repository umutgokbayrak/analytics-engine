(ns analytics.services.sites-test
  (:require [clojure.test :refer :all]
            [analytics.utils :as util]
            [analytics.services.sites :refer :all]))

(deftest get-session-test
  (testing "FIXME, I fail."
    (is (> (num-of-sites) 0))))

(def new-site-id (util/uuid))

(deftest add-session-test
  (testing "FIXME, I fail."
    (let [n (num-of-sites)]
      (add-site! new-site-id "Site Name" "Description" "domain.com")
      (is (= (num-of-sites) (+ 1 n)))
      (delete-site-by-id! new-site-id))))
