package sk.uniba.fmph.dcs;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;

public class PatternLineTest {
    private PatternLine patternLine;
    private UsedTiles usedTiles;
    private Floor floor;
    private ArrayList<WallLine> wallLines;
    private WallLine wallLine;
    private final int CAPACITY = 5;

    @BeforeEach
    void setUp() {
        usedTiles = new UsedTiles();

        ArrayList<Points> pointsPattern = new ArrayList<>();
        Collections.addAll(pointsPattern,
                new Points(1), new Points(1),
                new Points(2), new Points(2), new Points(2),
                new Points(3), new Points(3), new Points(3), new Points(3));

        floor = new Floor(usedTiles, new ArrayList<>());

        Tile[] tiles = new Tile[Tile.values().length-1];
        for(int i = 0; i < tiles.length; i++) {
            tiles[i] = Tile.values()[i+1];
        }
        wallLine = new WallLine(tiles, null, null);
        patternLine = new PatternLine(CAPACITY, usedTiles, wallLine, floor);
    }

    @Test
    void test_put_one_tile() {
        Tile[] tiles = new Tile[]{Tile.RED};
        patternLine.put(tiles);
        assertEquals(1, patternLine.getPatternTiles().size());
        assertEquals(Tile.RED, patternLine.getPatternTiles().get(0));
    }

    @Test
    void test_put_two_tiles() {
        Tile[] tiles = new Tile[]{Tile.RED, Tile.RED};
        patternLine.put(tiles);
        assertEquals(2, patternLine.getPatternTiles().size());
        assertEquals(Tile.RED, patternLine.getPatternTiles().get(0));
        assertEquals(Tile.RED, patternLine.getPatternTiles().get(1));
    }

    @Test
    void test_put_two_tiles_different_color() {
        Tile[] tiles = new Tile[]{Tile.RED, Tile.BLUE};
        patternLine.put(tiles);
        assertEquals(1, patternLine.getPatternTiles().size());
        assertEquals(Tile.RED, patternLine.getPatternTiles().get(0));
        assertEquals("B", floor.state());
    }

    @Test
    void test_put_two_tiles_different_color_and_one_is_starting_player_tile() {
        Tile[] tiles = new Tile[]{Tile.STARTING_PLAYER, Tile.BLUE};
        patternLine.put(tiles);
        assertEquals(1, patternLine.getPatternTiles().size());
        assertEquals(Tile.BLUE, patternLine.getPatternTiles().get(0));
    }

    @Test
    void test_put_two_tiles_different_color_and_one_is_starting_player_tile_and_one_is_not() {
        Tile[] tiles = new Tile[]{Tile.STARTING_PLAYER, Tile.BLUE, Tile.RED};
        patternLine.put(tiles);
        assertEquals(1, patternLine.getPatternTiles().size());
        assertEquals(Tile.BLUE, patternLine.getPatternTiles().get(0));
        assertEquals("R", floor.state());
    }

    @Test
    void test_put_two_tiles_different_color_and_one_is_starting_player_tile_and_one_is_not_and_one_is_not() {
        Tile[] tiles = new Tile[]{Tile.STARTING_PLAYER, Tile.BLUE, Tile.RED, Tile.GREEN};
        patternLine.put(tiles);
        assertEquals(1, patternLine.getPatternTiles().size());
        assertEquals(Tile.BLUE, patternLine.getPatternTiles().get(0));
        assertEquals("RG", floor.state());
    }


    @Test
    void test_pattern_line_overflow_puts_extra_tiles_in_floor() {
        Tile[] tiles = new Tile[]{Tile.RED, Tile.RED, Tile.RED, Tile.RED, Tile.RED, Tile.RED};
        patternLine.put(tiles);
        assertEquals(CAPACITY, patternLine.getPatternTiles().size());
        assertEquals(1, floor.state().length());
    }

    @Test
    void test_full_wall_line_does_not_accept_tiles() {
        // Fill the wallLine here before the test
        wallLine.putTile(Tile.RED);
        Tile[] tiles = new Tile[]{Tile.RED};
        patternLine.put(tiles);
        assertEquals(0, patternLine.getPatternTiles().size());
    }

