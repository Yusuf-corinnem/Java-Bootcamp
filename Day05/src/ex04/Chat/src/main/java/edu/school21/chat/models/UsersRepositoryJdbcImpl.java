package edu.school21.chat.models;

import edu.school21.chat.exceptions.NotSavedSubEntityException;
import edu.school21.chat.repositories.UsersRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcImpl implements UsersRepository {
    private DataSource dataSource;

    public UsersRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<User> findById(Long id) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            String query = "SELECT *\n" +
                    "FROM users\n" +
                    "WHERE id = " + id;
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                return Optional.of(user);
            }
        } catch (SQLException e) {
            System.out.println("Error while executing the request: " + e.getMessage());
            return Optional.empty();
        }

        return Optional.empty();
    }

    @Override
    public void delete(User user) {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            String query = "DELETE FROM users\n" +
                    "WHERE id = " + user.getId() + ";";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("Error while executing the request: " + e.getMessage());
            System.exit(-1);
        }
    }

    @Override
    public void save(User user) {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            String query = "INSERT INTO users (id, login, password) VALUES\n" +
                    "(" + user.getId() +
                    ", '" + user.getLogin() +
                    "', '" + user.getPassword() + "');";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("Error while executing the request: " + e.getMessage());
            System.exit(-1);
        }
    }

    @Override
    public void update(User user) {
        if (user.getLogin().length() < 1) {
            System.out.println("login: too short");
            return;
        } else if (user.getPassword().length() < 1) {
            System.out.println("password: too short");
            return;
        }
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            String query = "UPDATE users\n" +
                    "SET login = '" + user.getLogin() +
                    "', password = '" + user.getPassword() +
                    "'\nWHERE id = " + user.getId() + ";";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("Error while executing the request: " + e.getMessage());
            System.exit(-1);
        }
    }

    @Override
    public List<User> findAll(int page, int size) {
        LinkedList<User> users = new LinkedList<>();
        LinkedList<Chatroom> createdChatrooms = new LinkedList<>();
        String query =
                "WITH Created_chats_CTE AS (\n" +
                        "    SELECT chatrooms.id AS created_chat_id,\n" +
                        "           chatrooms.owner_id,\n" +
                        "           chatrooms.name AS created_room_name\n" +
                        "    FROM chatrooms\n" +
                        "    JOIN users ON users.id = chatrooms.owner_id\n" +
                        "),\n" +
                        "Participant_chats_CTE AS (\n" +
                        "    SELECT messages.room_id AS participant_chat_id,\n" +
                        "           messages.author_id,\n" +
                        "           chatrooms.name AS participant_room_name\n" +
                        "    FROM messages\n" +
                        "    JOIN users ON users.id = messages.author_id\n" +
                        "    JOIN chatrooms ON messages.room_id = chatrooms.id\n" +
                        ")\n" +
                        "SELECT users.id,\n" +
                        "       users.login,\n" +
                        "       users.password,\n" +
                        "       created.created_chat_id,\n" +
                        "       created.created_room_name,\n" +
                        "       participant.participant_chat_id,\n" +
                        "       participant.participant_room_name\n" +
                        "FROM users\n" +
                        "LEFT JOIN Created_chats_CTE created ON users.id = created.owner_id\n" +
                        "LEFT JOIN Participant_chats_CTE participant ON users.id = participant.author_id\n" +
                        "ORDER BY users.id ASC\n" +
                        "LIMIT ? OFFSET ?;";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, size); // количество записей на страницу
            statement.setInt(2, page * size); // смещение

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setCreatedRooms(new LinkedList<>());
                user.setChatrooms(new LinkedList<>());

                long createdRoomId = resultSet.getLong("created_chat_id");
                if (createdRoomId > 0) {
                    Chatroom createdRoom = new Chatroom(createdRoomId, resultSet.getString("created_room_name"), user, new LinkedList<>());
                    user.getCreatedRooms().add(createdRoom);
                }

                long participatedRoomId = resultSet.getLong("participant_chat_id");
                if (participatedRoomId > 0) {
                    Chatroom participatedRoom = new Chatroom(participatedRoomId, resultSet.getString("participant_room_name"), null, new LinkedList<>());
                    user.getChatrooms().add(participatedRoom);
                }

                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Error while executing the request: " + e.getMessage());
            System.exit(-1);
        }

        return users;
    }
}
