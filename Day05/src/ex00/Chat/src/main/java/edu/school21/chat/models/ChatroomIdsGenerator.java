package edu.school21.chat.models;

public class ChatroomIdsGenerator {
    private static ChatroomIdsGenerator instance;
    private static Long id = 0L;

    public static ChatroomIdsGenerator getInstance() {
        if (instance == null) {
            instance = new ChatroomIdsGenerator();
        }
        return instance;
    }

    public static Long generateId() {
        return id++;
    }
}
