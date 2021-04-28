package com.example.venux;

import java.util.ArrayList;

public class Game {

    private ArrayList<Player> players;
    private Player currentPlayer;
    private MoveList moveList;
    private Move currentMove;
    private int numberOfRounds; //Kanske är onödig, vet ej än
    private int currentRound;

    public Game(){
        this.players = new ArrayList<Player>();
        this.moveList = new MoveList();
        this.numberOfRounds=0;
        this.currentRound=0;
    }

    private Move createNewMove(){
        currentMove = new Move();
        return currentMove;
    }

    private Move setMovePostions(float xRot, float yRot, float zRot){
        currentMove.getxRotation()[0] = xRot;
        currentMove.getyRotation()[0] = yRot;
        currentMove.getzRotation()[0] = zRot;
     return currentMove;
    }

    private boolean wasMoveCorrect(){
      return currentMove.isSimpleMoveCloseEnough(moveList.getMove(currentRound));
    }

    public void recordNewMove(float xRot, float yRot, float zRot){
        createNewMove();
        setMovePostions(xRot, yRot, zRot);
        moveList.addMove(currentMove);
        numberOfRounds++;
        currentRound =0;
    }

    public boolean compareMove(float xRot, float yRot, float zRot){
        createNewMove();
        setMovePostions(xRot, yRot, zRot);
        boolean wasMoveCorrect= wasMoveCorrect();
        if(wasMoveCorrect) nextRound();
        else restartRounds();
        return wasMoveCorrect;
    }

    public int nextRound(){
        currentRound ++;
        if(currentRound == numberOfRounds){
            numberOfRounds++;
        }
        return currentRound;
    }

    public int restartRounds(){
        currentRound = 0;
        return currentRound;
    }

    public boolean needToRecordNewMove(){
        return currentRound==numberOfRounds;
    }

    public int getCurrentRound(){
        return currentRound;
    }

    public boolean playNextRound(float xRot, float yRot, float zRot){
        if(needToRecordNewMove()){
            recordNewMove(xRot, yRot, zRot);
            return true;
        }
        else return compareMove(xRot, yRot, zRot);
    }

}
