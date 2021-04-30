package com.example.venux;

public class Controller {

    private Game game;

    public Controller(){

    }


    public void createMonkeyGame(){
       this.game = new MonkeyModeGame();
    }

    public void createLionGame(){
        this.game = new LionModeGame();
    }

    public boolean playNextRound(float xRot, float yRot, float zRot){
        return game.playNextRound(xRot, yRot, zRot);
    }

    public void readyNextRound(){
        game.readyNextRound();
    }

    public int getCurrentRound(){
        return game.getCurrentRound();
    }

    public boolean needToRecordNewMove(){
        return game.needToRecordNewMove();
    }

    public void resetGame(){ game.resetGame(); }

    public boolean isNextRoundRecorderRound(){
        return game.isNextRoundRecorderRound();
    }

    public void addPlayersToGame(){
        /*
         * ToDo implement a way to add players to the game
         *  needs to fix things in Game before this is possible.
         *  See Todos in Game
         */
    }

    /*
     * ToDo add multiple methods to get information about players.
     *  See Game
     */
}
