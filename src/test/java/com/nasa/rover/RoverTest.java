package com.nasa.rover;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class RoverTest {

    @Test
    void roverFollowsMovementInstructions() {
        Rover rover = new Rover(1, 2, "N", new Plateau(5, 5));

        rover.execute("LMLMLMLMM");

        assertEquals("1 3 N", rover.toString());
    }

    @Test
    void roverDoesNotMoveOutsidePlateau() {
        Rover rover = new Rover(5, 5, "N", new Plateau(5, 5));

        rover.execute("M");

        assertEquals("5 5 N", rover.toString());
    }

    @Test
    void invalidInitialPositionIsRejected() {
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> new Rover(6, 0, "N", new Plateau(5, 5)));

        assertEquals("Initial rover position is outside the plateau.", thrown.getMessage());
    }

    @Test
    void invalidInstructionIsRejected() {
        Rover rover = new Rover(0, 0, "N", new Plateau(5, 5));

        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> rover.execute("X"));

        assertEquals("Unknown instruction: X", thrown.getMessage());
    }

    @Test
    void blankInstructionsAreRejected() {
        Rover rover = new Rover(0, 0, "N", new Plateau(5, 5));

        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> rover.execute(""));

        assertEquals("Instructions cannot be null or blank.", thrown.getMessage());
    }
}
