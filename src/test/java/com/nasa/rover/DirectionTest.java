package com.nasa.rover;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class DirectionTest {

    @Test
    void turningLeftAndRightUsesCardinalRotation() {
        assertEquals(Direction.W, Direction.N.turnLeft());
        assertEquals(Direction.E, Direction.N.turnRight());
        assertEquals(Direction.S, Direction.W.turnLeft());
        assertEquals(Direction.N, Direction.W.turnRight());
    }

    @Test
    void movementDeltasMatchCardinalDirections() {
        assertEquals(0, Direction.N.getDeltaX());
        assertEquals(1, Direction.N.getDeltaY());
        assertEquals(1, Direction.E.getDeltaX());
        assertEquals(0, Direction.E.getDeltaY());
        assertEquals(0, Direction.S.getDeltaX());
        assertEquals(-1, Direction.S.getDeltaY());
        assertEquals(-1, Direction.W.getDeltaX());
        assertEquals(0, Direction.W.getDeltaY());
    }
}
