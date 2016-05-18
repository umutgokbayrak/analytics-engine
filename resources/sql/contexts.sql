-- :name db-get-property-context-by-op-id :? :1
-- :doc retrieve a property context by op id
SELECT * FROM property_context
WHERE op_id = :op_id


-- :name db-create-property-context! :! :n
-- :doc creates a new property context
INSERT INTO property_context
(op_id, op_type, site_id, user_id,
 session_id, page, event, property_name,
 property_value, created_at)
VALUES (:op_id, :op_type, :site_id, :user_id,
        :session_id, :page, :event, :property_name,
        :property_value, :created_at)


-- :name db-get-location-context-by-op-id :? :1
-- :doc retrieve a location context by op id
SELECT * FROM location_context
WHERE op_id = :op_id


-- :name db-create-location-context! :! :n
-- :doc creates a new location context
INSERT INTO location_context
(op_id, op_type, site_id, user_id,
 session_id, page, event, loc_latitude,
 loc_longitude, loc_country_code, loc_city,
 loc_postal_code, loc_organization, created_at)
VALUES (:op_id, :op_type, :site_id, :user_id,
        :session_id, :page, :event, :loc_latitude,
        :loc_longitude, :loc_country_code,
        :loc_city, :loc_postal_code, :loc_organization,
        :created_at)


-- :name db-get-app-context-by-op-id :? :1
-- :doc retrieve a app context by op id
SELECT * FROM app_context
WHERE op_id = :op_id


-- :name db-create-app-context! :! :n
-- :doc creates a new app context
INSERT INTO app_context
(op_id, op_type, site_id, user_id,
 session_id, page, event, app_name,
 app_version, created_at)
VALUES (:op_id, :op_type, :site_id, :user_id,
        :session_id, :page, :event, :app_name,
        :app_version, :created_at)


-- :name db-get-campaign-context-by-op-id :? :1
-- :doc retrieve a campaign context by op id
SELECT * FROM campaign_context
WHERE op_id = :op_id


-- :name db-create-campaign-context! :! :n
-- :doc creates a new campaign context
INSERT INTO campaign_context
(op_id, op_type, site_id, user_id,
 session_id, page, event, campaign_name,
 campaign_source, campaign_medium, campaign_term,
 campaign_content, created_at)
VALUES (:op_id, :op_type, :site_id, :user_id,
        :session_id, :page, :event, :campaign_name,
        :campaign_source, :campaign_medium,
        :campaign_term, :campaign_content, :created_at)


-- :name db-get-device-context-by-op-id :? :1
-- :doc retrieve a device context by op id
SELECT * FROM device_context
WHERE op_id = :op_id


-- :name db-create-device-context! :! :n
-- :doc creates a new device context
INSERT INTO device_context
(op_id, op_type, site_id, user_id,
 session_id, page, event, device_id,
 device_manufacturer, device_model,
 device_name, created_at)
VALUES (:op_id, :op_type, :site_id, :user_id,
        :session_id, :page, :event, :device_id,
        :device_manufacturer, :device_model,
        :device_name, :created_at)


-- :name db-get-network-context-by-op-id :? :1
-- :doc retrieve a network context by op id
SELECT * FROM network_context
WHERE op_id = :op_id


-- :name db-create-network-context! :! :n
-- :doc creates a new network context
INSERT INTO network_context
(op_id, op_type, site_id, user_id,
 session_id, page, event, network_carrier, network_isp,
 network_bluetooth, network_cellular, network_wifi,
 created_at)
VALUES (:op_id, :op_type, :site_id, :user_id,
        :session_id, :page, :event, :network_carrier,
        :network_isp, :network_bluetooth,
        :network_cellular, :network_wifi,
        :created_at)


-- :name db-get-screen-context-by-op-id :? :1
-- :doc retrieve a screen context by op id
SELECT * FROM screen_context
WHERE op_id = :op_id


-- :name db-create-screen-context! :! :n
-- :doc creates a new screen context
INSERT INTO screen_context
(op_id, op_type, site_id, user_id,
 session_id, page, event, screen_height, screen_width,
 screen_height_width, created_at)
VALUES (:op_id, :op_type, :site_id, :user_id,
        :session_id, :page, :event, :screen_height,
        :screen_width, :screen_height_width, :created_at)


-- :name db-get-page-context-by-op-id :? :1
-- :doc retrieve a page context by op id
SELECT * FROM page_context
WHERE op_id = :op_id


-- :name db-create-page-context! :! :n
-- :doc creates a new page context
INSERT INTO page_context
(op_id, op_type, site_id, user_id,
 session_id, page, event, referrer_type,
 referrer_url, referrer_domain, page_title,
 page_url, created_at)
VALUES (:op_id, :op_type, :site_id, :user_id,
        :session_id, :page, :event, :referrer_type,
        :referrer_url, :referrer_domain, :page_title,
        :page_url, :created_at)


-- :name db-get-os-context-by-op-id :? :1
-- :doc retrieve a os context by op id
SELECT * FROM os_context
WHERE op_id = :op_id


-- :name db-create-os-context! :! :n
-- :doc creates a new os context
INSERT INTO os_context
(op_id, op_type, site_id, user_id,
 session_id, page, event, os_name,
 os_version, created_at)
VALUES (:op_id, :op_type, :site_id, :user_id,
        :session_id, :page, :event, :os_name,
        :os_version, :created_at)


-- :name db-get-browser-context-by-op-id :? :1
-- :doc retrieve a browser context by op id
SELECT * FROM browser_context
WHERE op_id = :op_id


-- :name db-create-browser-context! :! :n
-- :doc creates a new browser context
INSERT INTO browser_context
(op_id, op_type, site_id, user_id,
 session_id, page, event, browser_name,
 browser_version, browser_major, created_at)
VALUES (:op_id, :op_type, :site_id, :user_id,
        :session_id, :page, :event, :browser_name,
        :browser_version, :browser_major, :created_at)
