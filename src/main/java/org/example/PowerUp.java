package org.example;

import javax.swing.*;
import java.awt.*;

public class PowerUp extends Object {
    private Image sprite;

    public PowerUp(int x, int y, String spritePath) {
        super(x, y, 50, 50, Color.WHITE);
        this.sprite = new ImageIcon(spritePath).getImage();
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.drawImage(sprite, x, y, width, height, null);
    }

    @Override
    public void move() {
    }

    static void changePaddleLength(Paddle paddle, float percentage) {
        if (paddle == null) return;
        paddle.changeHeightByPercentage(percentage);
        Timer resetTimer = new Timer(5000, e -> {
            paddle.resetLength();
        });
        resetTimer.setRepeats(false);
        resetTimer.start();
    }

    static void changeBallSize(Ball ball, float percentage) {
        if (ball == null) return;
        ball.changeSizeByPercentage(percentage);
        Timer resetTimer = new Timer(5000, e -> {
            ball.resetSize();
        });
        resetTimer.setRepeats(false);
        resetTimer.start();
    }
}
