package edu.school21.sockets.services.interfaces;

import edu.school21.sockets.models.User;

import java.util.Optional;

public interface UsersService {
    boolean signUp(String name, String password);

    Optional<User> signIn(String name, String password);
}