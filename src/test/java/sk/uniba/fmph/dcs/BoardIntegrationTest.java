package sk.uniba.fmph.dcs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class BoardIntegrationTest {
    private BoardInterface board;

    @BeforeEach
    public void setUp() {
        board = GameInitializing.createBoard();
    }

    @Test
    public void testGameIntegrationOneHorizontalLine() {
        // Check the initial state of the board
        assertEquals(FinishRoundResult.NORMAL, board.finishRound());
        assertEquals(0, board.getPoints().getValue());
        assertEquals("Points [value=0]\n" +
                "P1: Capacity: 1 | Current state: 0 | Color: EMPTY\n" +
                "W1: -----\n" +
                "P2: Capacity: 2 | Current state: 0 | Color: EMPTY\n" +
                "W2: -----\n" +
                "P3: Capacity: 3 | Current state: 0 | Color: EMPTY\n" +
                "W3: -----\n" +
                "P4: Capacity: 4 | Current state: 0 | Color: EMPTY\n" +
                "W4: -----\n" +
                "P5: Capacity: 5 | Current state: 0 | Color: EMPTY\n" +
                "W5: -----\n" +
                "Floor: ", board.state());
        board.endGame();
        assertEquals(0, board.getPoints().getValue());
        assertEquals(FinishRoundResult.NORMAL, board.finishRound());

        // Put tiles on the board
        board.put(0, new Tile[]{Tile.RED});
        board.put(1, new Tile[]{Tile.BLUE});
        board.put(2, new Tile[]{Tile.GREEN, Tile.GREEN, Tile.GREEN});

        // Check the state of the board
        assertEquals(0, board.getPoints().getValue());
        assertEquals("Points [value=0]\n" +
                "P1: Capacity: 1 | Current state: 1 | Color: R\n" +
                "W1: -----\n" +
                "P2: Capacity: 2 | Current state: 1 | Color: B\n" +
                "W2: -----\n" +
                "P3: Capacity: 3 | Current state: 3 | Color: G\n" +
                "W3: -----\n" +
                "P4: Capacity: 4 | Current state: 0 | Color: EMPTY\n" +
                "W4: -----\n" +
                "P5: Capacity: 5 | Current state: 0 | Color: EMPTY\n" +
                "W5: -----\n" +
                "Floor: ", board.state());

        // Finish the round
        assertEquals(FinishRoundResult.NORMAL, board.finishRound());
        assertEquals(2, board.getPoints().getValue());
        assertEquals("Points [value=2]\n" +
                "P1: Capacity: 1 | Current state: 0 | Color: EMPTY\n" +
                "W1: R----\n" +
                "P2: Capacity: 2 | Current state: 1 | Color: B\n" +
                "W2: -----\n" +
                "P3: Capacity: 3 | Current state: 0 | Color: EMPTY\n" +
                "W3: ---G-\n" +
                "P4: Capacity: 4 | Current state: 0 | Color: EMPTY\n" +
                "W4: -----\n" +
                "P5: Capacity: 5 | Current state: 0 | Color: EMPTY\n" +
                "W5: -----\n" +
                "Floor: ", board.state());

        // Fill first row
        board.put(0, new Tile[]{Tile.YELLOW, Tile.YELLOW});
        assertEquals(2, board.getPoints().getValue());
        assertEquals("Points [value=2]\n" +
                "P1: Capacity: 1 | Current state: 1 | Color: I\n" +
                "W1: R----\n" +
                "P2: Capacity: 2 | Current state: 1 | Color: B\n" +
                "W2: -----\n" +
                "P3: Capacity: 3 | Current state: 0 | Color: EMPTY\n" +
                "W3: ---G-\n" +
                "P4: Capacity: 4 | Current state: 0 | Color: EMPTY\n" +
                "W4: -----\n" +
                "P5: Capacity: 5 | Current state: 0 | Color: EMPTY\n" +
                "W5: -----\n" +
                "Floor: -1 I | ", board.state());

        assertEquals(FinishRoundResult.NORMAL, board.finishRound());
        assertEquals(2, board.getPoints().getValue());
        assertEquals("Points [value=2]\n" +
                "P1: Capacity: 1 | Current state: 0 | Color: EMPTY\n" +
                "W1: R-I--\n" +
                "P2: Capacity: 2 | Current state: 1 | Color: B\n" +
                "W2: -----\n" +
                "P3: Capacity: 3 | Current state: 0 | Color: EMPTY\n" +
                "W3: ---G-\n" +
                "P4: Capacity: 4 | Current state: 0 | Color: EMPTY\n" +
                "W4: -----\n" +
                "P5: Capacity: 5 | Current state: 0 | Color: EMPTY\n" +
                "W5: -----\n" +
                "Floor: ", board.state());

        board.put(0, new Tile[]{Tile.BLUE});
        assertEquals(FinishRoundResult.NORMAL, board.finishRound());
        assertEquals(4, board.getPoints().getValue());
        board.put(0, new Tile[]{Tile.GREEN});
        assertEquals(FinishRoundResult.NORMAL, board.finishRound());
        assertEquals(8, board.getPoints().getValue());
        assertEquals("Points [value=8]\n" +
                "P1: Capacity: 1 | Current state: 0 | Color: EMPTY\n" +
                "W1: RGIB-\n" +
                "P2: Capacity: 2 | Current state: 1 | Color: B\n" +
                "W2: -----\n" +
                "P3: Capacity: 3 | Current state: 0 | Color: EMPTY\n" +
                "W3: ---G-\n" +
                "P4: Capacity: 4 | Current state: 0 | Color: EMPTY\n" +
                "W4: -----\n" +
                "P5: Capacity: 5 | Current state: 0 | Color: EMPTY\n" +
                "W5: -----\n" +
                "Floor: ", board.state());

        board.put(0, new Tile[]{Tile.RED});
        assertEquals("Points [value=8]\n" +
                "P1: Capacity: 1 | Current state: 0 | Color: EMPTY\n" +
                "W1: RGIB-\n" +
                "P2: Capacity: 2 | Current state: 1 | Color: B\n" +
                "W2: -----\n" +
                "P3: Capacity: 3 | Current state: 0 | Color: EMPTY\n" +
                "W3: ---G-\n" +
                "P4: Capacity: 4 | Current state: 0 | Color: EMPTY\n" +
                "W4: -----\n" +
                "P5: Capacity: 5 | Current state: 0 | Color: EMPTY\n" +
                "W5: -----\n" +
                "Floor: -1 R | ", board.state());

        board.put(0, new Tile[]{Tile.BLACK, Tile.BLACK, Tile.BLACK});
        assertEquals("Points [value=8]\n" +
                "P1: Capacity: 1 | Current state: 1 | Color: L\n" +
                "W1: RGIB-\n" +
                "P2: Capacity: 2 | Current state: 1 | Color: B\n" +
                "W2: -----\n" +
                "P3: Capacity: 3 | Current state: 0 | Color: EMPTY\n" +
                "W3: ---G-\n" +
                "P4: Capacity: 4 | Current state: 0 | Color: EMPTY\n" +
                "W4: -----\n" +
                "P5: Capacity: 5 | Current state: 0 | Color: EMPTY\n" +
                "W5: -----\n" +
                "Floor: -1 R | -1 L | -2 L | ", board.state());

        assertEquals(FinishRoundResult.GAME_FINISHED, board.finishRound());
        assertEquals(9, board.getPoints().getValue());
        assertEquals("Points [value=9]\n" +
                "P1: Capacity: 1 | Current state: 0 | Color: EMPTY\n" +
                "W1: RGIBL\n" +
                "P2: Capacity: 2 | Current state: 1 | Color: B\n" +
                "W2: -----\n" +
                "P3: Capacity: 3 | Current state: 0 | Color: EMPTY\n" +
                "W3: ---G-\n" +
                "P4: Capacity: 4 | Current state: 0 | Color: EMPTY\n" +
                "W4: -----\n" +
                "P5: Capacity: 5 | Current state: 0 | Color: EMPTY\n" +
                "W5: -----\n" +
                "Floor: ", board.state());

        board.endGame();
        assertEquals(11, board.getPoints().getValue());
    }

    @Test
    public void testAllEndGameScoringRules() {
        board = GameInitializing.createBoard();
        // fill vertical line
        board.put(0, new Tile[]{Tile.RED});
        board.put(1, new Tile[]{Tile.BLACK, Tile.BLACK});
        board.put(2, new Tile[]{Tile.BLUE, Tile.BLUE, Tile.BLUE});
        board.put(3, new Tile[]{Tile.YELLOW, Tile.YELLOW, Tile.YELLOW, Tile.YELLOW});
        board.put(4, new Tile[]{Tile.GREEN, Tile.GREEN, Tile.GREEN, Tile.GREEN, Tile.GREEN});

        assertEquals(FinishRoundResult.NORMAL, board.finishRound());
        assertEquals(15, board.getPoints().getValue());
        assertEquals("Points [value=15]\n" +
                "P1: Capacity: 1 | Current state: 0 | Color: EMPTY\n" +
                "W1: R----\n" +
                "P2: Capacity: 2 | Current state: 0 | Color: EMPTY\n" +
                "W2: L----\n" +
                "P3: Capacity: 3 | Current state: 0 | Color: EMPTY\n" +
                "W3: B----\n" +
                "P4: Capacity: 4 | Current state: 0 | Color: EMPTY\n" +
                "W4: I----\n" +
                "P5: Capacity: 5 | Current state: 0 | Color: EMPTY\n" +
                "W5: G----\n" +
                "Floor: ", board.state());

        // fill diagonal line
        board.put(1, new Tile[]{Tile.RED, Tile.RED});
        board.put(2, new Tile[]{Tile.RED, Tile.RED, Tile.RED});
        board.put(3, new Tile[]{Tile.RED, Tile.RED, Tile.RED, Tile.RED});
        board.put(4, new Tile[]{Tile.RED, Tile.RED, Tile.RED, Tile.RED, Tile.RED});
        assertEquals(FinishRoundResult.NORMAL, board.finishRound());
        assertEquals(20, board.getPoints().getValue());

        // fill horizontal line
        board.put(0, new Tile[]{Tile.GREEN});
        assertEquals(FinishRoundResult.NORMAL, board.finishRound());
        board.put(0, new Tile[]{Tile.YELLOW});
        assertEquals(FinishRoundResult.NORMAL, board.finishRound());
        board.put(0, new Tile[]{Tile.BLUE});
        assertEquals(FinishRoundResult.NORMAL, board.finishRound());
        board.put(0, new Tile[]{Tile.BLACK});
        assertEquals(FinishRoundResult.GAME_FINISHED, board.finishRound());
        assertEquals(36, board.getPoints().getValue());
        assertEquals("Points [value=36]\n" +
                "P1: Capacity: 1 | Current state: 0 | Color: EMPTY\n" +
                "W1: RGIBL\n" +
                "P2: Capacity: 2 | Current state: 0 | Color: EMPTY\n" +
                "W2: LR---\n" +
                "P3: Capacity: 3 | Current state: 0 | Color: EMPTY\n" +
                "W3: B-R--\n" +
                "P4: Capacity: 4 | Current state: 0 | Color: EMPTY\n" +
                "W4: I--R-\n" +
                "P5: Capacity: 5 | Current state: 0 | Color: EMPTY\n" +
                "W5: G---R\n" +
                "Floor: ", board.state());
    }
}
