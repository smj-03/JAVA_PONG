package org.example;

import static org.junit.jupiter.api.Assertions.*;
import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SettingsTest {

    private static final String CONFIG_FILE = "config.properties";
    private File configFile;

    @BeforeEach
    void setUp() {
        // Jeżeli w bieżącym katalogu istnieje config.properties, przenieśmy go (na wszelki wypadek).
        configFile = new File(CONFIG_FILE);
        if (configFile.exists()) {
            // np. tymczasowo zmieniamy nazwę na config_backup.properties
            File backup = new File("config_backup.properties");
            configFile.renameTo(backup);
        }
        // Ustawiamy wartości domyślne w Settings – takie, jakie są zadeklarowane w klasie.
        // Jeżeli ktoś to w międzyczasie zmodyfikował, przywracamy na start „fabryczne”.
        Settings.ballColor = Color.WHITE;
        Settings.paddleColor = Color.WHITE;
        Settings.ballSpeed = 5;
        Settings.paddleWidth = 10;
        Settings.paddleHeight = 50;
    }

    @AfterEach
    void tearDown() {
        // Po teście usuwamy plik config.properties, aby nie zostawiać śladu.
        if (configFile.exists()) {
            configFile.delete();
        }
        // A jeżeli istniał backup z @BeforeEach, przywracamy go.
        File backup = new File("config_backup.properties");
        if (backup.exists()) {
            backup.renameTo(new File(CONFIG_FILE));
        }
    }

    @Test
    void testSaveAndLoadProperties_ShouldPersistValues() throws IOException {
        // Krok 1: zmieńmy teraz ustawienia
        Settings.ballColor = Color.RED;
        Settings.paddleColor = Color.GREEN;
        Settings.ballSpeed = 123;
        Settings.paddleWidth = 77;
        Settings.paddleHeight = 88;

        // Krok 2: zapiszmy do pliku
        Settings.saveProperties();

        // Zweryfikujmy, że plik istnieje i coś w środku jest
        assertTrue(configFile.exists(),
                "Po saveProperties() powinien pojawić się plik config.properties");

        // Krok 3: nadpiszmy wartości w pamięci, aby nie korzystały z tych, które zapisaliśmy
        Settings.ballColor = Color.WHITE;
        Settings.paddleColor = Color.WHITE;
        Settings.ballSpeed = 5;
        Settings.paddleWidth = 10;
        Settings.paddleHeight = 50;

        // Krok 4: wczytajmy z pliku
        Settings.loadProperties();

        // Krok 5: upewnijmy się, że wartości są odczytane poprawnie
        assertEquals(Color.RED.getRGB(), Settings.ballColor.getRGB(),
                "Po wczytaniu z pliku ballColor powinien być RED (czyli RGB=RED.getRGB())");
        assertEquals(Color.GREEN.getRGB(), Settings.paddleColor.getRGB(),
                "Po wczytaniu paddleColor powinien być GREEN");
        assertEquals(123, Settings.ballSpeed,
                "Po wczytaniu ballSpeed powinno być 123");
        assertEquals(77, Settings.paddleWidth,
                "Po wczytaniu paddleWidth powinno być 77");
        assertEquals(88, Settings.paddleHeight,
                "Po wczytaniu paddleHeight powinno być 88");
    }

    @Test
    void testLoadProperties_NoFile_ShouldKeepDefaults() {
        // Jeżeli config.properties nie istnieje, loadProperties złapie IOException
        // i pozostawi ustawienia domyślne. Już w @BeforeEach usunęliśmy plik, więc testujemy
        //
        // Na starcie: Settings.ballColor = WHITE, paddleColor = WHITE, ballSpeed=5 etc.
        Settings.loadProperties();

        assertEquals(Color.WHITE.getRGB(), Settings.ballColor.getRGB(),
                "Jeśli nie ma pliku, ballColor ma zostać WHITE");
        assertEquals(Color.WHITE.getRGB(), Settings.paddleColor.getRGB(),
                "Jeśli nie ma pliku, paddleColor ma zostać WHITE");
        assertEquals(5, Settings.ballSpeed,
                "Jeśli nie ma pliku, ballSpeed ma zostać domyślne 5");
        assertEquals(10, Settings.paddleWidth,
                "Jeśli nie ma pliku, paddleWidth ma zostać domyślne 10");
        assertEquals(50, Settings.paddleHeight,
                "Jeśli nie ma pliku, paddleHeight ma zostać domyślne 50");
    }
}
