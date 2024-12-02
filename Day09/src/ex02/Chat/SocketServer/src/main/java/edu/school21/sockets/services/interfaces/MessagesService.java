package edu.school21.sockets.services.interfaces;

import edu.school21.sockets.models.Room;
import edu.school21.sockets.models.User;

public interface MessagesService {
    void sendMessage(User user, String message, Room room);
}
