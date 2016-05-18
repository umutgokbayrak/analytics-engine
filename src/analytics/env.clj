(ns analytics.env
  (:use [analytics.config.db]
        [analytics.config.metrics]
        [analytics.config.analytics])
  (:gen-class))


(def env-var (or (System/getenv "ENV") "dev"))

(defn db [k]
  (get (get db-map (keyword env-var)) k))

(defn metrics [k]
  (get metrics-map k))

(defn analytics [k]
  (get analytics-map k))


(defn enabled? [k]
  (or (get analytics-map k) false))

(defn db-init-scripts []
  "CREATE TABLE IF NOT EXISTS sites
  (site_id VARCHAR(256) NOT NULL,
  site_name VARCHAR(256) NOT NULL,
  site_description VARCHAR(256) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (site_id));

  INSERT INTO `sites` (`site_id`, `site_name`, `site_description`, `created_at`)
  VALUES ('TEST-ID','Test','Test Site','2016-04-22 23:16:29');

  CREATE TABLE IF NOT EXISTS sites_domains
  (id BIGINT AUTO_INCREMENT,
  site_id VARCHAR(256) NOT NULL,
  domain_name VARCHAR(256) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  FOREIGN KEY (site_id) REFERENCES sites(site_id) ON DELETE CASCADE);


  CREATE TABLE IF NOT EXISTS users
  (anonymous_id VARCHAR(50) NOT NULL,
  user_id VARCHAR(256) NOT NULL,
  site_id VARCHAR(256) NOT NULL,
  channel VARCHAR(20) NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (anonymous_id),
  FOREIGN KEY (site_id) REFERENCES sites(site_id) ON DELETE CASCADE);


  CREATE TABLE IF NOT EXISTS users_traits
  (site_id VARCHAR(256) NOT NULL,
  user_id VARCHAR(256) NOT NULL,
  address VARCHAR(256) NULL,
  city VARCHAR(100) NULL,
  country_code VARCHAR(5) NULL,
  postal_code CHAR(5) NULL,
  state VARCHAR(50) NULL,
  age SMALLINT NULL,
  avatar_url VARCHAR(256) NULL,
  birthday DATE NULL,
  description VARCHAR(256) NULL,
  email VARCHAR(100) NULL,
  full_name VARCHAR(150) NULL,
  gender CHAR(1) NULL,
  phone VARCHAR(20) NULL,
  title VARCHAR(150) NULL,
  username VARCHAR(50) NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NULL,
  PRIMARY KEY (site_id, user_id),
  FOREIGN KEY (user_id) REFERENCES users(user_id) ON UPDATE CASCADE,
  FOREIGN KEY (site_id) REFERENCES sites(site_id) ON DELETE CASCADE);


  CREATE TABLE IF NOT EXISTS users_custom_traits
  (id BIGINT AUTO_INCREMENT,
  site_id VARCHAR(256) NOT NULL,
  user_id VARCHAR(256) NOT NULL,
  trait_name VARCHAR(50) NOT NULL,
  trait_value VARCHAR(150) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES users(user_id) ON UPDATE CASCADE,
  FOREIGN KEY (site_id) REFERENCES sites(site_id) ON DELETE CASCADE);


  CREATE TABLE IF NOT EXISTS sessions
  (session_id VARCHAR(50) NOT NULL,
  site_id VARCHAR(256) NOT NULL,
  user_id VARCHAR(256) NOT NULL,
  prev_session_count INT DEFAULT 0,
  channel VARCHAR(20) NULL,
  ip VARCHAR(50) NULL,
  locale VARCHAR(50) NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NULL,
  PRIMARY KEY (session_id),
  FOREIGN KEY (user_id) REFERENCES users(user_id) ON UPDATE CASCADE,
  FOREIGN KEY (site_id) REFERENCES sites(site_id) ON DELETE CASCADE);


  CREATE TABLE IF NOT EXISTS ops
  (op_id BIGINT AUTO_INCREMENT,
  op_type VARCHAR(10) NOT NULL,     -- session/hit/event/identify/start/stop
  site_id VARCHAR(256) NOT NULL,
  user_id VARCHAR(256) NOT NULL,
  session_id VARCHAR(50) NOT NULL,
  hash_code VARCHAR(50) NOT NULL,
  channel VARCHAR(20) NULL,         -- mobile, web, api, tablet
  page VARCHAR(500) NULL,           -- page for web, screen for mobile
  event VARCHAR(500) NULL,          -- event key
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (op_id),
  FOREIGN KEY (user_id) REFERENCES users(user_id) ON UPDATE CASCADE,
  FOREIGN KEY (session_id) REFERENCES sessions(session_id) ON DELETE CASCADE,
  FOREIGN KEY (site_id) REFERENCES sites(site_id) ON DELETE CASCADE);


  CREATE TABLE IF NOT EXISTS property_context
  (id BIGINT AUTO_INCREMENT,
  op_id BIGINT NOT NULL,
  op_type VARCHAR(10) NOT NULL,
  site_id VARCHAR(256) NOT NULL,
  user_id VARCHAR(256) NOT NULL,
  session_id VARCHAR(50) NOT NULL,
  page VARCHAR(500) NULL,
  event VARCHAR(500) NULL,
  property_name VARCHAR(100) NOT NULL,
  property_value VARCHAR(150) NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES users(user_id) ON UPDATE CASCADE,
  FOREIGN KEY (session_id) REFERENCES sessions(session_id) ON DELETE CASCADE,
  FOREIGN KEY (site_id) REFERENCES sites(site_id) ON DELETE CASCADE);


  CREATE TABLE IF NOT EXISTS location_context
  (id BIGINT AUTO_INCREMENT,
  op_id BIGINT NOT NULL,
  op_type VARCHAR(10) NOT NULL,
  site_id VARCHAR(256) NOT NULL,
  user_id VARCHAR(256) NOT NULL,
  session_id VARCHAR(50) NOT NULL,
  page VARCHAR(500) NULL,
  event VARCHAR(500) NULL,
  loc_latitude VARCHAR(20) NULL,
  loc_longitude VARCHAR(20) NULL,
  loc_country_code VARCHAR(5) NULL,
  loc_city VARCHAR(100) NULL,
  loc_postal_code CHAR(5) NULL,
  loc_organization VARCHAR(100) NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES users(user_id) ON UPDATE CASCADE,
  FOREIGN KEY (session_id) REFERENCES sessions(session_id) ON DELETE CASCADE,
  FOREIGN KEY (site_id) REFERENCES sites(site_id) ON DELETE CASCADE);


  CREATE TABLE IF NOT EXISTS app_context
  (id BIGINT AUTO_INCREMENT,
  op_id BIGINT NOT NULL,
  op_type VARCHAR(10) NOT NULL,
  site_id VARCHAR(256) NOT NULL,
  user_id VARCHAR(256) NOT NULL,
  session_id VARCHAR(50) NOT NULL,
  page VARCHAR(500) NULL,
  event VARCHAR(500) NULL,
  app_name VARCHAR(50) NULL,
  app_version VARCHAR(10) NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES users(user_id) ON UPDATE CASCADE,
  FOREIGN KEY (session_id) REFERENCES sessions(session_id) ON DELETE CASCADE,
  FOREIGN KEY (site_id) REFERENCES sites(site_id) ON DELETE CASCADE);


  CREATE TABLE IF NOT EXISTS campaign_context
  (id BIGINT AUTO_INCREMENT,
  op_id BIGINT NOT NULL,
  op_type VARCHAR(10) NOT NULL,
  site_id VARCHAR(256) NOT NULL,
  user_id VARCHAR(256) NOT NULL,
  session_id VARCHAR(50) NOT NULL,
  page VARCHAR(500) NULL,
  event VARCHAR(500) NULL,
  campaign_name VARCHAR(250) NULL,
  campaign_source VARCHAR(250) NULL,
  campaign_medium VARCHAR(250) NULL,
  campaign_term VARCHAR(250) NULL,
  campaign_content VARCHAR(250) NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES users(user_id) ON UPDATE CASCADE,
  FOREIGN KEY (session_id) REFERENCES sessions(session_id) ON DELETE CASCADE,
  FOREIGN KEY (site_id) REFERENCES sites(site_id) ON DELETE CASCADE);


  CREATE TABLE IF NOT EXISTS device_context
  (id BIGINT AUTO_INCREMENT,
  op_id BIGINT NOT NULL,
  op_type VARCHAR(10) NOT NULL,
  site_id VARCHAR(256) NOT NULL,
  user_id VARCHAR(256) NOT NULL,
  session_id VARCHAR(50) NOT NULL,
  page VARCHAR(500) NULL,
  event VARCHAR(500) NULL,
  device_id VARCHAR(100) NULL,
  device_manufacturer VARCHAR(100) NULL,
  device_model VARCHAR(100) NULL,
  device_name VARCHAR(150) NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES users(user_id) ON UPDATE CASCADE,
  FOREIGN KEY (session_id) REFERENCES sessions(session_id) ON DELETE CASCADE,
  FOREIGN KEY (site_id) REFERENCES sites(site_id) ON DELETE CASCADE);


  CREATE TABLE IF NOT EXISTS network_context
  (id BIGINT AUTO_INCREMENT,
  op_id BIGINT NOT NULL,
  op_type VARCHAR(10) NOT NULL,
  site_id VARCHAR(256) NOT NULL,
  user_id VARCHAR(256) NOT NULL,
  session_id VARCHAR(50) NOT NULL,
  page VARCHAR(500) NULL,
  event VARCHAR(500) NULL,
  network_carrier VARCHAR(100) NULL,
  network_isp VARCHAR(150) NULL,           -- location'dan buraya tasindi
  network_bluetooth SMALLINT DEFAULT 0,    -- 0/1
  network_cellular SMALLINT DEFAULT 0,     -- 0/1
  network_wifi SMALLINT DEFAULT 0,         -- 0/1
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES users(user_id) ON UPDATE CASCADE,
  FOREIGN KEY (session_id) REFERENCES sessions(session_id) ON DELETE CASCADE,
  FOREIGN KEY (site_id) REFERENCES sites(site_id) ON DELETE CASCADE);


  CREATE TABLE IF NOT EXISTS screen_context
  (id BIGINT AUTO_INCREMENT,
  op_id BIGINT NOT NULL,
  op_type VARCHAR(10) NOT NULL,
  site_id VARCHAR(256) NOT NULL,
  user_id VARCHAR(256) NOT NULL,
  session_id VARCHAR(50) NOT NULL,
  page VARCHAR(500) NULL,
  event VARCHAR(500) NULL,
  screen_height SMALLINT NULL DEFAULT 0,
  screen_width SMALLINT NULL DEFAULT 0,
  screen_height_width VARCHAR(21) NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES users(user_id) ON UPDATE CASCADE,
  FOREIGN KEY (session_id) REFERENCES sessions(session_id) ON DELETE CASCADE,
  FOREIGN KEY (site_id) REFERENCES sites(site_id) ON DELETE CASCADE);


  CREATE TABLE IF NOT EXISTS page_context
  (id BIGINT AUTO_INCREMENT,
  op_id BIGINT NOT NULL,
  op_type VARCHAR(10) NOT NULL,
  site_id VARCHAR(256) NOT NULL,
  user_id VARCHAR(256) NOT NULL,
  session_id VARCHAR(50) NOT NULL,
  page VARCHAR(500) NULL,
  event VARCHAR(500) NULL,
  referrer_type VARCHAR(20) NULL,    -- internal/external
  referrer_url VARCHAR(500) NULL,
  referrer_domain VARCHAR(100) NULL,
  page_title VARCHAR(500) NULL,
  page_url VARCHAR(500) NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES users(user_id) ON UPDATE CASCADE,
  FOREIGN KEY (session_id) REFERENCES sessions(session_id) ON DELETE CASCADE,
  FOREIGN KEY (site_id) REFERENCES sites(site_id) ON DELETE CASCADE);


  CREATE TABLE IF NOT EXISTS os_context
  (id BIGINT AUTO_INCREMENT,
  op_id BIGINT NOT NULL,
  op_type VARCHAR(10) NOT NULL,
  site_id VARCHAR(256) NOT NULL,
  user_id VARCHAR(256) NOT NULL,
  session_id VARCHAR(50) NOT NULL,
  page VARCHAR(500) NULL,
  event VARCHAR(500) NULL,
  os_name VARCHAR(40) NULL,
  os_version VARCHAR(20) NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES users(user_id) ON UPDATE CASCADE,
  FOREIGN KEY (session_id) REFERENCES sessions(session_id) ON DELETE CASCADE,
  FOREIGN KEY (site_id) REFERENCES sites(site_id) ON DELETE CASCADE);


  CREATE TABLE IF NOT EXISTS metrics
  (id BIGINT AUTO_INCREMENT,
  context VARCHAR(20) DEFAULT NULL,
  metric_type VARCHAR(10) DEFAULT '',
  date_prefix VARCHAR(20) DEFAULT NULL,
  dim_name DECIMAL(50),
  dim_name1 VARCHAR(50) DEFAULT NULL,
  dim_name2 VARCHAR(50) DEFAULT NULL,
  dim_name3 VARCHAR(50) DEFAULT NULL,
  dim_name4 VARCHAR(50) DEFAULT NULL,
  dim_value DECIMAL(50),
  dim_value1 VARCHAR(256) DEFAULT NULL,
  dim_value2 VARCHAR(256) DEFAULT NULL,
  dim_value3 VARCHAR(256) DEFAULT NULL,
  dim_value4 VARCHAR(256) DEFAULT NULL,
  metric_value double DEFAULT 0,
  updated_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id));


  CREATE TABLE IF NOT EXISTS browser_context
  (id BIGINT AUTO_INCREMENT,
  op_id BIGINT NOT NULL,
  op_type VARCHAR(10) NOT NULL,
  site_id VARCHAR(256) NOT NULL,
  user_id VARCHAR(256) NOT NULL,
  session_id VARCHAR(50) NOT NULL,
  page VARCHAR(500) NULL,
  event VARCHAR(500) NULL,
  browser_name VARCHAR(40) NULL,
  browser_version VARCHAR(20) NULL,
  browser_major VARCHAR(20) NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES users(user_id) ON UPDATE CASCADE,
  FOREIGN KEY (session_id) REFERENCES sessions(session_id) ON DELETE CASCADE,
  FOREIGN KEY (site_id) REFERENCES sites(site_id) ON DELETE CASCADE);

  CREATE INDEX user_ind ON users(user_id);
  CREATE UNIQUE INDEX user_trait_name ON users_custom_traits(site_id, user_id, trait_name);
  CREATE UNIQUE INDEX user_session_hash ON ops(user_id, session_id, hash_code);
  CREATE INDEX metrics_index ON metrics(context,metric_type,date_prefix,dim_name,dim_value);"
)
