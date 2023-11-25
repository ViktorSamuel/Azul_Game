package sk.uniba.fmph.dcs;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TableAreaIntegrationTest {
    private TableAreaInterface tableArea;

    @Before
    public void setUp() {
        tableArea = GameInitializing.createTableArea(4);
    }

    @Test
    public void testTableArea() {
        for(int q = 0; q < 2; q++){
            tableArea.startNewRound();
            assertFalse(tableArea.isRoundEnd());

            ArrayList<Tile> taken = new ArrayList<>();

            // Invalid sourceId, color
            assertEquals(0, tableArea.take(-5, -8).length);

            // Invalid sourceId
            assertEquals(0, tableArea.take(28, 1).length);

            // Invalid color from table center
            assertEquals(0, tableArea.take(0, -5).length);

            // Invalid color from factory
            for (int i = 1; i < 10; i++) {
                assertEquals(0, tableArea.take(i, 0).length);
            }

            // Take everything from factories
            for (int i = 1; i < 10; i++) {
                int j = 0;
                Tile[] tiles = tableArea.take(i, j);
                while (tiles.length == 0) {
                    j = (j + 1) % 5;
                    tiles = tableArea.take(i, j);
                }
                for (Tile tile : tiles) {
                    taken.add(tile);
                }
            }


            assertFalse(tableArea.isRoundEnd());
            for (Tile tile : taken) {
                assertFalse(tile == Tile.STARTING_PLAYER);
            }

            // Take everything from table center
            for (int i = 1; i <= 5; i++) {
                Tile[] tiles = tableArea.take(0, i);
                for (Tile tile : tiles) {
                    taken.add(tile);
                }
            }

            assertTrue(tableArea.isRoundEnd());

            for(int i = 0; i < 10; i++) {
                for(int j = 0; j <= 5; j++) {
                    assertEquals(0, tableArea.take(i, j).length);
                }
            }

            boolean startPlayer = false;
            for(Tile tile : taken) {
                if(tile == Tile.STARTING_PLAYER) {
                    startPlayer = true;
                }
            }
            assertTrue(startPlayer);

            assertEquals("Table:\n" +
                    "Table center: \n" +
                    "[]\n" +
                    "[]\n" +
                    "[]\n" +
                    "[]\n" +
                    "[]\n" +
                    "[]\n" +
                    "[]\n" +
                    "[]\n" +
                    "[]\n", tableArea.state());
        }
    }
}
