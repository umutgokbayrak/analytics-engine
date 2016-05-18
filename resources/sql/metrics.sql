-- :name db-get-metric :? :1
-- :doc retrieve a user for given params
SELECT * FROM metrics
WHERE context = :context
AND metric_type = :metric_type
AND date_prefix = :date_prefix
AND dim_name = :dim_name
AND dim_value = :dim_value

-- :name db-create-metric! :! :n
-- :doc creates a new user record
INSERT INTO metrics
  (context, metric_type, date_prefix, dim_name, dim_name1,
   dim_name2, dim_name3, dim_name4, dim_value, dim_value1,
   dim_value2, dim_value3, dim_value4, metric_value, updated_at)
VALUES
  (:context, :metric_type, :date_prefix, :dim_name, :dim_name1,
   :dim_name2, :dim_name3, :dim_name4, :dim_value, :dim_value1,
  :dim_value2, :dim_value3, :dim_value4, :metric_value, :updated_at)

-- :name db-increment-metric! :! :n
-- :doc increments a metric value for a given value
UPDATE metrics
SET metric_value = metric_value + :incr_value
WHERE id = :metric_id
<<<<<<< HEAD


-- :name db-get-all-metrics :? :*
-- :doc retrieve all metrics
SELECT * FROM metrics
ORDER BY updated_at DESC
LIMIT 50
=======
>>>>>>> 2da17280865eb9169ab38d131b93e94c318288a2
