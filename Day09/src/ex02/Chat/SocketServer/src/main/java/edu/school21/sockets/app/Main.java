package edu.school21.sockets.app;

import edu.school21.sockets.config.SocketsApplicationConfig;
import edu.school21.sockets.server.Server;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1 || !args[0].startsWith("--port=")) {
            System.out.println("Usage: java -jar target/socket-server.jar --port=8081");
            return;
        }

        int port = Integer.parseInt(args[0].substring("--port=".length()));
        System.out.println("PORT: " + port);

        // Update the properties file with the new port value
        updatePropertiesFile("db.properties", "server.port", String.valueOf(port));

        ApplicationContext context = new AnnotationConfigApplicationContext(SocketsApplicationConfig.class);
        Server server = context.getBean(Server.class);
        server.start();
    }

    private static void updatePropertiesFile(String filePath, String key, String value) {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(filePath)) {
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }

        properties.setProperty(key, value);

        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            properties.store(fos, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}