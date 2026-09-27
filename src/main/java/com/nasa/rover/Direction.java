package com.nasa.rover;

/**
 * Represents one of the four cardinal directions used by the rover.
 *
 * <p>
 * Each direction knows how to rotate and how to move in the X and Y axes.
 * This centralizes navigation rules and avoids scattering direction logic
 * across the application.
 * </p>
 */
public enum Direction {
    N(0, 1),
    E(1, 0),
    S(0, -1),
    W(-1, 0);

    private final int deltaX;
    private final int deltaY;

    Direction(int deltaX, int deltaY) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    /**
     * Rotates the heading by 90 degrees to the left.
     *
     * @return the new direction after a left turn
     */
    public Direction turnLeft() {
        return switch (this) {
            case N -> W;
            case W -> S;
            case S -> E;
            case E -> N;
        };
    }

    /**
     * Rotates the heading by 90 degrees to the right.
     *
     * @return the new direction after a right turn
     */
    public Direction turnRight() {
        return switch (this) {
            case N -> E;
            case E -> S;
            case S -> W;
            case W -> N;
        };
    }

    /**
     * @return the horizontal movement delta associated with this direction
     */
    public int getDeltaX() {
        return deltaX;
    }

    /**
     * @return the vertical movement delta associated with this direction
     */
    public int getDeltaY() {
        return deltaY;
    }
}
