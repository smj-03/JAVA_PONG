package org.example;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Panel ustawien – pozwala na wybor:
 *  - koloru pilki,
 *  - koloru paletek,
 *  - predkosci pilki,
 *  - szerokosci paletek,
 *  - wysokosci paletek.
 * Po kazdej zmianie zapisujemy do pliku config.properties.
 * za pomocą klasy `Settings`
 */
public class SettingsMenu extends JPanel {
    //Ustalona szerokosc dla wszystkich elementow interfejsu
    private static final int BOX_WIDTH = 300;

    //konstruktor
    public SettingsMenu(JFrame frame, JPanel mainMenuPanel) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.BLACK);
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        //tytul
        JLabel title = new JLabel("Ustawienia Gry");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setMaximumSize(new Dimension(BOX_WIDTH, 35));
        add(title);
        add(Box.createRigidArea(new Dimension(0, 30)));

        //wybor koloru pilki i zapis do pliku
        JButton ballColorButton = new JButton("Wybierz kolor piłki");
        ballColorButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        ballColorButton.setMaximumSize(new Dimension(BOX_WIDTH, 40));
        ballColorButton.setFont(new Font("Arial", Font.PLAIN, 16));
        ballColorButton.addActionListener(e -> {
            Color chosen = JColorChooser.showDialog(
                    SettingsMenu.this,
                    "Wybierz kolor piłki",
                    Settings.ballColor
            );
            if (chosen != null) {
                Settings.ballColor = chosen;
                Settings.saveProperties();//zapis
            }
        });
        add(ballColorButton);
        add(Box.createRigidArea(new Dimension(0, 20)));

        //wybor koloru paletki i zapis do pliku
        JButton paddleColorButton = new JButton("Wybierz kolor paletek");
        paddleColorButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        paddleColorButton.setMaximumSize(new Dimension(BOX_WIDTH, 40));
        paddleColorButton.setFont(new Font("Arial", Font.PLAIN, 16));
        paddleColorButton.addActionListener(e -> {
            Color chosen = JColorChooser.showDialog(
                    SettingsMenu.this,
                    "Wybierz kolor paletek",
                    Settings.paddleColor
            );
            if (chosen != null) {
                Settings.paddleColor = chosen;
                Settings.saveProperties();
            }
        });
        add(paddleColorButton);
        add(Box.createRigidArea(new Dimension(0, 30)));

        //predkosc pilki i zapis do pliku
        JPanel ballSpeedPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        ballSpeedPanel.setBackground(Color.BLACK);
        ballSpeedPanel.setMaximumSize(new Dimension(BOX_WIDTH,  35));
        ballSpeedPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel ballSpeedLabel = new JLabel("Prędkość piłki:");
        ballSpeedLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        ballSpeedLabel.setForeground(Color.WHITE);
        ballSpeedLabel.setHorizontalAlignment(SwingConstants.CENTER);
        ballSpeedPanel.add(ballSpeedLabel);

        JSpinner ballSpeedSpinner = new JSpinner(
                new SpinnerNumberModel(Settings.ballSpeed, 1, 20, 1)
        );
        ((JSpinner.DefaultEditor) ballSpeedSpinner.getEditor()).getTextField().setColumns(2);
        ballSpeedSpinner.addChangeListener(e -> {
            int nowaPredkosc = (Integer) ballSpeedSpinner.getValue();
            Settings.ballSpeed = nowaPredkosc;
            Settings.saveProperties();
        });
        ballSpeedPanel.add(ballSpeedSpinner);

        add(ballSpeedPanel);
        add(Box.createRigidArea(new Dimension(0, 20)));

        //szerokosc paletek i zapis do pliku
        JPanel paddleWidthPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        paddleWidthPanel.setBackground(Color.BLACK);
        paddleWidthPanel.setMaximumSize(new Dimension(BOX_WIDTH,  35));
        paddleWidthPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel paddleWidthLabel = new JLabel("Szerokość paletek:");
        paddleWidthLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        paddleWidthLabel.setForeground(Color.WHITE);
        paddleWidthLabel.setHorizontalAlignment(SwingConstants.CENTER);
        paddleWidthPanel.add(paddleWidthLabel);

        JSpinner paddleWidthSpinner = new JSpinner(
                new SpinnerNumberModel(Settings.paddleWidth, 5, 100, 1)
        );
        ((JSpinner.DefaultEditor) paddleWidthSpinner.getEditor()).getTextField().setColumns(3);
        paddleWidthSpinner.addChangeListener(e -> {
            int newWidth = (Integer) paddleWidthSpinner.getValue();
            Settings.paddleWidth = newWidth;
            Settings.saveProperties();
        });
        paddleWidthPanel.add(paddleWidthSpinner);

        add(paddleWidthPanel);
        add(Box.createRigidArea(new Dimension(0, 20)));

        //wysokosc palek i zapis do pliku
        JPanel paddleHeightPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        paddleHeightPanel.setBackground(Color.BLACK);
        paddleHeightPanel.setMaximumSize(new Dimension(BOX_WIDTH,  35));
        paddleHeightPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel paddleHeightLabel = new JLabel("Wysokość paletek:");
        paddleHeightLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        paddleHeightLabel.setForeground(Color.WHITE);
        paddleHeightLabel.setHorizontalAlignment(SwingConstants.CENTER);
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
        paddleHeightPanel.add(paddleHeightSpinner);

        add(paddleHeightPanel);
        add(Box.createRigidArea(new Dimension(0, 30)));

        //powrot do menu
        JButton backButton = new JButton("Powrót");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setMaximumSize(new Dimension(BOX_WIDTH, 40));
        backButton.setFont(new Font("Arial", Font.PLAIN, 16));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Po kliknięciu „Powrót” wracamy do głównego menu:
                frame.setContentPane(mainMenuPanel);
                frame.revalidate();
                SwingUtilities.invokeLater(() -> {
                    mainMenuPanel.requestFocusInWindow();
                    if (mainMenuPanel instanceof MainMenu) {
                        ((MainMenu) mainMenuPanel).resumeAnimation();
                    }
                });
            }
        });
        add(backButton);
    }
}
