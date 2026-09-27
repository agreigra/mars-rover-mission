package com.nasa.rover;

/**
 * Represents a rover placed on the plateau.
 *
 * <p>
 * The rover manages its own coordinates, current heading and movement
 * instructions, while respecting the plateau boundaries.
 * </p>
 */
public class Rover {
    private final Plateau plateau;
    private int x;
    private int y;
    private Direction direction;

    /**
     * Creates a rover from a textual orientation.
     *
     * @param x           the initial x coordinate
     * @param y           the initial y coordinate
     * @param orientation the cardinal orientation such as N, E, S or W
     * @param plateau     the plateau on which the rover moves
     */
    public Rover(int x, int y, String orientation, Plateau plateau) {
        this(x, y, Direction.valueOf(orientation.toUpperCase()), plateau);
    }

    /**
     * Creates a rover from a direction enum instance.
     *
     * @param x         the initial x coordinate
     * @param y         the initial y coordinate
     * @param direction the initial heading
     * @param plateau   the plateau on which the rover moves
     * @throws IllegalArgumentException if the plateau is null or the initial
     *                                  position is outside the plateau
     */
    public Rover(int x, int y, Direction direction, Plateau plateau) {
        if (plateau == null) {
            throw new IllegalArgumentException("Plateau cannot be null.");
        }
        if (!plateau.contains(x, y)) {
            throw new IllegalArgumentException("Initial rover position is outside the plateau.");
        }

        this.plateau = plateau;
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    /**
     * Executes a sequence of instructions for this rover.
     *
     * @param instructions a string containing L, R and M commands
     * @throws IllegalArgumentException if an unknown instruction is encountered
     */
    public void execute(String instructions) {
        if (instructions == null) {
            throw new IllegalArgumentException("Instructions cannot be null.");
        }

        for (char instruction : instructions.toCharArray()) {
            switch (instruction) {
                case 'L' -> turnLeft();
                case 'R' -> turnRight();
                case 'M' -> moveForward();
                default -> throw new IllegalArgumentException("Unknown instruction: " + instruction);
            }
        }
    }

    /**
     * Rotates the rover 90 degrees to the left.
     */
    public void turnLeft() {
        direction = direction.turnLeft();
    }

    /**
     * Rotates the rover 90 degrees to the right.
     */
    public void turnRight() {
        direction = direction.turnRight();
    }

    /**
     * Moves the rover forward by one grid point if the destination remains inside
     * the plateau.
     */
    public void moveForward() {
        int nextX = x + direction.getDeltaX();
        int nextY = y + direction.getDeltaY();

        if (plateau.contains(nextX, nextY)) {
            x = nextX;
            y = nextY;
        }
    }

    /**
     * @return the current x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * @return the current y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * @return the current heading of the rover
     */
    public Direction getDirection() {
        return direction;
    }

    @Override
    public String toString() {
        return x + " " + y + " " + direction.name();
    }
}
