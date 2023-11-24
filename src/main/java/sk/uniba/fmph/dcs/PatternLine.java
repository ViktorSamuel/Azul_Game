package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class PatternLine implements PatternLineInterface{
    private int capacity;
    private ArrayList<Tile> tiles;
    private Floor floor;
    private WallLine wallLine;
    private UsedTiles usedTiles;
    public PatternLine(int capacity, Floor floor, WallLine wallLine, UsedTiles usedTiles){
        this.floor = floor;
        this.wallLine = wallLine;
        this.capacity = capacity;
        this.usedTiles = usedTiles;
        tiles = new ArrayList<Tile>();
    }
    public void put(Tile[] tiles){
        boolean canPut = wallLine.canPutTile(tiles[0]);
        Collection<Tile> redundant = new ArrayList<Tile>();
        for(Tile tile : tiles){
            if(canPut && this.tiles.size() < capacity) this.tiles.add(tile);
            else redundant.add(tile);
        }
        floor.put(redundant);
    }
    public Points finishRound(){
        int points = 0;
        if(tiles.size() == capacity){
            points =  wallLine.putTile(tiles.remove(tiles.size()-1)).getValue();
            usedTiles.give(tiles.toArray(new Tile[tiles.size()]));
            tiles = new ArrayList<Tile>();
        }
        return new Points(points);
    }
    public String state(){
        return "Capacity: " + capacity + " | Current state: " + tiles.size() + " | Color: " + tiles.get(0).toString() + "\n";
    }
}
