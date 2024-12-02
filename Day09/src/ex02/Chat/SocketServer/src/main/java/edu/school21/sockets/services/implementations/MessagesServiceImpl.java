package edu.school21.sockets.services.implementations;

import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.Room;
import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.interfaces.MessagesRepository;
import edu.school21.sockets.services.interfaces.MessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MessagesServiceImpl implements MessagesService {
    private MessagesRepository<Message> messagesRepository;

    @Autowired
    public MessagesServiceImpl(MessagesRepository<Message> messagesRepository) {
        this.messagesRepository = messagesRepository;
    }

    @Override
    public void sendMessage(User user, String message, Room room) {
        if (user == null || user.getId() == null) {
            throw new IllegalArgumentException("User and user ID must not be null");
        }

        messagesRepository.save(new Message(null, user, message, room, LocalDateTime.now()));
    }
}
