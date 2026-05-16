import java.io.*;
import java.net.*;

/**
 * ClientHandler.java
 * Handles communication with one connected client
 */
public class ClientHandler implements Runnable {

    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private String clientName;

    // Constructor - sets up the connection
    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            // Setup input and output streams
            reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);

            // Ask client for their name
            writer.println("Enter your name: ");
            clientName = reader.readLine();

            // Announce to everyone that this person joined
            System.out.println("👤 " + clientName + " joined the chat!");
            Server.broadcastMessage("🟢 " + clientName + " joined the chat!", this);

            // Keep reading messages from this client
            String message;
            while ((message = reader.readLine()) != null) {

                // If client types "exit", disconnect them
                if (message.equalsIgnoreCase("exit")) {
                    break;
                }

                // Print message on server console
                System.out.println("[" + clientName + "]: " + message);

                // Send message to all other clients
                Server.broadcastMessage("[" + clientName + "]: " + message, this);
            }

        } catch (IOException e) {
            System.out.println("Connection error: " + e.getMessage());
        } finally {
            // Clean up when client disconnects
            try {
                Server.broadcastMessage("🔴 " + clientName + " left the chat.", this);
                Server.removeClient(this);
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Method to send a message to this client
    public void sendMessage(String message) {
        writer.println(message);
    }
}