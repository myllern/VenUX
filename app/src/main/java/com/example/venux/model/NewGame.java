package com.example.venux.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class NewGame {

    private ArrayList<Player> playersAlive;
    private LinkedList<Player> playersDead;
    private ArrayList<Move> moveList;
    private ArrayList<Move> acceptedMoves;
    private int currentPlayer = 0, currentMove = 0;
    private boolean isCreateMoveState = true;

    public NewGame(ArrayList<Player> initialPlayers) {
        this.playersAlive = initialPlayers;
        playersDead = new LinkedList<Player>();
        moveList = new ArrayList<Move>();
        acceptedMoves = new ArrayList<Move>();
        createAcceptedMoves();
    }

    public void createAcceptedMoves(){
        acceptedMoves.add(new Move(0,0,9)); // screen up
        acceptedMoves.add(new Move(0,9,0)); // screen towards player
        acceptedMoves.add(new Move(9,0,0)); // screen left
        acceptedMoves.add(new Move(-9,0,0)); // screen right
    }

    private boolean addMove(Move newMove) {
        for (Move move: acceptedMoves) {
            if(newMove.compareTo(move) == 0){
                moveList.add(move);
                return true;
            }
        }
        return false;
    }

    private void killCurrentPlayer() {
        int playerToKill = currentPlayer;
        if(currentPlayer == playersAlive.size() - 1) currentPlayer = 0;
        playersDead.addFirst(playersAlive.remove(playerToKill));
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

    public LinkedList<Player> getDeadPlayers() {
        return playersDead;
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
            if(addMove(otherMove)){
                nextPlayer();
                isCreateMoveState = false;
                return true;
            }
            else{
                return false;
            }


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


    public Player getCurrentPlayer() {
        return this.playersAlive.get(currentPlayer);
    }
}
