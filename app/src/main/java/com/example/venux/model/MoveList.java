package com.example.venux.model;

import java.util.ArrayList;

public class MoveList {

    private ArrayList<Move> moves;

    public MoveList() {
        this.moves = new ArrayList<Move>();
    }

    public void addMove(Move move) {
        moves.add(move);
    }

    public ArrayList<Move> getMoves() {
        return moves;
    }

    public Move getMove(int index) {
        return moves.get(index);
    }
}
