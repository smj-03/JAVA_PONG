package org.example;

import java.awt.*;

public class Paddle extends Object {
    private int speed;

    public Paddle(int x, int y, int width, int height, Color color) {
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
}
