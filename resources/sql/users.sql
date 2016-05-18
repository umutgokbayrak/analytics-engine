-- :name db-get-user :? :1
-- :doc retrieve a user given the user id.
SELECT * FROM users
WHERE user_id = :user_id
AND site_id = :site_id

-- :name db-create-user! :! :n
-- :doc creates a new user record
INSERT INTO users
(anonymous_id, user_id, site_id, channel, created_at)
VALUES (:anonymous_id, :user_id, :site_id, :channel, :created_at)

-- :name db-identify-user! :! :n
-- :doc update an existing user record
UPDATE users
SET user_id = :user_id
WHERE anonymous_id = :anonymous_id

-- :name db-delete-user! :! :n
-- :doc delete a user given the id
DELETE FROM users
WHERE user_id = :user_id
AND site_id = :site_id

-- :name db-delete-user-by-anon-id! :! :n
-- :doc delete a user given the anonymous_id
DELETE FROM users
WHERE anonymous_id = :anonymous_id

-- :name db-create-user-traits! :! :n
-- :doc creates a new user record
INSERT INTO users_traits
(site_id, user_id, address, city, country_code, postal_code,
 state, age, avatar_url, birthday, description, email, full_name,
 gender, phone, title, username, created_at)
VALUES (:site_id, :user_id, :address, :city, :country_code, :postal_code,
 :state, :age, :avatar_url, :birthday, :description, :email,
 :full_name, :gender, :phone, :title, :username, :created_at)

-- :name db-update-user-traits! :! :n
-- :doc update an existing user traits record
UPDATE users_traits
SET address = :address,
 city = :city,
 country_code = :country_code,
 postal_code = :postal_code,
 state = :state,
 age = :age,
 avatar_url = :avatar_url,
 birthday = :birthday,
 description = :description,
 email = :email,
 full_name = :full_name,
 gender = :gender,
 phone = :phone,
 title = :title,
 username = :username,
 updated_at = :updated_at
WHERE user_id = :user_id
AND site_id = :site_id


-- :name db-get-user-traits :? :1
-- :doc retrieve a user trait given the user id.
SELECT * FROM users_traits
WHERE user_id = :user_id
AND site_id = :site_id


-- :name db-get-custom-user-trait :? :1
-- :doc retrieve a custom user trait given the user id.
SELECT * FROM users_custom_traits
WHERE user_id = :user_id
AND site_id = :site_id
AND trait_name = :trait_name


-- :name db-create-custom-user-trait! :! :n
-- :doc creates a new custom user trait record
INSERT INTO users_custom_traits
(site_id, user_id, trait_name, trait_value, created_at)
VALUES (:site_id, :user_id, :trait_name, :trait_value, :created_at)

-- :name db-update-custom-user-trait! :! :n
-- :doc update an existing custom user traits record
UPDATE users_custom_traits
SET trait_value = :trait_value,
    updated_at = :updated_at
WHERE user_id = :user_id
AND site_id = :site_id
AND trait_name = :trait_name


-- :name db-count-users :? *
-- :doc retrieve number of users
SELECT count(*) FROM users


-- :name db-count-users-by-site :? :1
-- :doc count users given the user id.
SELECT count(*) FROM users
WHERE site_id = :site_id

-- :name db-count-users-by-site-and-date :? :1
-- :doc count users for a channel
SELECT count(*) FROM users
WHERE site_id = :site_id
AND created_at >= :begin_date
AND created_at <= :end_date

-- :name db-count-users-by-channel :? :1
-- :doc count users for a channel
SELECT count(*) FROM users
WHERE site_id = :site_id
AND channel = :channel

-- :name db-count-users-by-channel-and-date :? :1
-- :doc count users for a channel
SELECT count(*) FROM users
WHERE site_id = :site_id
AND channel = :channel
AND created_at >= :begin_date
AND created_at <= :end_date
