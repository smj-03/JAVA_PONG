package org.example;

import javax.swing.*;

public class ImageButton extends JButton {
    public ImageButton(String filepath, int x, int y) {
        ImageIcon image = new ImageIcon(filepath);
        this.setBounds(x, y, image.getIconWidth(), image.getIconHeight());
        this.setIcon(image);
        this.setBorderPainted(false);
        this.setFocusPainted(false);
        this.setContentAreaFilled(false);
    }
}
