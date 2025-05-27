package org.example;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Gra PONG"); // create a new JFrame to hold the game

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exit the application when the window is closed

        frame.setSize(800, 600); // set the size of the frame

        frame.setResizable(true); // allow resizing of the window

        frame.setLocationRelativeTo(null); // center the frame on the screen

        Game gamePanel = new Game(); // create an instance of the game panel
        frame.add(gamePanel); // add the game panel to the frame


        frame.setVisible(true); // make the frame visible

        gamePanel.startGame(); // start the game
    }
}