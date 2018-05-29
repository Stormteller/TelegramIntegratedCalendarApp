CREATE TYPE RECURRING_TYPE AS ENUM ('DAILY', 'WEEKLY', 'MONTHLY', 'YEARLY');
CREATE TYPE WEEK_DAYS AS ENUM ('MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY', 'SUNDAY');

CREATE TABLE IF NOT EXISTS recurring_rule (
  id BIGSERIAL PRIMARY KEY,
  week_days WEEK_DAYS[],
  recurring_type RECURRING_TYPE NOT NULL,
  interval INT NOT NULL
);

CREATE TABLE IF NOT EXISTS event (
  id BIGSERIAL PRIMARY KEY,
  title VARCHAR(255) NOT NULL,
  start_at TIMESTAMP,
  finish_at TIMESTAMP,
  location_latitude DOUBLE PRECISION,
  location_longitude DOUBLE PRECISION,
  location_address VARCHAR(255),
  location_title VARCHAR(255),
  creator_id BIGINT NOT NULL,
  description TEXT,
  is_public BOOLEAN DEFAULT TRUE ,
  is_recurring BOOLEAN DEFAULT FALSE,
  is_whole_day BOOLEAN DEFAULT FALSE,
  recurring_rule_id BIGINT,
  created_at TIMESTAMP DEFAULT NOW(),

  FOREIGN KEY (recurring_rule_id) REFERENCES "recurring_rule" (id)
  MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE,

  FOREIGN KEY (creator_id) REFERENCES "users" (id)
  MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS event_participant (
  id BIGSERIAL PRIMARY KEY,
  user_id BIGINT,
  event_id BIGINT,
  created_at TIMESTAMP DEFAULT NOW(),

  FOREIGN KEY (user_id) REFERENCES "users" (id)
  MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE,

  FOREIGN KEY (event_id) REFERENCES "event" (id)
  MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS reminder (
  id BIGSERIAL PRIMARY KEY,
  event_id BIGINT,
  reminding_time TIMESTAMP DEFAULT NOW(),
  message VARCHAR(255) NOT NULL,
  is_sent BOOLEAN DEFAULT FALSE,

  FOREIGN KEY (event_id) REFERENCES "event" (id)
  MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE
);