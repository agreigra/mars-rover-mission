package com.nasa.rover;

/**
 * Represents the rectangular plateau on which the rovers move.
 *
 * <p>
 * The plateau defines the valid navigation area and prevents rovers
 * from moving outside its limits.
 * </p>
 */
public class Plateau {
    private final int maxX;
    private final int maxY;

    /**
     * Creates a plateau with the given upper-right coordinates.
     *
     * @param maxX the maximum x coordinate of the plateau
     * @param maxY the maximum y coordinate of the plateau
     * @throws IllegalArgumentException if either dimension is negative
     */
    public Plateau(int maxX, int maxY) {
        if (maxX < 0 || maxY < 0) {
            throw new IllegalArgumentException("Plateau dimensions must be non-negative.");
        }
        this.maxX = maxX;
        this.maxY = maxY;
    }

    /**
     * Checks whether the given coordinates are inside the plateau boundaries.
     *
     * @param x the x coordinate to validate
     * @param y the y coordinate to validate
     * @return true if the position is within the plateau, false otherwise
     */
    public boolean contains(int x, int y) {
        return x >= 0 && y >= 0 && x <= maxX && y <= maxY;
    }
}
