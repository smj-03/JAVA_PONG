package org.example;

import org.junit.jupiter.api.Test;
import java.awt.Color;

import static org.junit.jupiter.api.Assertions.*;

class BallTest {

    @Test
    void testMove() {
        // Początkowe współrzędne (0,0), prędkość (2,3)
        Ball ball = new Ball(0, 0, 10, 2, 3, Color.WHITE);
        ball.move();
        // Powinno przesunąć o wektor (2,3)
        assertEquals(2, ball.getX());
        assertEquals(3, ball.getY());
    }

    @Test
    void testReverseXDirection() {
        // Początkowe x=10, xSpeed=5
        Ball ball = new Ball(10, 10, 10, 5, 0, Color.WHITE);
        // Odwracamy kierunek w osi X
        ball.reverseXDirection();
        // Teraz xSpeed = -5, więc po move() x = 10 + (-5) = 5
        ball.move();
        assertEquals(5, ball.getX());
    }

    @Test
    void testReverseYDirection() {
        // Początkowe y=10, ySpeed=5
        Ball ball = new Ball(10, 10, 10, 0, 5, Color.WHITE);
        // Odwracamy kierunek w osi Y
        ball.reverseYDirection();
        // Teraz ySpeed = -5, więc po move() y = 10 + (-5) = 5
        ball.move();
        assertEquals(5, ball.getY());
    }

    @Test
    void testBounceOffWallsTop() {
        // Ustawiamy ball tak, żeby y < 0 (np. y = -5) i ySpeed = -3
        Ball ball = new Ball(10, -5, 10, 0, -3, Color.WHITE);
        // Metoda bounceOffWalls(int top, int bottom) odwróci ySpeed, bo y < 0
        ball.bounceOffWalls(0, 600);
        // Po odwróceniu ySpeed = +3, a następnie move(): y = -5 + 3 = -2
        ball.move();
        assertEquals(-2, ball.getY());
    }

    @Test
    void testBounceOffWallsBottom() {
        // Ustawiamy ball tak, żeby y > 540 (np. y = 550) i ySpeed = 3
        Ball ball = new Ball(10, 550, 10, 0, 3, Color.WHITE);
        // Metoda bounceOffWalls(int top, int bottom) odwróci ySpeed, bo y > 540 (stała w kodzie)
        ball.bounceOffWalls(0, 600);
        // Po odwróceniu ySpeed = -3, a następnie move(): y = 550 + (-3) = 547
        ball.move();
        assertEquals(547, ball.getY());
    }
}
