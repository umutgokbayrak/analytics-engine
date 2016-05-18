-- :name db-get-session :? :1
-- :doc retrieve a session given the session id.
SELECT * FROM sessions
WHERE session_id = :session_id

-- :name db-create-session! :! :n
-- :doc creates a new session record
INSERT INTO sessions
(session_id, site_id, user_id, prev_session_count,
 channel, ip, locale, created_at, updated_at)
VALUES (:session_id, :site_id, :user_id,
        :prev_session_count, :channel, :ip, :locale,
        :created_at, :updated_at)

-- :name db-delete-session! :! :n
-- :doc delete a session given the id
DELETE FROM sessions
WHERE session_id = :session_id


-- :name db-update-session! :! :n
-- :doc update an existing session record
UPDATE sessions
SET prev_session_count = :prev_session_count,
updated_at = :updated_at
WHERE session_id = :session_id


-- :name db-update-session-updated-date! :! :n
-- :doc update an existing session's updated_at field
UPDATE sessions
SET updated_at = :updated_at
WHERE session_id = :session_id


-- :name db-count-sessions-by-user :? :1
-- :doc count sessions given the user id.
SELECT count(*) as num FROM sessions
WHERE site_id = :site_id
AND user_id = :user_id


-- :name db-count-sessions-by-user-date :? :1
-- :doc count sessions given the user id.
SELECT count(*) as num FROM sessions
WHERE site_id = :site_id
AND user_id = :user_id
AND created_at >= :begin_date
AND created_at <= :end_date


-- :name db-session-last-updated-at :? :1
-- :doc returns last updated date for a session record
SELECT updated_at FROM sessions
WHERE session_id = :session_id
