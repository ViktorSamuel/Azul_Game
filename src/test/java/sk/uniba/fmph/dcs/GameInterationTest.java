package sk.uniba.fmph.dcs;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class GameInterationTest {
    @Test
    public void invalidRequests() {
        for(int numberOfPlayers = 2; numberOfPlayers <= 4; numberOfPlayers++){
            // Create game
            BoardInterface[] boards = new BoardInterface[numberOfPlayers];
            for (int i = 0; i < numberOfPlayers; i++) {
                boards[i] = GameInitializing.createBoard();
            }
            TableAreaInterface tableArea = GameInitializing.createTableArea(numberOfPlayers);
            GameInterface game = new Game(boards, tableArea, new GameObserver());

            // Invalid requests
            // Invalid playerId, sourceId, idx, destinationIdx
            assertFalse(game.take(-5, -8, -1, -1));

            // Invalid playerId
            assertFalse(game.take(28, 1, 1, 1));

            // Invalid idx
            assertFalse(game.take(0, 1, -1, 1));

            // Invalid destinationIdx
            assertFalse(game.take(0, 1, 1, -1));

            // Invalid sourceId
            assertFalse(game.take(0, 28, 1, 1));
        }
    }

    private GameInterface game;
    private TableAreaInterface tableArea;
    private BoardInterface[] boards;
    @Before
    public void setUp() {
        boards = new BoardInterface[2];
        for (int i = 0; i < 2; i++) {
            boards[i] = GameInitializing.createBoard();
        }

        ArrayList<Tile> bagTiles = new ArrayList<>();
        for(int i = 0; i < 100; i++){
            bagTiles.add(Tile.values()[(i % (Tile.values().length-1))+1]);
        }
        FakeBag bag = new FakeBag(bagTiles);

        TableCenter tableCenter = new TableCenter();

        ArrayList<TyleSource> tileSources = new ArrayList<>();
        tileSources.add(tableCenter);

        for(int i = 0; i < 5; i++){
            tileSources.add(new Factory(bag, tableCenter));
        }

        tableArea = new TableArea(tileSources);

        game = new Game(boards, tableArea, new GameObserver());
    }

    @Test
    public void testGameStartToEndScenario(){
        assertFalse(game.isGameOver());


    }
}
