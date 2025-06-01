package org.example;

import com.google.gson.Gson;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Game extends JPanel implements Runnable, KeyListener {
    private Thread gameThread;
    private boolean running = false;
    private final Paddle user1Paddle, user2Paddle;

    private boolean upPressedPaddle1 = false;
    private boolean downPressedPaddle1 = false;

    private boolean upPressedPaddle2 = false;
    private boolean downPressedPaddle2 = false;

    private boolean escapePressed = false;

    private Ball ball;

    private int user1Score, user2Score;

    private JButton stopButton;
    private JButton playAgainButton; // pole w klasie Game
    private JButton returnToMenuButton;

    private boolean vsAI;

    private aiDifficulty aiDifficulty; // pole w klasie Game

    public Game(boolean vsAI, aiDifficulty aiDifficulty) {
        this.vsAI = vsAI;
        this.aiDifficulty = aiDifficulty; // Default AI difficulty
        // Initialize game components here
        setLayout(null);
        setFocusable(true); // Allow the panel to receive focus for key events
        requestFocusInWindow(); // Request focus for the panel
        addKeyListener(this); // Add key listener to handle key events

        // Create paddles for players
        user1Paddle = new Paddle(10, 200, 15, 150, Color.BLUE);
        user2Paddle = new Paddle(760, 200, 15, 150, Color.BLUE);

        // Initialize scores for both players
        user1Score = 0;
        user2Score = 0;

        // Create a ball
        ball = new Ball(400, 300, 20, 3, 3, Color.WHITE); // Initial position and speed of the ball
        // Create and configure the stop button
        stopButton = new JButton("Stop Game");
        stopButton.setBounds(350, 10, 100, 30); // Position the button
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleGameState();
                requestFocusInWindow();
            }
        });
        add(stopButton); // Add the button to the panel

        playAgainButton = new JButton("Play Again");
        playAgainButton.setBounds(350, 10, 100, 30); // Position the button
        playAgainButton.setVisible(false);
        playAgainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playAgainButton.setVisible(false); // Hide the play again button
                stopButton.setVisible(true); // Show the stop button
                returnToMenuButton.setVisible(false); // Hide the return to menu button
                resetGame(); // Reset the game state
                user1Score = 0; // Reset player 1's score
                user2Score = 0; // Reset player 2's score
                //running = true; // Set running to true to start the game loop
                startGame(); // Start the game
            }
        });
        add(playAgainButton); // Add the button to the panel

        // create and configure the return to menu button
        returnToMenuButton = new JButton("Return to Menu");
        returnToMenuButton.setBounds(325, 50, 150, 30);
        returnToMenuButton.setVisible(false);
        returnToMenuButton.addActionListener(e -> {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            MainMenu menuPanel = new MainMenu(topFrame);
            topFrame.setContentPane(menuPanel);
            topFrame.revalidate();
        });
        add(returnToMenuButton);
    }

    public void startGame() {
        if (running) return;
        running = true; // Set the running flag to true to start the game loop
        requestFocusInWindow(); // Ensure the panel regains focus for key events
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
    public void endGame() {
        running = false; // Zatrzymaj pętlę gry
        resetGame();
        // Opcjonalnie: pokaż przycisk powrotu do menu lub ponownego startu gry
        returnToMenuButton.setVisible(true);
        playAgainButton.setVisible(true);
        stopButton.setVisible(false);
        StatisticsData stats = loadStatsFromJson();
        stats.gamesPlayed++; // Zwiększ liczbę rozegranych gier
        if (vsAI){
            if (stats != null) {
                // Aktualizuj statystyki na podstawie wyniku
                if (user1Score > user2Score) {
                    if(aiDifficulty == aiDifficulty.EASY) {
                        stats.easyWins++;
                    } else if (aiDifficulty == aiDifficulty.MEDIUM) {
                        stats.mediumWins++;
                    } else if (aiDifficulty == aiDifficulty.HARD) {
                        stats.hardWins++;
                    }
                } else {
                    if(aiDifficulty == aiDifficulty.EASY) {
                        stats.easyLosses++;
                    } else if (aiDifficulty == aiDifficulty.MEDIUM) {
                        stats.mediumLosses++;
                    } else if (aiDifficulty == aiDifficulty.HARD) {
                        stats.hardLosses++;
                    }
                }
            }
            // Zapisz zaktualizowane statystyki do pliku JSON
            try (FileWriter writer = new FileWriter("src/main/resources/stats.json")) {
                new Gson().toJson(stats, writer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // Pokaż wynik i zresetuj stan gry
        JOptionPane.showMessageDialog(this, "Game Over! Final Score: Player 1 - " + user1Score + ", Player 2 - " + user2Score);

    }
    @Override
    public void run() {
        // game loop
        while (running) {
            updateGame(); // Update game state
            repaint();    // Game rendering
            ball.move(); // Move the ball
            ball.bounceOffWalls(0, getHeight()); // Bounce the ball off the walls
            if (user1Paddle.intersects(ball)) {
                ball.bounceFromPaddle(user1Paddle); // Bounce the ball off player 1's paddle
            }

            if (user2Paddle.intersects(ball)) {
                ball.bounceFromPaddle(user2Paddle); // Bounce the ball off player 2's paddle
            }
            // Check for scoring conditions
            if (ball.getX() < 0) { // Ball went out on player 2's side
                user2Score++; // Increment player 2's score
                resetGame(); // Reset the game after scoring
            } else if (ball.getX() + ball.getDiameter() > getWidth()) { // Ball went out on player 1's side
                user1Score++; // Increment player 1's score
                resetGame(); // Reset the game after scoring
            }

            // check end game conditions
            if (user1Score >= 5 || user2Score >= 5) { // Example condition for ending the game
                running = false; // Stop the game loop
                endGame();
            }
            try {
                Thread.sleep(16); // about 60 FPS (1000 ms / 60 = ~16 ms)
            } catch (InterruptedException e) {
                e.printStackTrace(); // Handle interruption exception
            }
        }
    }

    private void toggleGameState() {
        if (running) {
            stopGame(); // Stop the game
            stopButton.setText("Resume Game"); // Change button text to "Resume Game"
            returnToMenuButton.setVisible(true);
        } else {
            startGame(); // Resume the game
            stopButton.setText("Stop Game"); // Change button text to "Stop Game"
            returnToMenuButton.setVisible(false);
        }
    }

    private void updateGame() {

        if (upPressedPaddle1) {
            user1Paddle.move(user1Paddle.getY() - 5, getHeight()); // move up player 1
        }
        if (downPressedPaddle1) {
            user1Paddle.move(user1Paddle.getY() + 5, getHeight()); // move down player 1
        }

        if (vsAI) {
            int paddleCenter = user2Paddle.getY() + user2Paddle.getHeight() / 2;
            int ballCenter = ball.getY() + ball.getDiameter() / 2;
            int dy = ballCenter - paddleCenter;

            int deadZone = 20; // dead zone for AI paddle movement
            int speed; // speed of AI paddle movement

            switch (aiDifficulty) {
                case EASY:
                    speed = 2;
                    break;
                case MEDIUM:
                    speed = 3;
                    break;
                case HARD:
                    speed = 6;
                    break;
                default:

                    speed = 3;
            }

            if (Math.abs(dy) > deadZone) {
                int direction = (dy > 0) ? 1 : -1;
                user2Paddle.move(user2Paddle.getY() + direction * speed, getHeight());
            }
        }
        else{
            if (upPressedPaddle2) {
                user2Paddle.move(user2Paddle.getY() - 5, getHeight()); // move up player 2
            }
            if (downPressedPaddle2) {
                user2Paddle.move(user2Paddle.getY() + 5, getHeight()); // move down player 2
            }
        }

    }

    public void resetGame() {
        //pause for a second
        try{
            Thread.sleep(1000);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        // Reset game state for a new game
        ball = new Ball(400, 300, 20, 3, 3, Color.WHITE); // Reset ball position and speed
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

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE && !escapePressed) {
            escapePressed = true;
            toggleGameState();
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

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            escapePressed = false;
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

        // Draw scores
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 24)); // Set font for the score
        g.drawString("Player 1: " + user1Score, 50, 30); // Draw player 1's score
        g.drawString("Player 2: " + user2Score, getWidth() - 150, 30); // Draw player 2's score

    }

    private StatisticsData loadStatsFromJson() {
        try (FileReader reader = new FileReader("src/main/resources/stats.json")) {
            return new Gson().fromJson(reader, StatisticsData.class);
        } catch (IOException e) {
            return null;
        }
    }
}
