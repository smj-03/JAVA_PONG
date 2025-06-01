package org.example;

import java.awt.*;

/**
 * Klasa abstrakcyjna reprezentuje obiekt w grze.
 * Zawiera polozenie, wymiary, kolor oraz operacje ruchu i rysowania.
 */
public abstract class Object {

    protected int x, y; //wspolrzedne obiektu
    protected int width, height; //wysokosc i dlugosc obiektu
    protected Color color; //kolor obiektu

    //inicjalizacja wlasciwosci obiektu - konstruktor
    public Object(int x, int y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    //gettery i settery

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    //Metoda abstrakcyjna odpowiedzialna za logike ruchu obiektu
    //kazda klasa dziedziczaca ma wlasna implementacje wiec tu jest zdefiniowana tylko metoda
    public abstract void move();

    //Metoda abstrakcyjna do rysowania obiektu na ekranie tak samo jak move().
    public abstract void draw(Graphics g);


    //Metoda ruchu z uwzględnieniem granic
    public void moveYWithinBounds(int y, int panelHeight) {
        this.y = y;//ustalamy nowy y
        //jezeli y jest mniejsze niz 0(gorna granica) to ustawiamy wlasnie na 0 czyli gorna granice
        if (this.y < 0) {
            this.y = 0;
        }
        //identyczny mechanizm dla dolnej granicy ekranu
        if (this.y + height > panelHeight) {
            this.y = panelHeight - height;
        }
    }
    //analogiczna metoda ruchu dla wspolednej x
    public void moveXWithinBounds(int x, int panelWidth) {
        this.x = y;
        if (this.x < 0) {
            this.x = 0;
        }
        if (this.x + width > panelWidth) {
            this.x = panelWidth - width;
        }
    }

    //metoda sprawdzajaca przeciecie sie z innym obiektem
    public boolean intersects(Object object) {
        return (object.getX() < x + width && object.getX() + object.getWidth() > x &&
                object.getY() < y + height && object.getY() + object.getHeight() > y);
    }
}
