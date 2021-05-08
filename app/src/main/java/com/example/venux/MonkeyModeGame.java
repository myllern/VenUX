package com.example.venux;

public class MonkeyModeGame extends Game{

    /**
     * MonkeyMode is the mode where you need to do
     * the right move on a beat
     */
    public MonkeyModeGame(){
        super();
    }

    /**
     * Compares the Move that was done to the Move that
     * should be compared this round.
     * If Move was correct, move on to next Round.
     * If Move was failing, set round to 0 again to
     * start over with Kopying from the beginning
     * @param xRot the xValue that will be compared
     * @param yRot the yValue that will be compared
     * @param zRot the zValue that will be compared
     * @return true if the Move was correct, otherwise false
     */
    public boolean compareMove(float xRot, float yRot, float zRot){
        super.createNewMove();
        super.setMovePositions(xRot, yRot, zRot);
        boolean wasMoveCorrect= super.wasMoveCorrect();
        if(wasMoveCorrect) nextRound();
        else {
            restartRounds();
            /*
             * Todo set next line to visible when players are
             *  implemented in frontend too
             */
            //super.currentPlayer.died();
            currentPlayer = super.getNextPlayer();
        }

        return wasMoveCorrect;
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

    public void readyNextRound(){
        //I think this can be empty
    }
}
