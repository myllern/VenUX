package com.example.venux;

import com.example.venux.model.Game;

public class LionModeGame extends Game {

    private boolean succededTheRound;

    /**
     * LionMode is the mode where you need to do the
     * right move within a certain time
     */
    public LionModeGame(){
        super();
        this.succededTheRound = false;
    }

    /**
     * Compares the Move that was done to the Move that
     * should be compared this round.
     * Does not inherently go to the next round.
     * @param xRot
     * @param yRot
     * @param zRot
     * @return true if the Move was correct, otherwise false
     */
    private boolean compareMove(float xRot, float yRot, float zRot){
        super.setMovePositions(xRot, yRot, zRot);
        this.succededTheRound = this.succededTheRound ? true : super.wasMoveCorrect();
        return this.succededTheRound;
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
        /*
         * ToDo i think this method might need change, or
         *  will at least be a part of a thread thing
         */
    }

    /**
     * Makes the Game ready for the next round by
     * setting currentMove to a new one and the
     * currentRound to the next one. Also sets
     * succededTheRound to false, because the
     * player have not yet succeeded the round.
     */
    public void readyNextRound(){
        this.succededTheRound = false;
        super.createNewMove();
        super.nextRound();
    }
}
