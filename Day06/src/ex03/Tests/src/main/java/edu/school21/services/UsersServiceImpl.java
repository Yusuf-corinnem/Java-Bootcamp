package edu.school21.services;

import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.exceptions.EntityNotFoundException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;

import org.mockito.Mockito;

public class UsersServiceImpl {
    private UsersRepository usersRepository = Mockito.mock(UsersRepository.class);

    boolean authenticate(String login, String password) {
        User user = null;
        try {
            user = usersRepository.findByLogin(login);
        } catch (EntityNotFoundException e) {
            System.out.println(e.getMessage());
            return false;
        }

        if (user.isAuthenticationSuccessStatus()) {
            throw new AlreadyAuthenticatedException("The user is already authenticated");
        }

        if (!user.getPassword().equals(password)) return false;

        user.setAuthenticationSuccessStatus(true);
        usersRepository.update(user);

        return true;
    }
}
