package org.example;

import java.awt.*;

public class Paddle {
    private int x, y, speed; // position of the paddle
    private final int width, height; // dimensions of the paddle
    private final Color color; // color of the paddle

    public Paddle(int x, int y, int width, int height, Color color) {
        this.x = x; // x position of the paddle
        this.y = y; // y position of the paddle
        this.width = width; // width of the paddle
        this.height = height; // height of the paddle
        this.color = color; // color of the paddle
    }

    public void paint(Graphics g) {
        //paint the rectangle for the paddle
        g.setColor(color);
        g.fillRect(x, y, width, height);

    }

    public int getX() {
        return x; // return the x position of the paddle
    }

    public int getY() {
        return y; // return the y position of the paddle
    }

    public void move(int newY, int panelHeight) {

        this.y = newY; // update the y position of the paddle
        if (this.y < 0) {
            this.y = 0; // prevent paddle from going above the top edge
        }
        if (this.y + height > panelHeight) { // assuming the height of the game area is 600
            this.y = panelHeight - height; // prevent paddle from going below the bottom edge
        }
    }

    public boolean intersects(Ball ball) {
        // Check if the paddle intersects with the ball
        return (ball.getX() < x + width && ball.getX() + ball.getDiameter() > x &&
                ball.getY() < y + height && ball.getY() + ball.getDiameter() > y);
    }
}
