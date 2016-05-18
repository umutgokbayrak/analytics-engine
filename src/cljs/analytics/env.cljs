(ns analytics.env)

(def js-map {:app-name "Foo"
             :app-version "1.0"
             :api-url "http://localhost:3000/api"})
             ;; :api-url "//google.com/api"})

(defn js [k]
  (get js-map k))
