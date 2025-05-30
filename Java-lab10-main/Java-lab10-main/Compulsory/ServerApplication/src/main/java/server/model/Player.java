package server.model;

import java.net.Socket;

public class Player {
    private final String name;
    private final Socket socket;
    private PlayerColor color;
    private int remainingTimeSeconds;
    private boolean isWinner = false;

    public Player(String name, Socket socket) {
        this.name = name;
        this.socket = socket;
    }

    public String getName() {
        return name;
    }

    public Socket getSocket() {
        return socket;
    }

    public PlayerColor getColor() {
        return color;
    }

    public void setColor(PlayerColor color) {
        this.color = color;
    }

    public int getRemainingTimeSeconds() {
        return remainingTimeSeconds;
    }

    public void setRemainingTimeSeconds(int remainingTimeSeconds) {
        this.remainingTimeSeconds = remainingTimeSeconds;
    }

    public boolean isWinner() {
        return isWinner;
    }

    public void setWinner(boolean winner) {
        isWinner = winner;
    }
}
