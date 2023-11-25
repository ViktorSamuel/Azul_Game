package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Bag {
    private List<Tile> tiles;
    private final Random random;

    public Bag(){
        this.tiles = new ArrayList<>();
        this.random = new Random();
        initializeTiles();
    }

    private void initializeTiles(){
        // TODO: Add the correct number of each type of Tile to the bag.
    }

    public List<Tile> take(int count){
        List<Tile> drawnTiles = new ArrayList<>();
        // TODO: Implement the logic to draw random tiles from the bag.
        return drawnTiles;
    }

    public String state(){
        // TODO: Implement a method to return the state of the bag for debugging purposes.
        return "";
    }
}
