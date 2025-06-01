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

    //zmiana dlugosci paletki(uzywane przy PowerUp)
    public void changeLengthBy(int length) {
        height += length;
        y -= length / 2;
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
