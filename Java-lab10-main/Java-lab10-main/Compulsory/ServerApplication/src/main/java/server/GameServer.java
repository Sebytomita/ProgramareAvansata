package server;

import server.model.Game;
import server.model.Player;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameServer {
    public static final int PORT = 5000;
    private boolean running = true;
    private final Map<String, Game> games = new HashMap<>();
    private final Map<String, Player> players = new HashMap<>();
    private final ExecutorService pool = Executors.newFixedThreadPool(10);

    public void start() {
        System.out.println("Server started on port " + PORT + "...");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (running) {
                Socket clientSocket = serverSocket.accept();
                ClientThread clientThread = new ClientThread(clientSocket, this);
                pool.execute(clientThread);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            pool.shutdown();
        }
    }

    public synchronized Game createGame(Player creator, int size, int timeControlSeconds) {
        Game game = new Game(size, timeControlSeconds);
        games.put(game.getGameId(), game);
        game.joinGame(creator);
        return game;
    }

    public synchronized Game getGame(String gameId) {
        return games.get(gameId);
    }

    public synchronized Game joinGame(String gameId, Player player) {
        Game game = games.get(gameId);
        if (game != null) {
            boolean joined = game.joinGame(player);
            if (joined) {
                return game;
            }
        }
        return null;
    }

    public synchronized void registerPlayer(Player player) {
        players.put(player.getName(), player);
    }

    public synchronized Map<String, Game> getAvailableGames() {
        Map<String, Game> availableGames = new HashMap<>();
        for (Map.Entry<String, Game> entry : games.entrySet()) {
            if (entry.getValue().getStatus() == Game.GameStatus.WAITING_FOR_PLAYERS) {
                availableGames.put(entry.getKey(), entry.getValue());
            }
        }
        return availableGames;
    }

    public void stop() {
        running = false;
        System.out.println("Server stopped.");
    }

    public static void main(String[] args) {
        new GameServer().start();
    }
}
