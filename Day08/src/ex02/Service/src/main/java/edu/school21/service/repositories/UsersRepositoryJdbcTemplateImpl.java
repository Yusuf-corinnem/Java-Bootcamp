package edu.school21.service.repositories;

import edu.school21.service.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class UsersRepositoryJdbcTemplateImpl implements UsersRepository<User> {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UsersRepositoryJdbcTemplateImpl(@Qualifier("hikariCPConfig") DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<User> findById(Long id) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE id = ?",
                (rs, rowNum) -> new User(rs.getLong("id"), rs.getString("email"), rs.getString("password")), id);

        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("SELECT * FROM users",
                (rs, rowNum) -> new User(rs.getLong("id"), rs.getString("email"), rs.getString("password")));
    }

    @Override
    public void save(User entity) {
        jdbcTemplate.update("INSERT INTO users (email, password) VALUES (?, ?)",
                entity.getEmail(), entity.getPassword());
    }

    @Override
    public void update(User entity) {
        jdbcTemplate.update("UPDATE users SET email = ?, password = ? WHERE id = ?",
                entity.getEmail(), entity.getPassword(), entity.getId());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM users WHERE id = ?", id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE email = ?",
                (rs, rowNum) -> new User(rs.getLong("id"), rs.getString("email"), rs.getString("password")), email);

        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }
}