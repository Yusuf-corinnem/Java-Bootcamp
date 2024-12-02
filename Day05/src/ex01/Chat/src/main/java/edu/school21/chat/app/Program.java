package edu.school21.chat.app;

import edu.school21.chat.models.Message;
import edu.school21.chat.models.MessagesRepositoryJdbcImpl;
import edu.school21.chat.repositories.HikariCPConfig;

import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a message ID");
        MessagesRepositoryJdbcImpl messagesRepositoryJdbc = new MessagesRepositoryJdbcImpl(HikariCPConfig.HikariCPConfig());
        Message message = messagesRepositoryJdbc.findById(scanner.nextLong())
                .orElseThrow(() -> new RuntimeException("Message not found"));
        System.out.println(message.toString());
    }
}