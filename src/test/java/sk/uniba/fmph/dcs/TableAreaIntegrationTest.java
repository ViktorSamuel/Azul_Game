package sk.uniba.fmph.dcs;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.util.*;

public class TableAreaIntegrationTest {

    private TableArea tableArea;
    private Factory factory;
    private TableCenter tableCenter;
    private Bag bag;

    @BeforeEach
    void setUp() {
        // Initialize the collaborators

    }

    @Test
    void testPlayerTakesTilesFromFactoryAndTableCenter() {
        TableAreaInterface tableArea = GameInitialization.createTableArea(2, true);

        assertFalse(tableArea.isRoundEnd());

        ArrayList<Tile> takenTiles = new ArrayList<>();
        // Take tiles from the factory
        Tile[] tiles = tableArea.take(4, Tile.BLUE.ordinal());
        takenTiles.addAll(Arrays.asList(tiles));

        // Take tiles from the table center
        Tile[] tilesTABLECENTER = tableArea.take(0, Tile.BLUE.ordinal());
        takenTiles.addAll(Arrays.asList(tilesTABLECENTER));
        assertEquals(1, List.of(tilesTABLECENTER).size());

        // Check that the tiles were taken correctly
        assertEquals(4, takenTiles.size());
        assertEquals(Tile.BLUE, takenTiles.get(0));
        assertEquals(Tile.BLUE, takenTiles.get(1));
        assertEquals(Tile.BLUE, takenTiles.get(2));
        assertEquals(Tile.STARTING_PLAYER, takenTiles.get(3));

        assertFalse(tableArea.isRoundEnd());
        takenTiles.clear();
        takenTiles.addAll(Arrays.asList(tableArea.take(3, Tile.RED.ordinal())));
        assertEquals(4, takenTiles.size());
        assertEquals(Tile.RED, takenTiles.get(0));
        assertEquals(Tile.RED, takenTiles.get(1));
        assertEquals(Tile.RED, takenTiles.get(2));

        tableArea.startNewRound();

        assertEquals("TableCenter state: S\n" +
                "Factory state: I I I I\n" +
                "Factory state: R R R R\n" +
                "Factory state: R I B B\n" +
                "Factory state: R R R R\n" +
                "Factory state: R I L L\n", tableArea.state());

    }
}

