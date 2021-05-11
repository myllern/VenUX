package com.example.venux.model;

import java.util.ArrayList;
import java.util.HashMap;

public class NewGame {

    private ArrayList<Player> playersAlive;
    private ArrayList<Player> playersDead;
    private ArrayList<Move> moveList;
    private int currentPlayer = 0, currentMove = 0;
    private boolean isCreateMoveState = true;

    public NewGame(ArrayList<Player> initialPlayers) {
        this.playersAlive = initialPlayers;
        playersDead = new ArrayList<Player>();
        moveList = new ArrayList<Move>();
    }

    private void addMove(Move newMove) {
        moveList.add(newMove);
    }

    private void killCurrentPlayer() {
        if(currentPlayer == playersAlive.size() - 1) currentPlayer = 0;
        playersDead.add(playersAlive.remove(currentPlayer));
        currentMove = 0;
    }

    private boolean compareMove(Move otherMove) {
        if (otherMove.compareTo(moveList.get(currentMove)) == 0) {
            return true;
        } else {
            return false;
        }
    }

    private void nextPlayer() {
        if (currentPlayer >= playersAlive.size() - 1) {
            currentPlayer = 0;
        } else {
            currentPlayer++;
        }
    }

    private void nextMove() {
        if (currentMove >= moveList.size() - 1) {
            currentMove = 0;
            isCreateMoveState = true;
        } else {
            currentMove++;
        }
    }

    public int getCurrentMove() {
        return currentMove;
    }

    public int getAmountOfMoves() {
        return moveList.size();
    }

    public boolean isLastMove() {
        return (currentMove == moveList.size() - 1);
    }

    public boolean isCreateMoveState() {
        return isCreateMoveState;
    }


    public boolean isGameFinished() {
        if (playersAlive.size() == 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean playNextStep(Move otherMove) {
        //If game is finished, we just return false and do nothing.
        if (isGameFinished()) {
            return false;
        }
        // If we are in move creation state, we add move and move to next player.
        if (isCreateMoveState) {
            addMove(otherMove);
            nextPlayer();
            isCreateMoveState = false;
            return true;
        } else {
            // If we are in KopyMove state, we compare moves and increment to next move or player.
            boolean succeeded = compareMove(otherMove);
            if (!succeeded) {
                // If we kill the current player, the index will fix automatically.
                killCurrentPlayer();
            } else {
                nextMove();
            }
            return succeeded;
        }
    }


}
