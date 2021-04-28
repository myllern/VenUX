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

    public void startGame(){
        //ToDo set currentPlayer to first Player in list players
    }

    private Move createNewMove(){
        currentMove = new Move();
        return currentMove;
    }

    private Move setMovePositions(float xRot, float yRot, float zRot){
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
        setMovePositions(xRot, yRot, zRot);
        moveList.addMove(currentMove);
        numberOfRounds++;
        currentRound =0;
        //ToDo should we change player here(after a new move is recorded)? yes?
    }

    public boolean compareMove(float xRot, float yRot, float zRot){
        createNewMove();
        setMovePositions(xRot, yRot, zRot);
        boolean wasMoveCorrect= wasMoveCorrect();
        if(wasMoveCorrect) nextRound();
        else restartRounds();
        //ToDo set currentPlayer = nextPlayer?
        //ToDo maybe setPlayerDead somehow (method does not exist yet)
        return wasMoveCorrect;
    }

    public int nextRound(){
        currentRound ++;
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

    public void resetGame(){
        moveList = new MoveList();
        restartRounds();
        numberOfRounds =0;
        //ToDo perhaps reset all players? Or do we keep players?
    }

    public boolean isNextRoundRecorderRound(){
        return currentRound==numberOfRounds-1;
    }


    //----------------------------//
    // Methods for players//

    public void addNewPlayer(String name){
        players.add(new Player(name));
    }

    public String getCurrentPlayerName(){
        return currentPlayer.getName();
    }

    public String getCurrentPlayerColor(){
        return currentPlayer.getColor();
    }

    public int getCurrentPlayerScore(){
        return currentPlayer.getPlayerTotalScore();
    }

    private Player GetNextPlayer(){
        int index = players.indexOf(currentPlayer);
        if((index + 1) ==players.size()){
            index = 0;
        } else{
            index++;
        }
        /* ToDo something with isPlayerDead
         * If player is dead, go to next player
         * Maybe foorLoop? Must have a good stop somehow
         */

        return players.get(index);
    }

    private boolean isPlayerDead(){
        /*
        * ToDo implement check for isPlayerDead
        * Then maybe add an attribute+methods in Player for life or something?
        * should that be a boolean, or do we have an amount of lives?
        */
        return true;
    }



}
