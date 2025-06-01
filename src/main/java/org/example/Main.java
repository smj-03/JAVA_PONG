package org.example;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("PONG"); // create a new JFrame to hold the game

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exit the application when the window is closed

        frame.setSize(800, 600); // set the size of the frame

        frame.setResizable(false); // allow resizing of the window

        frame.setLocationRelativeTo(null); // center the frame on the screen

        MainMenu menuPanel = new MainMenu(frame);
        frame.setContentPane(menuPanel); // pokaż menu
        frame.setVisible(true);
    }
}