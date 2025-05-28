package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JPanel {

    private Ball ball;
    private Timer timer;

    private aiDifficulty difficulty;

    public MainMenu(JFrame frame) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.BLACK);

        // Initialize ball in center with speed
        ball = new Ball(100, 100, 20, 4, 4, Color.WHITE);

        timer = new Timer(16, e -> {
            ball.move();
            ball.bounceOffWalls(0, getHeight());
            repaint();
        });
        timer.start();

        JLabel title = new JLabel("Pong Game");
        title.setAlignmentX(CENTER_ALIGNMENT);
        title.setFont(new Font("Arial", Font.BOLD, 48));
        title.setForeground(Color.WHITE);
        add(Box.createVerticalStrut(50));
        add(title);

        JLabel author = new JLabel("Created by Chat Enjoyers");
        author.setAlignmentX(CENTER_ALIGNMENT);
        author.setFont(new Font("Arial", Font.ITALIC, 18));
        author.setForeground(Color.GRAY);
        add(author);

        add(Box.createVerticalStrut(100));

        // Utwórz panel na przyciski gry
        JPanel playButtonsPanel = new JPanel();
        playButtonsPanel.setMaximumSize(new Dimension(520, 50));
        playButtonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0)); // 20 px odstępu
        playButtonsPanel.setOpaque(false);

        JButton playWithComputerButton = new JButton("Play with Computer");
        JButton play1v1Button = new JButton("1v1");
        JButton settingsButton = new JButton("Settings");
        JButton quitButton = new JButton("Quit");

        playWithComputerButton.setAlignmentX(CENTER_ALIGNMENT);
        play1v1Button.setAlignmentX(CENTER_ALIGNMENT);

        settingsButton.setAlignmentX(CENTER_ALIGNMENT);
        quitButton.setAlignmentX(CENTER_ALIGNMENT);

        Dimension buttonSize = new Dimension(200, 40);
        playWithComputerButton.setPreferredSize(buttonSize);
        play1v1Button.setPreferredSize(buttonSize);

        settingsButton.setMaximumSize(buttonSize);
        quitButton.setMaximumSize(buttonSize);

        add(playButtonsPanel);
        playButtonsPanel.add(playWithComputerButton);
        playButtonsPanel.add(play1v1Button);
        add(Box.createVerticalStrut(20));
        add(settingsButton);
        add(Box.createVerticalStrut(20));
        add(quitButton);

        playWithComputerButton.addActionListener(e -> {
            timer.stop(); // stop animation
            Game gamePanel = new Game(true, aiDifficulty.EASY); // Start game with AI difficulty
            frame.setContentPane(gamePanel);
            frame.revalidate();
            SwingUtilities.invokeLater(gamePanel::requestFocusInWindow);
            gamePanel.startGame();
        });

        play1v1Button.addActionListener(e -> {
            timer.stop(); // stop animation
            Game gamePanel = new Game(false, aiDifficulty.EASY); // Start game without AI
            frame.setContentPane(gamePanel);
            frame.revalidate();
            SwingUtilities.invokeLater(gamePanel::requestFocusInWindow);
            gamePanel.startGame();
        });

        settingsButton.addActionListener(e -> {
            timer.stop(); // stop animation
            SettingsMenu settingsPanel = new SettingsMenu(frame, this);
            frame.setContentPane(settingsPanel);
            frame.revalidate();
            SwingUtilities.invokeLater(settingsPanel::requestFocusInWindow);
        });

        quitButton.addActionListener(e -> System.exit(0));
    }

    public void resumeAnimation() {
        if (timer != null && !timer.isRunning()) {
            timer.start();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ball.paint(g); // draw the ball in the background
    }
}
