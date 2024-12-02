package edu.school21.sockets.client;

import java.io.*;
import java.net.Socket;
import java.util.Objects;

public class Client {
    private String serverAddress;
    private int serverPort;
    private Socket serverSocket;
    private BufferedReader consoleInput;
    private BufferedReader in;
    private BufferedWriter out;

    public Client(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    public void start() {

        try {
            serverSocket = new Socket(serverAddress, serverPort);
            consoleInput = new BufferedReader(new InputStreamReader(System.in));
            in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(serverSocket.getOutputStream()));

            System.out.println("Connected to server");

            while (true) {
                String str = in.readLine();
                System.out.println(str);

                if (Objects.equals(str, "Successful!") || str == null) {
                    break;
                }

                out.write(consoleInput.readLine() + "\n");
                out.flush();
            }

            consoleInput.close();
            in.close();
            out.close();
            serverSocket.close();

        } catch (IOException e) {
            throw new RuntimeException("Failed to connect to server", e);
        } finally {
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