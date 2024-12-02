package edu.school21.chat.models;

import edu.school21.chat.repositories.HikariCPConfig;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class IdsGenerator {
    public static Long generateId(String table) {
        try (Connection connection = HikariCPConfig.HikariCPConfig().getConnection();
             Statement statement = connection.createStatement()) {
            String query = "SELECT MAX(id)\n" +
                    "FROM " + table + ";";
            ResultSet resultSet = statement.executeQuery(query);

            if (!resultSet.next()) return 0L;

            return resultSet.getLong(1) + 1L;
        } catch (SQLException e) {
            System.out.println("Error while executing the request: " + e.getMessage());
            System.exit(-1);
        }

        return 0L;
    }
}
