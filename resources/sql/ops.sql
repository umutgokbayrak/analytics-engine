-- :name db-create-op! :insert
-- :doc creates a new op record
INSERT INTO ops
(op_type, site_id, user_id, session_id,
 hash_code, channel, page, event, created_at)
VALUES (:op_type, :site_id, :user_id,
        :session_id, :hash_code, :channel, :page,
        :event, :created_at)


-- :name db-get-op :? :1
-- :doc retrieve an op given the op id.
SELECT * FROM ops
WHERE op_id = :op_id


-- :name db-delete-op! :! :n
-- :doc delete an op given the id
DELETE FROM ops
WHERE op_id = :op_id


-- :name db-count-ops-by-user :? :1
-- :doc count ops given the user id.
SELECT count(*) as num FROM ops
WHERE site_id = :site_id
AND user_id = :user_id


-- :name db-count-ops-by-user-date :? :1
-- :doc count users given the user id.
SELECT count(*) as num FROM ops
WHERE site_id = :site_id
AND user_id = :user_id
AND created_at >= :begin_date
AND created_at <= :end_date


-- :name db-count-ops-by-user-date-type :? :1
-- :doc count users given the user id.
SELECT count(*) as num FROM ops
WHERE site_id = :site_id
AND user_id = :user_id
AND op_type = :op_type
AND created_at >= :begin_date
AND created_at <= :end_date
