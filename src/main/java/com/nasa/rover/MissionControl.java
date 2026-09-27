package com.nasa.rover;

import java.util.ArrayList;
import java.util.List;

/**
 * Coordinates the execution of a full mission across multiple rovers.
 *
 * <p>
 * Each rover is added sequentially and its instructions are executed before
 * the next rover starts moving.
 * </p>
 */
public class MissionControl {
    private final Plateau plateau;
    private final List<MissionEntry> entries = new ArrayList<>();

    /**
     * Creates a mission controller for a specific plateau.
     *
     * @param plateau the navigation area shared by all rovers
     */
    public MissionControl(Plateau plateau) {
        this.plateau = plateau;
    }

    /**
     * Adds a new rover to the mission without executing its instructions yet.
     *
     * @param x            the initial x coordinate
     * @param y            the initial y coordinate
     * @param orientation  the initial heading in the form N, E, S or W
     * @param instructions the movement commands to apply
     */
    public void addRover(int x, int y, String orientation, String instructions) {
        Rover rover = new Rover(x, y, orientation, plateau);
        entries.add(new MissionEntry(rover, instructions));
    }

    /**
     * Executes the mission and returns the final rover states as a formatted
     * string.
     *
     * @return the final positions and headings of all rovers, one per line
     */
    public String execute() {
        StringBuilder result = new StringBuilder();

        for (MissionEntry entry : entries) {
            entry.rover().execute(entry.instructions());

            if (result.length() > 0) {
                result.append(System.lineSeparator());
            }
            result.append(entry.rover());
        }

        return result.toString();
    }

    private record MissionEntry(Rover rover, String instructions) {
    }
}
