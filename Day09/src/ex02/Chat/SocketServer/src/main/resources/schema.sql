DROP TABLE IF EXISTS users CASCADE;

CREATE TABLE IF NOT EXISTS users (
  id SERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL UNIQUE
 );

  DROP TABLE IF EXISTS rooms;

   CREATE TABLE IF NOT EXISTS rooms (
     id SERIAL PRIMARY KEY,
     title VARCHAR(255) NOT NULL UNIQUE,
     owner_id BIGINT,
     CONSTRAINT fk_owner FOREIGN KEY(owner_id) REFERENCES users(id)
    );

 DROP TABLE IF EXISTS messages;

 CREATE TABLE IF NOT EXISTS messages (
   id SERIAL PRIMARY KEY,
   sender_id BIGINT,
   message VARCHAR(255) NOT NULL,
   room_id BIGINT,
   date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   CONSTRAINT fk_sender FOREIGN KEY(sender_id) REFERENCES users(id),
   CONSTRAINT fk_room FOREIGN KEY(room_id) REFERENCES rooms(id)
  );
