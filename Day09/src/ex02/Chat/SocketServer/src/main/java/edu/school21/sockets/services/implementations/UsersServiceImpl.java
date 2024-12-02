package edu.school21.sockets.services.implementations;

import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.interfaces.UsersRepository;
import edu.school21.sockets.services.interfaces.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import java.util.Optional;

@Service
public class UsersServiceImpl implements UsersService {
    private final UsersRepository<User> usersRepository;

    @Autowired
    public UsersServiceImpl(UsersRepository<User> usersRepository) {
        this.usersRepository = usersRepository;
    }

    public UsersRepository<User> getUsersRepository() {
        return usersRepository;
    }

    @Override
    public boolean signUp(String name, String password) {
        try {
            usersRepository.save(new User(null, name, password));
        } catch (PersistenceException e) {
            return false;
        }

        return true;
    }

    @Override
    public Optional<User> signIn(String name, String password) {
        Optional<User> userOptional = usersRepository.findByName(name);
        return userOptional.filter(user -> {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
            return encoder.matches(password, user.getPassword());
        });
    }
}
