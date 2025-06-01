package org.example;

import javax.swing.*;
import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import com.google.gson.Gson;

public class Stats extends MainMenu{

    private final MainMenu parent;
    public Stats(JFrame frame, MainMenu parent) {
        super(frame);
        this.parent = parent;
        showStats();
    }

    private void showStats() {
        StatisticsData stats = loadStatsFromJson();
        String statsText;
        if (stats != null) {
            statsText = "<html>"
                    + "<div style='text-align:center;'>"
                    + "<h3>Statystyki gier</h3>"
                    + "<table style='margin: 0 auto; text-align:left;'>"
                    + "<tr><th>Poziom</th><th>Wygrane</th><th>Przegrane</th></tr>"
                    + "<tr><td><b>Easy</b></td><td>" + stats.easyWins + "</td><td>" + stats.easyLosses + "</td></tr>"
                    + "<tr><td><b>Medium</b></td><td>" + stats.mediumWins + "</td><td>" + stats.mediumLosses + "</td></tr>"
                    + "<tr><td><b>Hard</b></td><td>" + stats.hardWins + "</td><td>" + stats.hardLosses + "</td></tr>"
                    + "</table>"
                    + "<p><b>Liczba rozegranych gier:</b> " + stats.gamesPlayed + "</p>"
                    + "</div>"
                    + "</html>";
        } else {
            statsText = "Brak danych statystyk.";
        }

        JLabel statsLabel = new JLabel(statsText);
        statsLabel.setFont(new Font("Arial", Font.BOLD, 12));
        statsLabel.setForeground(Color.WHITE);

        // Pomocniczy panel do centrowania
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.setOpaque(false); // przezroczysty, by nie zakrywał tła
        centerPanel.add(statsLabel);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(Box.createVerticalGlue());
        add(centerPanel);
        add(Box.createVerticalGlue());
    }

    private StatisticsData loadStatsFromJson() {
        try (FileReader reader = new FileReader("src/main/resources/stats.json")) {
            return new Gson().fromJson(reader, StatisticsData.class);
        } catch (IOException e) {
            return null;
        }
    }
    @Override
    protected void createButtons(JFrame frame) {
        JButton backButton = new JButton("Back");
        backButton.setMaximumSize(new Dimension(200, 40));
        backButton.setAlignmentX(CENTER_ALIGNMENT);
        add(Box.createVerticalStrut(15));
        add(backButton);

        backButton.addActionListener(e -> {
            frame.setContentPane(parent);
            frame.revalidate();
            parent.resumeAnimation();
        });
    }
}
