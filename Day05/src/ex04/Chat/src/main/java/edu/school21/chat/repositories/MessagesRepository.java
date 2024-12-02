package edu.school21.chat.repositories;

import edu.school21.chat.models.Message;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface MessagesRepository {
    Optional<Message> findById(Long id) throws SQLException;
    void delete(Message message);
    void save(Message message);
    void update(Message message);

    List<Message> findAll(int page, int size);
}
