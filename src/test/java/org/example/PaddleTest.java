package org.example;

import org.junit.jupiter.api.Test;
import java.awt.Color;

import static org.junit.jupiter.api.Assertions.*;

class PaddleTest {

    @Test
    void testMoveWithinBounds() {
        // Paddle o wymiarach 10×50, początkowe y = 100
        Paddle paddle = new Paddle(0, 100, 10, 50, Color.WHITE);
        // Przesuwamy do y = 200 w obszarze o wysokości 600
        paddle.move(200, 600);
        assertEquals(200, paddle.getY());
    }

    @Test
    void testMoveAboveTop() {
        // Paddle o wymiarach 10×50, chcemy ustawić y = -20
        Paddle paddle = new Paddle(0, 100, 10, 50, Color.WHITE);
        paddle.move(-20, 600);
        // Powinno zostać „przycięte” do 0
        assertEquals(0, paddle.getY());
    }

    @Test
    void testMoveBelowBottom() {
        // Paddle wysokości 50, panelHeight = 600
        // Próba ustawienia y = 570, ale 600 - 50 = 550 to maksymalne y
        Paddle paddle = new Paddle(0, 560, 10, 50, Color.WHITE);
        paddle.move(570, 600);
        assertEquals(550, paddle.getY());
    }

    @Test
    void testIntersectsTrue() {
        // Ustawiamy paddle w (50,50) o wymiarach 10×10
        Paddle paddle = new Paddle(50, 50, 10, 10, Color.WHITE);
        // Ustawiamy ball tak, żeby się stykał, np. ball.x = 55, ball.y = 55, średnica = 10
        Ball ball = new Ball(55, 55, 10, 0, 0, Color.WHITE);
        assertTrue(paddle.intersects(ball));
    }

    @Test
    void testIntersectsFalse() {
        Paddle paddle = new Paddle(50, 50, 10, 10, Color.WHITE);
        // Ball całkowicie poza strefą paddle
        Ball ball = new Ball(100, 100, 10, 0, 0, Color.WHITE);
        assertFalse(paddle.intersects(ball));
    }
}
