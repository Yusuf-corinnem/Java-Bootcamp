DROP TABLE IF EXISTS users;

CREATE TABLE IF NOT EXISTS users (
  id SERIAL PRIMARY KEY,
  email VARCHAR(255) NOT NULL UNIQUE
 );

INSERT INTO users (email)
VALUES ('email1@icloud.com'),
    ('email2@icloud.com'),
    ('email3@icloud.ru'),
    ('email4@icloud.org'),
    ('email5@icloud.edu');