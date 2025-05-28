package org.example;

import javax.swing.*;
import java.awt.*;

public class SettingsMenu extends JPanel {

    public SettingsMenu(JFrame frame, JPanel mainMenuPanel) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.DARK_GRAY);

        JLabel title = new JLabel("Settings – Coming Soon");
        title.setAlignmentX(CENTER_ALIGNMENT);
        title.setFont(new Font("Arial", Font.BOLD, 32));
        title.setForeground(Color.WHITE);

        add(Box.createVerticalStrut(100));
        add(title);
        add(Box.createVerticalStrut(50));

        JButton backButton = new JButton("Back");
        backButton.setAlignmentX(CENTER_ALIGNMENT);
        backButton.setMaximumSize(new Dimension(200, 40));
        add(backButton);

        backButton.addActionListener(e -> {
            frame.setContentPane(mainMenuPanel);
            frame.revalidate();
            SwingUtilities.invokeLater(() -> {
                mainMenuPanel.requestFocusInWindow();

                // Wznowienie animacji piłki
                if (mainMenuPanel instanceof MainMenu) {
                    ((MainMenu) mainMenuPanel).resumeAnimation();
                }
            });
        });

    }
}
