package edu.school21.service.repositories;

import edu.school21.service.model.User;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcTemplateImpl implements UsersRepository<User> {
    private final JdbcTemplate jdbcTemplate;

    public UsersRepositoryJdbcTemplateImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<User> findById(Long id) {
        List<User> products = jdbcTemplate.query("SELECT *\nFROM users\nWHERE id = ?;",
                (rs, rowNum) -> new User(rs.getLong("id"), rs.getString("email")), id);

        if (!products.isEmpty()) {
            return Optional.of(products.get(0));
        }

        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("SELECT *\nFROM users;",
                (rs, rowNum) -> new User(rs.getLong("id"), rs.getString("email")));
    }

    @Override
    public void save(User entity) {
        jdbcTemplate.update("INSERT INTO products (id, email) VALUES (?, ?)",
                entity.getId(), entity.getEmail());
    }

    @Override
    public void update(User entity) {
        jdbcTemplate.update("UPDATE users\nSET email = ?\nWHERE id = ?;",
                entity.getEmail(), entity.getId());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM users\nWHERE id = ?;", id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        List<User> products = jdbcTemplate.query("SELECT *\nFROM users\nWHERE email = ?;",
                (rs, rowNum) -> new User(rs.getLong("id"), rs.getString("email")), email);

        if (!products.isEmpty()) {
            return Optional.of(products.get(0));
        }

        return Optional.empty();
    }
}
