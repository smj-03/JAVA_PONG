package org.example;

import java.awt.*;

public class PowerUp extends Object {
    public PowerUp(int x, int y, int width, int height, Color color) {
        super(x, y, width, height, color);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, width, height);
    }

    @Override
    public void move() {
    }

    static void lengthenPaddle(Paddle paddle) {
        if (paddle == null) return;
        int newHeight = paddle.getHeight() + 50;
        int newY = paddle.getY() - 25;
        paddle.setHeight(newHeight);
        paddle.setY(newY);
    }
}
