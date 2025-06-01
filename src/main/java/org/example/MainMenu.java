package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class MainMenu extends JPanel {

    private Ball ball;
    private Timer timer;

    private Font font;

    private aiDifficulty difficulty;

    public MainMenu(JFrame frame) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.BLACK);

        // Initialize ball in center with speed
        ball = new Ball(100, 100, 20, 4, 4, Color.WHITE);

        timer = new Timer(16, e -> {
            ball.move();
            ball.bounceOffWalls(0, getHeight(), true); // Assuming no viewport is set
            repaint();
        });
        timer.start();

        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/fonts/MedodicaRegular.otf")).deriveFont(82f);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            font = new Font("Courier New", Font.PLAIN, 82); // Fallback font
        }

        JLabel title = new JLabel("Pong Game");
        title.setAlignmentX(CENTER_ALIGNMENT);
        title.setFont(font);
        title.setForeground(Color.WHITE);
        add(Box.createVerticalStrut(100));
        add(title);

        JLabel author = new JLabel("Created by Chat Enjoyers");
        author.setAlignmentX(CENTER_ALIGNMENT);
        author.setFont(font.deriveFont(24f));
        author.setForeground(Color.GRAY);
        add(author);

        add(Box.createVerticalStrut(50));

        createButtons(frame);
    }

    protected void createButtons(JFrame frame) {
        // Panel na przyciski trybu gry
        JPanel playButtonsPanel = new JPanel();
        playButtonsPanel.setMaximumSize(new Dimension(520, 50));
        playButtonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
        playButtonsPanel.setOpaque(false);

        TextButton playWithComputerButton = new TextButton("1 Player");
        TextButton play1v1Button = new TextButton("2 Player");
        TextButton settingsButton = new TextButton("Settings");
        TextButton statsButton = new TextButton("Statistics");
        TextButton quitButton = new TextButton("Quit");

        settingsButton.setAlignmentX(CENTER_ALIGNMENT);
        statsButton.setAlignmentX(CENTER_ALIGNMENT);
        quitButton.setAlignmentX(CENTER_ALIGNMENT);

        Dimension buttonSize = new Dimension(200, 40);
        playWithComputerButton.setPreferredSize(buttonSize);
        play1v1Button.setPreferredSize(buttonSize);
        settingsButton.setMaximumSize(buttonSize);
        statsButton.setMaximumSize(buttonSize);
        quitButton.setMaximumSize(buttonSize);

        // Dodawanie przycisków do panelu
        playButtonsPanel.add(playWithComputerButton);
        playButtonsPanel.add(play1v1Button);
        add(playButtonsPanel);
        add(Box.createVerticalStrut(20));
        add(settingsButton);
        add(Box.createVerticalStrut(20));
        add(statsButton);
        add(Box.createVerticalStrut(20));
        add(quitButton);

        // Obsługa kliknięć
        playWithComputerButton.addActionListener(e -> {
            timer.stop();
            org.example.DifficultyMenu difficultyPanel = new org.example.DifficultyMenu(frame, this);
            frame.setContentPane(difficultyPanel);
            frame.revalidate();
            SwingUtilities.invokeLater(difficultyPanel::requestFocusInWindow);
        });

        play1v1Button.addActionListener(e -> {
            timer.stop();
            Game gamePanel = new Game(false, aiDifficulty.EASY);
            frame.setContentPane(gamePanel);
            frame.revalidate();
            SwingUtilities.invokeLater(gamePanel::requestFocusInWindow);
            gamePanel.startGame();
        });

        settingsButton.addActionListener(e -> {
            timer.stop();
            SettingsMenu settingsPanel = new SettingsMenu(frame, this);
            frame.setContentPane(settingsPanel);
            frame.revalidate();
            SwingUtilities.invokeLater(settingsPanel::requestFocusInWindow);
        });

        statsButton.addActionListener(e -> {
            timer.stop();
            Stats statsPanel = new Stats(frame, this);
            frame.setContentPane(statsPanel);
            frame.revalidate();
            SwingUtilities.invokeLater(statsPanel::requestFocusInWindow);
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
        ball.draw(g); // draw the ball in the background
    }
}
