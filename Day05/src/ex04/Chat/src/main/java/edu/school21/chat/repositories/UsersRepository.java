package edu.school21.chat.repositories;

import edu.school21.chat.models.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UsersRepository {
    Optional<User> findById(Long id) throws SQLException;
    void delete(User user);
    void save(User user);
    void update(User user);

    List<User> findAll(int page, int size);
}
