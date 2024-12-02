package edu.school21.app;

import edu.school21.models.User;
import edu.school21.repositories.HikariCPConfig;
import edu.school21.services.OrmManager;

public class Main {
    public static void main(String[] args) {
        OrmManager ormManager = new OrmManager(HikariCPConfig.HikariCPConfig());

        ormManager.save(new User("Ivan", "Ivanov", 21));
        ormManager.save(new User("Sergey", "Sergeyev", 22));
        User user = new User("Alexander", "Alexandrov", 23);
        ormManager.save(user);

        System.out.println("Save user: " + user.toString());

        User foundUser = ormManager.findById(user.getId(), User.class);
        System.out.println("Found user: " + foundUser.toString());

        user.setAge(20);
        user.setLastName("Petrov");
        ormManager.update(user);

        System.out.println("Update user: " + user.toString());
    }
}
