package org.example;

import java.awt.*;

//klasa ball reprezentuje pilke dziedziczy po Object-klasie abstrakcyjnej
// zapewniajacej pozycje, wymiar kolor itp.
public class Ball extends Object {
    private int initialDiameter;
    private int xSpeed, ySpeed; //predkosc pilki
    //konstruktor pilki

    public Ball(int x, int y, int diameter, int xSpeed, int ySpeed, Color color) {
        super(x, y, diameter, diameter, color);
        this.initialDiameter = diameter;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }

    //implementacja metody abstrakcyjnej z klasy object
    //przesuwa pilke zgodnie z jej predkosciami w osi x i y
    @Override//nadpisanie metody
    public void move() {
        x += xSpeed;
        y += ySpeed;
    }

    //odwracanie kierunku ruchu pilki
    public void reverseXDirection() {
        xSpeed = -xSpeed;
    }

    public void reverseYDirection() {
        ySpeed = -ySpeed;
    }


    //odbijanie pilki od gornej i dolnej granicy
    public void bounceOffWalls(int top, int bottom, boolean isViewportSet) {
        //jezeli pilka jest ponizej dolnej krawedzi odwracamy kierunek
        if (y + height > bottom) {
            reverseYDirection();
        } else if (y < top) {
            reverseYDirection();
        }

        if (isViewportSet) {
            if (x + width > 782) {
                reverseXDirection();
            } else if (x <= 0) {
                reverseXDirection();
            }
        }
    }

    //odbijanie pilki od paletki
    public void bounceFromPaddle(Paddle paddle) {
        reverseXDirection(); // zmiana kierunku w poziomie

        //wyliczamy srodek  pilki i paletki
        int paddleCenter = paddle.getY() + paddle.getHeight() / 2;
        int ballCenter = y + height / 2;
        //roznica miedzy srodkiem pilki i paletki
        int relativeY = ballCenter - paddleCenter;

        // Ustal maksymalną prędkość i maksymalny Y-kąt odbicia
        int maxSpeed = 12;
        int maxYSpeed = 7; // ustal maksymalny sensowny kąt odbicia

        // Skaluj prędkość y na podstawie miejsca trafienia, ale ogranicz do maxYSpeed
        ySpeed = relativeY / 5;
        if (ySpeed > maxYSpeed) ySpeed = maxYSpeed;
        if (ySpeed < -maxYSpeed) ySpeed = -maxYSpeed;

        // Zapobiegaj zbyt płaskiemu odbiciu (0)
        if (ySpeed == 0) {
            ySpeed = (Math.random() > 0.5) ? 1 : -1;
        }

        // Zwiększ prędkość x, ale nie przekraczaj maxSpeed
        xSpeed += (xSpeed > 0) ? 1 : -1;
        if (Math.abs(xSpeed) > maxSpeed) {
            xSpeed = (xSpeed > 0) ? maxSpeed : -maxSpeed;
        }
    }

    public void changeSizeByPercentage(float percentage) {
        if (percentage <= 0) return;
        width = (int) (width * percentage);
        height = (int) (height * percentage);
    }

    public void resetSize() {
        width = initialDiameter;
        height = initialDiameter;
    }

    //rysowanie pilki metoda z klasy abstrakcyjnej object
    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, width, height);
    }
}
