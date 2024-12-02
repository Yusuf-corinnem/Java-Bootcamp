package edu.school21.service.repositories;

import java.util.Optional;

public interface UsersRepository<T> extends CrudRepository<T> {
    Optional<T> findByEmail(String email);
}
