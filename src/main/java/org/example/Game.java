package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game extends JPanel implements Runnable, KeyListener {
    private Thread gameThread;
    private boolean running = false;
    private final Paddle user1Paddle, user2Paddle;

    private boolean upPressedPaddle1 = false;
    private boolean downPressedPaddle1 = false;

    private boolean upPressedPaddle2 = false;
    private boolean downPressedPaddle2 = false;

    private Ball ball;

    public Game() {
        // Initialize game components here
        setFocusable(true); // Allow the panel to receive focus for key events
        requestFocusInWindow(); // Request focus for the panel
        addKeyListener(this); // Add key listener to handle key events

        // Create paddles for players
        user1Paddle = new Paddle(10, 200, 15, 150, Color.BLUE);
        user2Paddle = new Paddle(760, 200, 15, 150, Color.BLUE);

        // Create a ball
        ball = new Ball(400, 300, 20, 3, 3, Color.WHITE); // Initial position and speed of the ball
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
            ball.move(); // Move the ball
            ball.bounceOffWalls(0, getHeight()); // Bounce the ball off the walls
            if (user1Paddle.intersects(ball) || user2Paddle.intersects(ball)) {
                ball.reverseXDirection(); // zmień kierunek ruchu piłki w poziomie
            }

            try {
                Thread.sleep(16); // about 60 FPS (1000 ms / 60 = ~16 ms)
            } catch (InterruptedException e) {
                e.printStackTrace(); // Handle interruption exception
            }
        }
    }

    private void updateGame() {
        if (upPressedPaddle1) {
            user1Paddle.move(user1Paddle.getY() - 5, getHeight()); // move up player 1
        }
        if (downPressedPaddle1) {
            user1Paddle.move(user1Paddle.getY() + 5, getHeight()); // move down player 1
        }

        if (upPressedPaddle2) {
            user2Paddle.move(user2Paddle.getY() - 5, getHeight()); // move up player 2
        }
        if (downPressedPaddle2) {
            user2Paddle.move(user2Paddle.getY() + 5, getHeight()); // move down for player 2
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            upPressedPaddle1 = true; // Set flag for paddle 1 moving up
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            downPressedPaddle1 = true; // Set flag for paddle 1 moving down
        }

        if (e.getKeyCode() == KeyEvent.VK_UP) {
            upPressedPaddle2 = true; // Set flag for paddle 2 moving up
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            downPressedPaddle2 = true; // Set flag for paddle 2 moving down
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            upPressedPaddle1 = false; // Reset flag for paddle 1 moving up
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            downPressedPaddle1 = false; // Reset flag for paddle 1 moving down
        }

        if (e.getKeyCode() == KeyEvent.VK_UP) {
            upPressedPaddle2 = false; // Reset flag for paddle 2 moving up
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            downPressedPaddle2 = false; // Reset flag for paddle 2 moving down
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // not used
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw game elements here
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight()); // Fill background with black color

        //Draw paddles
        user1Paddle.paint(g);
        user2Paddle.paint(g);

        // Draw the ball
        ball.paint(g);
    }
}
