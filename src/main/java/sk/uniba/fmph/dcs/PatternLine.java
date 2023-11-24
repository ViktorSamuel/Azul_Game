package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.Collection;

public class PatternLine implements PatternLineInterface{
    private int capacity;
    private ArrayList<Tile> tiles;
    private FloorInterface floor;
    private WallLineInterface wallLine;
    private UsedTilesGiveInterface usedTiles;
    private Tile color = null;
    public PatternLine(int capacity, FloorInterface floor, WallLineInterface wallLine, UsedTilesGiveInterface usedTiles){
        this.floor = floor;
        this.wallLine = wallLine;
        this.capacity = capacity;
        this.usedTiles = usedTiles;
        tiles = new ArrayList<Tile>();
    }
    public void put(Tile[] tiles){
        boolean canPut = wallLine.canPutTile(tiles[0]) && (color == null || color == tiles[0]);
        Collection<Tile> redundant = new ArrayList<Tile>();
        if(tiles[tiles.length-1] == Tile.STARTING_PLAYER) redundant.add(Tile.STARTING_PLAYER);
        for(Tile tile : tiles){
            if(tile == null) continue;
            if(tile == Tile.STARTING_PLAYER) continue;
            if(canPut && this.tiles.size() < capacity) {
                if (color == null) {
                    color = tile;
                }
                this.tiles.add(tile);
            }
            else redundant.add(tile);
        }
        floor.put(redundant);
    }
    public Points finishRound(){
        int points = 0;
        if(tiles.size() == capacity){
            points =  wallLine.putTile(tiles.remove(tiles.size()-1)).getValue();
            usedTiles.give(tiles);
            tiles = new ArrayList<Tile>();
            color = null;
        }
        return new Points(points);
    }
    public String state(){
        String state = "EMPTY";
        if(color != null) state = tiles.get(0).toString();
        return "Capacity: " + capacity + " | Current state: " + tiles.size() + " | Color: " + state + "\n";
    }
}
