package org.example;

import java.awt.*;

public class Paddle {
    private int x, y; // position of the paddle
    private final int width, height; // dimensions of the paddle
    private final Color color; // color of the paddle

    public Paddle(int x, int y, int width, int height, Color color) {
        this.x = x; // x position of the paddle
        this.y = y; // y position of the paddle
        this.width = width; // width of the paddle
        this.height = height; // height of the paddle
        this.color = color; // color of the paddle
    }

    public void paint(Graphics g){
        //paint the rectangle for the paddle
        g.setColor(color);
        g.fillRect(x, y, width, height);

    }
}
