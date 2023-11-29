package sk.uniba.fmph.dcs;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FakeTableArea implements TableAreaInterface {
    private ArrayList<Tile> tiles;

    public FakeTableArea(){
        tiles = new ArrayList<>();
    }
    @Override
    public boolean isRoundEnd() {
        return tiles.size() == 0;
    }

    @Override
    public void startNewRound() {
        for(int i = 0; i < Tile.values().length; i++){
            tiles.add(Tile.values()[i]);
        }
    }

    @Override
    public Tile[] take(int source, int color) {
        if(source != 1 || color < 0 || color > Tile.values().length || !tiles.contains(Tile.values()[color])) return new Tile[0];
        return new Tile[]{tiles.remove(tiles.indexOf(Tile.values()[color]))};
    }

    @Override
    public String state(){
        return tiles.toString();
    }

}

class FakeBoard implements BoardInterface {
    private int points;
    private ArrayList<ArrayList<Tile>> wall;

    public FakeBoard() {
        wall = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            ArrayList<Tile> row = new ArrayList<>();
            wall.add(row);
            points = 0;
        }
    }

    @Override
    public void put(int destinationIdx, Tile[] tiles) {
        wall.get(destinationIdx).addAll(List.of(tiles));
    }

    @Override
    public FinishRoundResult finishRound() {
        boolean endGame;
        for(ArrayList<Tile> row : wall){
            endGame = true;
            for(Tile t : Tile.values()){
                if(t == Tile.STARTING_PLAYER) continue;
                if(!row.contains(t)){
                    endGame = false;
                }
            }
            if(endGame){
                return FinishRoundResult.GAME_FINISHED;
            }
        }
        return FinishRoundResult.NORMAL;
    }

    @Override
    public void endGame() {
       if (finishRound() == FinishRoundResult.GAME_FINISHED){
           points += 10;
       }
    }

    @Override
    public String state() {
        StringBuilder stateBuilder = new StringBuilder();
        for(ArrayList<Tile> row : wall){
            for(Tile t : row){
                stateBuilder.append(t.toString());
            }
            stateBuilder.append("\n");
        }
        return stateBuilder.toString();
    }

    @Override
    public Points getPoints() {
        return new Points(points);
    }
}

class FakeGameObserver implements GameObserverInterface{
    @Override
    public void notifyEverybody(String state){
        return;
    }
    @Override
    public void registerObserver(ObserverInterface observer){
        return;
    }
    @Override
    public void cancelObserver(ObserverInterface observer){
        return;
    }
}

public class GameTest {
    @Test
    public void solitaryGameTest(){
        BoardInterface boards[] = new BoardInterface[]{new FakeBoard(), new FakeBoard()};
        TableAreaInterface tableArea = new FakeTableArea();
        tableArea.startNewRound();
        GameInterface game = new Game(boards, tableArea, new FakeGameObserver());

        assertFalse(game.isGameOver());
        assertFalse(game.getGameObserver() == null);
        assertFalse(tableArea.isRoundEnd());

        assertEquals(0, game.getCurrentPlayerId());

        assertFalse(game.take(2, 2, 2, 2));
        assertFalse(game.take(0, 0, 9, 9));
        assertFalse(game.take(0, 0, 1, 0));
        assertFalse(game.take(0, 1, 1, 9));
        assertFalse(game.take(0, 1, 9, 0));

        assertEquals(0, boards[0].getPoints().getValue());
        assertEquals(0, boards[1].getPoints().getValue());

        assertTrue(game.take(0, 1, 1, 0));
        assertEquals(1, game.getCurrentPlayerId());

        assertTrue(game.take(1, 1, 0, 0));

        assertEquals("[G, I, B, L]", tableArea.state());
        assertEquals("R\n" +
                "\n" +
                "\n" +
                "\n", boards[0].state());
        assertEquals("S\n" +
                "\n" +
                "\n" +
                "\n", boards[1].state());

        assertTrue(game.take(0, 1, 2, 1));
        assertTrue(game.take(1, 1, 3, 1));

        assertTrue(game.take(0, 1, 4, 2));
        assertTrue(game.take(1, 1, 5, 2));

        assertEquals(1, game.getCurrentPlayerId());

        assertEquals("R\n" +
                "G\n" +
                "B\n" +
                "\n", boards[0].state());
        assertEquals("S\n" +
                "I\n" +
                "L\n" +
                "\n", boards[1].state());

        assertEquals(0, boards[0].getPoints().getValue());
        assertEquals(0, boards[1].getPoints().getValue());

        assertEquals("[S, R, G, I, B, L]", tableArea.state());

        assertTrue(game.take(1, 1, 1, 0));
        assertTrue(game.take(0, 1, 2, 0));

        assertTrue(game.take(1, 1, 3, 0));
        assertTrue(game.take(0, 1, 4, 0));

        assertTrue(game.take(1, 1, 5, 0));
        assertTrue(game.take(0, 1, 0, 0));

        assertEquals(0, game.getCurrentPlayerId());

        assertEquals("RGBS\n" +
                "G\n" +
                "B\n" +
                "\n", boards[0].state());
        assertEquals("SRIL\n" +
                "I\n" +
                "L\n" +
                "\n", boards[1].state());

        assertEquals(0, boards[0].getPoints().getValue());
        assertEquals(0, boards[1].getPoints().getValue());

        assertEquals("[S, R, G, I, B, L]", tableArea.state());

        assertTrue(game.take(0, 1, 5, 0));
        assertTrue(game.take(1, 1, 2, 0));

        assertFalse(game.isGameOver());

        assertTrue(game.take(0, 1, 3, 0));
        assertTrue(game.take(1, 1, 4, 2));

        assertTrue(game.take(0, 1, 1, 2));
        assertTrue(game.take(1, 1, 0, 2));

        assertEquals("RGBSLI\n" +
                "G\n" +
                "BR\n" +
                "\n", boards[0].state());
        assertEquals("SRILG\n" +
                "I\n" +
                "LBS\n" +
                "\n", boards[1].state());

        assertTrue(game.isGameOver());

        assertEquals(10, boards[0].getPoints().getValue());
        assertEquals(0, boards[1].getPoints().getValue());
    }
}
