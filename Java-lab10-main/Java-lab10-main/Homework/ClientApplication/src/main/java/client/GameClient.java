// GameClient.java
package client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameClient {
    public static final String SERVER_ADDRESS = "localhost";
    public static final int SERVER_PORT = 5000;
    private boolean running = true;
    private final ExecutorService pool = Executors.newFixedThreadPool(2);

    public void start() {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            System.out.print("Enter your name: ");
            String name = scanner.nextLine();
            out.println(name);

            pool.execute(() -> {
                try {
                    String response;
                    while ((response = in.readLine()) != null) {
                        System.out.println(response);
                    }
                } catch (IOException e) {
                    if (running) {
                        System.err.println("Error reading from server: " + e.getMessage());
                    }
                }
            });

            System.out.println("Welcome to Hex Game! Available commands:");
            System.out.println("create [board_size] [time_control_seconds] - Create a new game");
            System.out.println("list - List available games");
            System.out.println("join [game_id] - Join an existing game");
            System.out.println("move [row] [column] - Make a move in the game");
            System.out.println("exit - Exit the game");

            while (running) {
                String command = scanner.nextLine();

                if ("exit".equalsIgnoreCase(command)) {
                    running = false;
                    System.out.println("Client stopped.");
                    break;
                }

                out.println(command);
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            pool.shutdown();
        }
    }

    public static void main(String[] args) {
        new GameClient().start();
    }
}
