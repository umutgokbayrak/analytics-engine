(ns analytics.services.users-test
  (:require [clojure.test :refer :all]
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
