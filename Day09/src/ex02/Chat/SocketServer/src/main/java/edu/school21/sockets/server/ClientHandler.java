package edu.school21.sockets.server;

import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.Room;
import edu.school21.sockets.models.User;
import edu.school21.sockets.services.interfaces.MessagesService;
import edu.school21.sockets.services.interfaces.RoomsService;
import edu.school21.sockets.services.interfaces.UsersService;
import edu.school21.sockets.utils.JsonFormatConverter;
import edu.school21.sockets.utils.JsonObject;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Optional;

public class ClientHandler implements Runnable {
    private List<ClientHandler> clients;
    private Socket clientSocket;
    private UsersService usersService;
    private MessagesService messagesService;
    private RoomsService roomsService;
    private JsonFormatConverter jsonFormatConverter;
    private BufferedReader in;
    private BufferedWriter out;

    public ClientHandler(Socket socket, Server server) {
        this.clientSocket = socket;
        this.usersService = server.getUsersService();
        this.messagesService = server.getMessagesService();
        this.roomsService = server.getRoomsService();
        clients = server.getClients();
        jsonFormatConverter = new JsonFormatConverter();

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
            selectMethod();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    private void selectMethod() throws IOException {
        while (!clientSocket.isClosed()) {
            sendJsonMessage("Hello from Server!\n" +
                    "1. signIn\n" +
                    "2. SignUp\n" +
                    "3. Exit", 0L, 0L);

            JsonObject jsonCommand = jsonFormatConverter.convertJsonToObject(in.readLine());
            String command = jsonCommand.getMessage();

            if (command.equals("1")) {
                selectRoomMethod(processSignIn());
            } else if (command.equals("2")) {
                processSignUp();
            } else if (command.equals("3")) {
                close();
                break;
            } else {
                if (!clientSocket.isClosed()) {
                    out.write(jsonFormatConverter.convertToJson("Unknown command", 0L, 0L) + "\n");
                    out.flush();
                }
            }
        }
    }

    private void processSignUp() throws IOException {
        sendJsonMessage("Enter username", 0L, 0L);

        JsonObject jsonName = jsonFormatConverter.convertJsonToObject(in.readLine());
        String name = jsonName.getMessage();

        sendJsonMessage("Enter password", 0L, 0L);

        JsonObject jsonPassword = jsonFormatConverter.convertJsonToObject(in.readLine());
        String password = jsonPassword.getMessage();

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
        String bcryptPassword = encoder.encode(password);

        usersService.signUp(name, bcryptPassword);

        System.out.println("Client with name: " + name + " is added to DB");
        sendJsonMessage("Successful!", 0L, 0L);
    }

    private User processSignIn() throws IOException {
        sendJsonMessage("Enter username", 0L, 0L);

        JsonObject jsonName = jsonFormatConverter.convertJsonToObject(in.readLine());
        String name = jsonName.getMessage();

        sendJsonMessage("Enter password", 0L, 0L);

        JsonObject jsonPassword = jsonFormatConverter.convertJsonToObject(in.readLine());
        String password = jsonPassword.getMessage();

        User user = null;

        Optional<User> userOptional = usersService.signIn(name, password);
        if (userOptional.isPresent()) {
            user = userOptional.get();
            sendJsonMessage("Successful!", 0L, 0L);
        } else {
            sendJsonMessage("Authentication failed. Closing connection.", 0L, 0L);
            close();
        }

        return user;
    }

    private void selectRoomMethod(User user) throws IOException {

        while (!clientSocket.isClosed()) {
            sendJsonMessage("1. Create room\n" +
                    "2. Choose room\n" +
                    "3. Exit", 0L, 0L);

            String json = jsonFormatConverter.convertToJson(in.readLine(), user.getId(), 0L);
            JsonObject jsonObject = jsonFormatConverter.convertJsonToObject(json);
            String messageJson = jsonObject.getMessage();

            // Преобразуем messageJson в объект
            JsonObject messageObject = jsonFormatConverter.convertJsonToObject(messageJson);
            String methodName = messageObject.getMessage();

            if (methodName == null) break; // Если поток закрыт, выходим из цикла

            if (methodName.equals("1")) {
                processCreateRoom(user);
            } else if (methodName.equals("2")) {
                processChatMessages(processChooseRoom(user), user);
            } else if (methodName.equals("3")) {
                clientSocket.close();
                break;
            } else {
                sendJsonMessage("Unknown command", 0L, 0L);
            }
        }
    }

    private void processCreateRoom(User user) throws IOException {
        sendJsonMessage("Enter title:", 0L, 0L);

        String json = jsonFormatConverter.convertToJson(in.readLine(), user.getId(), 0L);
        JsonObject jsonTitle = jsonFormatConverter.convertJsonToObject(json);
        String title = jsonTitle.getMessage();

        if (user != null) {
            roomsService.createRoom(title, user);
            sendJsonMessage("Successful!", 0L, 0L);
        } else {
            sendJsonMessage("Creation failed. Closing connection.", 0L, 0L);
            close();
        }
    }

    private Room processChooseRoom(User user) throws IOException {
        while (!clientSocket.isClosed()) {
            sendJsonMessage("Rooms: ---", 0L, 0L);

            int count = 1;

            for (Room room : roomsService.getRooms()) {
                sendJsonMessage(count + ". " + room.getTitle(), user.getId(), room.getId());
                count++;
            }

            sendJsonMessage(count + ". Exit", 0L, 0L);

            String json = jsonFormatConverter.convertToJson(in.readLine(), user.getId(), 0L);
            JsonObject jsonObject = jsonFormatConverter.convertJsonToObject(json);
            String messageJson = jsonObject.getMessage();

            JsonObject jsonChoice = jsonFormatConverter.convertJsonToObject(messageJson);
            int choice = Integer.parseInt(jsonChoice.getMessage());

            if (choice > 0 && choice <= roomsService.getRooms().size()) {
                return roomsService.getRooms().get(choice - 1);
            } else if (choice == count) {
                // Если выбрана опция выхода, закрываем соединение
                sendJsonMessage("Closing connection.", 0L, 0L);
                close();
                break;
            } else {
                sendJsonMessage("Choosing failed. Try again.", 0L, 0L);
            }
        }

        return null;
    }

    private void processChatMessages(Room room, User user) throws IOException {
        printLastMessagesFromRoom(room, user, 30);

        String clientMessage;
        while ((clientMessage = in.readLine()) != null) {
            JsonObject jsonObject = jsonFormatConverter.convertJsonToObject(clientMessage);
            String message = jsonObject.getMessage();

            if (message.equals("Exit")) {
                broadcastMessage("left from chat", user.getId(), room.getId());
                close();
                break;
            }

            messagesService.sendMessage(user, message, room);
            broadcastMessage(message, user.getId(), room.getId());
        }
    }

    private void sendJsonMessage(String message, Long senderId, Long roomId) throws IOException {
        String json = jsonFormatConverter.convertToJson(message, senderId, roomId);
        JsonObject jsonObject = jsonFormatConverter.convertJsonToObject(json);
        out.write(jsonObject + "\n");
        out.flush();
    }

    private void printLastMessagesFromRoom(Room room, User user, int count) throws IOException {
        for (Message message : roomsService.getLastMessages(room, count)) {
            sendJsonMessage(message.getMessage(), message.getSender().getId(), room.getId());
        }
    }

    private void broadcastMessage(String message, Long senderId, Long roomId) throws IOException {
        synchronized (clients) {
            for (ClientHandler ch : clients) {
                ch.sendJsonMessage(message, senderId, roomId);
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