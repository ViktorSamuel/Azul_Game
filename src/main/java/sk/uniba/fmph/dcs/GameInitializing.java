package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.Arrays;

public class GameInitializing {
    private static int colorCount = Tile.values().length-1;
    private static UsedTiles usedTiles = new UsedTiles();
    public static BoardInterface createBoard(){

        ArrayList<Points> pointsPattern = new ArrayList<>();
        pointsPattern.add(new Points(1));
        pointsPattern.add(new Points(1));
        pointsPattern.add(new Points(2));
        pointsPattern.add(new Points(2));
        pointsPattern.add(new Points(2));
        pointsPattern.add(new Points(3));
        pointsPattern.add(new Points(3));

        Floor floor = new Floor(usedTiles, pointsPattern);

        WallLine[] wallLines = new WallLine[colorCount];
        for (int i = 0; i < colorCount; i++) {
            Tile[] tiles = new Tile[colorCount];
            int j = i;
            for (Tile tile : Tile.values()) {
                if (tile != Tile.STARTING_PLAYER) {
                    tiles[j] = tile;
                    j++;
                    if (j >= colorCount)
                        j = 0;
                }
            }
            WallLine lineUp = null;
            WallLine lineDown = null;
            wallLines[i] = new WallLine(tiles, lineUp, lineDown);
        }

        PatternLine[] patternLines = new PatternLine[colorCount];
        for (int i = 0; i < colorCount; i++) {
            WallLine lineUp = (i > 0) ? wallLines[i-1] : null;
            wallLines[i].setUp(lineUp);
            WallLine lineDown = (i < colorCount-1) ? wallLines[i+1] : null;
            wallLines[i].setDown(lineDown);
            patternLines[i] = new PatternLine(i+1, floor, wallLines[i], usedTiles);
        }

        return new Board(patternLines, wallLines, floor);
    }

    public static TableAreaInterface createTableArea(int numberOfPlayers){
        int factoryCount = 2*numberOfPlayers + 1;

        Bag bag = new Bag(usedTiles);
        TableCenter tableCenter = new TableCenter();

        ArrayList<TyleSource> tileSources = new ArrayList<>();
        tileSources.add(tableCenter);

        for(int i = 0; i < factoryCount; i++){
            tileSources.add(new Factory(bag, tableCenter));
        }

        return new TableArea(tileSources);
    }
}
