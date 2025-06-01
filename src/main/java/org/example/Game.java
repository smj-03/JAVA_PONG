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

//Klasa Game odpowiada za logike gry w Pong.
//Dziedziczy po JPanel, implementuje Runnable (petle gry) oraz KeyListener obsluga klawiatury
public class Game extends JPanel implements Runnable, KeyListener {
    //Watek odpowiadający za petlę gry
    private Thread gameThread;
    //czy gra uruchomiona
    private boolean running = false;

    //Paletki graczy
    private final Paddle user1Paddle;
    private final Paddle user2Paddle;
    //Referencja do ostatniej paletki, ktora uderzyla pilke
    private Paddle lastHitPaddle;
    //Referencja do paletki, ktora bedzie następna uderzac pilke
    private Paddle willHitPaddle;

    // Flagi klawiszy dla sterowania paletkami(gracz 1: W i S)
    private boolean upPressedPaddle1 = false;
    private boolean downPressedPaddle1 = false;

    // Flagi klawiszy dla sterowania paletkami(gracz 2: strzalki)
    private boolean upPressedPaddle2 = false;
    private boolean downPressedPaddle2 = false;
    //Flaga okreslajaca, czy klawisz ESC zostal nacisniety
    private boolean escapePressed = false;

    private Ball ball;
    private PowerUp powerUp;
    private int user1Score, user2Score;

    private final JButton stopButton;
    private final JButton playAgainButton; // pole w klasie Game
    private final JButton returnToMenuButton;

    private boolean vsAI;

    private aiDifficulty aiDifficulty; //pole w klasie Game
    //konstruktor
    public Game(boolean vsAI, aiDifficulty aiDifficulty) {
        this.vsAI = vsAI;
        this.aiDifficulty = aiDifficulty; //bazowy poziom trudnosci Ai

        setLayout(null);
        setFocusable(true); //umozliwia odbieranie zdarzen z kalwaitruy przez panel
        requestFocusInWindow();
        addKeyListener(this); //key listener

        // utworzenie paletek
        user1Paddle = new Paddle(10, 200, Settings.paddleWidth, Settings.paddleHeight, Settings.paddleColor);
        user2Paddle = new Paddle(760, 200, Settings.paddleWidth, Settings.paddleHeight, Settings.paddleColor);

        // poczatkowe wyniki graczy
        user1Score = 0;
        user2Score = 0;

        //tworzenie obiektu pilki
        ball = new Ball(400, 300, 20, Settings.ballSpeed, Settings.ballSpeed, Settings.ballColor);
        // Tworzenie i konfiguracja przycisku "Stop Game"
        stopButton = new JButton("Stop Game");
        stopButton.setBounds(350, 10, 100, 30); // Position the button
        stopButton.addActionListener(e -> {
            toggleGameState();
            requestFocusInWindow();
        });
        add(stopButton);

        // Tworzenie i konfiguracja przycisku "Play Again" (ukryty na start)
        playAgainButton = new JButton("Play Again");
        playAgainButton.setBounds(350, 10, 100, 30); // Position the button
        playAgainButton.setVisible(false);
        playAgainButton.addActionListener(new ActionListener() {

            // Po kliknięciu przycisku "Play Again"
            @Override
            public void actionPerformed(ActionEvent e) {
                playAgainButton.setVisible(false); // ukrycie przycisku "Play Again"
                stopButton.setVisible(true); // pokazanie przycisku "Stop Game"
                returnToMenuButton.setVisible(false); // ukrycie przycisku "Return to Menu"
                resetGame(); // reset gry
                user1Score = 0;
                user2Score = 0;
                startGame(); // uruchom gre od nowa
            }
        });
        add(playAgainButton);

        //Tworzenie i konfiguracja przycisku "Return to Menu"
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

        spawnPowerUp();
    }

    //Metoda uruchamiajaca gre
    public void startGame() {
        if (running) return;
        running = true;
        requestFocusInWindow(); // fokus na klawiature
        gameThread = new Thread(this); // utworzenie watku gry
        gameThread.start(); // uruchomienie watku gry
    }

