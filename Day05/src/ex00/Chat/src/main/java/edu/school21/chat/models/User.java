package edu.school21.chat.models;

import java.util.LinkedList;
import java.util.Objects;

public class User {
    private Long id;
    private String login;
    private String password;
    private LinkedList<Chatroom> createdRooms;
    private LinkedList<Chatroom> chatrooms;

    public User(Long id, String login, String password, LinkedList<Chatroom> createdRooms, LinkedList<Chatroom> chatrooms) {
        this.id = UserIdsGenerator.getInstance().generateId();
        this.login = login;
        this.password = password;
        this.createdRooms = createdRooms;
        this.chatrooms = chatrooms;
    }

    public User() { }

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
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", createdRooms=" + createdRooms +
                ", chatrooms=" + chatrooms +
                '}';
    }
}
