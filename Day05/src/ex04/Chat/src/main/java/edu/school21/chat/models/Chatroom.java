package edu.school21.chat.models;

import edu.school21.chat.models.IdsGenerator;

import java.util.LinkedList;
import java.util.Objects;

public class Chatroom {
    private long id;
    private String name;
    private User owner;
    private LinkedList<Message> messages;

    public Chatroom(long id, String name, User owner, LinkedList<Message> messages) {
//        this.id = IdsGenerator.generateId("chatrooms");
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.messages = messages;
    }

    public Chatroom() { }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void addMessages(Message message) {
        this.messages.add(message);
    }

    public String getName() {
        return name;
    }

    public User getOwner() {
        return owner;
    }

    public LinkedList<Message> getMessages() {
        return messages;
    }

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
                ", owner=" + (owner != null ? owner.getId() : "null") +
                ", messages=" + (messages != null ? messages.size() : 0) +
                '}';
    }
}
