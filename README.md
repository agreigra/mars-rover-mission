# Rover Mars Mission

## 1. Challenge overview

This project implements the classic Mars Rover exercise according to NASA rules:

- A rectangular plateau is defined by its maximum coordinates.
- Each rover has an initial position: x, y and orientation (N, E, S, W).
- The possible instructions are:
  - `L`: rotate 90° left
  - `R`: rotate 90° right
  - `M`: move forward one grid cell in the current direction
- Movements are executed sequentially, rover by rover.
- One important goal is to prevent the rover from leaving the grid.

The project is delivered as an executable Java application with automated tests to validate the expected behavior.

---

## 2. Technical objective

The goal was not only to "solve the algorithm", but to do so in a clean, readable, and maintainable way, with an architecture close to a real production-ready solution.

I wanted to demonstrate:

- separation of responsibilities,
- domain modeling,
- validation of edge cases,
- application of SOLID principles in a simple but realistic context,
- test-driven development (TDD).

---

## 3. Project architecture

The project is structured clearly as follows:

```text
src/
├── main/
│   └── java/
│       └── com/nasa/rover/
│           ├── Main.java
│           ├── Plateau.java
│           ├── Direction.java
│           ├── Rover.java
│           ├── MissionControl.java
│           └── ...
└── test/
    └── java/
        └── com/nasa/rover/
            └── RoverAppTest.java
```

### Responsibilities

#### `Plateau`

Represents the navigation area.

- Validates plateau dimensions.
- Ensures a position remains within the grid boundaries.

#### `Direction`

Enumerates the four cardinal directions: `N`, `E`, `S`, `W`.

- Handles left and right rotation.
- Stores X and Y movement deltas.

#### `Rover`

Represents the rover with:

- x and y coordinates,
- current orientation,
- reference to the plateau,
- execution logic for instructions.

This class encapsulates the rover state and business rules.

#### `MissionControl`

Orchestrates the full mission.

- Creates the grid.
- Adds rovers.
- Executes tasks sequentially.
- Prints the final result for each rover.

#### `Main`

Loads the input file, parses the data, and runs the mission.

---

## 4. Design choices

### 4.1 Domain modeling

Instead of writing a single monolithic block of loops and conditions, I modeled the business concepts explicitly:

- plateau,
- bounded area,
- direction,
- rover,
- mission.

This keeps the code close to the problem domain and easier to understand.

### 4.2 Enum for directions

The `Direction` enum is a key design choice because it centralizes the navigation rules:

- `turnLeft()`
- `turnRight()`
- `getDeltaX()`
- `getDeltaY()`

This avoids duplicating turning logic across the codebase.

### 4.3 Strong input validation

Errors are detected early:

- invalid plateau,
- rover outside the bounds,
- unknown instruction,
- malformed file format.

This is good engineering practice because it reduces runtime errors and keeps behavior predictable.

### 4.4 Separation of responsibilities

Each class has a clearly defined role. This improves:

- code readability,
- unit testing,
- maintainability,
- evolution of the system over time.

---

## 5. SOLID principles applied

### Single Responsibility Principle (SRP)

Each class has a single responsibility.

Examples:

- `Direction` handles only direction logic.
- `Plateau` handles only terrain boundaries.
- `Rover` handles only rover state and movement logic.

### Open/Closed Principle (OCP)

The code is extensible without changing existing behavior:

- if a new direction or rule is added later, it can be extended without rewriting the whole system.

### Liskov Substitution Principle (LSP)

This is respected because the domain objects remain consistent and their responsibilities remain compatible.

### Interface Segregation Principle (ISP)

The project does not force a heavy API on any class. Each class exposes only what it needs.

### Dependency Inversion Principle (DIP)

The rover depends on the `Plateau` abstraction rather than a fragile concrete implementation. This makes the design more flexible and easier to extend.

---

## 6. TDD testing approach

I followed a TDD (Test-Driven Development) approach by writing tests to validate the expected behavior before finalizing the implementation.

### Covered tests

1. Standard mission case:
   - `1 2 N` with `LMLMLMLMM` -> `1 3 N`
   - `3 3 E` with `MMRMMRMRRM` -> `5 1 E`

2. Boundary cases:
   - a rover must not leave the plateau,
   - movement is rejected if the destination is invalid.

3. Error validation:
   - initial position outside the plateau -> exception.

### Tool used

- JUnit 5

### Test command

```bash
mvn test
```

---

## 7. How to run the project

### Run tests

```bash
mvn test
```

### Build the project

```bash
mvn package
```

### Run the program

```bash
java -jar rover.jar input.txt
```

### Example input file

```text
5 5
1 2 N
LMLMLMLMM
3 3 E
MMRMMRMRRM
```

### Example output

```text
1 3 N
5 1 E
```

---

## 8. Possible alternatives and why I did not choose them

### Option 1: monolithic logic in a single class

Example: everything in `Main` or in a single `RoverMission` class.

Advantages:

- very quick to write

Disadvantages:

- less readable code,
- harder to test,
- more difficult to maintain,
- weaker respect for design principles.

### Option 2 : tableau 2D pour le plateau

Utiliser une grille `int[][]` ou `char[][]`.

Avantages :

- simple pour des petits jeux

Inconvénients :

- plus lourd à maintenir,
- plus difficile à exprimer comme un vrai métier,
- moins lisible selon le contexte.

### Option 3 : stratégie de commandes (

Command Pattern)
Chaque instruction (`L`, `R`, `M`) serait un objet command.

Avantages :

- très extensible

Inconvénients :

- trop complexe pour un besoin relativement simple,
- pas nécessaire pour le niveau du challenge.

### Ce que j'ai choisi

Le modèle avec `Direction` + `Rover` + `MissionControl` est le meilleur compromis entre :

- clarté,
- évolutivité,
- lisibilité,
- robustesse,
- complexité maîtrisée.

---

## 9. Points de discussion pour un entretien

Pendant un entretien, je présenterai ma solution comme suit :

> "J'ai choisi de modéliser les concepts du problème plutôt que d'écrire une logique procédurale lourde. Cela me permet de faire un code plus propre, de mieux respecter les principes de conception, et de tester le comportement métier de manière fiable. J'ai également choisi une séparation claire entre le plateau, les directions, le rover et la mission pour garder le code maintenable et extensible."

Et je peux ajouter :

> "Pour une petite application comme celle-ci, il ne faut pas sur-architecturer. Le bon niveau de complexité est celui qui garde le code lisible, robuste et explicite, sans ajouter de frameworks ou de couches inutiles."

---

## 10. Conclusion

Ce projet montre qu'il est possible de résoudre un exercice technique classique avec une approche professionnelle, claire et incorporant les bonnes pratiques de développement logiciel.

Le résultat final est :

- facile à comprendre,
- facile à tester,
- extensible,
- adapté à un entretien technique ou à un partage GitHub.

---

## 11. Commandes utiles

```bash
mvn test
mvn package
java -jar rover.jar input.txt
```

---

## 12. Licence

Projet de démonstration destiné à un exercice technique et à une présentation en entretien.
