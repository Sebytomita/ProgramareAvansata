package server.model;

import java.util.UUID;

public class Game {
    private final String gameId;
    private final Board board;
    private Player redPlayer;
    private Player bluePlayer;
    private Player currentPlayer;
    private GameStatus status;
    private final int timeControlSeconds;

    public Game(int size, int timeControlSeconds) {
        this.gameId = UUID.randomUUID().toString();
        this.board = new Board(size);
        this.status = GameStatus.WAITING_FOR_PLAYERS;
        this.timeControlSeconds = timeControlSeconds;
    }

    public boolean joinGame(Player player) {
        if (status != GameStatus.WAITING_FOR_PLAYERS) {
            return false;
        }

        if (redPlayer == null) {
            redPlayer = player;
            redPlayer.setColor(PlayerColor.RED);
            redPlayer.setRemainingTimeSeconds(timeControlSeconds);
            return true;
        } else if (bluePlayer == null) {
            bluePlayer = player;
            bluePlayer.setColor(PlayerColor.BLUE);
            bluePlayer.setRemainingTimeSeconds(timeControlSeconds);
            status = GameStatus.IN_PROGRESS;
            currentPlayer = redPlayer;
            return true;
        }

        return false;
    }

    public MoveResult makeMove(Player player, int row, int col) {
        if (player != currentPlayer) {
            return MoveResult.NOT_YOUR_TURN;
        }

        if (!board.isValidMove(row, col)) {
            return MoveResult.INVALID_MOVE;
        }

        board.placeStone(row, col, player.getColor());

        if (board.checkWin(player.getColor())) {
            status = GameStatus.FINISHED;
            return MoveResult.GAME_WON;
        }

        currentPlayer = (currentPlayer == redPlayer) ? bluePlayer : redPlayer;
        return MoveResult.SUCCESS;
    }

    public String getGameId() {
        return gameId;
    }

    public Board getBoard() {
        return board;
    }

    public Player getRedPlayer() {
        return redPlayer;
    }

    public Player getBluePlayer() {
        return bluePlayer;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void updateTime(Player player, int elapsedSeconds) {
        if (player == redPlayer || player == bluePlayer) {
            int remainingTime = player.getRemainingTimeSeconds() - elapsedSeconds;
            player.setRemainingTimeSeconds(Math.max(0, remainingTime));

            if (player.getRemainingTimeSeconds() <= 0 && status == GameStatus.IN_PROGRESS) {
                status = GameStatus.FINISHED;
                Player winner = (player == redPlayer) ? bluePlayer : redPlayer;
                winner.setWinner(true);
            }
        }
    }

    public enum GameStatus {
        WAITING_FOR_PLAYERS,
        IN_PROGRESS,
        FINISHED
    }

    public enum MoveResult {
        SUCCESS,
        NOT_YOUR_TURN,
        INVALID_MOVE,
        GAME_WON
    }
}
