package edu.school21.chat.models;

import edu.school21.chat.generators.MessageIdsGenerator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Message {
    private Long id;
    private User author;
    private Chatroom room;
    private String text;
    private Date date;

    public Message(Long id, User author, Chatroom room, String text, Date date) {
        this.id = MessageIdsGenerator.getInstance().generateId();
        this.author = author;
        this.room = room;
        this.text = text;
        this.date = date;
    }

    public Message() { }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setRoom(Chatroom room) {
        this.room = room;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public User getAuthor() {
        return author;
    }

    public Chatroom getRoom() {
        return room;
    }

    public String getText() {
        return text;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(id, message.id) && Objects.equals(author, message.author) && Objects.equals(room, message.room) && Objects.equals(text, message.text) && Objects.equals(date, message.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, room, text, date);
    }

    @Override
    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return "Message : {" +
                "\n  id=" + id +
                ",\n  author={id=" + author.getId() + ",login=\"" + author.getLogin() + "\"" + ",password=\"" + author.getPassword() + "\"" + ",createdRooms=" + author.getCreatedRooms() + ",rooms=" + author.getChatrooms() +
                "},\n  room={id=" + room.getId() + ",name=\"" + room.getName() + "\"" + ",creator=" + room.getOwner() + ",messages=" + room.getMessages() +
                "},\n  text=\"" + text + "\"" +
                ",\n  dateTime=" + formatter.format(date) +
                "\n}";
    }
}

//                ",\n  dateTime=" + date.getDay() + "/" + date.getMonth() + "/" + date.getYear() + " " + date.getHours() + ":" + date.getMinutes() +
