package sk.uniba.fmph.dcs;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BoardIntegrationTest {
    private BoardInterface board;

    @Before
    public void setUp() {
        board = GameInitializing.createBoard();
    }

    @Test
    public void testGameIntegration() {
        // Check the initial state of the board
        assertEquals(FinishRoundResult.NORMAL, board.finishRound());
        assertEquals(0, board.getPoints().getValue());
        assertEquals("Points [value=0]\n" +
                "Capacity: 1 | Current state: 0 | Color: EMPTY\n" +
                " |      \n" +
                "Capacity: 2 | Current state: 0 | Color: EMPTY\n" +
                " |      \n" +
                "Capacity: 3 | Current state: 0 | Color: EMPTY\n" +
                " |      \n" +
                "Capacity: 4 | Current state: 0 | Color: EMPTY\n" +
                " |      \n" +
                "Capacity: 5 | Current state: 0 | Color: EMPTY\n" +
                " |      \n", board.state());
        board.endGame();
        assertEquals(0, board.getPoints().getValue());

        // Put tiles on the board
        board.put(0, new Tile[]{Tile.RED});
        board.put(1, new Tile[]{Tile.BLUE});
        board.put(2, new Tile[]{Tile.GREEN, Tile.GREEN, Tile.GREEN});

        // Check the state of the board
        assertEquals(0, board.getPoints().getValue());
        assertEquals("Points [value=0]\n" +
                "Capacity: 1 | Current state: 1 | Color: R\n" +
                "     |      \n" +
                "Capacity: 2 | Current state: 1 | Color: B\n" +
                "     |      \n" +
                "Capacity: 3 | Current state: 3 | Color: G\n" +
                "     |      \n" +
                "Capacity: 4 | Current state: 0 | Color: EMPTY\n" +
                " |      \n" +
                "Capacity: 5 | Current state: 0 | Color: EMPTY\n" +
                " |      \n", board.state());

        // Finish the round
        assertEquals(FinishRoundResult.NORMAL, board.finishRound());
        assertEquals(2, board.getPoints().getValue());
        assertEquals("Points [value=2]\n" +
                "Capacity: 1 | Current state: 0 | Color: EMPTY\n" +
                " | R    \n" +
                "Capacity: 2 | Current state: 1 | Color: B\n" +
                "     |      \n" +
                "Capacity: 3 | Current state: 0 | Color: EMPTY\n" +
                " |    G \n" +
                "Capacity: 4 | Current state: 0 | Color: EMPTY\n" +
                " |      \n" +
                "Capacity: 5 | Current state: 0 | Color: EMPTY\n" +
                " |      \n", board.state());

        // Fill first row
        board.put(0, new Tile[]{Tile.YELLOW, Tile.YELLOW});
        assertEquals(2, board.getPoints().getValue());
        assertEquals("Points [value=2]\n" +
                "Capacity: 1 | Current state: 1 | Color: I\n" +
                "     | R    \n" +
                "Capacity: 2 | Current state: 1 | Color: B\n" +
                "     |      \n" +
                "Capacity: 3 | Current state: 0 | Color: EMPTY\n" +
                " |    G \n" +
                "Capacity: 4 | Current state: 0 | Color: EMPTY\n" +
                " |      \n" +
                "Capacity: 5 | Current state: 0 | Color: EMPTY\n" +
                " |      \n" +
                "I", board.state());

        assertEquals(FinishRoundResult.NORMAL, board.finishRound());
        assertEquals(2, board.getPoints().getValue());
        assertEquals("Points [value=2]\n" +
                "Capacity: 1 | Current state: 0 | Color: EMPTY\n" +
                " | R I  \n" +
                "Capacity: 2 | Current state: 1 | Color: B\n" +
                "     |      \n" +
                "Capacity: 3 | Current state: 0 | Color: EMPTY\n" +
                " |    G \n" +
                "Capacity: 4 | Current state: 0 | Color: EMPTY\n" +
                " |      \n" +
                "Capacity: 5 | Current state: 0 | Color: EMPTY\n" +
                " |      \n", board.state());

        board.put(0, new Tile[]{Tile.BLUE});
        assertEquals(FinishRoundResult.NORMAL, board.finishRound());
        assertEquals(4, board.getPoints().getValue());
        board.put(0, new Tile[]{Tile.GREEN});
        assertEquals(FinishRoundResult.NORMAL, board.finishRound());
        assertEquals(8, board.getPoints().getValue());
        assertEquals("Points [value=8]\n" +
                "Capacity: 1 | Current state: 0 | Color: EMPTY\n" +
                " | RGIB \n" +
                "Capacity: 2 | Current state: 1 | Color: B\n" +
                "     |      \n" +
                "Capacity: 3 | Current state: 0 | Color: EMPTY\n" +
                " |    G \n" +
                "Capacity: 4 | Current state: 0 | Color: EMPTY\n" +
                " |      \n" +
                "Capacity: 5 | Current state: 0 | Color: EMPTY\n" +
                " |      \n", board.state());
        board.put(0, new Tile[]{Tile.RED});
        assertEquals("Points [value=8]\n" +
                "Capacity: 1 | Current state: 0 | Color: EMPTY\n" +
                " | RGIB \n" +
                "Capacity: 2 | Current state: 1 | Color: B\n" +
                "     |      \n" +
                "Capacity: 3 | Current state: 0 | Color: EMPTY\n" +
                " |    G \n" +
                "Capacity: 4 | Current state: 0 | Color: EMPTY\n" +
                " |      \n" +
                "Capacity: 5 | Current state: 0 | Color: EMPTY\n" +
                " |      \n" +
                "R", board.state());
        board.put(0, new Tile[]{Tile.BLACK, Tile.BLACK, Tile.BLACK});
        assertEquals("Points [value=8]\n" +
                "Capacity: 1 | Current state: 1 | Color: L\n" +
                "     | RGIB \n" +
                "Capacity: 2 | Current state: 1 | Color: B\n" +
                "     |      \n" +
                "Capacity: 3 | Current state: 0 | Color: EMPTY\n" +
                " |    G \n" +
                "Capacity: 4 | Current state: 0 | Color: EMPTY\n" +
                " |      \n" +
                "Capacity: 5 | Current state: 0 | Color: EMPTY\n" +
                " |      \n" +
                "RLL", board.state());
        assertEquals(FinishRoundResult.GAME_FINISHED, board.finishRound());
        assertEquals(9, board.getPoints().getValue());
        assertEquals("Points [value=9]\n" +
                "Capacity: 1 | Current state: 0 | Color: EMPTY\n" +
                " | RGIBL\n" +
                "Capacity: 2 | Current state: 1 | Color: B\n" +
                "     |      \n" +
                "Capacity: 3 | Current state: 0 | Color: EMPTY\n" +
                " |    G \n" +
                "Capacity: 4 | Current state: 0 | Color: EMPTY\n" +
                " |      \n" +
                "Capacity: 5 | Current state: 0 | Color: EMPTY\n" +
                " |      \n", board.state());
        board.endGame();
        assertEquals(11, board.getPoints().getValue());
    }
}
