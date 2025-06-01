package org.example;

import java.awt.*;

public class Ball extends Object {
    private int xSpeed, ySpeed;

    public Ball(int x, int y, int diameter, int xSpeed, int ySpeed, Color color) {
        super(x, y, diameter, diameter, color);
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }

    @Override
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

    // TODO: CHANGE THE LOGIC IF VIEWPORT IS SET
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
        int ballCenter = y + height / 2;
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

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, width, height);
    }
}
