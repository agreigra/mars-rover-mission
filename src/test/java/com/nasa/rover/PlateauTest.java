package com.nasa.rover;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class PlateauTest {

    @Test
    void plateauContainsCoordinatesWithinBounds() {
        Plateau plateau = new Plateau(5, 5);

        assertTrue(plateau.contains(0, 0));
        assertTrue(plateau.contains(5, 5));
        assertTrue(plateau.contains(2, 3));
    }

    @Test
    void plateauRejectsCoordinatesOutsideBounds() {
        Plateau plateau = new Plateau(5, 5);

        assertFalse(plateau.contains(-1, 0));
        assertFalse(plateau.contains(0, -1));
        assertFalse(plateau.contains(6, 3));
        assertFalse(plateau.contains(3, 6));
    }

    @Test
    void plateauRejectsNegativeDimensions() {
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> new Plateau(-1, 5));

        assertEquals("Plateau dimensions must be non-negative.", thrown.getMessage());
    }
}
