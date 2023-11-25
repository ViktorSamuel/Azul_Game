package sk.uniba.fmph.dcs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TableAreaIntegrationTest {
    private TableAreaInterface tableArea;

    @BeforeEach
    public void setUp() {
        tableArea = GameInitializing.createTableArea(4);
    }

    @Test
    public void testTableArea(){

    }
}
