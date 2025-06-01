package org.example;

import java.awt.Color;
import java.io.*;
import java.util.Properties;


 //Klasa odpowiedzialna za przechowywanie ustawien gry,
 //ich ladowanie z pliku config.properties i zapisywanie po zmianie.
public class Settings {
    // sciezka do pliku konfiguracyjnego
    private static final String CONFIG_FILE = "config.properties";

    // Domyslne wartosci (jeśli plik nie istnieje lub właściwość nie została określona)
    public static Color ballColor   = Color.WHITE;
    public static Color paddleColor = Color.WHITE;
    public static int ballSpeed     = 3;
    public static int paddleWidth   = 15;
    public static int paddleHeight  = 150;
    //Blok statyczny – wykonuje sie automatycznie przy pierwszym zaladowaniu klasy
    static {
        // W bloku statycznym od razu ladujemy plik konfiguracji
        loadProperties();
    }

//Wczytuje plik config.properties (jesli istnieje) i ustawia wartosci w polach statycznych.
//Jesli cos sie nie powiedzie, pozostawia domyslne wartosci

    static void loadProperties() {
        Properties props = new Properties();
        File plik = new File(CONFIG_FILE);
        if (!plik.exists()) {
            // Plik nie istnieje, nic nie wczytujemy – uzyja sie wartosci domyslne.
            return;
        }

        try (FileInputStream in = new FileInputStream(plik)) {
            props.load(in);// ladowanie kluczy i wartosci z pliku .properties

            // 1) Kolor pilki (zapisywany jako liczba RGB)
            String ballColorStr = props.getProperty("ballColorRGB");
            if (ballColorStr != null) {
                try {
                    int rgb = Integer.parseInt(ballColorStr);
                    ballColor = new Color(rgb);
                } catch (NumberFormatException ignored) { }
            }

            // 2) Kolor paletek
            String paddleColorStr = props.getProperty("paddleColorRGB");
            if (paddleColorStr != null) {
                try {
                    int rgb = Integer.parseInt(paddleColorStr);
                    paddleColor = new Color(rgb);
                } catch (NumberFormatException ignored) { }
            }

            // 3) Predkosc pilki
            String ballSpeedStr = props.getProperty("ballSpeed");
            if (ballSpeedStr != null) {
                try {
                    int s = Integer.parseInt(ballSpeedStr);
                    if (s > 0) {
                        ballSpeed = s;
                    }
                } catch (NumberFormatException ignored) { }
            }

            // 4) Rozmiar paletek: szerokosc
            String paddleWidthStr = props.getProperty("paddleWidth");
            if (paddleWidthStr != null) {
                try {
                    int w = Integer.parseInt(paddleWidthStr);
                    if (w > 0) {
                        paddleWidth = w;
                    }
                } catch (NumberFormatException ignored) { }
            }

            // 5) Rozmiar paletek: wysokosc
            String paddleHeightStr = props.getProperty("paddleHeight");
            if (paddleHeightStr != null) {
                try {
                    int h = Integer.parseInt(paddleHeightStr);
                    if (h > 0) {
                        paddleHeight = h;
                    }
                } catch (NumberFormatException ignored) { }
            }

        } catch (IOException e) {
            // Jeśli nie udało się wczytac – dzialamy na domyslnych wartosciach
            System.err.println("Nie udalo się wczytac config.properties: " + e.getMessage());
        }
    }

    //zapis ustawien
    public static void saveProperties() {
        Properties props = new Properties();

        // Zapisujemy kolory jako liczby RGB (int zwrocony przez Color.getRGB())
        props.setProperty("ballColorRGB", Integer.toString(ballColor.getRGB()));
        props.setProperty("paddleColorRGB", Integer.toString(paddleColor.getRGB()));

        // Zapisujemy pozostale opcje jako ciagi liczb calkowitych
        props.setProperty("ballSpeed", Integer.toString(ballSpeed));
        props.setProperty("paddleWidth", Integer.toString(paddleWidth));
        props.setProperty("paddleHeight", Integer.toString(paddleHeight));

        //proba zapisania danych do pliku
        try (FileOutputStream out = new FileOutputStream(CONFIG_FILE)) {
            props.store(out, "Ustawienia gry (kolory, prędkość, rozmiar paletek)");
        } catch (IOException e) {
            System.err.println("Nie udało się zapisać config.properties: " + e.getMessage());
        }
    }
}
