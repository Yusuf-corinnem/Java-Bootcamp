package edu.school21.chat.models;

public class UserIdsGenerator {
    private static UserIdsGenerator instance;
    private static Long id = 0L;

    public static UserIdsGenerator getInstance() {
        if (instance == null) {
            instance = new UserIdsGenerator();
        }
        return instance;
    }

    public static Long generateId() {
        return id++;
    }
}
