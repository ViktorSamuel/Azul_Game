package sk.uniba.fmph.dcs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
class GameTest {

    private Game game;
    private FakeTableArea tableAreaFake;
    private Bag bagStub;
    private FakeGameObserver gameObserverFake;
    private ArrayList<BoardInterface> boardsFake;
    private FakeBoard boardFake1;
    private FakeBoard boardFake2;

    @BeforeEach
    void setUp() {
        tableAreaFake = new FakeTableArea();
        tableAreaFake.addTiles(Tile.RED, 5);
        bagStub = new Bag() {
            @Override
            public Tile[] take(int count) {
                // Return a predefined array of Tiles based on the test requirements
                return new Tile[count];
            }

            @Override
            public String state() {
                return "Stubbed bag state";
            }
        };
        gameObserverFake = new FakeGameObserver();

        boardFake1 = new FakeBoard();
        boardFake2 = new FakeBoard();
        boardsFake = new ArrayList<>();
        boardsFake.add(boardFake1);
        boardsFake.add(boardFake2);

        game = new Game(boardsFake, tableAreaFake, bagStub, gameObserverFake);
    }

    // Tests

    @Test
    void testTake_ValidTurn_PlayerTurnChanges() {
        assertTrue(game.take(0, 1, 1, 0), "Player should be able to take tiles");
        assertEquals(1, game.getCurrentPlayerId(), "Turn should go to the next player");
    }

    @Test
    void testTake_InvalidTurn_PlayerTurnDoesNotChange() {
        assertFalse(game.take(1, 1, 1, 0), "Player should not be able to take tiles on another's turn");
        assertEquals(0, game.getCurrentPlayerId(), "Turn should not change");
    }

    @Test
    void testTake_GameOver_NoActionTaken() {
        game.setGameOver(true);
        assertFalse(game.take(0, 1, 1, 0), "No action should be taken if the game is over");
    }

    @Test
    void testTake_InvalidIndices_NoActionTaken() {
        assertFalse(game.take(0, 1, 6, 0), "Invalid tile index should result in no action");
        assertFalse(game.take(0, 1, 1, 5), "Invalid destination index should result in no action");
    }

    @Test
    void testTake_RoundEnds_NewRoundStarts() {
        game.take(0, 1, 1, 0);
        game.take(1, 1, 1, 0);
        game.take(0, 1, 1, 0);
        game.take(1, 1, 1, 0);
        game.take(0, 1, 1, 0);

        assertFalse(game.take(0,1,1,0));
        assertTrue(tableAreaFake.tiles.isEmpty(), "New round should start if round ends");
    }

    @Test
    void testNotifyAllObservers_StateIsNotified() {
        // Set an observer to verify it's being notified with the correct state
        String[] notifiedState = new String[1];
        gameObserverFake = new FakeGameObserver() {
            @Override
            public void notifyEverybody(String state) {
                notifiedState[0] = state;
            }
        };
        game.registerObserver(gameObserverFake);

        game.notifyAllObservers();
        assertEquals("Table area:\nTableArea\nPlayers:\nBoard\nBoard\n", notifiedState[0], "Observers should be notified with the correct state");
    }

    @Test
    void testGetState_ReturnsCorrectState() {
        String state = game.getState();
        assertEquals("Table area:\nTableArea\nPlayers:\nBoard\nBoard\n", state, "The state should include states of table area and all boards");
    }

    @Test
    void testInitialState() {
        assertEquals(0, game.getCurrentPlayerId(), "Initial current player ID should be 0");
        assertFalse(game.getisGameOver(), "Game should not be over initially");
        assertNotNull(game.getState(), "Initial game state should not be null");
    }
    @Test
    void testCancelObserver_NoNotification() {
        game.registerObserver(gameObserverFake);
        game.cancelObserver(gameObserverFake);
        assertNull(game.getGameObserver(), "Canceled observer should not receive notifications");
    }

    @Test
    void testForGameFinished(){
        tableAreaFake.addTiles(Tile.RED, 5);
        game.take(0, 1, 1, 0);
        game.take(1, 1, 1, 0);
        game.take(0, 1, 1, 0);
        game.take(1, 1, 1, 0);
        game.take(0, 1, 1, 0);
        game.take(1, 1, 1, 0);
        game.take(0, 1, 1, 0);
        game.take(1, 1, 1, 0);
        game.take(0, 1, 1, 0);
        game.take(1, 1, 1, 0);
        assertTrue(game.getisGameOver(), "Game should be over");
    }

    @Test
    void testForGameOver(){
        tableAreaFake.addTiles(Tile.RED, 5);
        game.take(0, 1, 1, 0);
        game.take(1, 1, 1, 0);
        game.setGameOver(true);
        game.take(0, 1, 1, 0);
        assertTrue(game.getisGameOver(), "Game should be over");
    }


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
            if (tiles.isEmpty()) return null;
            if(color != 1 || source != 1) return new Tile[0];
            return new Tile[]{tiles.remove(0)};
        }

        @Override
        public String state(){
            return "TableArea";
        }

        public void addTiles(Tile color, int count) {
            for (int i = 0; i < count; i++) {
                tiles.add(color);
            }
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
            if(points == 5) return FinishRoundResult.GAME_FINISHED;
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
}

