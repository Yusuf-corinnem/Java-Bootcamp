package edu.school21.chat.app;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.MessagesRepositoryJdbcImpl;
import edu.school21.chat.models.User;
import edu.school21.chat.repositories.HikariCPConfig;

import java.text.ParseException;
import java.util.Date;
import java.util.LinkedList;
import java.util.Optional;

public class Program {
    public static void main(String[] args) throws ParseException {
        MessagesRepositoryJdbcImpl messagesRepositoryJdbc = new MessagesRepositoryJdbcImpl(HikariCPConfig.HikariCPConfig());

        Optional<Message> messageOptional = messagesRepositoryJdbc.findById(3L);
        if (messageOptional.isPresent()) {
            Message message = messageOptional.get();
            message.setText("Bye");
            message.setDate(null);
            messagesRepositoryJdbc.update(message);
        }
    }
}