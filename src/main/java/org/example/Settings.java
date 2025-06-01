package org.example;

import java.awt.Color;
import java.io.*;
import java.util.Properties;

/**
 * Klasa odpowiedzialna za przechowywanie ustawień gry,
 * ich ładowanie z pliku config.properties i zapisywanie po zmianie.
 */
public class Settings {
    // Ścieżka do pliku konfiguracyjnego (w katalogu roboczym aplikacji)
    private static final String CONFIG_FILE = "config.properties";

    // Domyślne wartości (jeśli plik nie istnieje lub właściwość nie została określona)
    public static Color ballColor   = Color.WHITE;
    public static Color paddleColor = Color.BLUE;
    public static int ballSpeed     = 3;
    public static int paddleWidth   = 15;
    public static int paddleHeight  = 150;

    static {
        // W bloku statycznym od razu ładujemy plik konfiguracji
        loadProperties();
    }

    /**
     * Wczytuje plik config.properties (jeśli istnieje) i ustawia wartości w polach statycznych.
     * Jeśli coś się nie powiedzie, pozostawia domyślne wartości.
     */
    static void loadProperties() {
        Properties props = new Properties();
        File plik = new File(CONFIG_FILE);
        if (!plik.exists()) {
            // Plik nie istnieje, nic nie wczytujemy – użyją się wartości domyślne.
            return;
        }

        try (FileInputStream in = new FileInputStream(plik)) {
            props.load(in);

            // 1) Kolor piłki (zapisywany jako liczba RGB)
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

            // 3) Prędkość piłki
            String ballSpeedStr = props.getProperty("ballSpeed");
            if (ballSpeedStr != null) {
                try {
                    int s = Integer.parseInt(ballSpeedStr);
                    if (s > 0) {
                        ballSpeed = s;
                    }
                } catch (NumberFormatException ignored) { }
            }

            // 4) Rozmiar paletek: szerokość
            String paddleWidthStr = props.getProperty("paddleWidth");
            if (paddleWidthStr != null) {
                try {
                    int w = Integer.parseInt(paddleWidthStr);
                    if (w > 0) {
                        paddleWidth = w;
                    }
                } catch (NumberFormatException ignored) { }
            }

            // 5) Rozmiar paletek: wysokość
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
            // Jeśli nie udało się wczytać – działamy na domyślnych wartościach
            System.err.println("Nie udało się wczytać config.properties: " + e.getMessage());
        }
    }

    /**
     * Zapisuje bieżące wartości ustawień do pliku config.properties.
     * Jeśli plik nie istnieje, zostanie utworzony.
     */
    public static void saveProperties() {
        Properties props = new Properties();

        // Zapisujemy kolory jako liczby RGB (int zwrócony przez Color.getRGB())
        props.setProperty("ballColorRGB", Integer.toString(ballColor.getRGB()));
        props.setProperty("paddleColorRGB", Integer.toString(paddleColor.getRGB()));

        // Zapisujemy pozostałe opcje jako ciągi liczb całkowitych
        props.setProperty("ballSpeed", Integer.toString(ballSpeed));
        props.setProperty("paddleWidth", Integer.toString(paddleWidth));
        props.setProperty("paddleHeight", Integer.toString(paddleHeight));

        try (FileOutputStream out = new FileOutputStream(CONFIG_FILE)) {
            props.store(out, "Ustawienia gry (kolory, prędkość, rozmiar paletek)");
        } catch (IOException e) {
            System.err.println("Nie udało się zapisać config.properties: " + e.getMessage());
        }
    }
}
