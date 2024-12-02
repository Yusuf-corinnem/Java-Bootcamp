package edu.school21.sockets.server;

import edu.school21.sockets.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

@Component
public class Server {
    private static final Logger logger = Logger.getLogger(Server.class.getName());

    private int port;
    private UsersService usersService;

    @Autowired
    public Server(UsersService usersService, int port) {
        this.usersService = usersService;
        this.port = port;
    }

    @PostConstruct
    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("Server started on port " + port);

            try (Socket clientSocket = serverSocket.accept();
                 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))) {

                logger.info("Client connected: " + clientSocket.getInetAddress());
                out.write("Hello from server\n");
                out.flush();

                if (in.readLine().equals("signUp")) {
                    out.write("Enter username\n");
                    out.flush();

                    String name = in.readLine();

                    out.write("Enter password\n");
                    out.flush();

                    String password = in.readLine();
                    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
                    String bcryptPassword = encoder.encode(password);

                    usersService.signUp(name, bcryptPassword);

                    System.out.println("Client with name: " + name + " is added to DB");
                    out.write("Successful!\n");
                    out.flush();
                }
            }

        } catch (IOException e) {
            logger.severe("Failed to start server: " + e.getMessage());
            throw new RuntimeException("Failed to start server", e);
        }
    }
}