package org.example;

import javax.swing.*;
import java.awt.*;

public class Game extends JPanel implements Runnable{
    private Thread gameThread;
    private boolean running = false;

    public Game() {
        // Initialize game components here
        setFocusable(true); // Allow the panel to receive focus for key events
        requestFocusInWindow(); // Request focus for the panel
    }
    public void startGame() {
        running = true; // Set the running flag to true to start the game loop
        gameThread = new Thread(this); // Create a new thread for the game loop
        gameThread.start(); // Start the game thread
    }

    public void stopGame() {
        running = false; // Set the running flag to false to stop the game loop
        try {
            gameThread.join(); // Wait for the game thread to finish
        } catch (InterruptedException e) {
            e.printStackTrace(); // Handle interruption exception
        }
    }

    @Override
    public void run() {
        // game loop
        while (running) {
            updateGame(); // Update game state
            repaint();    // Game rendering
            try {
                Thread.sleep(16); // about 60 FPS (1000 ms / 60 = ~16 ms)
            } catch (InterruptedException e) {
                e.printStackTrace(); // Handle interruption exception
            }
        }
    }

    private void updateGame() {
        // logic of the game (e.g. object movement, collisions)
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw game elements here
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight()); // Fill background with black color
    }
}
