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

        // Podwajamy wysokość (procent = 2.0f)
        paddle.changeHeightByPercentage(2.0f);

        // Nowa wysokość powinna wynosić 40 (20 * 2.0)
        assertEquals(40, paddle.getHeight(),
                "Po changeHeightByPercentage(2.0) wysokość paletki powinna zostać podwojona do 40");

        // y powinno przesunąć się o połowę zmiany wysokości: (40 - 20) / 2 = 10
        assertEquals(initialY - 10, paddle.getY(),
                "Po zwiększeniu wysokości paletki y powinno przesunąć się o połowę zmiany wysokości (10 jednostek w górę)");
    }

    @Test
    void testResetLength_ShouldRestoreInitialHeightAndCenterPosition() {
        Paddle paddle = new Paddle(50, 200, 10, 40, Color.MAGENTA);
        int initY = paddle.getY();      // 200
        int initHeight = paddle.getHeight(); // 40

        // Najpierw zmieniamy wysokość, np. na połowę (procent = 0.5f)
        paddle.changeHeightByPercentage(0.5f);
        assertEquals(20, paddle.getHeight(),
                "Po changeHeightByPercentage(0.5) wysokość powinna wynosić 20");
        // y zmieniło się z 200 na 200 + (40 - 20) / 2 = 210 (ponieważ height zmniejszyło się o połowę)
        assertEquals(210, paddle.getY(),
                "Po zmniejszeniu wysokości paletki y powinno przesunąć się o połowę zmiany wysokości (10 jednostek w dół)");

        // Teraz resetLength()
        paddle.resetLength();
        assertEquals(initHeight, paddle.getHeight(),
                "Po resetLength() wysokość powinna wrócić do początkowych 40");
        // Pozycja y: kod w resetLength robi:
        // y += (height - initialLength) / 2  (gdzie height=20, initialLength=40 -> y+=-10)
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
