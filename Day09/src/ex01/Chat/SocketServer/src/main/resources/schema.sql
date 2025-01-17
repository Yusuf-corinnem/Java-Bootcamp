DROP TABLE IF EXISTS users CASCADE;

CREATE TABLE IF NOT EXISTS users (
  id SERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL UNIQUE
 );

 DROP TABLE IF EXISTS messages;

 CREATE TABLE IF NOT EXISTS messages (
   id SERIAL PRIMARY KEY,
   sender_id BIGINT,
   message VARCHAR(255) NOT NULL,
   date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   CONSTRAINT fk_sender FOREIGN KEY(sender_id) REFERENCES users(id)
  );