    //metoda zatrzymujaca gre
    public void stopGame() {
        running = false;
        try {
            gameThread.join(); //czekanie na zakonczenie watku gry
        } catch (InterruptedException e) {
            e.printStackTrace(); //obsluga wyjatku w razie przerwania
        }
    }

    public void endGame() {
        running = false; // Zatrzymaj pętlę gry
        resetGame();
        // Opcjonalnie: pokaz przycisk powrotu do menu lub ponownego startu gry
        returnToMenuButton.setVisible(true);
        playAgainButton.setVisible(true);
        stopButton.setVisible(false);
        StatisticsData stats = loadStatsFromJson();
        stats.gamesPlayed++; // Zwieksz liczbę rozegranych gier
        if (vsAI) {
            if (stats != null) {
                // Aktualizuj statystyki na podstawie wyniku
                if (user1Score > user2Score) {
                    if (aiDifficulty == aiDifficulty.EASY) {
                        stats.easyWins++;
                    } else if (aiDifficulty == aiDifficulty.MEDIUM) {
                        stats.mediumWins++;
                    } else if (aiDifficulty == aiDifficulty.HARD) {
                        stats.hardWins++;
                    }
                } else {
                    if (aiDifficulty == aiDifficulty.EASY) {
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

    //glowna petla gry
    //Aktualizuje stan gry, rysuje elementy, obsluguje kolizje, sprawdza warunki punktacji i konca gry.
    @Override
    public void run() {
        // Glowna petla gry – wykonuje sie, dopoki flaga running jest true
        while (running) {
            updateGame(); // aktualizacja stanu paletek
            repaint();    // renderowanie gry, paintComponent() w celu odrysowania ekranu

            ball.move(); // ruch pilki
            ball.bounceOffWalls(0, getHeight()); // odbicie pilki od gornej i dolnej krawedzi
            //sprawdzanie kolizji pilka paletka dla gracza 1
            if (user1Paddle.intersects(ball)) {
                lastHitPaddle = user1Paddle;
                willHitPaddle = user2Paddle;
                ball.bounceFromPaddle(user1Paddle);
            }

            //sprawdzanie kolizji pilka paletka dla gracza 2
            if (user2Paddle.intersects(ball)) {
                lastHitPaddle = user2Paddle;
                willHitPaddle = user1Paddle;
                ball.bounceFromPaddle(user2Paddle);
            }

            //Jesli power-up istnieje i koliduje z pilka – zastosuj efekt power-upa
            if (powerUp != null && powerUp.intersects(ball)) applyPowerUp();

            //Sprawdzenie, czy pilka wyszla poza krawedz – punkt dla gracza
            if (ball.getX() < 0) {
                user2Score++;
                resetGame();
            } else if (ball.getX() + ball.getWidth() > getWidth()) {
                user1Score++;
                resetGame();
            }

            //Sprawdzenie warunku zakonczenia gry
            if (user1Score >= 5 || user2Score >= 5) {
                running = false;
                endGame();
            }
            // Opoznienie petli na około 16 ms dla okolo 60 klatek na sekunde
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //Metoda przelaczajaca stan gry: jesli gra dziala, zatrzymaj; jesli zatrzymana – wznow.
    //Aktualizuje takze tekst przycisku "Stop/Resume Game" i widocznosc przycisku "Return to Menu".
    private void toggleGameState() {
        if (running) {
            stopGame();
            stopButton.setText("Resume Game");
            returnToMenuButton.setVisible(true);
        } else {
            startGame();
            stopButton.setText("Stop Game");
            returnToMenuButton.setVisible(false);
        }
    }

    //Metoda aktualizujaca stan gry: ruch paletek graczy lub AI.
    private void updateGame() {
        //ruch paletek
        if (upPressedPaddle1) {
            user1Paddle.moveYWithinBounds(user1Paddle.getY() - 5, getHeight()); // move up player 1
        }
        if (downPressedPaddle1) {
            user1Paddle.moveYWithinBounds(user1Paddle.getY() + 5, getHeight()); // move down player 1
        }
        //sterowanie paletka ai
        if (vsAI) {
            int paddleCenter = user2Paddle.getY() + user2Paddle.getHeight() / 2;
            int ballCenter = ball.getY() + ball.getHeight() / 2;
            int dy = ballCenter - paddleCenter;

            int deadZone = 20; //jeśli piłka jest blisko środka paletki, AI nie ruszy paletki
            int speed; //szybkosc paletki ai
            //szybkosc paletki ai w zaleznosci od poziomu trudnosci
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
            //jezeli przekracza deadzone to paletka roza sie w dana strefe
            if (Math.abs(dy) > deadZone) {
                int direction = (dy > 0) ? 1 : -1;
                user2Paddle.moveYWithinBounds(user2Paddle.getY() + direction * speed, getHeight());
            }
        } else {
            if (upPressedPaddle2) {
                user2Paddle.moveYWithinBounds(user2Paddle.getY() - 5, getHeight()); // move up player 2
            }
            if (downPressedPaddle2) {
                user2Paddle.moveYWithinBounds(user2Paddle.getY() + 5, getHeight()); // move down player 2
            }
        }

    }
    //Metoda resetująca stan gry
    public void resetGame() {
        lastHitPaddle = null;
        willHitPaddle = null;
        user1Paddle.resetLength();
        user2Paddle.resetLength();

        //pause for a second
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Reset game state for a new game
        ball = new Ball(400, 300, 20, Settings.ballSpeed, Settings.ballSpeed, Settings.ballColor); // Reset ball position and speed
    }

    //Metoda wywolywana przy nacisnieciu klawisza
    @Override
    public void keyPressed(KeyEvent e) {
        // Sterowanie paletka gracza 1: W – w gore, S – w dol
        if (e.getKeyCode() == KeyEvent.VK_W) {
            upPressedPaddle1 = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            downPressedPaddle1 = true;
        }
        //Sterowanie paletka gracza 2: strzalki
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            upPressedPaddle2 = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            downPressedPaddle2 = true;
        }
        //wcisniecie ESC
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE && !escapePressed) {
            escapePressed = true;
            toggleGameState();
        }
    }

    //Metoda wywolywana przy zwolnieniu klawisza analogiczna do metody przy nacisnieciu
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            upPressedPaddle1 = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            downPressedPaddle1 = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_UP) {
            upPressedPaddle2 = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            downPressedPaddle2 = false;
        }
        //Przelaczanie stanu gry za pomoca klawisza ESC
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            escapePressed = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // not used
    }
    //Metoda rysująca komponenty gry
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        //rysowanie paletek
        user1Paddle.draw(g);
        user2Paddle.draw(g);

        //rysowanie pilki
        ball.draw(g);


        //rysowanie wynikow
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString("Player 1: " + user1Score, 50, 30);
        g.drawString("Player 2: " + user2Score, getWidth() - 150, 30);

        if (powerUp != null) {
            powerUp.draw(g);
        }
    }
    //Metoda stosująca efekt power-upa po kolizji z piłką
    private void applyPowerUp() {
        int number = (int) (Math.random() * 2) + 1;
        switch (number) {
            case 1:
                PowerUp.changePaddleLength(lastHitPaddle, 100);
                break;
            case 2:
                PowerUp.changePaddleLength(willHitPaddle, -100);
                break;
        }

        powerUp = null; // Remove the power-upi
        spawnPowerUp();
    }

    //Metoda tworząca power-up losowo po upływie 10 sekund od ostatniego spawnu
    private void spawnPowerUp() {
        Timer powerUpTimer = new Timer(10000, e -> {
            int randomX = (int) (Math.random() * 500) + 100;
            int randomY = (int) (Math.random() * 300) + 100;
            powerUp = new PowerUp(randomX, randomY, 50, 50, Color.GREEN);
            repaint();
        });
        powerUpTimer.setRepeats(false);
        powerUpTimer.start();
    }
    //Metoda wczytująca statystyki z pliku JSON.
    private StatisticsData loadStatsFromJson() {
        try (FileReader reader = new FileReader("src/main/resources/stats.json")) {
            return new Gson().fromJson(reader, StatisticsData.class);
        } catch (IOException e) {
            return null;
        }
    }
}
