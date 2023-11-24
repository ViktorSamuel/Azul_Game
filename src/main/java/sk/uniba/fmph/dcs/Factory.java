package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Factory extends TyleSource{
    private final int capacity = 4;
    private BagInterface bag;
    private TableCenterInterface tableCenter;
    private List<Tile> tiles;
    public Factory(BagInterface bag, TableCenterInterface tableCenter){
        this.bag = bag;
        this.tableCenter = tableCenter;
        tiles = new ArrayList<>();
    }
    @Override
    public Tile[] take(int idx){
        if(!tiles.contains(Tile.values()[idx])) return new Tile[0];
        ArrayList<Tile> takenTiles = new ArrayList<>();
        ArrayList<Tile> others = new ArrayList<>();
        for(Tile tile: tiles){
            if(tile.ordinal() == idx) takenTiles.add(tile);
            else others.add(tile);
        }
        tiles = new ArrayList<>();
        tableCenter.add(others.toArray(new Tile[others.size()]));
        return takenTiles.toArray(new Tile[takenTiles.size()]);
    }

    @Override
    public boolean isEmpty(){
        return tiles.isEmpty();
    }

    @Override
    public void startNewRound(){
        tiles = Arrays.asList(bag.take(capacity));
    }

    @Override
    public String state(){
        return tiles.toString();
    }
}
