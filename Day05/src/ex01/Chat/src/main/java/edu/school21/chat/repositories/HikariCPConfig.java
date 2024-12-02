package edu.school21.chat.repositories;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class HikariCPConfig {
    public static HikariDataSource HikariCPConfig() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:postgresql://localhost:5432/chat");
        hikariConfig.setUsername("postgres");
        hikariConfig.setPassword("1");
        return new HikariDataSource(hikariConfig);
    }
}
