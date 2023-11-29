package sk.uniba.fmph.dcs;

import java.util.*;

public class PatternLine implements PatternLineInterface{
    private int capacity;
    private int capacityMax;
    private Tile colorOfPatternLine = null;
    private List<Tile> patternTiles;
    private WallLine wallLine;
    private UsedTiles usedTiles;
    private Floor floor;

    public PatternLine(int capacity, UsedTiles usedTiles, WallLine wallLine, Floor floor){
        this.capacityMax = capacity;
        this.capacity = capacity;
        patternTiles = new ArrayList<>();
        this.usedTiles = usedTiles;
        this.wallLine = wallLine;
        this.floor = floor;
    }

    public void put(Tile[] tiles) {
        if (tiles == null) {
            throw new IllegalArgumentException("Cannot add null array of tiles.");
        }

        // Initialize a temporary list to hold tiles that can't be added to the pattern line.
        List<Tile> toRemoveTiles = new ArrayList<>();

        for (Tile tile : tiles) {
            if (tile == Tile.STARTING_PLAYER) {
                usedTiles.give(List.of(tile));
                continue;
            }

            if (colorOfPatternLine == null) {
                // Here you should also check if the wallLine can accept this color.
                if (wallLine.canPutTile(tile)) {
                    colorOfPatternLine = tile;
                } else {
                    toRemoveTiles.add(tile);
                    continue;
                }
            }

            // Check if the tile color matches and there's capacity.
            if (tile.ordinal() == colorOfPatternLine.ordinal() && capacity > 0) {
                patternTiles.add(tile);
                capacity--;
            } else {
                toRemoveTiles.add(tile);
            }
        }

        if (!toRemoveTiles.isEmpty()) {
            floor.put(toRemoveTiles);
        }
    }


    public Points finishRound(){
        if (patternTiles.isEmpty()) {
            return new Points(0);
        }
        if (capacity == 0) {
            Points points = wallLine.putTile(patternTiles.get(0));
            this.usedTiles.give(patternTiles);
            patternTiles.clear();
            colorOfPatternLine = null;
            capacity = capacityMax;
            return points;
        }
        return new Points(0);
    }

    public String state(){
        StringBuilder stringBuilder = new StringBuilder();
        for (Tile tile :
                patternTiles) {
            stringBuilder.append(tile.toString());
        }
        return stringBuilder.toString();
    }

    public int getCapacity(){
        return capacity;
    }

    public List<Tile> getPatternTiles(){
        return patternTiles;
    }

}
