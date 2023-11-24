package sk.uniba.fmph.dcs;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BagTest {
    @Test
    public void testBagSize() {
        UsedTiles usedTiles = new UsedTiles();
        Bag bag = new Bag(usedTiles);

        assertEquals("R: 20 G: 20 I: 20 B: 20 L: 20 \n", bag.state());

        // Take all from full bag
        Tile[] tiles = bag.take(100);
        assertEquals("R: 0 G: 0 I: 0 B: 0 L: 0 \n", bag.state());
        assertEquals(100, tiles.length);
        assertEquals(5, Arrays.stream(tiles).distinct().count());

        int[] counts = new int[Tile.values().length-1];
        for(Tile tile : tiles)
            counts[tile.ordinal()-1]++;
        for(int count : counts)
            assertEquals(20, count);

        // Filling empty bag with used tiles
        Collection<Tile> used = new ArrayList<>();
        for(int i = 0; i < 20; i++) used.add(Tile.values()[(i % 5) + 1]);
        usedTiles.give(used);
        tiles = bag.take(4);
        assertEquals(4, tiles.length);
        tiles = bag.take(16);
        assertEquals(16, tiles.length);
        assertEquals("R: 0 G: 0 I: 0 B: 0 L: 0 \n", bag.state());
    }
}
