package sk.uniba.fmph.dcs;

import java.util.*;

public class Bag {
    //The game of Azul should contain a bag filled with 100 tiles, which includes 20 of each color (blue, yellow, red, black, white).
    private List<Tile> tiles;
    private final Random random;

    public Bag(){
        this.tiles = new ArrayList<>();
        this.random = new Random();
        initializeTiles();
    }
    //  Fill the bag with initial 100 tiles, 20 of each color Azul rules.
    private void initializeTiles(){
        // Example: Add 20 of each tile type to the bag
        for (Tile tile : Tile.values()) {
            for (int i = 0; i < 20; i++) {
                this.tiles.add(tile);
            }
        }
        // Shuffle the tiles to randomize the draw
        Collections.shuffle(this.tiles, random);
    }
    //  Draw a number count tiles from the bag
    public List<Tile> take(int count){
        List<Tile> drawnTiles = new ArrayList<>();
        for (int i = 0; i < count && !this.tiles.isEmpty(); i++) {
            drawnTiles.add(this.tiles.remove(this.tiles.size() - 1));
        }
        return drawnTiles;
    }
    //  Provide a string representation of the current state of the bag.
    public String state(){
        StringBuilder stateBuilder = new StringBuilder();
        stateBuilder.append("Bag contains ").append(this.tiles.size()).append(" tiles: \n");
        for (Tile tileType : Tile.values()) {
            long count = this.tiles.stream().filter(tile -> tile == tileType).count();
            stateBuilder.append(tileType).append(": ").append(count).append("\n");
        }
        return stateBuilder.toString();
    }
    //  Fill the bag with newTiles and shuffle the bag.
    public void fill(List<Tile> newTiles) {
        this.tiles.addAll(newTiles);
        Collections.shuffle(this.tiles, random);
    }
    //  Check if the bag is empty.
    public boolean isEmpty(){
        return this.tiles.isEmpty();
    }
}
