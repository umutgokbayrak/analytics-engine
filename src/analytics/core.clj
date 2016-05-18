(ns analytics.core
  (:gen-class))

;; multi methods to use when tracking
(defmulti track-op
  (fn[data] (:type data)))

(defmethod track-op :default [_])

(defmulti track-context
  (fn [context op-id data] (first context)))

(defmethod track-context :default [_ _ _])
