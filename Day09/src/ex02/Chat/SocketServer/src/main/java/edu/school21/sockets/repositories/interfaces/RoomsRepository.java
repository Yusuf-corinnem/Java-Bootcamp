package edu.school21.sockets.repositories.interfaces;

import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.Room;

import java.util.List;

public interface RoomsRepository<T> extends CrudRepository<T> {
    List<Message> getLastMessages(Room room, int count);
}
