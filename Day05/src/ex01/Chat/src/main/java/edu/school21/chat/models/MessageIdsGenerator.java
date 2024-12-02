package edu.school21.chat.models;

public class MessageIdsGenerator {
    private static MessageIdsGenerator instance;
    private static Long id = 0L;

    public static MessageIdsGenerator getInstance() {
        if (instance == null) {
            instance = new MessageIdsGenerator();
        }
        return instance;
    }

    public static Long generateId() {
        return id++;
    }
}
