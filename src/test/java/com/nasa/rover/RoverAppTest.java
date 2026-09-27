package com.nasa.rover;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.Field;
import java.util.List;

import org.junit.jupiter.api.Test;

class RoverAppTest {

    @Test
    void sampleMissionProducesExpectedPositions() {
        MissionControl missionControl = new MissionControl(new Plateau(5, 5));
        missionControl.addRover(1, 2, "N", "LMLMLMLMM");
        missionControl.addRover(3, 3, "E", "MMRMMRMRRM");

        assertEquals("1 3 N\n5 1 E", missionControl.execute());
    }

    @Test
    void addRoverDoesNotExecuteInstructionsUntilMissionRuns() throws Exception {
        MissionControl missionControl = new MissionControl(new Plateau(5, 5));

        missionControl.addRover(1, 2, "N", "L");

        Field roversField = MissionControl.class.getDeclaredField("rovers");
        roversField.setAccessible(true);
        @SuppressWarnings("unchecked")
        List<Rover> rovers = (List<Rover>) roversField.get(missionControl);

        assertEquals(Direction.N, rovers.get(0).getDirection());
        assertEquals("1 2 W", missionControl.execute());
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
}
