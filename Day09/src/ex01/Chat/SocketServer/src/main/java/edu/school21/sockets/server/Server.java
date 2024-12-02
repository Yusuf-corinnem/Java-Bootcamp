package edu.school21.sockets.server;

import edu.school21.sockets.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

@Component
public class Server {
    private static final Logger logger = Logger.getLogger(Server.class.getName());

    private int port;
    private UsersService usersService;
    private final List<ClientHandler> clients = new LinkedList<>();

    @Autowired
    public Server(UsersService usersService, int port) {
        this.usersService = usersService;
        this.port = port;
    }


    @PostConstruct
    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("Server started on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                logger.info("Client connected: " + clientSocket.getInetAddress());

                ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                clients.add(clientHandler);
                new Thread(clientHandler).start();
            }

        } catch (IOException e) {
            logger.severe("Failed to start server: " + e.getMessage());
            throw new RuntimeException("Failed to start server", e);
        }
    }

    public UsersService getUsersService() {
        return usersService;
    }

    public List<ClientHandler> getClients() {
        return clients;
    }
}
