# Mars Rover Mission

This project implements the classic Mars rover challenge in Java with Maven.

## Goal

A plateau is defined by its upper-right coordinates. Each rover starts at a position `(x, y)` with a heading (`N`, `E`, `S`, `W`) and executes a sequence of instructions:

- `L`: turn left
- `R`: turn right
- `M`: move forward one cell

A rover cannot leave the plateau. The program prints the final position of each rover.

## Architecture

The solution is simple and follows a clear domain model:

- `Plateau`: validates the navigation area
- `Direction`: centralizes heading logic and movement deltas
- `Rover`: owns the rover state and movement rules
- `MissionControl`: orchestrates the whole mission
- `Main`: parses the input file and launches the application

## Example

Input:

```text
5 5
1 2 N
LMLMLMLMM
3 3 E
MMRMMRMRRM
```

Output:

```text
1 3 N
5 1 E
```

## Run

Build and test:

```bash
mvn test
```

Package the application:

```bash
mvn package
```

Run the executable JAR:

```bash
java -jar target/rover-1.0.0.jar input.txt
```
