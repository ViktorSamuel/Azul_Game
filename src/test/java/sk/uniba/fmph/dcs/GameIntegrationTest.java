package sk.uniba.fmph.dcs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class GameIntegrationTest {
    @Test
    @DisplayName("Game should be played correctly")
    void testWholeGameFromBeginingToEnd() {
        //Setup
        ArrayList<BoardInterface> boards = new ArrayList<>();
        boards.add(GameInitialization.createWholeBoard());
        boards.add(GameInitialization.createWholeBoard());

        for (BoardInterface board : boards) {
            assertEquals("Points [value=0]\n" +
                    "P1: Capacity: 1 | Current state: 0 | Color: EMPTY\n" +
                    "W1: -----\n" +
                    "P2: Capacity: 2 | Current state: 0 | Color: EMPTY\n" +
                    "W2: -----\n" +
                    "P3: Capacity: 3 | Current state: 0 | Color: EMPTY\n" +
                    "W3: -----\n" +
                    "P4: Capacity: 4 | Current state: 0 | Color: EMPTY\n" +
                    "W4: -----\n" +
                    "P5: Capacity: 5 | Current state: 0 | Color: EMPTY\n" +
                    "W5: -----\n" +
                    "Floor -> ", board.state());
        }

        ArrayList<Object> tableAndBag = GameInitialization.createTableAreaFullBag(2, true);

        TableAreaInterface table = (TableAreaInterface) tableAndBag.get(0);
        Bag bag = (Bag) tableAndBag.get(1);
        assertEquals("Bag contains 100 tiles: \n" +
                "S: 0\n" +
                "R: 20\n" +
                "G: 20\n" +
                "I: 20\n" +
                "B: 20\n" +
                "L: 20\n", bag.state());

        assertEquals("TableCenter state: S\n" +
            "Factory state: L B I G\n" +
            "Factory state: R L B I\n" +
            "Factory state: G R L B\n" +
            "Factory state: I G R L\n" +
            "Factory state: B I G R\n", table.state());

        GameObserverInterface gameObserver = new GameObserver();
        //GAME START
        Game game = new Game(boards, table, bag, gameObserver);
        assertEquals(0, game.getCurrentPlayerId());
        game.cancelObserver(gameObserver);
        assertNull(game.getGameObserver());
        game.registerObserver(gameObserver);
        assertNotNull(game.getGameObserver());
        assertEquals(game.getGameObserver(), gameObserver);

        System.out.println(game.getState());

        //Test
        System.out.println(game.take(0, 1, 1, 1));
        System.out.println(game.getState());
        System.out.println(bag.state());
    }
}
