(ns analytics.services.users-test
  (:require [clojure.test :refer :all]
<<<<<<< HEAD
            [analytics.utils :as util]
            [analytics.services.users :refer :all]))


(def new-anon-id (util/uuid))
(def site-id "AN-2423486b")

(deftest add-user-test
  (testing "FIXME, I fail."
    (is (add-user!
          new-anon-id
          new-anon-id
          site-id
          "web")))
  (delete-user-by-id!
    site-id
    new-anon-id))


(deftest insert-user-if-needed-test
  (testing "FIXME, I fail."
    (is (insert-user-if-needed!
          new-anon-id
          new-anon-id
          site-id
          "web")))
  (delete-user-by-id!
    site-id
    new-anon-id))


(deftest delete-user-by-anon-id-test
  (testing "FIXME, I fail."
    (is (delete-user-by-anon-id!
          new-anon-id))))
=======
            [analytics.services.users :refer :all]
            [analytics.services.sites :refer :all]
            [analytics.utils :as util]))


(def site-id (util/uuid))
(def site-name "Hebelek")
(def site-description "Hebelek Site")
(def site-domain "http://www.pismail.com")
(def anonymous-id (util/uuid))


(defn tear-down []
  (delete-user-by-anon-id! anonymous-id)
  (delete-site-by-id! site-id))


(defn add-site-test []
  (add-site! site-id site-name
            site-description site-domain))

(deftest add-user-test
  (testing "FIXME, I fail."
    (add-site-test)
    (is (= 1 (add-user!
               anonymous-id
               nil
               site-id
               "mobile")))))
>>>>>>> 2da17280865eb9169ab38d131b93e94c318288a2


(deftest identify-user-test
  (testing "FIXME, I fail."
<<<<<<< HEAD
    (identify-user! new-anon-id "hebelek@pismail.com")
    (is (nil? (get-user site-id new-anon-id)))))

=======
    (add-site-test)
    (add-user! anonymous-id nil site-id "mobile")
    (identify-user! anonymous-id "umutgokbayrak@yahoo.com")))


(defn my-test-fixture [f]
  (f)
  (tear-down))

(use-fixtures :each my-test-fixture)
>>>>>>> 2da17280865eb9169ab38d131b93e94c318288a2
