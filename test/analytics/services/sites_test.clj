(ns analytics.services.sites-test
  (:require [clojure.test :refer :all]
<<<<<<< HEAD
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
=======
            [analytics.services.sites :refer :all]
            [analytics.utils :as util]))


(def site-id (util/uuid))
(def site-name "Hebelek")
(def site-description "Hebelek Site")
(def site-domain "http://www.pismail.com")

(deftest add-site-test-by-id
  (testing "FIXME, I fail."
    (is (= 1 (add-site!
               site-id
               site-name
               site-description
               site-domain)))))


(deftest add-site-test-by-no-id
  (testing "FIXME, I fail."
    (is (= 1 (add-site!
               nil
               site-name
               site-description
               site-domain)))))


(defn my-test-fixture [f]
  (f)
  (delete-site-by-id! site-id))

(use-fixtures :each my-test-fixture)
>>>>>>> 2da17280865eb9169ab38d131b93e94c318288a2
