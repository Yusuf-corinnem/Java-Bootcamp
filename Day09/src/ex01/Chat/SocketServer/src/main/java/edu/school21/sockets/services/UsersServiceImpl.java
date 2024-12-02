package edu.school21.sockets.services;

import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.MessagesRepository;
import edu.school21.sockets.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UsersServiceImpl implements UsersService {
    private final UsersRepository<User> usersRepository;
    private final MessagesRepository<Message> messagesRepository;

    @Autowired
    public UsersServiceImpl(UsersRepository<User> usersRepository, MessagesRepository<Message> messagesRepository) {
        this.usersRepository = usersRepository;
        this.messagesRepository = messagesRepository;
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

    @Override
    public void sendMessage(User user, String message) {
        if (user == null || user.getId() == null) {
            throw new IllegalArgumentException("User and user ID must not be null");
        }

        messagesRepository.save(new Message(user, message, LocalDateTime.now()));
    }
}
