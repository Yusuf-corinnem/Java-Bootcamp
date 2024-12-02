package edu.school21.sockets.repositories.implementations;

import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.Room;
import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.interfaces.MessagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class MessagesRepositoryImpl implements MessagesRepository<Message> {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public MessagesRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Message> getMessagesByUserId(Long id) {
        List<Message> messages = jdbcTemplate.query("SELECT * FROM messages WHERE id = ?",
                (rs, rowNum) -> new Message(
                        rs.getLong("id"),
                        findUserById(rs.getLong("sender_id")),
                        rs.getString("message"),
                        findRoomById(rs.getLong("room_id")),
                        rs.getTimestamp("date") != null ? rs.getTimestamp("date").toLocalDateTime() : null), id);
        return messages;
    }

    @Override
    public Optional findById(Long id) {
        List<Message> messages = jdbcTemplate.query("SELECT * FROM messages WHERE id = ?",
                (rs, rowNum) -> new Message(
                        rs.getLong("id"),
                        findUserById(rs.getLong("sender_id")),
                        rs.getString("message"),
                        findRoomById(rs.getLong("room_id")),
                        rs.getTimestamp("date") != null ? rs.getTimestamp("date").toLocalDateTime() : null), id);

        return messages.isEmpty() ? Optional.empty() : Optional.of(messages.get(0));
    }

    @Override
    public List findAll() {
        return jdbcTemplate.query("SELECT * FROM messages",
                (rs, rowNum) -> new Message(
                        rs.getLong("id"),
                        findUserById(rs.getLong("sender_id")),
                        rs.getString("message"),
                        findRoomById(rs.getLong("room_id")),
                        rs.getTimestamp("date") != null ? rs.getTimestamp("date").toLocalDateTime() : null));
    }

    @Override
    public void save(Message entity) {
        jdbcTemplate.update("INSERT INTO messages (sender_id, message, room_id) VALUES (?, ?, ?)",
                entity.getSender().getId(), entity.getMessage(), entity.getRoom().getId());
    }

    @Override
    public void update(Message entity) {
        jdbcTemplate.update("UPDATE messages SET sender_id = ?, message = ?, room_id = ? WHERE id = ?",
                entity.getSender().getId(), entity.getMessage(), entity.getRoom().getId(), entity.getId());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM messages WHERE id = ?", id);
    }

    private User findUserById(Long id) {
        User user = jdbcTemplate.queryForObject("SELECT * FROM users WHERE id = ?",
                (rs, rowNum) -> new User(rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("password")), id);

        if (user == null) {
            throw new NullPointerException("User with id " + id + " not found");
        }

        return user;
    }

    private Room findRoomById(Long id) {
        Room room = jdbcTemplate.queryForObject("SELECT * FROM rooms WHERE id = ?",
                (rs, rowNum) -> new Room(rs.getLong("id"),
                        rs.getString("title"),
                        findUserById(rs.getLong("owner_id"))), id);

        if (room == null) {
            throw new NullPointerException("User with id " + id + " not found");
        }

        return room;
    }
}
