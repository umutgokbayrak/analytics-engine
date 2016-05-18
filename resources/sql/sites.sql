-- :name db-count-sites :? :1
-- :doc retrieve number of sites
SELECT count(*) as num FROM sites


-- :name db-create-site! :! :n
-- :doc creates a new site record
INSERT INTO sites
(site_id, site_name, site_description, created_at)
VALUES (:site_id, :site_name, :site_description, :created_at)


-- :name db-create-site-domain! :! :n
-- :doc creates a new site domain record
INSERT INTO sites_domains
(site_id, domain_name, created_at)
VALUES (:site_id, :domain_name, :created_at)


-- :name db-delete-site! :! :n
-- :doc delete a site given the id
DELETE FROM sites
WHERE site_id = :site_id

