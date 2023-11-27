package sk.uniba.fmph.dcs;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

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
        for(int i = 0; i < tiles.size(); i++){
            tiles.add(Tile.values()[1]);
        }
    }

    @Override
    public Tile[] take(int source, int color) {
        if(color != 1 || source != 1) return new Tile[0];
        return new Tile[]{tiles.remove(0)};
    }

    @Override
    public String state(){
        return "TableArea";
    }

}

class FakeBoard implements BoardInterface {
    private int points;
    public FakeBoard(){
        points = 0;
    }
    @Override
    public void put(int index, Tile[] tiles) {
        points++;
    }

    @Override
    public FinishRoundResult finishRound() {
        if(points == 10) return FinishRoundResult.GAME_FINISHED;
        return FinishRoundResult.NORMAL;
    }

    @Override
    public void endGame() {

    }

    @Override
    public String state(){
        return "Board";
    }

    @Override
    public Points getPoints(){
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
        GameInterface game = new Game(new BoardInterface[]{new FakeBoard()}, new FakeTableArea(), new FakeGameObserver());

        assertNull(game.getGameObserver());

        assertEquals(0, game.getCurrentPlayerId());

        assertFalse(game.isGameOver());
        assertFalse(game.take(2, 2, 2, 2));
        assertFalse(game.take(0, 0, 0, 0));
        assertFalse(game.take(0, 1, 1, 0));

        assertTrue(game.take(0, 1, 1, 0));

        assertEquals(0, game.getCurrentPlayerId());

        for(int i = 0; i < 7; i++)
            assertTrue(game.take(0, 1, 1, 0));

        assertFalse(game.take(0, 1, 1, 0));
        assertFalse(game.take(2, 2, 2, 2));
        assertFalse(game.take(0, 0, 0, 0));
        assertFalse(game.take(0, 1, 1, 0));

        assertTrue(game.isGameOver());


    }
}
