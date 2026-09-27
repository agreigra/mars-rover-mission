package com.nasa.rover;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class MissionControlTest {

    @Test
    void sampleMissionProducesExpectedPositions() {
        MissionControl missionControl = new MissionControl(new Plateau(5, 5));
        missionControl.addRover(1, 2, "N", "LMLMLMLMM");
        missionControl.addRover(3, 3, "E", "MMRMMRMRRM");

        assertEquals("1 3 N\n5 1 E", missionControl.execute());
    }

    @Test
    void missionExecutesInstructionsOnlyWhenMissionRuns() {
        MissionControl missionControl = new MissionControl(new Plateau(5, 5));

        missionControl.addRover(1, 2, "N", "L");

        assertEquals("1 2 W", missionControl.execute());
    }
}
