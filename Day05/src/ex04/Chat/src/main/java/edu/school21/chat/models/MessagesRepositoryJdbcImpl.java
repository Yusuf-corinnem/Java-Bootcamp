package edu.school21.chat.models;

import edu.school21.chat.exceptions.NotSavedSubEntityException;
import edu.school21.chat.repositories.MessagesRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {
    private DataSource dataSource;

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
        } catch (SQLException e) {
            System.out.println("Error while executing the request: " + e.getMessage());
            return Optional.empty();
        }

        return Optional.empty();
    }

    @Override
    public void delete(Message message) {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            String query = "DELETE FROM messages\n" +
                    "WHERE id = " + message.getId() + ";";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("Error while executing the request: " + e.getMessage());
            System.exit(-1);
        }
    }

    @Override
    public void save(Message message) {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            if (message.getAuthor().getId() == 0) {
                throw new NotSavedSubEntityException("Author ID is zero");
            } else if (getAuthorById(message.getAuthor().getId(), connection) == null) {
                throw new NotSavedSubEntityException("Author not found");
            } else if (message.getRoom().getId() == 0) {
                throw new NotSavedSubEntityException("Room ID is zero");
            } else if (getChatroomById(message.getRoom().getId(), connection) == null) {
                throw new NotSavedSubEntityException("Room not found");
            }


            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String query = "INSERT INTO messages (id, author_id, room_id, text, date) VALUES\n" +
                    "(" + message.getId() +
                    ", " + message.getAuthor().getId() +
                    ", " + message.getRoom().getId() +
                    ", '" + message.getText() + "', '" + formatter.format(message.getDate()) + "');";
            message.getRoom().addMessages(message);
            statement.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("Error while executing the request: " + e.getMessage());
            System.exit(-1);
        } catch (NotSavedSubEntityException e) {
            System.out.println("Error: " + e.getMessage());
            System.exit(-1);
        }
    }

    @Override
    public void update(Message message) {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            String date = "";
            if (message.getDate() == null) date = "NULL";
            else {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                date = "'" + formatter.format(message.getDate()) + "'";
            }

            String query = "UPDATE messages\n" +
                    "SET author_id = " + message.getAuthor().getId() +
                    ", room_id = " + message.getRoom().getId() +
                    ", text = '" + message.getText() +
                    "', date = " + date +
                    "\nWHERE id = " + message.getId() + ";";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("Error while executing the request: " + e.getMessage());
            System.exit(-1);
        }
    }

    @Override
    public List<Message> findAll(int page, int size) {
        LinkedList<Message> messages = new LinkedList<>();
        String query = "SELECT * FROM messages LIMIT ? OFFSET ?;";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, size);
            statement.setInt(2, page * size);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Message message = new Message();
                message.setId(resultSet.getLong("id"));
                message.setAuthor(getAuthorById(resultSet.getLong("author_id"), connection));
                message.setRoom(getChatroomById(resultSet.getLong("room_id"), connection));
                message.setText(resultSet.getString("text"));
                message.setDate(resultSet.getTimestamp("date"));
                messages.add(message);
            }
        } catch (SQLException e) {
            System.out.println("Error while executing the request: " + e.getMessage());
            System.exit(-1);
        }

        return messages;
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


}
