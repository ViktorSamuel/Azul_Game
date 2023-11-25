package sk.uniba.fmph.dcs;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Introduce the game
        System.out.println("Hello world! Welcome in Azul board game!");
        System.out.println("This is a simple console version of the game.");
        System.out.println();

        // Enter number of players
        int numberOfPlayers = 0;
        System.out.println("Enter number of players in interval 2-4:");
        Scanner scanner = new Scanner(System.in);
        numberOfPlayers = scanner.nextInt();
        while (numberOfPlayers < 2 || numberOfPlayers > 4) {
            System.out.println("Wrong number of players. Try again:");
            numberOfPlayers = scanner.nextInt();
        }
        int numOfFactories = 0;
        switch (numberOfPlayers) {
            case 2:
                numOfFactories = 5;
                break;
            case 3:
                numOfFactories = 7;
                break;
            case 4:
                numOfFactories = 9;
                break;
        }
        System.out.println("Initializing game for " + numberOfPlayers + " players...");

        // Initialize the game
        BoardInterface[] boards = new Board[numberOfPlayers];
        for (int i = 0; i < numberOfPlayers; i++) {
            boards[i] = GameInitializing.createBoard();
        }

        TableAreaInterface tableArea = GameInitializing.createTableArea(numberOfPlayers);

        Game game = new Game(boards, tableArea, new GameObserver());

        // Start the game
        System.out.println("New Game started!");
        tableArea.startNewRound();

        // Main game loop
        while(!game.isGameOver()){
            System.out.println();
            System.out.println("Player " + (game.getCurrentPlayerId() + 1) + " turn:");
            System.out.println();

            System.out.println(tableArea.state());
            System.out.println();

            System.out.println("Your board:");
            System.out.println(boards[game.getCurrentPlayerId()].state());
            System.out.println();

            System.out.println("Sources:");
            System.out.println("0 - Center");
            System.out.println("1-"+ numOfFactories +" - Factories");
            System.out.println("Choose source:");
            int source = scanner.nextInt();
            System.out.println();

            System.out.println("Choose color:");
            char color = scanner.next().charAt(0);
            int idx = -1;
            switch (color){
                case 'S':
                    idx = 0;
                    break;
                case 'R':
                    idx = 1;
                    break;
                case 'G':
                    idx = 2;
                    break;
                case 'I':
                    idx = 3;
                    break;
                case 'B':
                    idx = 4;
                    break;
                case 'L':
                    idx = 5;
                    break;
            }
            System.out.println();

            System.out.println("Destinations:");
            System.out.println("1 - Pattern line 1");
            System.out.println("2 - Pattern line 2");
            System.out.println("3 - Pattern line 3");
            System.out.println("4 - Pattern line 4");
            System.out.println("5 - Pattern line 5");
            System.out.println("Choose destination:");
            int destination = scanner.nextInt() - 1;
            System.out.println();

            if(game.take(game.getCurrentPlayerId(), source, idx, destination))
                System.out.println("Move successful!");
            else
                System.out.println("Move failed!");
            System.out.println();
        }

        System.out.println("Game over!");
        for(int i = 0; i < numberOfPlayers; i++){
            System.out.println("Player " + (i+1) + " score: " + boards[i].getPoints());
        }


    }
}
