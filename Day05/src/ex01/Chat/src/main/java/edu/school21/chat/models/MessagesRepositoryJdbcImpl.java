package edu.school21.chat.models;

import edu.school21.chat.repositories.MessagesRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {
    private DataSource dataSource;
    private LinkedList<User> users;

    public MessagesRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Message> findById(Long id) {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            String query = "SELECT *\n" +
                            "FROM messages\n" +
                            "WHERE id = " + id;
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Message message = new Message();
                message.setId(resultSet.getLong("id"));
                message.setAuthor(getAuthorById(resultSet.getLong("author_id"), connection));
                message.setRoom(getChatroomById(resultSet.getLong("room_id"), connection));
                message.setText(resultSet.getString("text"));
                message.setDate(resultSet.getTimestamp("date"));
                return Optional.of(message);
            }

            connection.close();
        } catch (SQLException e) {
            System.out.println("Error while executing the request: " + e.getMessage());
            return Optional.empty();
        }

        return Optional.empty();
    }

    private User getAuthorById(Long id, Connection connection) {
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT *\n" +
                    "FROM users\n" +
                    "WHERE id = " + id;
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                return user;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }

        return null;
    }

    private Chatroom getChatroomById(Long id, Connection connection) {
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT *\n" +
                    "FROM chatrooms\n" +
                    "WHERE id = " + id;
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Chatroom chatroom = new Chatroom();
                chatroom.setId(resultSet.getLong("id"));
                chatroom.setName(resultSet.getString("name"));
                chatroom.setOwner(getAuthorById(resultSet.getLong("owner_id"), connection));
                return chatroom;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }

        return null;
    }

    @Override
    public void delete(Message message) {

    }

    @Override
    public void save(Message message) {

    }

    @Override
    public void update(Message message) {

    }

    @Override
    public List<Message> findAll() {
        return null;
    }
}
