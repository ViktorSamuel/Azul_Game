package sk.uniba.fmph.dcs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class GameInterationTest {
    @Test
    public void testGameStartToEndScenario() {
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
}