    @Test
    void test_null_tiles_throw_exception() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            patternLine.put(null);
        });

        String expectedMessage = "Cannot add null array of tiles.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testPuttingMultipleTilesOfDifferentColors() {
        Tile[] tiles = new Tile[]{Tile.RED, Tile.BLUE, Tile.GREEN, Tile.RED, Tile.YELLOW};
        patternLine.put(tiles);
        assertEquals(2, patternLine.getPatternTiles().size(), "Only tiles of the first color should be added to the pattern line.");
        assertEquals("BGI", floor.state(), "All other colored tiles should be on the floor.");
    }

    @Test
    void testCapacityExceededWithSameColor() {
        Tile[] tiles = new Tile[]{Tile.RED, Tile.RED, Tile.RED, Tile.RED, Tile.RED, Tile.RED};
        patternLine.put(tiles);
        assertEquals(CAPACITY, patternLine.getPatternTiles().size(), "Pattern line should only fill up to its capacity.");
        assertEquals("R", floor.state(), "Excess tiles should be on the floor.");
    }

    @Test
    void testPuttingTilesWhenColorIsSet() {
        Tile[] initialTiles = new Tile[]{Tile.RED, Tile.RED};
        patternLine.put(initialTiles);

        Tile[] newTiles = new Tile[]{Tile.GREEN, Tile.RED};
        patternLine.put(newTiles);
        assertEquals(3, patternLine.getPatternTiles().size(), "Should only add tiles of the set color to the pattern line.");
        assertEquals("G", floor.state(), "Tiles of other colors should be on the floor.");
    }

    @Test
    void testFinishRoundWithFullPatternLine() {
        Tile[] tiles = new Tile[]{Tile.RED, Tile.RED, Tile.RED, Tile.RED, Tile.RED};
        patternLine.put(tiles);
        Points points = patternLine.finishRound();
        assertNotNull(points, "Points should be awarded for a full pattern line.");
        assertEquals(0, patternLine.getPatternTiles().size(), "Pattern line should be empty after finishing the round.");
    }

    @Test
    void testFinishRoundWithPartiallyFilledPatternLine() {
        Tile[] tiles = new Tile[]{Tile.RED, Tile.RED, Tile.RED};
        patternLine.put(tiles);
        Points points = patternLine.finishRound();
        assertEquals(new Points(0), points, "No points should be awarded for a partially filled pattern line.");
        assertEquals(3, patternLine.getPatternTiles().size(), "Pattern line should retain its tiles after finishing the round.");
    }

    @Test
    void testFullWallLineDoesNotAcceptTiles() {
        wallLine.putTile(Tile.RED);
        Tile[] tiles = new Tile[]{Tile.RED};
        patternLine.put(tiles);
        assertTrue(patternLine.getPatternTiles().isEmpty(), "Pattern line should not accept tiles for a color that the wall line is full for.");
    }

    @Test
    void testStateAfterVariousOperations() {
        Tile[] tiles = new Tile[]{Tile.RED, Tile.RED, Tile.RED, Tile.RED, Tile.RED};
        patternLine.put(tiles);
        assertEquals("Capacity: 0 | Current state: 5 | Color: R\n", patternLine.state(), "State should reflect the pattern line content.");

        patternLine.finishRound();
        assertEquals("Capacity: 5 | Current state: 0 | Color: EMPTY\n", patternLine.state(), "State should be empty after finishing the round.");
    }

    @Test
    void testPatternLineWithStartingPlayerTile() {
        Tile[] tiles = new Tile[]{Tile.STARTING_PLAYER, Tile.RED};
        patternLine.put(tiles);
        assertEquals("Capacity: 4 | Current state: 1 | Color: R\n", patternLine.state(), "Pattern line should only contain the RED tile.");
    }

    @Test
    void testPatternLineWithNoTiles() {
        Points points = patternLine.finishRound();
        assertEquals(new Points(0), points, "No points should be awarded when no tiles have been added.");
    }

    @Test
    void testAddingTilesToEmptyPatternLineAfterPreviousRound() {
        Tile[] initialTiles = new Tile[]{Tile.RED, Tile.RED, Tile.RED, Tile.RED, Tile.RED};
        patternLine.put(initialTiles);
        patternLine.finishRound();
        Tile[] newTiles = new Tile[]{Tile.GREEN, Tile.GREEN};
        patternLine.put(newTiles);
        assertEquals("Capacity: 3 | Current state: 2 | Color: G\n", patternLine.state(), "Pattern line should contain the new tiles after finishing the round.");
    }

    @Test
    void testAddingTileThatCannotBePlacedOnWallLine() {
        wallLine.putTile(Tile.RED);
        Tile[] tiles = new Tile[]{Tile.RED};
        patternLine.put(tiles);
        assertTrue(patternLine.getPatternTiles().isEmpty(), "Pattern line should not accept tiles that cannot be placed on the wall line.");
        assertEquals("R", floor.state(), "Tile that cannot be placed should go to the floor.");
    }

}
