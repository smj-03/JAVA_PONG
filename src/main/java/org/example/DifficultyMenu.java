package org.example;

import javax.swing.*;
import java.awt.*;

public class DifficultyMenu extends MainMenu {

    private final MainMenu parent;

    public DifficultyMenu(JFrame frame, MainMenu parent) {
        super(frame);
        this.parent = parent;
    }

    @Override
    protected void createButtons(JFrame frame) {
        TextButton easyButton = new TextButton("Easy");
        TextButton mediumButton = new TextButton("Medium");
        TextButton hardButton = new TextButton("Hard");
        TextButton backButton = new TextButton("Back");

        Dimension buttonSize = new Dimension(200, 40);
        for (TextButton b : new TextButton[]{easyButton, mediumButton, hardButton, backButton}) {
            b.setMaximumSize(buttonSize);
            b.setAlignmentX(CENTER_ALIGNMENT);
            add(Box.createVerticalStrut(15));
            add(b);
        }

        easyButton.addActionListener(e -> startGame(frame, aiDifficulty.EASY));
        mediumButton.addActionListener(e -> startGame(frame, aiDifficulty.MEDIUM));
        hardButton.addActionListener(e -> startGame(frame, aiDifficulty.HARD));
        backButton.addActionListener(e -> {
            frame.setContentPane(parent);
            frame.revalidate();
            parent.resumeAnimation();
        });
    }

    private void startGame(JFrame frame, aiDifficulty difficulty) {
        Game gamePanel = new Game(true, difficulty);
        frame.setContentPane(gamePanel);
        frame.revalidate();
        SwingUtilities.invokeLater(gamePanel::requestFocusInWindow);
        gamePanel.startGame();
    }
}
