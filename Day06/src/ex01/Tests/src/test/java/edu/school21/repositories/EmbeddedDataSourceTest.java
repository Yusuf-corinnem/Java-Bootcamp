package edu.school21.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.sql.SQLException;

@SpringJUnitConfig
public class EmbeddedDataSourceTest {
    private EmbeddedDatabase database;

    @BeforeEach
    void init() {
        database = new EmbeddedDatabaseBuilder()
                .setName("Store")
                .setType(EmbeddedDatabaseType.H2)
                .addScript("schema.sql")
                .addScripts("data.sql")
                .build();
    }

    @Test
    public void testDataSourceCreation() throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(database);

        Assertions.assertNotNull(database.getConnection(), "Database connection should not be null");
    }
}
