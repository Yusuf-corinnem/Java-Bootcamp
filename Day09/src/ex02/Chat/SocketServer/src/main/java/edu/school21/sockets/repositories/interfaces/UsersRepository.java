package edu.school21.sockets.repositories.interfaces;

import java.util.Optional;

public interface UsersRepository<T> extends CrudRepository<T> {
    Optional<T> findByName(String name);
}
