package edu.school21.sockets.repositories.implementations;

import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.Room;
import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.interfaces.RoomsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class RoomsRepositoryImpl implements RoomsRepository<Room> {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RoomsRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<Room> findById(Long id) {
        List<Room> rooms = jdbcTemplate.query("SELECT * FROM rooms WHERE id = ?",
                (rs, rowNum) -> new Room(
                        rs.getLong("id"),
                        rs.getString("title"),
                        findUserById(rs.getLong("owner_id"))), id);

        return rooms.isEmpty() ? Optional.empty() : Optional.of(rooms.get(0));
    }

    @Override
    public List<Room> findAll() {
        return jdbcTemplate.query("SELECT * FROM rooms",
                (rs, rowNum) -> new Room(
                        rs.getLong("id"),
                        rs.getString("title"),
                        findUserById(rs.getLong("owner_id"))));
    }

    @Override
    public void save(Room entity) {
        jdbcTemplate.update("INSERT INTO rooms (title, owner_id) VALUES (?, ?)",
                entity.getTitle(), entity.getOwner().getId());
    }

    @Override
    public void update(Room entity) {
        jdbcTemplate.update("UPDATE rooms SET title = ?, owner_id = ? WHERE id = ?",
                entity.getTitle(), entity.getOwner().getId(), entity.getId());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM rooms WHERE id = ?", id);
    }

    @Override
    public List<Message> getLastMessages(Room room, int count) {
        List<Message> messages = jdbcTemplate.query(
                "SELECT * FROM messages WHERE room_id = ? ORDER BY date DESC LIMIT ?",
                new Object[]{room.getId(), count},
                (rs, rowNum) -> new Message(
                        rs.getLong("id"),
                        findUserById(rs.getLong("sender_id")),
                        rs.getString("message"),
                        room,
                        rs.getTimestamp("date") != null ? rs.getTimestamp("date").toLocalDateTime() : null
                )
        );

        // Разворачиваем список, чтобы последние сообщения были снизу
        Collections.reverse(messages);

        return messages;
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

}
