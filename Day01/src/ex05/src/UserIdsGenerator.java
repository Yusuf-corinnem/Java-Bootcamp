public class UserIdsGenerator {
    private static UserIdsGenerator instance;
    private static Integer id = 1;

    public static UserIdsGenerator getInstance() {
        if (instance == null) {
            instance = new UserIdsGenerator();
        }
        return instance;
    }

    public static Integer generateId() {
        return id++;
    }
}
