package com.example.venux;

import com.example.venux.model.Move;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MoveTest {

    Move move1;
    Move move2;

    @Before
    public void setUp() throws Exception {
        move1 = new Move();
        move2 = new Move();
    }

    @After
    public void tearDown() throws Exception {
        move1 = null;
        move2 = null;
    }

    @Test
    public void testSimpleMoveComparator(){

        move1.getxRotation()[0]=3;
        move1.getxRotation()[0]=3;
        move1.getxRotation()[0]=3;

        move2.getxRotation()[0]=3;
        move2.getxRotation()[0]=3;
        move2.getxRotation()[0]=-1;
        assertFalse(move1.isSimpleMoveCloseEnough(move2));

    }

    @Test
    public void testSimpleMoveComparatorSuccess(){

        move1.getxRotation()[0]=3;
        move1.getxRotation()[0]=3;
        move1.getxRotation()[0]=3;

        move2.getxRotation()[0]=3;
        move2.getxRotation()[0]=3;
        move2.getxRotation()[0]=0;
        assertTrue(move1.isSimpleMoveCloseEnough(move2));
    }
}