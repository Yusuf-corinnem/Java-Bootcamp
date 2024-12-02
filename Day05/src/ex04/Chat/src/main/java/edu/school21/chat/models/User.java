package edu.school21.chat.models;

//import edu.school21.chat.models.IdsGenerator;

import java.util.LinkedList;
import java.util.Objects;

public class User {
    private Long id;
    private String login;
    private String password;
    private LinkedList<Chatroom> createdRooms;
    private LinkedList<Chatroom> chatrooms;

    public User(Long id, String login, String password, LinkedList<Chatroom> createdRooms, LinkedList<Chatroom> chatrooms) {
//        this.id = IdsGenerator.generateId("users");
        this.id = id;
        this.login = login;
        this.password = password;
        this.createdRooms = createdRooms;
        this.chatrooms = chatrooms;
    }

    public User() { }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public LinkedList<Chatroom> getCreatedRooms() {
        return createdRooms;
    }

    public LinkedList<Chatroom> getChatrooms() {
        return chatrooms;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCreatedRooms(LinkedList<Chatroom> createdRooms) {
        this.createdRooms = createdRooms;
    }

    public void setChatrooms(LinkedList<Chatroom> chatrooms) {
        this.chatrooms = chatrooms;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(login, user.login) && Objects.equals(password, user.password) && Objects.equals(createdRooms, user.createdRooms) && Objects.equals(chatrooms, user.chatrooms);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, createdRooms, chatrooms);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("User{");
        sb.append("id=").append(id)
                .append(", login=\"").append(login).append("\"")
                .append(", password=\"").append(password).append("\"")
                .append(", createdRooms=").append(createdRooms != null ? createdRooms : "[]")
                .append(", chatrooms=").append(chatrooms != null ? chatrooms : "[]")
                .append('}');
        return sb.toString();
    }
}
