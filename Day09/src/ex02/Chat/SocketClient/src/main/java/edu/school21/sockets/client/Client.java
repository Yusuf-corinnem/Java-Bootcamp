package edu.school21.sockets.client;

import edu.school21.sockets.client.utils.JsonObject;
import edu.school21.sockets.client.utils.JsonFormatConverter;

import java.io.*;
import java.net.Socket;

public class Client {
    private String serverAddress;
    private int serverPort;
    private Socket serverSocket;
    private JsonFormatConverter jsonFormatConverter;
    private BufferedReader consoleInput;
    private BufferedReader in;
    private BufferedWriter out;

    public Client(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        jsonFormatConverter = new JsonFormatConverter();
    }

    public void start() {
        try {
            serverSocket = new Socket(serverAddress, serverPort);
            consoleInput = new BufferedReader(new InputStreamReader(System.in));
            in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(serverSocket.getOutputStream()));

            System.out.println("Connected to server");

            receivingMessagesFromServer();

            sendingMessages();

        } catch (IOException e) {
            throw new RuntimeException("Failed to connect to server", e);
        } finally {
            close();
        }
    }

    private void receivingMessagesFromServer() {
        Thread serverMessageThread = new Thread(() -> {
            try {
                String serverMessage;
                while ((serverMessage = in.readLine()) != null) {
                    System.out.println(serverMessage);
                }
            } catch (IOException e) {
                close();
            }
        });
        serverMessageThread.start();
    }

    private void sendingMessages() throws IOException {
        String userInput = "";

        while (!userInput.equals("Exit")) {
            userInput = consoleInput.readLine();
            String jsonInput = jsonFormatConverter.convertToJson(userInput, 0L, 0L);
            JsonObject jsonObject = jsonFormatConverter.convertJsonToObject(jsonInput);
            out.write(jsonObject + "\n");
            out.flush();
        }
    }

    private void close() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
            if (consoleInput != null) {
                consoleInput.close();
            }
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if (args.length != 1 || !args[0].startsWith("--server-port=")) {
            System.out.println("Usage: java -jar target/socket-client.jar --server-port=8081");
            return;
        }

        int serverPort = Integer.parseInt(args[0].substring("--server-port=".length()));
        Client client = new Client("localhost", serverPort);
        client.start();
    }
}