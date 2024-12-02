package edu.school21.sockets.services.interfaces;

import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.Room;
import edu.school21.sockets.models.User;

import java.util.List;

public interface RoomsService {
    void createRoom(String title, User owner);

    List<Room> getRooms();

    List<Message> getLastMessages(Room room, int count);
}
