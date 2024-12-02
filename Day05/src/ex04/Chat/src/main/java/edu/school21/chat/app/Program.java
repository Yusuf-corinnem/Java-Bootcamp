package edu.school21.chat.app;

import edu.school21.chat.models.*;
import edu.school21.chat.repositories.HikariCPConfig;

import java.text.ParseException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class Program {
    public static void main(String[] args) throws ParseException {
        UsersRepositoryJdbcImpl usersRepositoryJdbc = new UsersRepositoryJdbcImpl(HikariCPConfig.HikariCPConfig());
        List<User> users = usersRepositoryJdbc.findAll(1, 4);

        for (int i = 0; i < users.size(); ++i) {
            System.out.println(users.get(i).toString());
        }
    }
}