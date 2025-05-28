package org.example;

import java.awt.*;

public class Ball {
    private int x, y; // position of the ball
    private int diameter; // diameter of the ball
    private int xSpeed, ySpeed; // speed of the ball in x and y directions
    private int size; // size of the ball
    private Color color; // color of the ball

    public Ball(int x, int y, int diameter, int xSpeed, int ySpeed, Color color) {
        this.x = x;
        this.y = y;
        this.diameter = diameter;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.color = color;
    }

    public void move() {
        x += xSpeed;
        y += ySpeed;
    }

    public void reverseXDirection() {
        xSpeed = -xSpeed;
    }

    public void reverseYDirection() {
        ySpeed = -ySpeed;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDiameter() {
        return diameter;
    }

    public void bounceOffWalls(int top, int bottom) {
        //if the y value is at the bottom of the screen
        if (y > 540) {
            reverseYDirection();
        }
        //if y value is at top of screen
        else if (y < 0) {
            reverseYDirection();
        }

    }

    public void bounceFromPaddle(Paddle paddle) {
        reverseXDirection(); // zmiana kierunku w poziomie

        int paddleCenter = paddle.getY() + paddle.getHeight() / 2;
        int ballCenter = y + diameter / 2;
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

    public void paint(Graphics g) {

        //set the brush color to the ball color
        g.setColor(color);

        //paint the ball at x, y with a width and height of the ball size
        g.fillOval(x, y, diameter, diameter);

    }
}
