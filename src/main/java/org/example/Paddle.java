package org.example;

import java.awt.*;

//klasa Paddle reprezentuje paletke dziedziczy po Object-klasie abstrakcyjnej
// zapewniajacej pozycje, wymiar kolor itp.
public class Paddle extends Object {
    //poczatkowa dlugosc paletki
    private int initialLength;
    //konstruktor
    public Paddle(int x, int y, int width, int height, Color color) {
        super(x, y, width, height, color);
        this.initialLength = height;
    }

    public void changeHeightByPercentage(float percentage) {
        if (percentage <= 0) return;
        int newHeight = (int) (initialLength * percentage); // Scale height by the factor
        y += (height - newHeight) / 2; // Adjust position to center the paddle
        height = newHeight; // Update the height
    }

    //przywracanie dlugosci paletki
    public void resetLength() {
        y += (height - initialLength) / 2;
        height = initialLength;
    }

    //nadpisanie metod z klasy abstrakcyjnej Object
    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, width, height);
    }

    @Override
    public void move() {
    }
}
