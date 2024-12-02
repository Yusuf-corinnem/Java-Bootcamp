DROP SEQUENCE IF EXISTS users_id_seq CASCADE;
DROP SEQUENCE IF EXISTS chatrooms_id_seq CASCADE;
CREATE SEQUENCE users_id_seq;
CREATE SEQUENCE chatrooms_id_seq;

INSERT INTO users (id, login, password) VALUES
(NEXTVAL('users_id_seq'), 'user1', 'pass1'),
(NEXTVAL('users_id_seq'), 'user2', 'pass2'),
(NEXTVAL('users_id_seq'), 'user3', 'pass3'),
(NEXTVAL('users_id_seq'), 'user4', 'pass4'),
(NEXTVAL('users_id_seq'), 'user5', 'pass5'),
(NEXTVAL('users_id_seq'), 'user6', 'pass6'),
(NEXTVAL('users_id_seq'), 'user7', 'pass7'),
(NEXTVAL('users_id_seq'), 'user8', 'pass8'),
(NEXTVAL('users_id_seq'), 'user9', 'pass9'),
(NEXTVAL('users_id_seq'), 'user10', 'pass10'),
(NEXTVAL('users_id_seq'), '11', '11'),
(NEXTVAL('users_id_seq'), '12', '12'),
(NEXTVAL('users_id_seq'), '13', '13'),
(NEXTVAL('users_id_seq'), '14', '14'),
(NEXTVAL('users_id_seq'), '15', '15'),
(NEXTVAL('users_id_seq'), '16', '16'),
(NEXTVAL('users_id_seq'), '17', '17'),
(NEXTVAL('users_id_seq'), '18', '18'),
(NEXTVAL('users_id_seq'), '19', '19'),
(NEXTVAL('users_id_seq'), '20', '20');

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

WITH Created_chats_CTE AS (
                            SELECT chatrooms.id AS created_chat_id,
                                   chatrooms.owner_id,
                                   chatrooms.name AS created_room_name
                            FROM chatrooms
                            JOIN users ON users.id = chatrooms.owner_id
                        ),
                        Participant_chats_CTE AS (
                            SELECT messages.room_id AS participant_chat_id,
                                   messages.author_id,
                                   chatrooms.name AS participant_room_name
                            FROM messages
                            JOIN users ON users.id = messages.author_id
                            JOIN chatrooms ON messages.room_id = chatrooms.id
                        )
                        SELECT users.id,
                               users.login,
                               users.password,
                               created.created_chat_id,
                               created.created_room_name,
                               participant.participant_chat_id,
                               participant.participant_room_name
                        FROM users
                        LEFT JOIN Created_chats_CTE created ON users.id = created.owner_id
                        LEFT JOIN Participant_chats_CTE participant ON users.id = participant.author_id
                        ORDER BY users.id ASC
                        LIMIT 1 OFFSET 4;
