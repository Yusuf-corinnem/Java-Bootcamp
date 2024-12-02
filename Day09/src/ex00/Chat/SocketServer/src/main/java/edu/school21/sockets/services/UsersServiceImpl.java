package edu.school21.sockets.services;

import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;

@Service
public class UsersServiceImpl implements UsersService {
    private final UsersRepository<User> usersRepository;

    @Autowired
    public UsersServiceImpl(UsersRepository<User> usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public boolean signUp(String name, String password) {
        try {
            usersRepository.save(new User(name, password));
        } catch (PersistenceException e) {
            return false;
        }

        return true;
    }
}
