DROP SEQUENCE IF EXISTS users_id_seq CASCADE;
DROP SEQUENCE IF EXISTS chatrooms_id_seq CASCADE;
CREATE SEQUENCE users_id_seq;
CREATE SEQUENCE chatrooms_id_seq;

INSERT INTO users (id, login, password) VALUES
(NEXTVAL('users_id_seq'), 'user1', 'pass1'),
(NEXTVAL('users_id_seq'), 'user2', 'pass2'),
(NEXTVAL('users_id_seq'), 'user3', 'pass3'),
(NEXTVAL('users_id_seq'), 'user4', 'pass4'),
(NEXTVAL('users_id_seq'), 'user5', 'pass5');

INSERT INTO chatrooms (id, name, owner_id) VALUES
(NEXTVAL('chatrooms_id_seq'), 'General Chat', 1),
(NEXTVAL('chatrooms_id_seq'), 'Tech Talk', 2),
(NEXTVAL('chatrooms_id_seq'), 'Gaming Zone', 3),
(NEXTVAL('chatrooms_id_seq'), 'Music Lovers', 4),
(NEXTVAL('chatrooms_id_seq'), 'Book Club', 5);

INSERT INTO messages (author_id, room_id, text, date) VALUES
(1, 1, 'Welcome to the General Chat!', NOW()),
(2, 1, 'Hello everyone!', NOW()),
(3, 2, 'What’s the latest in tech?', NOW()),
(4, 3, 'Who’s up for a game tonight?', NOW()),
(5, 4, 'What’s your favorite genre of music?', NOW()),
(1, 2, 'I love discussing new gadgets.', NOW()),
(2, 3, 'I’m playing the latest RPG!', NOW()),
(3, 5, 'I just finished a great book!', NOW()),
(4, 4, 'Any recommendations for new music?', NOW()),
(5, 1, 'Let’s plan a reading session!', NOW());

DELETE FROM messages WHERE id > 10;