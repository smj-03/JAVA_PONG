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

    public void bounceOffWalls(int top , int bottom) {
        //if the y value is at the bottom of the screen
        if (y > 540) {
            reverseYDirection();
        }
        //if y value is at top of screen
        else if(y < 0){
            reverseYDirection();
        }

    }

    public void paint(Graphics g){

        //set the brush color to the ball color
        g.setColor(color);

        //paint the ball at x, y with a width and height of the ball size
        g.fillOval(x, y, diameter, diameter);

    }
}
