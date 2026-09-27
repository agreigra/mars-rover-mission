package com.nasa.rover;

import static java.lang.String.format;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Entry point of the Mars rover application.
 *
 * <p>
 * This class reads the input file, builds the plateau and rover mission,
 * then prints the final positions of each rover.
 * </p>
 */
public class Main {
    /**
     * @param args expects exactly one argument: the path to the input file
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java -jar rover.jar <input-file>");
            System.exit(1);
        }

        try {
            Path inputPath = Path.of(args[0]);
            List<String> lines = Files.readAllLines(inputPath).stream()
                    .map(String::trim)
                    .filter(line -> !line.isEmpty())
                    .toList();

            if (lines.isEmpty()) {
                throw new IllegalArgumentException("Input file is empty.");
            }

            Plateau plateau = parsePlateau(lines.get(0));
            MissionControl missionControl = new MissionControl(plateau);

            for (int i = 1; i < lines.size(); i += 2) {
                if (i + 1 >= lines.size()) {
                    throw new IllegalArgumentException(
                            "Each rover requires a position line and an instruction line.");
                }

                String[] roverPosition = splitOnWhitespace(
                        lines.get(i),
                        "Rover position must be in format: x y orientation");
                if (roverPosition.length != 3) {
                    throw new IllegalArgumentException("Rover position must be in format: x y orientation");
                }

                int x = parseCoordinate(roverPosition[0], "Rover x coordinate is invalid.");
                int y = parseCoordinate(roverPosition[1], "Rover y coordinate is invalid.");
                String orientation = parseOrientation(roverPosition[2]);

                String instructions = lines.get(i + 1);
                if (instructions.isBlank()) {
                    throw new IllegalArgumentException("Rover instructions cannot be empty.");
                }

                missionControl.addRover(x, y, orientation, instructions);
            }

            System.out.println(missionControl.execute());
        } catch (IOException e) {
            System.err.println("Unable to read input file: " + e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            System.err.println("Invalid input: " + e.getMessage());
            System.exit(1);
        }
    }

    private static Plateau parsePlateau(String line) {
        String[] values = splitOnWhitespace(line, "First line must contain plateau upper-right coordinates.");
        if (values.length != 2) {
            throw new IllegalArgumentException("First line must contain plateau upper-right coordinates.");
        }

        int maxX = parseCoordinate(values[0], "Plateau x coordinate is invalid.");
        int maxY = parseCoordinate(values[1], "Plateau y coordinate is invalid.");
        return new Plateau(maxX, maxY);
    }

    private static String parseOrientation(String rawOrientation) {
        String orientation = rawOrientation.trim();
        try {
            Direction.valueOf(orientation.toUpperCase());
            return orientation.toUpperCase();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    format("Rover orientation must be one of: N, E, S, W. Received: %s", rawOrientation),
                    e);
        }
    }

    private static String[] splitOnWhitespace(String line, String errorMessage) {
        if (line == null || line.isBlank()) {
            throw new IllegalArgumentException(errorMessage);
        }
        return line.split("\\s+");
    }

    private static int parseCoordinate(String value, String errorMessage) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(errorMessage, e);
        }
    }
}
