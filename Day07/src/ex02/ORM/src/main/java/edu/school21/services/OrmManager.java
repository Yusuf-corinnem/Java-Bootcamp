package edu.school21.services;

import edu.school21.annotations.*;
import org.reflections.Reflections;
import org.springframework.jdbc.core.JdbcTemplate;

import edu.school21.repositories.OrmRepository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class OrmManager implements OrmRepository {
    private JdbcTemplate jdbcTemplate;
    private String tableName;

    public OrmManager(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        dropTables();
        createTable();
    }

    private void dropTables() {
        Reflections reflections = new Reflections("edu.school21.models");
        Set<Class<?>> elements = reflections.getTypesAnnotatedWith(OrmEntity.class);

        for (Class<?> element : elements) {
            OrmEntity ormEntity = element.getAnnotation(OrmEntity.class);
            String tableName = ormEntity.table();
            String query = "DROP TABLE IF EXISTS " + tableName + " CASCADE;";
            jdbcTemplate.execute(query);
        }
    }

    private void createTable() {
        Reflections reflections = new Reflections("edu.school21.models");
        Set<Class<?>> elements = reflections.getTypesAnnotatedWith(OrmEntity.class);

        for (Class<?> element : elements) {
            OrmEntity ormEntity = element.getAnnotation(OrmEntity.class);
            tableName = ormEntity.table();
            StringBuilder query = new StringBuilder("CREATE TABLE IF NOT EXISTS ").append(tableName).append(" (\n");

            for (Field field : element.getDeclaredFields()) {
                if (field.isAnnotationPresent(OrmColumnId.class)) {
                    query.append("id SERIAL PRIMARY KEY,\n");
                } else if (field.isAnnotationPresent(OrmColumn.class)) {
                    OrmColumn ormColumn = field.getAnnotation(OrmColumn.class);
                    String columnType = getColumnType(field.getType());
                    String columnName = ormColumn.name();
                    int columnLength = ormColumn.length();
                    query.append(columnName).append(" ").append(columnType);

                    if (columnType.equals("VARCHAR") && columnLength > 0) {
                        query.append("(").append(columnLength).append(")");
                    }

                    query.append(",\n");
                }
            }

            query.setLength(query.length() - 2);
            query.append("\n);");
            jdbcTemplate.execute(query.toString());
        }
    }


    @Override
    public void save(Object entity) {
        if (!entity.getClass().isAnnotationPresent(OrmEntity.class)) {
            System.err.println("Entity is not annotated!");
            return;
        }

        StringBuilder query = new StringBuilder("INSERT INTO ").append(tableName);
        List<String> columnNames = new ArrayList<>();
        List<Object> columnValues = new ArrayList<>();

        for (Field field : entity.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(OrmColumn.class)) {
                OrmColumn ormColumn = field.getAnnotation(OrmColumn.class);
                columnNames.add(ormColumn.name());

                field.setAccessible(true);

                try {
                    Object value = field.get(entity);
                    columnValues.add(value);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }

                field.setAccessible(false);
            }
        }

        if (columnNames.isEmpty()) {
            System.err.println("No columns to insert!");
            return;
        }

        query.append(" (").append(String.join(", ", columnNames)).append(")\n");
        query.append("VALUES (").append(columnNames.stream().map(s -> "?").collect(Collectors.joining(", "))).append(");");

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(query.toString(), new String[]{"id"});

            for (int i = 0; i < columnValues.size(); i++) {
                ps.setObject(i + 1, columnValues.get(i));
            }

            return ps;
        }, keyHolder);

        // Обновляем ID в объекте
        if (keyHolder.getKey() != null) {
            try {
                Field idField = entity.getClass().getDeclaredField("id");
                idField.setAccessible(true);
                idField.set(entity, keyHolder.getKey().longValue());
                idField.setAccessible(false);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }


    @Override
    public void update(Object entity) {
        if (!entity.getClass().isAnnotationPresent(OrmEntity.class)) {
            System.err.println("Entity is not annotated!");
            return;
        }

        StringBuilder query = new StringBuilder("UPDATE ").append(tableName).append(" SET ");
        List<String> columnNames = new ArrayList<>();
        List<String> columnValues = new ArrayList<>();
        Long id = null;
        String idColumnName = "id";

        for (Field field : entity.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(OrmColumnId.class)) {
                field.setAccessible(true);
                OrmColumnId ormColumnId = field.getAnnotation(OrmColumnId.class);
                idColumnName = ormColumnId.name();
                try {
                    id = (Long) field.get(entity);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }

                field.setAccessible(false);
            } else if (field.isAnnotationPresent(OrmColumn.class)) {
                OrmColumn ormColumn = field.getAnnotation(OrmColumn.class);
                columnNames.add(ormColumn.name());

                field.setAccessible(true);

                try {
                    Object value = field.get(entity);
                    if (getColumnType(field.getType()).equals("VARCHAR")) {
                        columnValues.add("'" + value.toString() + "'");
                    } else {
                        columnValues.add(value.toString());
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }

                field.setAccessible(false);
            }
        }

        if (id == null) {
            throw new IllegalStateException("Entity does not have an id field!");
        }

        for (int i = 0; i < columnNames.size(); i++) {
            query.append(columnNames.get(i)).append(" = ").append(columnValues.get(i));
            if (i < columnNames.size() - 1) {
                query.append(", ");
            }
        }

        query.append(" WHERE ").append(idColumnName).append(" = ").append(id);
        jdbcTemplate.execute(query.toString());
    }

    @Override
    public <T> T findById(Long id, Class<T> aClass) {
        if (!aClass.isAnnotationPresent(OrmEntity.class)) {
            System.err.println("Entity is not annotated!");
            return null;
        }

        OrmEntity ormEntity = aClass.getAnnotation(OrmEntity.class);
        String tableName = ormEntity.table();

        return jdbcTemplate.queryForObject("SELECT *" + "\nFROM " + tableName + "\nWHERE id = ?", new Object[]{id}, (rs, rowNum) -> {
            try {
                T instance = aClass.getDeclaredConstructor().newInstance();

                for (Field field : aClass.getDeclaredFields()) {
                    if (field.isAnnotationPresent(OrmColumn.class)) {
                        OrmColumn ormColumn = field.getAnnotation(OrmColumn.class);

                        field.setAccessible(true);
                        Object value = rs.getObject(ormColumn.name(), field.getType());
                        field.set(instance, value);
                        field.setAccessible(false);
                    } else if (field.isAnnotationPresent(OrmColumnId.class)) {
                        OrmColumnId ormColumnId = field.getAnnotation(OrmColumnId.class);

                        field.setAccessible(true);
                        // Явное преобразование int4 в Long
                        Long value = rs.getLong(ormColumnId.name());
                        field.set(instance, value);
                        field.setAccessible(false);
                    }
                }

                return instance;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private String getColumnType(Class<?> fieldType) {
        if (fieldType == String.class) {
            return "VARCHAR";
        } else if (fieldType == Integer.class || fieldType == int.class) {
            return "INT";
        } else if (fieldType == Double.class || fieldType == double.class) {
            return "DOUBLE";
        } else if (fieldType == Boolean.class || fieldType == boolean.class) {
            return "BOOLEAN";
        } else if (fieldType == Long.class || fieldType == long.class) {
            return "BIGINT";
        } else {
            throw new IllegalArgumentException("Unsupported field type: " + fieldType.getName());
        }
    }
}
