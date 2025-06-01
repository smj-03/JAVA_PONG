package org.example;

import static org.junit.jupiter.api.Assertions.*;
import java.awt.Color;
import org.junit.jupiter.api.Test;

class PaddleTest {

    @Test
    void testChangeLengthBy_IncreaseHeightPercentage() {
        // Załóżmy, że domyślnie paddleHeight = 20 (np. z Settings),
        // ale tutaj bezpośrednio w konstruktorze podajemy height=20.
        Paddle paddle = new Paddle(0, 100, 10, 20, Color.BLUE);
        int initialY = paddle.getY();
        int initialHeight = paddle.getHeight();

        paddle.changeHeightByPercentage(10);
        // Nowa wysokość: 20 + 10 = 30
        assertEquals(30, paddle.getHeight(),
                "Po changeLengthBy(10) paddleHeight powinno być 30");

        // y przesuwa się o połowę przyrostu w górę: y = 100 - (10/2) = 95
        assertEquals(initialY - 5, paddle.getY(),
                "Po zwiększeniu długości paletki y powinno przesunąć się o 5 w górę (100->95)");
    }

    @Test
    void testResetLength_ShouldRestoreInitialHeightAndCenterPosition() {
        Paddle paddle = new Paddle(50, 200, 10, 40, Color.MAGENTA);
        int initY = paddle.getY();      // 200
        int initHeight = paddle.getHeight(); // 40

        // najpierw zmieniamy długość, np. o +20
        paddle.changeHeightByPercentage(20);
        assertEquals(60, paddle.getHeight(),
                "Po changeLengthBy(20) wysokość = 60");
        // y zmieniło się z 200 na 200 - 10 = 190 (ponieważ length/2 = 10)
        assertEquals(190, paddle.getY());

        // Teraz resetLength()
        paddle.resetLength();
        assertEquals(initHeight, paddle.getHeight(),
                "Po resetLength() wysokość powinna wrócić do początkowych 40");
        // Pozycja y: kod w resetLength robi:
        // y += (height - initialLength)/2  (gdzie height=i teraz=initialLength=40 -> y+=0)
        // a następnie height=initialLength
        // Czyli y wróci dokładnie do 200.
        assertEquals(initY, paddle.getY(),
                "Po resetLength() pozycja y powinna wrócić do oryginalnego 200");
    }

    @Test
    void testInheritedMoveYWithinBounds_FromPaddle() {
        // Chcemy sprawdzić, czy paletka dziedziczy poprawnie moveYWithinBounds.
        Paddle paddle = new Paddle(0, -50, 10, 30, Color.ORANGE);
        paddle.moveYWithinBounds(-50, 80);
        assertEquals(0, paddle.getY(),
                "Paddle dziedziczy moveYWithinBounds – jeżeli y<0, powinno być y=0");

        // Teraz umieśćmy ją tak, żeby y+height > panelHeight -> np. y=70, height=30, panelHeight=80
        paddle.setY(70);
        paddle.moveYWithinBounds(70, 80);
        // wówczas 70+30=100>80, więc y=80-30=50
        assertEquals(50, paddle.getY(),
                "Jeżeli y+height > panelHeight, to paletka zostanie przesunięta na y=50");
    }

    @Test
    void testInheritedIntersects_TwoPaddles() {
        Paddle p1 = new Paddle(10, 10, 15, 20, Color.CYAN);
        Paddle p2 = new Paddle(20, 25, 15, 20, Color.PINK);
        // p1 zajmuje obszar [10..25) x [10..30),
        // p2 zajmuje      [20..35) x [25..45) -> nachodzą one w obszarze [20..25)x[25..30)
        assertTrue(p1.intersects(p2), "Paletki nachodzą na siebie – intersects powinno zwrócić true");
        assertTrue(p2.intersects(p1), "i w odwrotną stronę też true");
    }
}
