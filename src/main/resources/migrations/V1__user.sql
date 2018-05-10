CREATE TABLE IF NOT EXISTS users (
  id BIGSERIAL PRIMARY KEY,
  email VARCHAR(255),
  password VARCHAR(255)
);
CREATE UNIQUE INDEX users_username_uindex ON "users" (email);

CREATE TABLE IF NOT EXISTS profile (
  id BIGSERIAL PRIMARY KEY,
  first_name VARCHAR(100),
  last_name VARCHAR(100),
  avatar VARCHAR(255),
  telegram_id VARCHAR(50),
  google_token VARCHAR(50),
  notification_token VARCHAR(50)
);