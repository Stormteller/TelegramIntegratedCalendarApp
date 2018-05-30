CREATE TYPE TO_DO_LIST_TYPE AS ENUM ('GLOBAL', 'DAILY', 'EVENT_BASED');

CREATE TABLE IF NOT EXISTS to_do_list(
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(100),
  for_day DATE,
  event_id BIGINT,
  type TO_DO_LIST_TYPE NOT NULL,
  creator_id BIGINT NOT NULL,

  FOREIGN KEY (event_id) REFERENCES "event" (id)
  MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE,

  FOREIGN KEY (creator_id) REFERENCES "users" (id)
  MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS to_do_item (
  id BIGSERIAL PRIMARY KEY,
  is_done BOOLEAN DEFAULT FALSE,
  text VARCHAR(255) NOT NULL ,
  to_do_list_id BIGINT NOT NULL,
  created_at TIMESTAMP DEFAULT NOW(),

  FOREIGN KEY (to_do_list_id) REFERENCES "to_do_list" (id)
  MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE
);