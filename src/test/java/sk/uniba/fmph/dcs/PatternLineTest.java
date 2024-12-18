package sk.uniba.fmph.dcs;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.Assert.assertEquals;


import java.util.ArrayList;

public class PatternLineTest {
    private Floor floor;
    private WallLine wallLine;
    private UsedTiles usedTiles;
    @BeforeEach
    public void setUp() {
        usedTiles = new UsedTiles();
        ArrayList<Points> pointsPattern = new ArrayList<>();
        pointsPattern.add(new Points(1));
        pointsPattern.add(new Points(1));
        pointsPattern.add(new Points(2));
        pointsPattern.add(new Points(2));
        pointsPattern.add(new Points(2));
        pointsPattern.add(new Points(3));
        pointsPattern.add(new Points(3));
        pointsPattern.add(new Points(3));
        pointsPattern.add(new Points(3));
        floor = new Floor(usedTiles,pointsPattern);
        Tile[] tiles = new Tile[Tile.values().length-1];
        for(int i = 0; i < tiles.length; i++) {
            tiles[i] = Tile.values()[i+1];
        }
        wallLine = new WallLine(tiles, null, null);
    }

    @Test
    public void patternLineComplexTest() {
        PatternLine patternLine = new PatternLine(5, floor, wallLine, usedTiles);

        // Test empty pattern line
        assertEquals("Capacity: 5 | Current state: 0 | Color: EMPTY\n", patternLine.state());
        patternLine.put(new Tile[Tile.values().length-1]);
        assertEquals("Capacity: 5 | Current state: 0 | Color: EMPTY\n", patternLine.state());
        assertEquals(0, patternLine.finishRound().getValue());

        // Test pattern line with fewer tiles than capacity
        patternLine.put(new Tile[]{Tile.RED, Tile.RED});
        assertEquals("Capacity: 5 | Current state: 2 | Color: R\n", patternLine.state());
        assertEquals(0, patternLine.finishRound().getValue());
        assertEquals("Floor: ", floor.state());

        // Test pattern line with wrong color
        patternLine.put(new Tile[]{Tile.BLUE, Tile.BLUE});
        assertEquals("Capacity: 5 | Current state: 2 | Color: R\n", patternLine.state());
        assertEquals(0, patternLine.finishRound().getValue());
        assertEquals("Floor: -1 B | -1 B | ", floor.state());
        assertEquals("UsedTiles{count=0, usedTiles=[]}", usedTiles.state());

        // Test pattern line with more tiles than capacity
        patternLine.put(new Tile[]{Tile.RED, Tile.RED, Tile.RED, Tile.RED, Tile.RED, Tile.RED});
        assertEquals("Capacity: 5 | Current state: 5 | Color: R\n", patternLine.state());
        assertEquals("Floor: -1 B | -1 B | -2 R | -2 R | -2 R | ", floor.state());
        assertEquals(1, patternLine.finishRound().getValue());
        assertEquals("UsedTiles{count=4, usedTiles=[R, R, R, R]}", usedTiles.state());

        // Test put into pattern line color that is already in wall line
        patternLine.put(new Tile[]{Tile.RED, Tile.RED, Tile.RED});
        assertEquals("Capacity: 5 | Current state: 0 | Color: EMPTY\n", patternLine.state());
        assertEquals("Floor: -1 B | -1 B | -2 R | -2 R | -2 R | -3 R | -3 R | -3 R | ", floor.state());
        assertEquals(0, patternLine.finishRound().getValue());
        assertEquals("UsedTiles{count=4, usedTiles=[R, R, R, R]}", usedTiles.state());

        // Test full pattern line with correct color
        patternLine.put(new Tile[]{Tile.GREEN, Tile.GREEN, Tile.GREEN, Tile.GREEN, Tile.GREEN});
        assertEquals("Capacity: 5 | Current state: 5 | Color: G\n", patternLine.state());
        assertEquals("Floor: -1 B | -1 B | -2 R | -2 R | -2 R | -3 R | -3 R | -3 R | ", floor.state());
        assertEquals(2, patternLine.finishRound().getValue());
        assertEquals("UsedTiles{count=8, usedTiles=[R, R, R, R, G, G, G, G]}", usedTiles.state());
        assertEquals("Capacity: 5 | Current state: 0 | Color: EMPTY\n", patternLine.state());

        // Test pattern line with starting player tile
        patternLine.put(new Tile[]{Tile.YELLOW, Tile.STARTING_PLAYER});
        assertEquals("Capacity: 5 | Current state: 1 | Color: I\n", patternLine.state());
        assertEquals("Floor: -1 B | -1 B | -2 R | -2 R | -2 R | -3 R | -3 R | -3 R | -3 S | ", floor.state());
        assertEquals(0, patternLine.finishRound().getValue());
        assertEquals("UsedTiles{count=8, usedTiles=[R, R, R, R, G, G, G, G]}", usedTiles.state());

        // Test pattern line with starting player tile and color already in wall line
        patternLine.put(new Tile[]{Tile.GREEN, Tile.GREEN, Tile.STARTING_PLAYER});
        assertEquals("Capacity: 5 | Current state: 1 | Color: I\n", patternLine.state());
        assertEquals("Floor: -1 B | -1 B | -2 R | -2 R | -2 R | -3 R | -3 R | -3 R | -3 S | ", floor.state());
        assertEquals(0, patternLine.finishRound().getValue());
        assertEquals("UsedTiles{count=8, usedTiles=[R, R, R, R, G, G, G, G]}", usedTiles.state());

        // Test pattern line with starting player tile and different color than is in pattern line
        patternLine.put(new Tile[]{Tile.BLUE, Tile.STARTING_PLAYER});
        assertEquals("Capacity: 5 | Current state: 1 | Color: I\n", patternLine.state());
        assertEquals("Floor: -1 B | -1 B | -2 R | -2 R | -2 R | -3 R | -3 R | -3 R | -3 S | ", floor.state());
        assertEquals(0, patternLine.finishRound().getValue());
        assertEquals("UsedTiles{count=8, usedTiles=[R, R, R, R, G, G, G, G]}", usedTiles.state());

        // Test pattern line with just starting player
        patternLine.put(new Tile[]{Tile.STARTING_PLAYER});
        assertEquals("Capacity: 5 | Current state: 1 | Color: I\n", patternLine.state());
        assertEquals("Floor: -1 B | -1 B | -2 R | -2 R | -2 R | -3 R | -3 R | -3 R | -3 S | ", floor.state());
        assertEquals(0, patternLine.finishRound().getValue());
        assertEquals("UsedTiles{count=8, usedTiles=[R, R, R, R, G, G, G, G]}", usedTiles.state());
    }
}
