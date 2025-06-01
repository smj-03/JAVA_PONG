package org.example;

import java.awt.*;

public class Paddle extends Object {
    private int initialLength;

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

    public void resetLength() {
        y += (height - initialLength) / 2; // Adjust the position to center the paddle
        height = initialLength; // Reset the height to the initial value
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
