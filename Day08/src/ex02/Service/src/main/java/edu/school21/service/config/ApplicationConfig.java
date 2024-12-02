package edu.school21.service.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:db.properties")
@ComponentScan(basePackages = "edu.school21.service")
public class ApplicationConfig {

    @Value("${db.url}")
    private String url;

    @Value("${db.user}")
    private String user;

    @Value("${db.password}")
    private String password;

    @Value("${db.driver.name}")
    private String driverClassName;

    @Bean
    public HikariDataSource hikariCPConfig() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(user);
        hikariConfig.setPassword(password);
        hikariConfig.setDriverClassName(driverClassName);
        return new HikariDataSource(hikariConfig);
    }

    @Bean
    public DataSource driverManagerDataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setUrl(url);
        driverManagerDataSource.setUsername(user);
        driverManagerDataSource.setPassword(password);
        driverManagerDataSource.setDriverClassName(driverClassName);
        return driverManagerDataSource;
    }
}