package com.example.venux;

import java.util.ArrayList;

public class Game {

    private ArrayList<Player> players;
    private Player currentPlayer;
    private MoveList moveList;
    private Move currentMove;
    private int numberOfRounds;
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

    /**
     * Creates a new "empty" Move and sets it to be the currentMove
     * @return currentMove that is now "empty"
     */
    private Move createNewMove(){
        currentMove = new Move();
        return currentMove;
    }

    /**
     * Sets the positions for the currentMove
     * @param xRot - the xValue for the Move
     * @param yRot - the yValue for the Move
     * @param zRot - the zValue for the Move
     * @return the currentMove that is now updated with positions
     */
    private Move setMovePositions(float xRot, float yRot, float zRot){
        currentMove.getxRotation()[0] = xRot;
        currentMove.getyRotation()[0] = yRot;
        currentMove.getzRotation()[0] = zRot;
     return currentMove;
    }

    /**
     * Checks wether the currentMove was correct compared
     * to the Move that should be Kopied this round
     * @return true if the Move was correct, false otherwise
     */
    private boolean wasMoveCorrect(){
      return currentMove.isSimpleMoveCloseEnough(moveList.getMove(currentRound));
    }

    /**
     * Records a new Move and adds it to the MoveList.
     * Also increases the number of rounds.
     * Also sets the currentRound to 0, so next player will
     * start to Kopy from the first Move in MoveList
     * @param xRot - xValue of the new Move
     * @param yRot - yValue of the new Move
     * @param zRot - zValue of the new Move
     */
    public void recordNewMove(float xRot, float yRot, float zRot){
        createNewMove();
        setMovePositions(xRot, yRot, zRot);
        moveList.addMove(currentMove);
        numberOfRounds++;
        currentRound =0;
        //ToDo should we change player here(after a new move is recorded)? yes?
    }

    /**
     * Compares the Move that was done to the Move that
     * should be compared this round.
     * If Move was correct, move on to next Round.
     * If Move was failing, set round to 0 again to
     * start over with Kopying from the beginning
     * @param xRot
     * @param yRot
     * @param zRot
     * @return true if the Move was correct, otherwise false
     */
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

    /**
     * Sets currentRound to the next value.
     * @return the new currentRound
     */
    public int nextRound(){
        currentRound ++;
        return currentRound;
    }

    /**
     * sets currentRound to 0.
     * Is used to start again from the beginning.
     * @return currentRound that is now 0
     */
    public int restartRounds(){
        currentRound = 0;
        return currentRound;
    }

    /**
     * Tells us if we need to record a new Move
     * (instead of Kopying a move). This happens
     * when currentRound is the same as numberOfRounds
     * @return true if we need to record a new round, otherwise false
     */
    public boolean needToRecordNewMove(){
        return currentRound==numberOfRounds;
    }

    /**
     * Gets the currentRound
     * @return int currentRound
     */
    public int getCurrentRound(){
        return currentRound;
    }

    /**
     * Plays next round. If we need to record a new Move,
     * it uses the x/y/z-values to record the Move.
     * But if we need to compare a Move, it uses the
     * x/y/z-values to compare the Move to the Move of
     * the currentRound instead.
     * @param xRot - xValue of the Move inputed
     * @param yRot - yValue of the Move inputed
     * @param zRot - zValue of the Move inputed
     * @return true if a Move was recorded or if the Move
     * was correctly Kopied. If Move was not correctly Kopied
     * it returns false.
     */
    public boolean playNextRound(float xRot, float yRot, float zRot){
        if(needToRecordNewMove()){
            recordNewMove(xRot, yRot, zRot);
            return true;
        }
        else return compareMove(xRot, yRot, zRot);
    }

    /**
     * Resets the game to have no Moves, no rounds
     * and start from round 0.
     */
    public void resetGame(){
        moveList = new MoveList();
        restartRounds();
        numberOfRounds =0;
        //ToDo perhaps reset all players? Or do we keep players?
    }

    /**
     * Checks weather the next round is going to
     * be a recorder round (instead of a Kopy round)
     * @return true if next round is recorder round,
     * otherwise false.
     */
    public boolean isNextRoundRecorderRound(){
        return currentRound==numberOfRounds-1;
    }


    //----------------------------//
    // Methods for players//

    /**
     * Creates a new Player with name name and puts
     * it in the players list.
     * @param name - name of the player
     */
    public void addNewPlayer(String name){
        players.add(new Player(name));
    }

    /**
     * Gets the name of the currentPlayer
     * @return - name of the currentPlayer
     */
    public String getCurrentPlayerName(){
        return currentPlayer.getName();
    }

    /**
     * Gets the color of the currentPlayer
     * @return - color of the currentPlayer
     */
    public String getCurrentPlayerColor(){
        return currentPlayer.getColor();
    }

    /**
     * Gets the total score of the currentPlayer
     * @return - total score of the currentPlayer
     */
    public int getCurrentPlayerScore(){
        return currentPlayer.getPlayerTotalScore();
    }

    /**
     * Gets the next Player that will play the game
     * @return - the next Player that will play the game
     */
    private Player GetNextPlayer(){
        int index = players.indexOf(currentPlayer);
        if((index + 1) ==players.size()){
            index = 0;
        } else{
            index++;
        }
        /* ToDo something with isPlayerDead
         *  If player is dead, go to next player
         *  Maybe forLoop? Must have a good stop somehow
         */

        return players.get(index);
    }

    /**
     * Checks weather the Player is dead or not
     * @return - true if the Player is dead, otherwise false
     */
    private boolean isPlayerDead(){
        /*
        * ToDo implement check for isPlayerDead
        *  Then maybe add an attribute + methods in Player for life or something?
        *  should that be a boolean, or do we have an amount of lives?
        */
        return true;
    }

    /**
     * Randomizes what player will play the game.
     */
    private void randomizePlayer(){
        /*
        ToDo !LOW PRIORITY! - maybe not even a thing to do?
         if we want to randomize what player should play now
         Example: currentPlayer = (do something to get a random player)
         remember to check if player is dead
         */
    }



}
