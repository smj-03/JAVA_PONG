package org.example;

import java.awt.*;

public class PowerUp extends Object {
    public PowerUp(int x, int y, int diameter, Color color) {
        super(x, y, diameter, diameter, color);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        // FIXME: DUPA
        g.fillOval(x - width / 2, y - height / 2, width, height);
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
