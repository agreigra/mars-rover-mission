package com.nasa.rover;

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

            String[] plateauValues = lines.get(0).split("\\s+");
            if (plateauValues.length != 2) {
                throw new IllegalArgumentException("First line must contain plateau upper-right coordinates.");
            }

            Plateau plateau = new Plateau(Integer.parseInt(plateauValues[0]), Integer.parseInt(plateauValues[1]));
            MissionControl missionControl = new MissionControl(plateau);

            if (lines.size() > 1) {
                for (int i = 1; i < lines.size(); i += 2) {
                    if (i + 1 >= lines.size()) {
                        throw new IllegalArgumentException(
                                "Each rover requires a position line and an instruction line.");
                    }

                    String[] roverPosition = lines.get(i).split("\\s+");
                    if (roverPosition.length != 3) {
                        throw new IllegalArgumentException("Rover position must be in format: x y orientation");
                    }

                    missionControl.addRover(
                            Integer.parseInt(roverPosition[0]),
                            Integer.parseInt(roverPosition[1]),
                            roverPosition[2],
                            lines.get(i + 1));
                }
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
}
