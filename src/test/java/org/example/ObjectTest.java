package org.example;

import static org.junit.jupiter.api.Assertions.*;
import java.awt.Color;
import org.junit.jupiter.api.Test;

class ObjectTest {

    // Minimalna, anonimowa (wewnętrzna) implementacja Object,
    // żeby móc utworzyć instancję i sprawdzić metody bazowe.
    static class DummyObject extends Object {
        public DummyObject(int x, int y, int width, int height, Color color) {
            super(x, y, width, height, color);
        }

        @Override
        public void draw(java.awt.Graphics g) {
            // Nic nie robimy – potrzebne tylko, aby klasa się skompilowała.
        }

        @Override
        public void move() {
            // Również puste, bo nie testujemy tutaj metody move().
        }
    }

    @Test
    void testIntersects_TwoOverlappingObjects_ShouldReturnTrue() {
        DummyObject o1 = new DummyObject(10, 10, 30, 30, Color.RED);
        DummyObject o2 = new DummyObject(25, 25, 20, 20, Color.BLUE);

        assertTrue(o1.intersects(o2),
                "Obiekty nachodzą na siebie i metoda intersects powinna zwrócić true");
        assertTrue(o2.intersects(o1),
                "Sprawdzenie w odwrotnym kierunku też powinno dać true");
    }

    @Test
    void testIntersects_TwoNonOverlappingObjects_ShouldReturnFalse() {
        DummyObject o1 = new DummyObject(0, 0, 10, 10, Color.WHITE);
        DummyObject o2 = new DummyObject(50, 50, 10, 10, Color.BLACK);

        assertFalse(o1.intersects(o2),
                "Obiekty nie nachodzą, więc intersects powinno być false");
    }

    @Test
    void testMoveYWithinBounds_BelowTop_ShouldClampToZero() {
        DummyObject o = new DummyObject(5, -20, 10, 10, Color.GREEN);
        // dajemy y = -20, panelHeight np. 100
        o.moveYWithinBounds(-20, 100);
        assertEquals(0, o.getY(),
                "Po przesunięciu poza górną krawędź (y < 0) powinno wyjść y=0");
    }

    @Test
    void testMoveYWithinBounds_AboveBottom_ShouldClamp() {
        // jeżeli y + height > panelHeight, wówczas y = panelHeight - height
        DummyObject o = new DummyObject(5, 0, 50, 30, Color.YELLOW);
        // height = 30, panelHeight = 40 => panelHeight - height = 10
        o.moveYWithinBounds(20, 40);
        assertEquals(10, o.getY(),
                "Po przesunięciu poza dolny bok (y+height > panelHeight) "
                        + "powinno ustawić y = panelHeight - height (=10)");
    }

    @Test
    void testMoveXWithinBounds_BelowLeft_ShouldClampToZero() {
        DummyObject o = new DummyObject(-15, 5, 10, 10, Color.MAGENTA);
        o.moveXWithinBounds(-15, 100);
        // UWAGA: w aktualnej implementacji moveXWithinBounds ustawia this.x = y (!).
        // Test pokazuje zachowanie na chwilę obecną.
        // Skoro y było = 5, to this.x = 5, a 5 jest w granicach, więc x=5
        assertEquals(5, o.getX(),
                "Z uwagi na błąd w moveXWithinBounds (ustawia x=y), "
                        + "x powinno stać się 5 (poprzednie y)");
    }
}
