package main.java;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void init() {
        Game game = new Research();
        int test = game.getGameID();
        assertEquals(test < 3 && test > 0, game.getGameID());
    }
}