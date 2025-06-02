package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

//punkt startowy aplikacji
public class Main {
    public static void main(String[] args) {

        JFrame frame = new JFrame("PONG"); //tworzenie nowego okna 


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //zakonczenie programu przy zamknieciu okna

        try {
            // logo gry
            Image logo = ImageIO.read(new File("src/main/resources/images/pong.png"));
            // ustawienie loga gry
            frame.setIconImage(logo);
        } catch (IOException e) {
            e.printStackTrace();
        }

        frame.setSize(800, 600); //rozmiar okna


        frame.setResizable(false);//blokowanie mozliwosci zmiany rozmiaru okna

        frame.setLocationRelativeTo(null);//ustawianie okna na srodek ekranu

        MainMenu menuPanel = new MainMenu(frame);//utworzenie menu
        frame.setContentPane(menuPanel);//ustawienie menu na zawartosc okna
        frame.setVisible(true);//pokazanie menu
    }
}