package sk.uniba.fmph.dcs;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameInterationTest {
    @Test
    public void testGameStartToEndScenario(){
        int nextRoundStartPlayerId = 0;
        // Create game
        BoardInterface[] boards = new BoardInterface[2];
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

        TableAreaInterface tableArea = new TableArea(tileSources);
        tableArea.startNewRound();

        GameInterface game = new Game(boards, tableArea, new GameObserver());

        // Testing game
        assertFalse(game.isGameOver());
        assertEquals(nextRoundStartPlayerId, game.getCurrentPlayerId());

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

        // Play
        for(int i = 0; i < 2; i++){
            Assert.assertEquals("Points [value=0]\n" +
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
                    "Floor: ", boards[i].state());
        }

        assertEquals("Table:\n" +
                "Table center: S\n" +
                "[R, G, I, B]\n" +
                "[L, R, G, I]\n" +
                "[B, L, R, G]\n" +
                "[I, B, L, R]\n" +
                "[G, I, B, L]\n", tableArea.state());

        assertTrue(game.take(0, 1, 1, 0));
        assertTrue(game.take(1, 0, 2, 0));

        assertTrue(game.take(0, 2, 5, 1));
        assertTrue(game.take(1, 0, 2, 1));

        assertTrue(game.take(0, 3, 4, 2));
        assertTrue(game.take(1, 0, 2, 2));

        assertTrue(game.take(0, 4, 3, 3));
        assertTrue(game.take(1, 0, 4, 3));

        assertTrue(game.take(0, 5, 2, 4));
        assertTrue(game.take(1, 0, 1, 4));


        assertEquals("Points [value=0]\n" +
                "P1: Capacity: 1 | Current state: 1 | Color: R\n" +
                "W1: -----\n" +
                "P2: Capacity: 2 | Current state: 1 | Color: L\n" +
                "W2: -----\n" +
                "P3: Capacity: 3 | Current state: 1 | Color: B\n" +
                "W3: -----\n" +
                "P4: Capacity: 4 | Current state: 1 | Color: I\n" +
                "W4: -----\n" +
                "P5: Capacity: 5 | Current state: 1 | Color: G\n" +
                "W5: -----\n" +
                "Floor: ", boards[0].state());

        assertEquals("Points [value=0]\n" +
                "P1: Capacity: 1 | Current state: 1 | Color: G\n" +
                "W1: -----\n" +
                "P2: Capacity: 2 | Current state: 1 | Color: G\n" +
                "W2: -----\n" +
                "P3: Capacity: 3 | Current state: 1 | Color: G\n" +
                "W3: -----\n" +
                "P4: Capacity: 4 | Current state: 2 | Color: B\n" +
                "W4: -----\n" +
                "P5: Capacity: 5 | Current state: 3 | Color: R\n" +
                "W5: -----\n" +
                "Floor: -1 S | ", boards[1].state());

        assertEquals("Table:\n" +
                "Table center: IILLIBL\n" +
                "[]\n" +
                "[]\n" +
                "[]\n" +
                "[]\n" +
                "[]\n", tableArea.state());

        assertTrue(game.take(0, 0, 3, 3));
        assertTrue(game.take(1, 0, 4, 3));

        assertTrue(game.take(0, 0, 5, 1));

        assertEquals("Points [value=2]\n" +
                "P1: Capacity: 1 | Current state: 0 | Color: EMPTY\n" +
                "W1: R----\n" +
                "P2: Capacity: 2 | Current state: 0 | Color: EMPTY\n" +
                "W2: L----\n" +
                "P3: Capacity: 3 | Current state: 1 | Color: B\n" +
                "W3: -----\n" +
                "P4: Capacity: 4 | Current state: 0 | Color: EMPTY\n" +
                "W4: I----\n" +
                "P5: Capacity: 5 | Current state: 1 | Color: G\n" +
                "W5: -----\n" +
                "Floor: ", boards[0].state());

        assertEquals("Points [value=0]\n" +
                "P1: Capacity: 1 | Current state: 0 | Color: EMPTY\n" +
                "W1: -G---\n" +
                "P2: Capacity: 2 | Current state: 1 | Color: G\n" +
                "W2: -----\n" +
                "P3: Capacity: 3 | Current state: 1 | Color: G\n" +
                "W3: -----\n" +
                "P4: Capacity: 4 | Current state: 3 | Color: B\n" +
                "W4: -----\n" +
                "P5: Capacity: 5 | Current state: 3 | Color: R\n" +
                "W5: -----\n" +
                "Floor: ", boards[1].state());

        assertEquals("Table:\n" +
                "Table center: S\n" +
                "[R, G, I, B]\n" +
                "[L, R, G, I]\n" +
                "[B, L, R, G]\n" +
                "[I, B, L, R]\n" +
                "[G, I, B, L]\n", tableArea.state());

        assertEquals(1, game.getCurrentPlayerId());
        assertTrue(game.take(1, 1, 2, 1));

        assertTrue(game.take(0, 2, 2, 4));
        assertTrue(game.take(1, 5, 2, 2));

        assertTrue(game.take(0, 0, 4, 2));
        assertTrue(game.take(1, 0, 1, 4));

        assertTrue(game.take(0, 0, 5, 1));
        assertTrue(game.take(1, 3, 2,  2));

        assertTrue(game.take(0, 0, 4, 3));
        assertTrue(game.take(1, 4, 1, 3));

        assertTrue(game.take(0, 0, 3, 0));
        assertTrue(game.take(1, 0, 4, 3));

        assertEquals("Points [value=2]\n" +
                "P1: Capacity: 1 | Current state: 1 | Color: I\n" +
                "W1: R----\n" +
                "P2: Capacity: 2 | Current state: 0 | Color: EMPTY\n" +
                "W2: L----\n" +
                "P3: Capacity: 3 | Current state: 3 | Color: B\n" +
                "W3: -----\n" +
                "P4: Capacity: 4 | Current state: 1 | Color: B\n" +
                "W4: I----\n" +
                "P5: Capacity: 5 | Current state: 2 | Color: G\n" +
                "W5: -----\n" +
                "Floor: -1 S | -1 L | -2 L | -2 I | -2 I | -3 I | ", boards[0].state());

        assertEquals("Points [value=0]\n" +
                "P1: Capacity: 1 | Current state: 0 | Color: EMPTY\n" +
                "W1: -G---\n" +
                "P2: Capacity: 2 | Current state: 2 | Color: G\n" +
                "W2: -----\n" +
                "P3: Capacity: 3 | Current state: 3 | Color: G\n" +
                "W3: -----\n" +
                "P4: Capacity: 4 | Current state: 4 | Color: B\n" +
                "W4: -----\n" +
                "P5: Capacity: 5 | Current state: 5 | Color: R\n" +
                "W5: -----\n" +
                "Floor: -1 R | ", boards[1].state());

        assertEquals("Table:\n" +
                "Table center: LRL\n" +
                "[]\n" +
                "[]\n" +
                "[]\n" +
                "[]\n" +
                "[]\n", tableArea.state());

        assertTrue(game.take(0, 0, 1, 3));
        assertTrue(game.take(1, 0, 5, 3));

        assertEquals("Points [value=0]\n" +
                "P1: Capacity: 1 | Current state: 0 | Color: EMPTY\n" +
                "W1: R-I--\n" +
                "P2: Capacity: 2 | Current state: 0 | Color: EMPTY\n" +
                "W2: L----\n" +
                "P3: Capacity: 3 | Current state: 0 | Color: EMPTY\n" +
                "W3: B----\n" +
                "P4: Capacity: 4 | Current state: 1 | Color: B\n" +
                "W4: I----\n" +
                "P5: Capacity: 5 | Current state: 2 | Color: G\n" +
                "W5: -----\n" +
                "Floor: ", boards[0].state());

        assertEquals("Points [value=0]\n" +
                "P1: Capacity: 1 | Current state: 0 | Color: EMPTY\n" +
                "W1: -G---\n" +
                "P2: Capacity: 2 | Current state: 0 | Color: EMPTY\n" +
                "W2: --G--\n" +
                "P3: Capacity: 3 | Current state: 0 | Color: EMPTY\n" +
                "W3: ---G-\n" +
                "P4: Capacity: 4 | Current state: 0 | Color: EMPTY\n" +
                "W4: -B---\n" +
                "P5: Capacity: 5 | Current state: 0 | Color: EMPTY\n" +
                "W5: ----R\n" +
                "Floor: ", boards[1].state());

        assertEquals("Table:\n" +
                "Table center: S\n" +
                "[R, G, I, B]\n" +
                "[L, R, G, I]\n" +
                "[B, L, R, G]\n" +
                "[I, B, L, R]\n" +
                "[G, I, B, L]\n", tableArea.state());

        assertEquals(0, game.getCurrentPlayerId());
        assertTrue(game.take(0, 1, 4, 0));
        assertTrue(game.take(1, 2, 1, 0));

        assertTrue(game.take(0, 3, 4, 3));
        assertTrue(game.take(1, 0, 1, 4));

        assertTrue(game.take(0, 0, 2, 4));
        assertTrue(game.take(1, 4, 5, 2));



        assertTrue(game.take(0, 5, 4, 3));
        assertTrue(game.take(1, 0, 3, 3));

        assertTrue(game.take(0, 0, 5, 0));
        assertTrue(game.take(1, 0, 1, 3));

        assertTrue(game.take(0, 0, 4, 3));
        assertTrue(game.take(1, 0, 2, 2));

        assertEquals("Points [value=5]\n" +
                "P1: Capacity: 1 | Current state: 0 | Color: EMPTY\n" +
                "W1: R-IB-\n" +
                "P2: Capacity: 2 | Current state: 0 | Color: EMPTY\n" +
                "W2: L----\n" +
                "P3: Capacity: 3 | Current state: 0 | Color: EMPTY\n" +
                "W3: B----\n" +
                "P4: Capacity: 4 | Current state: 0 | Color: EMPTY\n" +
                "W4: IB---\n" +
                "P5: Capacity: 5 | Current state: 0 | Color: EMPTY\n" +
                "W5: G----\n" +
                "Floor: ", boards[0].state());

        assertEquals("Points [value=0]\n" +
                "P1: Capacity: 1 | Current state: 0 | Color: EMPTY\n" +
                "W1: RG---\n" +
                "P2: Capacity: 2 | Current state: 0 | Color: EMPTY\n" +
                "W2: --G--\n" +
                "P3: Capacity: 3 | Current state: 1 | Color: L\n" +
                "W3: ---G-\n" +
                "P4: Capacity: 4 | Current state: 0 | Color: EMPTY\n" +
                "W4: IB---\n" +
                "P5: Capacity: 5 | Current state: 0 | Color: EMPTY\n" +
                "W5: ----R\n" +
                "Floor: ", boards[1].state());

        assertEquals("Table:\n" +
                "Table center: S\n" +
                "[R, G, I, B]\n" +
                "[L, R, G, I]\n" +
                "[B, L, R, G]\n" +
                "[I, B, L, R]\n" +
                "[G, I, B, L]\n", tableArea.state());

        assertEquals(1, game.getCurrentPlayerId());
        assertTrue(game.take(1, 2, 2, 4));
        assertTrue(game.take(0, 1, 2, 0));

        assertTrue(game.take(1, 0, 2, 4));
        assertTrue(game.take(0, 3, 4, 2));


        assertTrue(game.take(1, 5, 2, 4));
        assertTrue(game.take(0, 0, 4, 1));

        assertTrue(game.take(1, 4, 3, 3));
        assertTrue(game.take(0, 0, 5, 3));

        assertTrue(game.take(1, 0, 1, 3));
        assertTrue(game.take(0, 0, 4, 3));

        assertEquals("Points [value=5]\n" +
                "P1: Capacity: 1 | Current state: 1 | Color: G\n" +
                "W1: R-IB-\n" +
                "P2: Capacity: 2 | Current state: 2 | Color: B\n" +
                "W2: L----\n" +
                "P3: Capacity: 3 | Current state: 0 | Color: EMPTY\n" +
                "W3: B----\n" +
                "P4: Capacity: 4 | Current state: 4 | Color: L\n" +
                "W4: IB---\n" +
                "P5: Capacity: 5 | Current state: 0 | Color: EMPTY\n" +
                "W5: G----\n" +
                "Floor: -1 B | -1 B | ", boards[0].state());

        assertEquals("Points [value=0]\n" +
                "P1: Capacity: 1 | Current state: 0 | Color: EMPTY\n" +
                "W1: RG---\n" +
                "P2: Capacity: 2 | Current state: 0 | Color: EMPTY\n" +
                "W2: --G--\n" +
                "P3: Capacity: 3 | Current state: 1 | Color: L\n" +
                "W3: ---G-\n" +
                "P4: Capacity: 4 | Current state: 4 | Color: R\n" +
                "W4: IB---\n" +
                "P5: Capacity: 5 | Current state: 2 | Color: G\n" +
                "W5: ----R\n" +
                "Floor: -1 S | -1 I | ", boards[1].state());

        assertEquals("Table:\n" +
                "Table center: IIGI\n" +
                "[]\n" +
                "[]\n" +
                "[]\n" +
                "[]\n" +
                "[]\n", tableArea.state());

        assertTrue(game.take(1, 0, 2, 4));
        assertTrue(game.take(0, 0, 3, 3));

        assertEquals("Points [value=5]\n" +
                "P1: Capacity: 1 | Current state: 0 | Color: EMPTY\n" +
                "W1: RGIB-\n" +
                "P2: Capacity: 2 | Current state: 0 | Color: EMPTY\n" +
                "W2: L---B\n" +
                "P3: Capacity: 3 | Current state: 0 | Color: EMPTY\n" +
                "W3: B----\n" +
                "P4: Capacity: 4 | Current state: 0 | Color: EMPTY\n" +
                "W4: IBL--\n" +
                "P5: Capacity: 5 | Current state: 0 | Color: EMPTY\n" +
                "W5: G----\n" +
                "Floor: ", boards[0].state());

        assertEquals("Points [value=0]\n" +
                "P1: Capacity: 1 | Current state: 0 | Color: EMPTY\n" +
                "W1: RG---\n" +
                "P2: Capacity: 2 | Current state: 0 | Color: EMPTY\n" +
                "W2: --G--\n" +
                "P3: Capacity: 3 | Current state: 1 | Color: L\n" +
                "W3: ---G-\n" +
                "P4: Capacity: 4 | Current state: 0 | Color: EMPTY\n" +
                "W4: IB-R-\n" +
                "P5: Capacity: 5 | Current state: 3 | Color: G\n" +
                "W5: ----R\n" +
                "Floor: ", boards[1].state());

        assertEquals("Table:\n" +
                "Table center: S\n" +
                "[R, G, I, B]\n" +
                "[L, R, G, I]\n" +
                "[B, L, R, G]\n" +
                "[I, B, L, R]\n" +
                "[G, I, B, L]\n", tableArea.state());

        assertEquals(1, game.getCurrentPlayerId());
        assertTrue(game.take(1, 1, 2, 4));
        assertTrue(game.take(0, 0, 4, 4));

        assertTrue(game.take(1, 2, 2, 4));
        assertTrue(game.take(0, 5, 4, 4));

        assertTrue(game.take(1, 3, 2, 3));
        assertTrue(game.take(0, 4, 4, 4));

        assertTrue(game.take(1, 0, 2, 3));
        assertTrue(game.take(0, 0, 4, 4));

        assertTrue(game.take(1, 0, 5, 2));
        assertTrue(game.take(0, 0, 3, 2));

        assertTrue(game.take(1, 0, 1, 2));

        assertEquals("Points [value=5]\n" +
                "P1: Capacity: 1 | Current state: 0 | Color: EMPTY\n" +
                "W1: RGIB-\n" +
                "P2: Capacity: 2 | Current state: 0 | Color: EMPTY\n" +
                "W2: L---B\n" +
                "P3: Capacity: 3 | Current state: 0 | Color: EMPTY\n" +
                "W3: B---I\n" +
                "P4: Capacity: 4 | Current state: 0 | Color: EMPTY\n" +
                "W4: IBL--\n" +
                "P5: Capacity: 5 | Current state: 4 | Color: B\n" +
                "W5: G----\n" +
                "Floor: ", boards[0].state());

        assertEquals("Points [value=0]\n" +
                "P1: Capacity: 1 | Current state: 0 | Color: EMPTY\n" +
                "W1: RG---\n" +
                "P2: Capacity: 2 | Current state: 0 | Color: EMPTY\n" +
                "W2: --G--\n" +
                "P3: Capacity: 3 | Current state: 0 | Color: EMPTY\n" +
                "W3: -L-G-\n" +
                "P4: Capacity: 4 | Current state: 2 | Color: G\n" +
                "W4: IB-R-\n" +
                "P5: Capacity: 5 | Current state: 0 | Color: EMPTY\n" +
                "W5: G---R\n" +
                "Floor: ", boards[1].state());

        assertEquals("Table:\n" +
                "Table center: S\n" +
                "[R, G, I, B]\n" +
                "[L, R, G, I]\n" +
                "[B, L, R, G]\n" +
                "[I, B, L, R]\n" +
                "[G, I, B, L]\n", tableArea.state());

        assertEquals(0, game.getCurrentPlayerId());
        assertTrue(game.take(0, 4, 5, 0));
        assertTrue(game.take(1, 2, 2, 3));

        assertTrue(game.take(0, 0, 3, 1));
        assertTrue(game.take(1, 5, 2, 3));

        assertTrue(game.take(0, 3, 4, 4));
        assertTrue(game.take(1, 1, 3, 0));

        assertEquals("Points [value=5]\n" +
                "P1: Capacity: 1 | Current state: 1 | Color: L\n" +
                "W1: RGIB-\n" +
                "P2: Capacity: 2 | Current state: 2 | Color: I\n" +
                "W2: L---B\n" +
                "P3: Capacity: 3 | Current state: 0 | Color: EMPTY\n" +
                "W3: B---I\n" +
                "P4: Capacity: 4 | Current state: 0 | Color: EMPTY\n" +
                "W4: IBL--\n" +
                "P5: Capacity: 5 | Current state: 5 | Color: B\n" +
                "W5: G----\n" +
                "Floor: -1 S | ", boards[0].state());

        assertEquals("Points [value=0]\n" +
                "P1: Capacity: 1 | Current state: 1 | Color: I\n" +
                "W1: RG---\n" +
                "P2: Capacity: 2 | Current state: 0 | Color: EMPTY\n" +
                "W2: --G--\n" +
                "P3: Capacity: 3 | Current state: 0 | Color: EMPTY\n" +
                "W3: -L-G-\n" +
                "P4: Capacity: 4 | Current state: 4 | Color: G\n" +
                "W4: IB-R-\n" +
                "P5: Capacity: 5 | Current state: 0 | Color: EMPTY\n" +
                "W5: G---R\n" +
                "Floor: ", boards[1].state());

        assertEquals("Table:\n" +
                "Table center: BRLRIBLLRGRGB\n" +
                "[]\n" +
                "[]\n" +
                "[]\n" +
                "[]\n" +
                "[]\n", tableArea.state());

        assertTrue(game.take(0, 0, 2, 2));
        assertTrue(game.take(1, 0, 5, 1));

        assertTrue(game.take(0, 0, 1, 3));
        assertTrue(game.take(1, 0, 4, 2));

        assertTrue(game.take(0, 0, 3, 4));

        assertTrue(game.isGameOver());

        assertEquals("Points [value=40]\n" +
                "P1: Capacity: 1 | Current state: 0 | Color: EMPTY\n" +
                "W1: RGIBL\n" +
                "P2: Capacity: 2 | Current state: 0 | Color: EMPTY\n" +
                "W2: L--IB\n" +
                "P3: Capacity: 3 | Current state: 2 | Color: G\n" +
                "W3: B---I\n" +
                "P4: Capacity: 4 | Current state: 0 | Color: EMPTY\n" +
                "W4: IBLR-\n" +
                "P5: Capacity: 5 | Current state: 0 | Color: EMPTY\n" +
                "W5: G-B--\n" +
                "Floor: ", boards[0].state());

        assertEquals("Points [value=34]\n" +
                "P1: Capacity: 1 | Current state: 0 | Color: EMPTY\n" +
                "W1: RGI--\n" +
                "P2: Capacity: 2 | Current state: 0 | Color: EMPTY\n" +
                "W2: L-G--\n" +
                "P3: Capacity: 3 | Current state: 0 | Color: EMPTY\n" +
                "W3: BL-G-\n" +
                "P4: Capacity: 4 | Current state: 0 | Color: EMPTY\n" +
                "W4: IB-RG\n" +
                "P5: Capacity: 5 | Current state: 0 | Color: EMPTY\n" +
                "W5: G---R\n" +
                "Floor: ", boards[1].state());
    }
}
