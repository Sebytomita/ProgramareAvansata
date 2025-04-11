/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab7;

/**
 *
 * @author Seby
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Game {
    private Bag bag;
    private Dictionary dictionary;
    private Board board;
    private List<Player> players;
    private volatile boolean isGameRunning; 
    private static final int GAME_DURATION_SECONDS = 30; 

    public Game() {
        this.bag = new Bag();
        this.dictionary = new Dictionary();
        this.board = new Board();
        this.players = new ArrayList<>();
        this.isGameRunning = true;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void play() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("\nTime's up! Game ending...");
                isGameRunning = false;
                timer.cancel(); 
            }
        }, GAME_DURATION_SECONDS * 1000); 

        List<Thread> playerThreads = new ArrayList<>();
        for (Player player : players) {
            Thread thread = new Thread(player);
            playerThreads.add(thread);
            thread.start();
        }

        while (isGameRunning && !bag.isEmpty()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        isGameRunning = false;

        System.out.println("\nGame Over! Final Scores:");
        Player winner = null;
        int highestScore = -1;
        for (Player player : players) {
            System.out.println(player.getName() + ": " + player.getScore());
            if (player.getScore() > highestScore) {
                highestScore = player.getScore();
                winner = player;
            }
        }
        System.out.println("Winner: " + (winner != null ? winner.getName() + " with " + winner.getScore() + " points!" : "No winner (no points scored)."));
    }

    public Bag getBag() {
        return bag;
    }

    public Dictionary getDictionary() {
        return dictionary;
    }

    public Board getBoard() {
        return board;
    }

    public boolean isGameRunning() {
        return isGameRunning;
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.addPlayer(new Player("Player 1", game));
        game.addPlayer(new Player("Player 2", game));
        game.addPlayer(new Player("Player 3", game));
        game.play();
    }
}