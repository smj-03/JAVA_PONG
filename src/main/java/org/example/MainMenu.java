package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

//Klasa MainMenu – ekran startowy gry PONG dziedziczy po JPanel potrzebny do tworzenia ekranu/interfejsu
public class MainMenu extends JPanel {


    private Ball ball;
    private Timer timer;


    private Font font;

    private aiDifficulty difficulty;

    //konstruktor menu
    public MainMenu(JFrame frame) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));//uklad w pionie
        setBackground(Color.BLACK);//czarne tlo

        // inicjalizacja pilki
        ball = new Ball(100, 100, 20, 4, 4, Color.WHITE);

        //co 16 sekund rysuje pilke i odbija ja od sciany menu
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


        //tytul gry i ustawienia wizualne
        JLabel title = new JLabel("Pong Game");
        title.setAlignmentX(CENTER_ALIGNMENT);
        title.setFont(font);
        title.setForeground(Color.WHITE);
        add(Box.createVerticalStrut(100));
        add(title);
        //podpis i ustawienia wizualne
        JLabel author = new JLabel("Created by Chat Enjoyers");
        author.setAlignmentX(CENTER_ALIGNMENT);
        author.setFont(font.deriveFont(24f));
        author.setForeground(Color.GRAY);
        add(author);
        add(Box.createVerticalStrut(100));//odstep
      
        createButtons(frame);//tworzenie przyciskow

    }

    protected void createButtons(JFrame frame) {
        // Panel na przyciski trybu gry
        JPanel playButtonsPanel = new JPanel();
        playButtonsPanel.setMaximumSize(new Dimension(520, 50));
        playButtonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
        playButtonsPanel.setOpaque(false);
      
      //tworzenie przyciskow
        TextButton playWithComputerButton = new TextButton("1 Player");
        TextButton play1v1Button = new TextButton("2 Player");
        TextButton settingsButton = new TextButton("Settings");
        TextButton statsButton = new TextButton("Statistics");
        TextButton quitButton = new TextButton("Quit");

        //wyrownianie przyciskow  do srodka ekranu
        settingsButton.setAlignmentX(CENTER_ALIGNMENT);
        statsButton.setAlignmentX(CENTER_ALIGNMENT);
        quitButton.setAlignmentX(CENTER_ALIGNMENT);

        //rozmiar przyciskow
        Dimension buttonSize = new Dimension(200, 40);
        playWithComputerButton.setPreferredSize(buttonSize);
        play1v1Button.setPreferredSize(buttonSize);
        settingsButton.setMaximumSize(buttonSize);
        statsButton.setMaximumSize(buttonSize);
        quitButton.setMaximumSize(buttonSize);

        // Dodawanie przyciskow do panelu
        playButtonsPanel.add(playWithComputerButton);
        playButtonsPanel.add(play1v1Button);
        add(playButtonsPanel);
        add(Box.createVerticalStrut(20));
        add(settingsButton);
        add(Box.createVerticalStrut(20));
        add(statsButton);
        add(Box.createVerticalStrut(20));
        add(quitButton);

        // Obsługa klikniec

        //Gra z komputerem -> przejście do wyboru trudnosci
        playWithComputerButton.addActionListener(e -> {
            timer.stop();
            org.example.DifficultyMenu difficultyPanel = new org.example.DifficultyMenu(frame, this);
            frame.setContentPane(difficultyPanel);
            frame.revalidate();
            SwingUtilities.invokeLater(difficultyPanel::requestFocusInWindow);
        });

        //Tryb 1v1
        play1v1Button.addActionListener(e -> {
            timer.stop();
            Game gamePanel = new Game(false, aiDifficulty.EASY);
            frame.setContentPane(gamePanel);
            frame.revalidate();
            SwingUtilities.invokeLater(gamePanel::requestFocusInWindow);
            gamePanel.startGame();
        });

        //przejscie do ustawien
        settingsButton.addActionListener(e -> {
            timer.stop();
            SettingsMenu settingsPanel = new SettingsMenu(frame, this);
            frame.setContentPane(settingsPanel);
            frame.revalidate();
            SwingUtilities.invokeLater(settingsPanel::requestFocusInWindow);
        });

        //przejscie do statystyk
        statsButton.addActionListener(e -> {
            timer.stop();
            Stats statsPanel = new Stats(frame, this);
            frame.setContentPane(statsPanel);
            frame.revalidate();
            SwingUtilities.invokeLater(statsPanel::requestFocusInWindow);
        });
        //wyjscie z gry
        quitButton.addActionListener(e -> System.exit(0));
    }

    //wznawianie animacji
    public void resumeAnimation() {
        if (timer != null && !timer.isRunning()) {
            timer.start();
        }
    }

    //rysowanie pilki w tle
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ball.draw(g);
    }
}
