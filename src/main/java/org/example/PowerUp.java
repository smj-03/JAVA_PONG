package org.example;

import javax.swing.*;
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

    static void changePaddleLength(Paddle paddle, int length) {
        if (paddle == null) return;
        paddle.changeLengthBy(length);
        Timer resetTimer = new Timer(5000, e -> {
            paddle.resetLength();
        });
        resetTimer.setRepeats(false);
        resetTimer.start();
    }
}
