package server;

import server.model.Game;
import server.model.Player;

import java.io.*;
import java.net.Socket;
import java.util.Map;

public class ClientThread extends Thread {
    private final Socket socket;
    private final GameServer server;
    private Player player;
    private Game currentGame;
    private BufferedReader in;
    private PrintWriter out;
    private long lastMoveTime;

    public ClientThread(Socket socket, GameServer server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            String playerName = in.readLine();
            player = new Player(playerName, socket);
            server.registerPlayer(player);

            out.println("Welcome to Hex Game Server, " + playerName + "!");

            processCommands();

        } catch (IOException e) {
            System.err.println("Error handling client: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println("Failed to close socket: " + e.getMessage());
            }
        }
    }

    private void processCommands() throws IOException {
        String request;
        while ((request = in.readLine()) != null) {
            System.out.println("Received from " + player.getName() + ": " + request);

            String[] parts = request.split("\\s+");
            String command = parts[0].toLowerCase();

            switch (command) {
                case "create":
                    handleCreateGame(parts);
                    break;
                case "list":
                    handleListGames();
                    break;
                case "join":
                    handleJoinGame(parts);
                    break;
                case "move":
                    handleMakeMove(parts);
                    break;
                case "stop":
                    out.println("Server stopped");
                    server.stop();
                    return;
                default:
                    out.println("Unknown command: " + command);
                    break;
            }

            if (currentGame != null &&
                    currentGame.getStatus() == Game.GameStatus.IN_PROGRESS &&
                    currentGame.getCurrentPlayer() == player) {

                long now = System.currentTimeMillis();
                if (lastMoveTime > 0) {
                    int elapsedSeconds = (int)((now - lastMoveTime) / 1000);
                    currentGame.updateTime(player, elapsedSeconds);

                    if (player.getRemainingTimeSeconds() <= 0) {
                        out.println("You lost on time!");
                        Player opponent = (player == currentGame.getRedPlayer()) ?
                                currentGame.getBluePlayer() : currentGame.getRedPlayer();

                        PrintWriter opponentOut = new PrintWriter(
                                opponent.getSocket().getOutputStream(), true);
                        opponentOut.println("Your opponent lost on time! You win!");
                    }
                }
            }
        }
    }

    private void handleCreateGame(String[] parts) {
        if (parts.length < 3) {
            out.println("Usage: create [board_size] [time_control_seconds]");
            return;
        }

        try {
            int size = Integer.parseInt(parts[1]);
            int timeControl = Integer.parseInt(parts[2]);

            if (size < 5 || size > 19) {
                out.println("Board size must be between 5 and 19");
                return;
            }

            if (timeControl < 30) {
                out.println("Time control must be at least 30 seconds");
                return;
            }

            Game game = server.createGame(player, size, timeControl);
            currentGame = game;
            lastMoveTime = 0;

            out.println("Game created with ID: " + game.getGameId() + ". Waiting for opponent...");
        } catch (NumberFormatException e) {
            out.println("Invalid number format. Usage: create [board_size] [time_control_seconds]");
        }
    }

    private void handleListGames() {
        Map<String, Game> availableGames = server.getAvailableGames();

        if (availableGames.isEmpty()) {
            out.println("No available games. Create one with 'create [board_size] [time_control_seconds]'");
            return;
        }

        out.println("Available games:");
        for (Map.Entry<String, Game> entry : availableGames.entrySet()) {
            Game game = entry.getValue();
            out.println("ID: " + entry.getKey() +
                    ", Size: " + game.getBoard().getSize() +
                    ", Creator: " + game.getRedPlayer().getName() +
                    ", Time control: " + game.getRedPlayer().getRemainingTimeSeconds() + " seconds");
        }
    }

    private void handleJoinGame(String[] parts) {
        if (parts.length < 2) {
            out.println("Usage: join [game_id]");
            return;
        }

        String gameId = parts[1];
        Game game = server.joinGame(gameId, player);

        if (game == null) {
            out.println("Failed to join game. Game might be full or not exist.");
            return;
        }

        currentGame = game;
        lastMoveTime = System.currentTimeMillis();

        out.println("Joined game with ID: " + gameId);
        out.println("You are playing as " + player.getColor());
        out.println("Board size: " + game.getBoard().getSize() + "x" + game.getBoard().getSize());
        out.println("Time control: " + player.getRemainingTimeSeconds() + " seconds");
        out.println(game.getBoard().toString());

        Player opponent = (player == game.getRedPlayer()) ? game.getBluePlayer() : game.getRedPlayer();
        try {
            PrintWriter opponentOut = new PrintWriter(opponent.getSocket().getOutputStream(), true);
            opponentOut.println(player.getName() + " has joined the game!");
            opponentOut.println("Game is starting. You are playing as " + opponent.getColor());
            opponentOut.println(game.getBoard().toString());

            if (opponent == game.getCurrentPlayer()) {
                opponentOut.println("It's your turn!");
            }
        } catch (IOException e) {
            System.err.println("Error notifying opponent: " + e.getMessage());
        }

        if (player == game.getCurrentPlayer()) {
            out.println("It's your turn!");
        } else {
            out.println("Waiting for opponent's move...");
        }
    }

    private void handleMakeMove(String[] parts) {
        if (currentGame == null) {
            out.println("You are not in a game. Create or join a game first.");
            return;
        }

        if (currentGame.getStatus() != Game.GameStatus.IN_PROGRESS) {
            out.println("Game is not in progress.");
            return;
        }

        if (parts.length < 3) {
            out.println("Usage: move [row] [column]");
            return;
        }

        try {
            int row = Integer.parseInt(parts[1]);
            int col = Integer.parseInt(parts[2]);

            if (lastMoveTime > 0 && currentGame.getCurrentPlayer() == player) {
                long now = System.currentTimeMillis();
                int elapsedSeconds = (int)((now - lastMoveTime) / 1000);
                currentGame.updateTime(player, elapsedSeconds);

                if (player.getRemainingTimeSeconds() <= 0) {
                    out.println("You lost on time!");
                    return;
                }
            }

            Game.MoveResult result = currentGame.makeMove(player, row, col);

            switch (result) {
                case SUCCESS:
                    lastMoveTime = System.currentTimeMillis();
                    out.println("Move successful");
                    out.println("Remaining time: " + player.getRemainingTimeSeconds() + " seconds");
                    out.println(currentGame.getBoard().toString());

                    Player opponent = (player == currentGame.getRedPlayer()) ?
                            currentGame.getBluePlayer() : currentGame.getRedPlayer();
                    try {
                        PrintWriter opponentOut = new PrintWriter(
                                opponent.getSocket().getOutputStream(), true);
                        opponentOut.println("Opponent moved to row: " + row + ", column: " + col);
                        opponentOut.println(currentGame.getBoard().toString());
                        opponentOut.println("It's your turn!");
                        opponentOut.println("Remaining time: " + opponent.getRemainingTimeSeconds() + " seconds");
                    } catch (IOException e) {
                        System.err.println("Error notifying opponent: " + e.getMessage());
                    }
                    break;
                case NOT_YOUR_TURN:
                    out.println("It's not your turn");
                    break;
                case INVALID_MOVE:
                    out.println("Invalid move");
                    break;
                case GAME_WON:
                    player.setWinner(true);
                    lastMoveTime = System.currentTimeMillis();
                    out.println("Congratulations! You won the game!");
                    out.println(currentGame.getBoard().toString());

                    Player loser = (player == currentGame.getRedPlayer()) ?
                            currentGame.getBluePlayer() : currentGame.getRedPlayer();
                    try {
                        PrintWriter loserOut = new PrintWriter(
                                loser.getSocket().getOutputStream(), true);
                        loserOut.println("Opponent moved to row: " + row + ", column: " + col);
                        loserOut.println(currentGame.getBoard().toString());
                        loserOut.println("Game over. You lost!");
                    } catch (IOException e) {
                        System.err.println("Error notifying opponent: " + e.getMessage());
                    }
                    break;
            }
        } catch (NumberFormatException e) {
            out.println("Invalid number format. Usage: move [row] [column]");
        }
    }
}
