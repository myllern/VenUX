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
    }

    public boolean compareMove(float xRot, float yRot, float zRot){
        createNewMove();
        setMovePostions(xRot, yRot, zRot);
        return wasMoveCorrect();
    }

    public void nexRound(){
        currentRound ++;
        if(currentRound == numberOfRounds){
            numberOfRounds++;
        }
    }

    public void restartRounds(){
        currentRound = 0;
    }

}
