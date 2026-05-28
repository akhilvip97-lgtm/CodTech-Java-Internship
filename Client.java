import java.io.*;
import java.net.*;

/**
 * Client.java
 * Connects to the chat server and allows user to send/receive messages
 */
public class Client {

    public static void main(String[] args) throws Exception {

        // Connect to server (localhost = same computer, port 1234)
        Socket socket = new Socket("localhost", 1234);
        System.out.println("✅ Connected to Chat Server!");

        // Setup input and output
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader keyboard = new BufferedReader(
                new InputStreamReader(System.in));

        // Thread to constantly RECEIVE messages from server
        Thread receiveThread = new Thread(() -> {
            try {
                String message;
                while ((message = reader.readLine()) != null) {
                    System.out.println(message);
                }
            } catch (IOException e) {
                System.out.println("Disconnected from server.");
            }
        });
        receiveThread.start();

        // Main thread to SEND messages to server
        String input;
        while ((input = keyboard.readLine()) != null) {
            writer.println(input);

            // If user types exit, stop
            if (input.equalsIgnoreCase("exit")) {
                break;
            }
        }

        socket.close();
        System.out.println("👋 You left the chat.");
    }
}