package com.example.venux;

public class Controller {

    private Game game;

    public Controller(){
        this.game = new Game();
    }


    public boolean playNextRound(float xRot, float yRot, float zRot){
        return game.playNextRound(xRot, yRot, zRot);
    }

    public int getCurrentRound(){
        return game.getCurrentRound();
    }

    public boolean needToRecordNewMove(){
        return game.needToRecordNewMove();
    }



}
