package sk.uniba.fmph.dcs;

import java.util.*;

public class Game implements GameInterface{
    private List<Player> players; // List of players in the game
    private List<Factory> factories; // List of factories in the game
    private List<GameObserver> observers; // List of observers
    private List<Board> boards; // The board
    private Bag bag; // The bag of tiles
    private TableArea tableArea; // The table area
    private int currentPlayerIndex; // Index to track the current player's turn
    private boolean isGameOver; // Flag to check if the game is over

    // Create a game with a given number of players and factories
    public Game(int numberOfPlayers, int numberOfFactories) {
        this.players = new ArrayList<>();
        this.factories = new ArrayList<>();
        this.observers = new ArrayList<>();
        this.boards = new ArrayList<>();
        this.bag = new Bag();
        this.currentPlayerIndex = 0; // Start with the first player
        this.isGameOver = false;

        // Initialize players
        for (int i = 0; i < numberOfPlayers; i++) {
            players.add(new Player(i));
        }
        // Initialize factories
        initializeFactories(numberOfFactories);
        // Initialize table area
        initializeTableArea();
        // Initialize boards
        initializeBoards(numberOfPlayers);
    }

    //  Inicialize factories
    public void initializeFactories(int count) {
        for (int i = 0; i < count; i++) {
            factories.add(new Factory(4));
        }
        for (Factory factory : factories) {
            factory.startNewRound();
            factory.fillFactory(bag);
        }
    }

    //  Initialize the table area
    private void initializeTableArea() {
        // Create a list of tile sources for the table area
        ArrayList<TyleSource> tileSources = new ArrayList<>();
        tileSources.add(new TableCenter());
        tileSources.addAll(factories);
        this.tableArea = new TableArea(tileSources);
    }

    //  Initialize boards
    private void initializeBoards(int count) {
        for (int i = 0; i < count; i++) {
            //TODO: Add a new board to the list of boards
            //boards.add(new Board());
        }
    }


    @Override
    public boolean take(int playerId, int sourceId, int idx, int destinationIdx) {
        if (isGameOver || !isValidTake(playerId, sourceId, idx, destinationIdx)) {
            return false;
        }
        // TODO: Implement the take method
        return true;
    }

    public void notifyAllObservers() {
        for (GameObserver observer : observers) {
            observer.notifyEverybody(getState());
        }
    }

    public void registerObserver(GameObserver observer) {
        observers.add(observer);
    }

    public void cancelObserver(GameObserver observer) {
        observers.remove(observer);
    }

    public String getState() {
        StringBuilder stateBuilder = new StringBuilder();
        stateBuilder.append("Table area:\n");
        stateBuilder.append(tableArea.state());
        stateBuilder.append("\n");
        stateBuilder.append("Players:\n");
        for (Board board : boards) {
            stateBuilder.append(board.state());
            stateBuilder.append("\n");
        }
        return stateBuilder.toString();
    }

    public boolean isValidTake(int playerId, int sourceId, int idx, int destinationIdx) {
        if (playerId != currentPlayerIndex) {
            return false;
        }
        if (sourceId < 0 || sourceId >= factories.size() + 1) {
            return false;
        }
        if (destinationIdx < 0 || destinationIdx >= players.size()) {
            return false;
        }
        if (idx < 0 || idx >= 4) {
            return false;
        }
        return true;
    }

}
