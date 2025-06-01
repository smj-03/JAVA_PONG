package org.example;

import javax.swing.*;
import java.awt.*;

//Panel wyboru poziomu trudnosci gry dziedziczy po MainMenu.
public class DifficultyMenu extends MainMenu {

    // Referencja do menu glownego, potrzebna do powrotu po kliknieciu "Back"
    private final MainMenu parent;

    //konstruktor
    public DifficultyMenu(JFrame frame, MainMenu parent) {
        super(frame);
        this.parent = parent;
    }

    //tworzenie i dodawanie przyciskow do panelu
    @Override
    protected void createButtons(JFrame frame) {
        JButton easyButton = new JButton("Easy");
        JButton mediumButton = new JButton("Medium");
        JButton hardButton = new JButton("Hard");
        JButton backButton = new JButton("Back");

        Dimension buttonSize = new Dimension(200, 40);//wspolny rozmiar
        for (JButton b : new JButton[]{easyButton, mediumButton, hardButton, backButton}) {
            b.setMaximumSize(buttonSize);
            b.setAlignmentX(CENTER_ALIGNMENT);
            add(Box.createVerticalStrut(15));
            add(b);
        }

        //Obsluga klikniec przyciskow uruchamiajacych gre z wybrana trudnoscia
        easyButton.addActionListener(e -> startGame(frame, aiDifficulty.EASY));
        mediumButton.addActionListener(e -> startGame(frame, aiDifficulty.MEDIUM));
        hardButton.addActionListener(e -> startGame(frame, aiDifficulty.HARD));
        backButton.addActionListener(e -> {
            frame.setContentPane(parent);
            frame.revalidate();
            parent.resumeAnimation();
        });
    }
    //Metoda pomocnicza do uruchomienia gry z wybranym poziomem trudnosci
    private void startGame(JFrame frame, aiDifficulty difficulty) {
        Game gamePanel = new Game(true, difficulty);
        frame.setContentPane(gamePanel);
        frame.revalidate();
        SwingUtilities.invokeLater(gamePanel::requestFocusInWindow);
        gamePanel.startGame();
    }
}
