package org.example;

import java.awt.*;

public class Ball extends Object {
    private int diameter;
    private int xSpeed, ySpeed;

    public Ball(int x, int y, int diameter, int xSpeed, int ySpeed, Color color) {
        super(x, y, diameter, diameter, color);
        this.diameter = diameter;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
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

    public void setDiameter(int d) {
        diameter = d;
    }

    public int getDiameter() {
        return diameter;
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

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, diameter, diameter);
    }
}
