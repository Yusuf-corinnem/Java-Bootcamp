package edu.school21.sockets.repositories;

import edu.school21.sockets.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class UsersRepositoryImpl implements UsersRepository<User> {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UsersRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<User> findById(Long id) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE id = ?",
                (rs, rowNum) -> new User(rs.getLong("id"), rs.getString("name"), rs.getString("password")), id);

        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }

    @Override
    public Optional<User> findByName(String name) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE name = ?",
                (rs, rowNum) -> new User(rs.getLong("id"), rs.getString("name"), rs.getString("password")), name);

        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("SELECT * FROM users",
                (rs, rowNum) -> new User(rs.getLong("id"), rs.getString("name"), rs.getString("password")));
    }

    @Override
    public void save(User entity) {
        jdbcTemplate.update("INSERT INTO users (name, password) VALUES (?, ?)",
                entity.getName(), entity.getPassword());
    }

    @Override
    public void update(User entity) {
        jdbcTemplate.update("UPDATE users SET name = ?, password = ? WHERE id = ?",
                entity.getName(), entity.getPassword(), entity.getId());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM users WHERE id = ?", id);
    }
}