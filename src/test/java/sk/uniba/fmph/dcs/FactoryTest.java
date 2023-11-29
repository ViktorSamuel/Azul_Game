package sk.uniba.fmph.dcs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
class FactoryTest {

    private Factory factory;
    private Bag mockBag; // Using the actual Bag class for simplicity

    private class MockBag extends Bag {
        public MockBag(List<Tile> predefinedTiles) {
            super();
            this.tiles = predefinedTiles; // Directly set the tiles to predefined ones
        }
    }

    @BeforeEach
    void setUp() {
        // Create a predefined set of tiles
        List<Tile> predefinedTiles = new ArrayList<>();
        predefinedTiles.add(Tile.BLUE);
        predefinedTiles.add(Tile.YELLOW);
        predefinedTiles.add(Tile.RED);
        predefinedTiles.add(Tile.BLACK);

        // Initialize the mock bag with the predefined tiles
        mockBag = new MockBag(predefinedTiles);

        // Assuming the capacity of the factory is 4
        factory = new Factory(4, mockBag);
    }

    @Test
    @DisplayName("Factory should throw IllegalArgumentException if capacity is zero or negative")
    void testFactoryWithInvalidCapacity() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Factory(0, mockBag);
        });
        assertTrue(exception.getMessage().contains("Capacity must be greater than 0."));
    }

    @Test
    @DisplayName("Factory should be empty after creation")
    void testIsEmptyAfterCreation() {
        assertTrue(factory.isEmpty(), "Factory should be empty right after being created.");
    }

    @Test
    @DisplayName("Factory should not be empty after filling")
    void testIsNotEmptyAfterFilling() {
        factory.fillFactory();
        assertFalse(factory.isEmpty(), "Factory should not be empty after filling with tiles from bag.");
    }

    @Test
    @DisplayName("Factory should allow taking tiles once per round")
    void testTakeTilesOncePerRound() {
        factory.fillFactory();
        Tile[] takenTiles = factory.take(1); // Assuming we are taking tiles of the type at index 1
        assertTrue(takenTiles.length > 0, "Factory should allow taking tiles.");
        assertTrue(factory.wasAlreadyTaken(), "Factory should not allow taking more tiles once taken.");
    }

    @Test
    @DisplayName("Factory should return the correct state")
    void testState() {
        factory.fillFactory();
        String state = factory.state();
        assertNotNull(state, "State should not be null.");
        assertFalse(state.isEmpty(), "State should not be empty after filling the factory.");
        assertTrue(state.startsWith("Factory state:"), "State should start with 'Factory state:'.");
    }

    @Test
    @DisplayName("Factory should be reset correctly for a new round")
    void testStartNewRound() {
        factory.fillFactory();
        factory.take(1);
        factory.startNewRound();
        assertTrue(factory.isEmpty(), "Factory should be empty after starting a new round.");
        assertFalse(factory.wasAlreadyTaken(), "Factory should be reset to allow taking tiles again after a new round has started.");
    }

    @Test
    @DisplayName("Factory should handle taking with invalid index")
    void testTakeWithInvalidIndex() {
        factory.fillFactory();
        Tile[] takenTiles = factory.take(Tile.values().length); // Index out of bounds
        assertEquals(0, takenTiles.length, "Factory should not allow taking with an invalid index.");
    }

    @Test
    @DisplayName("Factory should not allow taking tiles again if already taken in the round")
    void testNoTakeIfAlreadyTaken() {
        factory.fillFactory();
        factory.take(1);
        Tile[] takenTilesSecondTime = factory.take(1);
        assertEquals(0, takenTilesSecondTime.length, "Factory should not allow taking tiles again after already taken in the same round.");
    }

    @Test
    @DisplayName("Factory should return an empty array when taking tiles from an empty factory")
    void testTakeWhenFactoryIsEmpty() {
        Tile[] takenTiles = factory.take(1);
        assertEquals(0, takenTiles.length, "Factory should return an empty array when taking tiles from an empty factory.");
    }

    @Test
    @DisplayName("Factory take method should handle different valid and invalid indexes")
    void testTakeWithVariousIndexes() {
        factory.fillFactory();
        Tile[] takenTiles = factory.take(2);
        assertNotNull(takenTiles, "Factory should allow taking tiles with a valid index.");

        takenTiles = factory.take(-1);
        assertEquals(0, takenTiles.length, "Factory should return an empty array for negative index.");

        takenTiles = factory.take(Tile.values().length + 1);
        assertEquals(0, takenTiles.length, "Factory should return an empty array for index out of bounds.");
    }

    @Test
    @DisplayName("Factory should be filled correctly when using fillFactory method with an empty bag")
    void testFillFactoryWithEmptyBag() {
        MockBag emptyBag = new MockBag(Collections.emptyList());
        factory = new Factory(4, emptyBag); // Create a new factory with an empty bag
        factory.fillFactory();
        assertTrue(factory.isEmpty(), "Factory should remain empty when filled with an empty bag.");
    }

    @Test
    @DisplayName("Factory should return all remaining tiles correctly")
    void testGetRemainingTiles() {
        factory.fillFactory();
        List<Tile> remainingTiles = factory.getRemainingTiles();
        assertEquals(0, remainingTiles.size(), "Factory should return no remaining tiles before taking.");
        factory.take(1);
        remainingTiles = factory.getRemainingTiles();
        assertEquals(3, remainingTiles.size(), "Factory should return all remaining tiles correctly after filling.");
    }

    @Test
    @DisplayName("Factory should return all available tiles correctly")
    void testGetAvailableTiles() {
        factory.fillFactory();
        List<Tile> availableTiles = factory.getAvailableTiles();
        assertEquals(4, availableTiles.size(), "Factory should return all available tiles correctly after filling.");
        factory.take(1);
        availableTiles = factory.getAvailableTiles();
        assertEquals(0, availableTiles.size(), "Factory should return no available tiles after taking.");
    }
}
