package org.example;

import java.awt.*;

public abstract class Object {
    protected int x, y;
    protected int width, height;
    protected Color color;

    public Object(int x, int y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public abstract void move();

    public void moveYWithinBounds(int y, int panelHeight) {
        this.y = y;
        if (this.y < 0) {
            this.y = 0;
        }
        if (this.y + height > panelHeight) {
            this.y = panelHeight - height;
        }
    }

    public void moveXWithinBounds(int x, int panelWidth) {
        this.x = y;
        if (this.x < 0) {
            this.x = 0;
        }
        if (this.x + width > panelWidth) {
            this.x = panelWidth - width;
        }
    }

    public abstract void draw(Graphics g);

    public boolean intersects(Object object) {
        return (object.getX() < x + width && object.getX() + object.getWidth() > x &&
                object.getY() < y + height && object.getY() + object.getHeight() > y);
    }
}
