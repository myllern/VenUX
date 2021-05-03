package com.example.venux;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameTest {

    Game game;

    //Todo fix this. Also make new test classes for monkey game and lion game

    @Before
    public void setUp() throws Exception {
        game = new Game();
        game.recordNewMove(3,3,3); //plats 0
    }

    @After
    public void tearDown() throws Exception {
        game = null;
    }

    @Test
    public void testCurrentMoveAgainstPreviousMove(){
        assertTrue(game.compareMove(1,2,3));
        assertFalse(game.compareMove(10,11,12));
    }

    @Test
    public void testRound(){
        game.nextRound();
        game.recordNewMove(10,9,8); //plats 1
        assertTrue(game.compareMove(11,12,10));
        assertFalse(game.compareMove(1,2,3));
    }

    /*
     * ToDo tests about Players, especially getNextPlayer()
     *  extra especially if Todos in that method is implemented
     */
}