package edu.school21.sockets.server;

import edu.school21.sockets.models.User;
import edu.school21.sockets.services.UsersService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Optional;

public class ClientHandler implements Runnable {
    private List<ClientHandler> clients;
    private Socket clientSocket;
    private UsersService usersService;
    private BufferedReader in;
    private BufferedWriter out;

    public ClientHandler(Socket socket, Server server) {
        this.clientSocket = socket;
        this.usersService = server.getUsersService();
        clients = server.getClients();

        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            out.write("Hello from Server!\n");
            out.flush();

            while (!clientSocket.isClosed()) {
                String methodName = in.readLine();

                if(methodName == null) break; // Если поток закрыт, выходим из цикла

                if (methodName.equals("signUp")) {
                    processSignUp();
                } else if (methodName.equals("signIn")) {
                    processSignIn();
                } else {
                    out.write("Unknown methodName\n");
                    out.flush();
                    clientSocket.close();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    private void processSignUp() throws IOException {
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

    private void processSignIn() throws IOException {
        out.write("Enter username:\n");
        out.flush();
        String name = in.readLine();

        out.write("Enter password:\n");
        out.flush();
        String password = in.readLine();

        Optional<User> userOptional = usersService.signIn(name, password);
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            broadcastMessage(user.getName() + " has joined the chat\n");
            out.write("Start messaging\n");
            out.flush();

            String clientMessage;
            while ((clientMessage = in.readLine()) != null) {
                if (clientMessage.equals("Exit")) {
                    broadcastMessage(user.getName() + " left from chat\n");
                    close();
                    break;
                }

                usersService.sendMessage(user, clientMessage);
                broadcastMessage(user.getName() + ": " + clientMessage + "\n");
            }
        } else {
            out.write("Authentication failed. Closing connection.\n");
            out.flush();
            close();
        }
    }

    private void broadcastMessage(String message) throws IOException {
        synchronized (clients) {
            for (ClientHandler ch : clients) {
                ch.out.write(message);
                ch.out.flush();
            }
        }
    }


    private void close() {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (clientSocket != null && !clientSocket.isClosed()) clientSocket.close();
            clients.remove(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}