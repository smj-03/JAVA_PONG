package org.example;
import javax.swing.*;
import java.awt.*;

public class Game extends JPanel{
    public Game() {
        // Initialize game components here
        setFocusable(true); // Allow the panel to receive focus for key events
        requestFocusInWindow(); // Request focus for the panel
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw game elements here
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight()); // Fill background with black color
    }
}
