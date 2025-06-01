package org.example;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Panel ustawień – pozwala na wybór:
 *  - koloru piłki,
 *  - koloru paletek,
 *  - prędkości piłki,
 *  - szerokości paletek,
 *  - wysokości paletek.
 * Po każdej zmianie zapisujemy do pliku config.properties.
 */
public class SettingsMenu extends JPanel {
    public SettingsMenu(JFrame frame, JPanel mainMenuPanel) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.DARK_GRAY);
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // ---------- Nagłówek ----------
        JLabel title = new JLabel("Ustawienia Gry");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        add(title);
        add(Box.createRigidArea(new Dimension(0, 30)));

        // ---------- Wybór koloru piłki ----------
        JButton ballColorButton = new JButton("Wybierz kolor piłki");
        ballColorButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        ballColorButton.setMaximumSize(new Dimension(280, 40));
        ballColorButton.setFont(new Font("Arial", Font.PLAIN, 16));
        ballColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color chosen = JColorChooser.showDialog(
                        SettingsMenu.this,
                        "Wybierz kolor piłki",
                        Settings.ballColor
                );
                if (chosen != null) {
                    Settings.ballColor = chosen;
                    Settings.saveProperties();
                }
            }
        });
        add(ballColorButton);
        add(Box.createRigidArea(new Dimension(0, 20)));

        // ---------- Wybór koloru paletek ----------
        JButton paddleColorButton = new JButton("Wybierz kolor paletek");
        paddleColorButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        paddleColorButton.setMaximumSize(new Dimension(280, 40));
        paddleColorButton.setFont(new Font("Arial", Font.PLAIN, 16));
        paddleColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color chosen = JColorChooser.showDialog(
                        SettingsMenu.this,
                        "Wybierz kolor paletek",
                        Settings.paddleColor
                );
                if (chosen != null) {
                    Settings.paddleColor = chosen;
                    Settings.saveProperties();
                }
            }
        });
        add(paddleColorButton);
        add(Box.createRigidArea(new Dimension(0, 30)));

        // ---------- Prędkosc pilki ----------
        JPanel ballSpeedPanel = new JPanel();
        ballSpeedPanel.setLayout(new BoxLayout(ballSpeedPanel, BoxLayout.X_AXIS));
        ballSpeedPanel.setBackground(Color.DARK_GRAY);

        JLabel ballSpeedLabel = new JLabel("Prędkość piłki: ");
        ballSpeedLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        ballSpeedLabel.setForeground(Color.WHITE);
        ballSpeedPanel.add(ballSpeedLabel);

        JSpinner ballSpeedSpinner = new JSpinner(
                new SpinnerNumberModel(Settings.ballSpeed, 1, 20, 1)
        );
        ((JSpinner.DefaultEditor) ballSpeedSpinner.getEditor()).getTextField().setColumns(2);
        ballSpeedSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int nowaPredkosc = (Integer) ballSpeedSpinner.getValue();
                Settings.ballSpeed = nowaPredkosc;
                Settings.saveProperties();
            }
        });
        ballSpeedPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        ballSpeedPanel.add(ballSpeedSpinner);
        ballSpeedPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(ballSpeedPanel);
        add(Box.createRigidArea(new Dimension(0, 20)));

        // ---------- Szerokość paletek (spinner) ----------
        JPanel paddleWidthPanel = new JPanel();
        paddleWidthPanel.setLayout(new BoxLayout(paddleWidthPanel, BoxLayout.X_AXIS));
        paddleWidthPanel.setBackground(Color.DARK_GRAY);

        JLabel paddleWidthLabel = new JLabel("Szerokość paletek: ");
        paddleWidthLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        paddleWidthLabel.setForeground(Color.WHITE);
        paddleWidthPanel.add(paddleWidthLabel);

        JSpinner paddleWidthSpinner = new JSpinner(
                new SpinnerNumberModel(Settings.paddleWidth, 5, 100, 1)
        );
        ((JSpinner.DefaultEditor) paddleWidthSpinner.getEditor()).getTextField().setColumns(3);
        paddleWidthSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int nowaSzerokosc = (Integer) paddleWidthSpinner.getValue();
                Settings.paddleWidth = nowaSzerokosc;
                Settings.saveProperties();
            }
        });
        paddleWidthPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        paddleWidthPanel.add(paddleWidthSpinner);
        paddleWidthPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(paddleWidthPanel);
        add(Box.createRigidArea(new Dimension(0, 20)));

        // ---------- Wysokość paletek (spinner) ----------
        JPanel paddleHeightPanel = new JPanel();
        paddleHeightPanel.setLayout(new BoxLayout(paddleHeightPanel, BoxLayout.X_AXIS));
        paddleHeightPanel.setBackground(Color.DARK_GRAY);

        JLabel paddleHeightLabel = new JLabel("Wysokość paletek: ");
        paddleHeightLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        paddleHeightLabel.setForeground(Color.WHITE);
        paddleHeightPanel.add(paddleHeightLabel);

        JSpinner paddleHeightSpinner = new JSpinner(
                new SpinnerNumberModel(Settings.paddleHeight, 20, 500, 5)
        );
        ((JSpinner.DefaultEditor) paddleHeightSpinner.getEditor()).getTextField().setColumns(3);
        paddleHeightSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int nowaWysokosc = (Integer) paddleHeightSpinner.getValue();
                Settings.paddleHeight = nowaWysokosc;
                Settings.saveProperties();
            }
        });
        paddleHeightPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        paddleHeightPanel.add(paddleHeightSpinner);
        paddleHeightPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(paddleHeightPanel);
        add(Box.createRigidArea(new Dimension(0, 30)));

        // ---------- Przycisk Powrót do menu ----------
        JButton backButton = new JButton("Powrót");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setMaximumSize(new Dimension(200, 35));
        backButton.setFont(new Font("Arial", Font.PLAIN, 16));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Po kliknięciu „Powrót” wracamy do głównego menu:
                frame.setContentPane(mainMenuPanel);
                frame.revalidate();
                SwingUtilities.invokeLater(() -> {
                    mainMenuPanel.requestFocusInWindow();
                    // Jeśli mainMenuPanel to np. instance MainMenu – możemy wznowić animację
                    if (mainMenuPanel instanceof MainMenu) {
                        ((MainMenu) mainMenuPanel).resumeAnimation();
                    }
                });
            }
        });
        add(backButton);
    }
}
