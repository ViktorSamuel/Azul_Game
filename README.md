# Azul Board Game in Java

Welcome to the Azul board game implemented in Java with Object-Oriented Programming (OOP) principles. This repository contains a simple console version of the game organized as a Maven project.

## Getting Started

### Prerequisites

Make sure you have Java and Maven installed on your system.

### How to Compile and Run

1. Clone the repository:

    ```bash
    git clone https://github.com/your-username/azul-board-game-java.git
    ```

2. Navigate to the project directory:

    ```bash
    cd azul-board-game-java
    ```

3. Compile the code using Maven:

    ```bash
    mvn compile
    ```

4. Run the game using Maven:

    ```bash
    mvn exec:java -Dexec.mainClass="sk.uniba.fmph.dcs.Main"
    ```

    Alternatively, you can combine compilation and execution in one command:

    ```bash
    mvn compile exec:java -Dexec.mainClass="sk.uniba.fmph.dcs.Main"
    ```

## Game Description

This implementation provides a console-based version of the Azul board game. The main runnable class is `Main` in the package `sk.uniba.fmph.dcs`.

### How to Play

1. Enter the number of players (2 to 4).
2. The game initializes with the specified number of players, creating boards and a table area.
3. The main game loop starts, allowing each player to take their turn.
4. On each turn, the player chooses a source, color, and destination for their move.
5. The game continues until it is over (see [game rules](https://www.wikihow.com/Play-Azul)).
6. The final scores for each player are displayed.

### Azul Game Rules

For detailed instructions on how to play Azul, refer to the official game rules: [Azul Game Rules](https://www.wikihow.com/Play-Azul).

## Code Structure

The main logic is implemented in the `Main` class, utilizing other classes such as `Board`, `Game`, `GameObserver`, and interfaces like `BoardInterface`, `TableAreaInterface`, and `GameInterface`.

## Contributing

Feel free to contribute to the project by opening issues or creating pull requests. Your feedback and contributions are highly appreciated.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
