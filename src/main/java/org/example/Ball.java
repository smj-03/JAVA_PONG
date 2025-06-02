package org.example;

import java.awt.*;

public class Ball extends Object {
    private int xSpeed, ySpeed;
    private int initialDiameter;

    public Ball(int x, int y, int diameter, int xSpeed, int ySpeed, Color color) {
        super(x, y, diameter, diameter, color);
        this.initialDiameter = diameter;
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

    public void bounceOffWalls(int top, int bottom, boolean isViewportSet) {
        // Adjust for the ball's height when checking the top and bottom boundaries
        if (y + height > bottom) { // Bottom boundary
            reverseYDirection();
        } else if (y < top) { // Top boundary
            reverseYDirection();
        }

        if (isViewportSet) {
            // Adjust for the ball's width when checking the left and right boundaries
            if (x + width > 782) { // Right boundary
                reverseXDirection();
            } else if (x <= 0) { // Left boundary
                reverseXDirection();
            }
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

    public void changeSizeByPercentage(float percentage) {
        if (percentage <= 0) return;
        width = (int) (width * percentage);
        height = (int) (height * percentage);
    }

    public void resetSize() {
        width = initialDiameter;
        height = initialDiameter;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, width, height);
    }
}
