package org.example;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class TextButton extends JButton {

    private Font font;

    TextButton(String text) {
        super(text);
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/fonts/MedodicaRegular.otf")).deriveFont(32f);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            font = new Font("Courier New", Font.PLAIN, 32); // Fallback font
        }
        this.setFont(font);
        this.setBackground(Color.WHITE);
        this.setForeground(Color.BLACK);
        this.setFocusPainted(false);
        this.setBorderPainted(false);
        this.setRolloverEnabled(false);
    }
}
