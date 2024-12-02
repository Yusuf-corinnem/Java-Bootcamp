package edu.school21.chat.models;

import java.util.LinkedList;
import java.util.Objects;

public class Chatroom {
    private long id;
    private String name;
    private User owner;
    private LinkedList<Message> messages;

    public Chatroom(long id, String name, User owner, LinkedList<Message> messages) {
        this.id = ChatroomIdsGenerator.getInstance().generateId();
        this.name = name;
        this.owner = owner;
        this.messages = messages;
    }

    public Chatroom() { }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chatroom chatroom = (Chatroom) o;
        return id == chatroom.id && Objects.equals(name, chatroom.name) && Objects.equals(owner, chatroom.owner) && Objects.equals(messages, chatroom.messages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, owner, messages);
    }

    @Override
    public String toString() {
        return "Chatroom{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", owner=" + owner +
                ", messages=" + messages +
                '}';
    }
}
