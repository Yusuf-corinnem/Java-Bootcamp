package edu.school21.repositories;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class HikariCPConfig {
    public static HikariDataSource HikariCPConfig() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:postgresql://localhost:5432/orm");
        hikariConfig.setUsername("postgres");
        hikariConfig.setPassword("1");
        return new HikariDataSource(hikariConfig);
    }
}

