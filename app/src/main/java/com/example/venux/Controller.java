package com.example.venux;

import android.content.Context;
import android.database.Cursor;

public class Controller {

    private Game game;
    public DatabaseController db;





    public Controller(){
        this.game = new Game();
    }

    public Controller(Context context){
        this.game = new Game();
        this.db = new DatabaseController(context);

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

    public void resetGame(){ game.resetGame(); }

    public boolean isNextRoundRecorderRound(){
        return game.isNextRoundRecorderRound();
    }
}
