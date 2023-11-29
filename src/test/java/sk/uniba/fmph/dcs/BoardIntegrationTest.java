package sk.uniba.fmph.dcs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class BoardIntegrationTest {
    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Board should be initialized correctly")
    void testBoardIntergrationTest() {
        BoardInterface board = GameInitialization.createWholeBoard();

        assertEquals(FinishRoundResult.NORMAL, board.finishRound());
        assertEquals(0, board.getPoints().getValue());

        board.put(0, new Tile[]{Tile.BLUE, Tile.BLUE});
        assertEquals("Points [value=0]\n" +
                "P1: Capacity: 0 | Current state: 1 | Color: B\n" +
                "W1: -----\n" +
                "P2: Capacity: 2 | Current state: 0 | Color: EMPTY\n" +
                "W2: -----\n" +
                "P3: Capacity: 3 | Current state: 0 | Color: EMPTY\n" +
                "W3: -----\n" +
                "P4: Capacity: 4 | Current state: 0 | Color: EMPTY\n" +
                "W4: -----\n" +
                "P5: Capacity: 5 | Current state: 0 | Color: EMPTY\n" +
                "W5: -----\n" +
                "Floor -> B", board.state());

        board.put(1, new Tile[]{Tile.BLUE, Tile.BLUE});
        assertEquals("Points [value=0]\n" +
                "P1: Capacity: 0 | Current state: 1 | Color: B\n" +
                "W1: -----\n" +
                "P2: Capacity: 0 | Current state: 2 | Color: B\n" +
                "W2: -----\n" +
                "P3: Capacity: 3 | Current state: 0 | Color: EMPTY\n" +
                "W3: -----\n" +
                "P4: Capacity: 4 | Current state: 0 | Color: EMPTY\n" +
                "W4: -----\n" +
                "P5: Capacity: 5 | Current state: 0 | Color: EMPTY\n" +
                "W5: -----\n" +
                "Floor -> B", board.state());
        board.put(2, new Tile[]{Tile.RED, Tile.RED});
        board.put(3, new Tile[]{Tile.BLACK, Tile.BLACK, Tile.BLACK, Tile.BLACK, Tile.BLACK});
        assertEquals("Points [value=0]\n" +
                "P1: Capacity: 0 | Current state: 1 | Color: B\n" +
                "W1: -----\n" +
                "P2: Capacity: 0 | Current state: 2 | Color: B\n" +
                "W2: -----\n" +
                "P3: Capacity: 1 | Current state: 2 | Color: R\n" +
                "W3: -----\n" +
                "P4: Capacity: 0 | Current state: 4 | Color: L\n" +
                "W4: -----\n" +
                "P5: Capacity: 5 | Current state: 0 | Color: EMPTY\n" +
                "W5: -----\n" +
                "Floor -> BL", board.state());

        board.finishRound();
        assertEquals("Points [value=1]\n" +
                "P1: Capacity: 1 | Current state: 0 | Color: EMPTY\n" +
                "W1: ---B-\n" +
                "P2: Capacity: 2 | Current state: 0 | Color: EMPTY\n" +
                "W2: --B--\n" +
                "P3: Capacity: 1 | Current state: 2 | Color: R\n" +
                "W3: -----\n" +
                "P4: Capacity: 4 | Current state: 0 | Color: EMPTY\n" +
                "W4: -L---\n" +
                "P5: Capacity: 5 | Current state: 0 | Color: EMPTY\n" +
                "W5: -----\n" +
                "Floor -> ", board.state());

        board.put(0, new Tile[]{Tile.RED, Tile.RED});
        assertEquals("Points [value=1]\n" +
                "P1: Capacity: 0 | Current state: 1 | Color: R\n" +
                "W1: ---B-\n" +
                "P2: Capacity: 2 | Current state: 0 | Color: EMPTY\n" +
                "W2: --B--\n" +
                "P3: Capacity: 1 | Current state: 2 | Color: R\n" +
                "W3: -----\n" +
                "P4: Capacity: 4 | Current state: 0 | Color: EMPTY\n" +
                "W4: -L---\n" +
                "P5: Capacity: 5 | Current state: 0 | Color: EMPTY\n" +
                "W5: -----\n" +
                "Floor -> R", board.state());
        assertEquals(FinishRoundResult.NORMAL, board.finishRound());
        assertEquals(1, board.getPoints().getValue());
        board.put(0, new Tile[]{Tile.BLACK, Tile.BLACK});
        assertEquals(FinishRoundResult.NORMAL, board.finishRound());
        assertEquals(2, board.getPoints().getValue());
        board.put(0, new Tile[]{Tile.YELLOW});
        assertEquals(FinishRoundResult.NORMAL, board.finishRound());
        assertEquals(7, board.getPoints().getValue());
        assertEquals("Points [value=7]\n"+
                "P1: Capacity: 1 | Current state: 0 | Color: EMPTY\n"+
                "W1: R-IBL\n"+
                "P2: Capacity: 2 | Current state: 0 | Color: EMPTY\n"+
                "W2: --B--\n"+
                "P3: Capacity: 1 | Current state: 2 | Color: R\n"+
                "W3: -----\n"+
                "P4: Capacity: 4 | Current state: 0 | Color: EMPTY\n"+
                "W4: -L---\n"+
                "P5: Capacity: 5 | Current state: 0 | Color: EMPTY\n"+
                "W5: -----\n"+
                "Floor -> ", board.state());
        board.put(0, new Tile[]{Tile.GREEN});

        assertEquals(FinishRoundResult.GAME_FINISHED, board.finishRound());
        assertEquals("Points [value=12]\n"+
                "P1: Capacity: 1 | Current state: 0 | Color: EMPTY\n"+
                "W1: RGIBL\n"+
                "P2: Capacity: 2 | Current state: 0 | Color: EMPTY\n"+
                "W2: --B--\n"+
                "P3: Capacity: 1 | Current state: 2 | Color: R\n"+
                "W3: -----\n"+
                "P4: Capacity: 4 | Current state: 0 | Color: EMPTY\n"+
                "W4: -L---\n"+
                "P5: Capacity: 5 | Current state: 0 | Color: EMPTY\n"+
                "W5: -----\n"+
                "Floor -> ", board.state());

    }


}
