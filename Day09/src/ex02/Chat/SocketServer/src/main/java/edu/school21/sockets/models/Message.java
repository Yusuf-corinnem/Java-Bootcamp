package edu.school21.sockets.models;

import edu.school21.sockets.repositories.interfaces.RoomsRepository;
import edu.school21.sockets.repositories.interfaces.UsersRepository;
import org.json.JSONObject;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private User sender;
    private String message;
    private Room room;
    private LocalDateTime date;

    public Message() {
    }

    public Message(Long id, User sender, String message, Room room, LocalDateTime date) {
        this.id = id;
        this.sender = sender;
        this.message = message;
        this.room = room;
        this.date = date;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("sender_id", sender.getId());
        json.put("message", message);
        json.put("room_id", room.getId());
        json.put("date", date.toString());
        return json;
    }

    public static Message fromJson(JSONObject json, UsersRepository<User> usersRepository, RoomsRepository<Room> roomsRepository) {
        Long id = json.getLong("id");
        User sender = usersRepository.findById(json.getLong("sender_id")).orElse(null);
        String message = json.getString("message");
        Room room = roomsRepository.findById(json.getLong("room_id")).orElse(null);
        LocalDateTime date = LocalDateTime.parse(json.getString("date"));
        return new Message(id, sender, message, room, date);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
