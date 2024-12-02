package edu.school21.sockets.services.implementations;

import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.Room;
import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.interfaces.RoomsRepository;
import edu.school21.sockets.services.interfaces.RoomsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomsServiceImpl implements RoomsService {
    private RoomsRepository<Room> roomsRepository;

    @Autowired
    public RoomsServiceImpl(RoomsRepository<Room> roomsRepository) {
        this.roomsRepository = roomsRepository;
    }

    @Override
    public void createRoom(String title, User owner) {
        roomsRepository.save(new Room(null, title, owner));
    }

    @Override
    public List<Room> getRooms() {
        return roomsRepository.findAll();
    }

    @Override
    public List<Message> getLastMessages(Room room, int count) {
        return roomsRepository.getLastMessages(room, count);
    }
}
