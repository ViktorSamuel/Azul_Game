package sk.uniba.fmph.dcs;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class FakeBag implements BagInterface {
    private ArrayList<Tile> tiles;
    public FakeBag(ArrayList<Tile> tiles) {
        this.tiles = tiles;
    }
    @Override
    public Tile[] take(int count) {
        Tile[] result = new Tile[count];
        for(int i = 0; i < count; i++) {
            result[i] = tiles.remove(0);
        }
        return result;
    }
    @Override
    public String state() {
        return tiles.toString();
    }
}

class FakeTableCenter implements TableCenterInterface {
    private ArrayList<Tile> tiles;
    public FakeTableCenter() {
        tiles = new ArrayList<>();
    }
    @Override
    public void add(Tile[] newTiles) {
        for(Tile tile: newTiles) {
            tiles.add(tile);
        }
    }
    public String state() {
        return tiles.toString();
    }
}

public class FactoryTest {
    @Test
    public void factoryTest() {
        ArrayList<Tile> tiles = new ArrayList<>();

        for(int i = 1; i < 5; i++) tiles.add(Tile.values()[i]);
        for(int i = 1; i < 5; i++) tiles.add(Tile.values()[5]);
        for(int i = 1; i < 5; i++) tiles.add(Tile.values()[(i%2)+2]);
        for(int i = 1; i < 5; i++) tiles.add(Tile.values()[(i%2)+2]);

        FakeBag bag = new FakeBag(tiles);
        FakeTableCenter tableCenter = new FakeTableCenter();
        Factory factory = new Factory(bag, tableCenter);

        // Empty factory
        assertEquals("[]", factory.state());
        assertEquals("[]", tableCenter.state());
        assertEquals("[]", Arrays.toString(factory.take(3)));

        // Start new round with 4 different tiles
        factory.startNewRound();
        assertEquals("[R, G, I, B]", factory.state());
        assertFalse(factory.isEmpty());

        Tile[] takenTiles = factory.take(1);
        assertEquals(1, takenTiles.length);
        assertEquals(Tile.RED, takenTiles[0]);
        assertEquals("[]", factory.state());
        assertEquals("[G, I, B]", tableCenter.state());
        assertTrue(factory.isEmpty());

        // Start new round with 4 same tiles
        factory.startNewRound();
        assertEquals("[L, L, L, L]", factory.state());
        assertFalse(factory.isEmpty());

        takenTiles = factory.take(5);
        assertEquals(4, takenTiles.length);
        assertEquals(Tile.BLACK, takenTiles[0]);
        assertEquals("[]", factory.state());
        assertEquals("[G, I, B]", tableCenter.state());
        assertTrue(factory.isEmpty());

        // Start new round with 2 different types tiles
        factory.startNewRound();
        assertEquals("[I, G, I, G]", factory.state());
        assertFalse(factory.isEmpty());

        takenTiles = factory.take(3);
        assertEquals(2, takenTiles.length);
        assertEquals(Tile.YELLOW, takenTiles[0]);
        assertEquals("[]", factory.state());
        assertEquals("[G, I, B, G, G]", tableCenter.state());

        // Invalid index
        factory.startNewRound();
        assertEquals("[I, G, I, G]", factory.state());
        assertFalse(factory.isEmpty());

        takenTiles = factory.take(5);
        assertEquals(0, takenTiles.length);
        assertEquals("[I, G, I, G]", factory.state());
        assertEquals("[G, I, B, G, G]", tableCenter.state());
    }
}
