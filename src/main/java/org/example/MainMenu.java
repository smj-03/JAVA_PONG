package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JPanel {

    private Ball ball;
    private Timer timer;

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

        JButton playButton = new JButton("Play");
        JButton settingsButton = new JButton("Settings");
        JButton quitButton = new JButton("Quit");

        playButton.setAlignmentX(CENTER_ALIGNMENT);
        settingsButton.setAlignmentX(CENTER_ALIGNMENT);
        quitButton.setAlignmentX(CENTER_ALIGNMENT);

        Dimension buttonSize = new Dimension(200, 40);
        playButton.setMaximumSize(buttonSize);
        settingsButton.setMaximumSize(buttonSize);
        quitButton.setMaximumSize(buttonSize);

        add(playButton);
        add(Box.createVerticalStrut(20));
        add(settingsButton);
        add(Box.createVerticalStrut(20));
        add(quitButton);

        // BUTTON ACTIONS
        playButton.addActionListener(e -> {
            timer.stop(); // stop animation
            Game gamePanel = new Game();
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
