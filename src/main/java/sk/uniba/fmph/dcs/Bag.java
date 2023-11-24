package sk.uniba.fmph.dcs;

import java.util.Random;

public class Bag implements BagInterface {
    private int capacity = 100;
    private int colourCount = Tile.values().length-1;
    private int colourCapacity = capacity/colourCount;
    private int[] tiles;
    private UsedTilesTakeAllInterface usedTiles;

    public Bag(UsedTilesTakeAllInterface usedTiles){
        this.usedTiles = usedTiles;
        tiles = new int[Tile.values().length-1];
    }

    private void fill(){
        for(int tile : tiles){
            if(tile != colourCapacity)
                return;
        }
        int[] counts = new int[Tile.values().length-1];
        Tile[] used = usedTiles.takeAll();
        for(Tile tile : used){
            counts[tile.ordinal()-1]++;
        }
        for(int i = 0; i < tiles.length; i++){
            tiles[i] = colourCapacity - counts[i];
        }
    }
    @Override
    public Tile[] take(int count){
        fill();
        Tile[] taken = new Tile[count];
        Random rand = new Random();

        for(int i = 0; i < count; i++){
            int tile = rand.nextInt(tiles.length);
            while(tiles[tile] == colourCapacity){
                tile = rand.nextInt(tiles.length);
            }
            tiles[tile]++;
            taken[i] = Tile.values()[tile+1];
        }
        return taken;
    }
    @Override
    public String state(){
        StringBuilder state = new StringBuilder();
        for(int i = 0; i < tiles.length; i++){
            state.append(Tile.values()[i+1]).append(": ").append(colourCapacity-tiles[i]).append(" ");
        }
        return state.append("\n").toString();
    }
}
