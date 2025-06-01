package org.example;

import javax.swing.*;

//punkt startowy aplikacji
public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Gra PONG"); //tworzenie nowego okna

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //zakonczenie programu przy zamknieciu okna

        frame.setSize(800, 600);//rozmiar okna

        frame.setResizable(false);//blokowanie mozliwosci zmiany rozmiaru okna

        frame.setLocationRelativeTo(null);//ustawianie okna na srodek ekranu

        MainMenu menuPanel = new MainMenu(frame);//utworzenie menu
        frame.setContentPane(menuPanel);//ustawienie menu na zawartosc okna
        frame.setVisible(true);//pokazanie menu
    }
}