import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Server.java
 * Starts the chat server and accepts multiple client connections
 */
public class Server {

    // List to keep track of all connected clients
    static List<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) throws Exception {

        // Start server on port 1234
        ServerSocket serverSocket = new ServerSocket(1234);
        System.out.println("✅ Chat Server Started! Waiting for clients...");

        // Keep running forever, accepting new clients
        while (true) {
            // Wait for a client to connect
            Socket socket = serverSocket.accept();
            System.out.println("🟢 New client connected: " + socket);

            // Create a handler for this client
            ClientHandler clientHandler = new ClientHandler(socket);
            clients.add(clientHandler);

            // Start a new thread for this client
            Thread thread = new Thread(clientHandler);
            thread.start();
        }
    }

    // Send a message to ALL connected clients
    static void broadcastMessage(String message, ClientHandler sender) {
        for (ClientHandler client : clients) {
            // Don't send message back to the person who sent it
            if (client != sender) {
                client.sendMessage(message);
            }
        }
    }

    // Remove a client when they disconnect
    static void removeClient(ClientHandler client) {
        clients.remove(client);
        System.out.println("🔴 A client disconnected.");
    }
}