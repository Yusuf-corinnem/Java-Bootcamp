package edu.school21.chat.app;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.MessagesRepositoryJdbcImpl;
import edu.school21.chat.models.User;
import edu.school21.chat.repositories.HikariCPConfig;

import java.text.ParseException;
import java.util.Date;
import java.util.LinkedList;

public class Program {
    public static void main(String[] args) throws ParseException {
        MessagesRepositoryJdbcImpl messagesRepositoryJdbc = new MessagesRepositoryJdbcImpl(HikariCPConfig.HikariCPConfig());

        User creator = new User(1L, "user1", "pass1", new LinkedList<>(), new LinkedList<>());
        User author = creator;
        Chatroom room = new Chatroom(1L, "General Chat", creator, new LinkedList<>());

        Message message = new Message(null, author, room, "Hello!", new Date());
        messagesRepositoryJdbc.save(message);
        System.out.println("Message id: " + message.getId());
    }
}