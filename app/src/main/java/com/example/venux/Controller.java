package com.example.venux;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

public class Controller {

    private Game game;
    public DatabaseController db;





    public Controller(){
        this.game = new MonkeyModeGame();
    }

    public Controller(Context context){
        this.game = new MonkeyModeGame();
        this.db = new DatabaseController(context);

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

    public void resetGame(){
        game.resetGame();
    }

    public void startGame(){
        game.startGame();
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

    public int getMovesLeft(){ return game.getMovesLeft();}







    public boolean isNextRoundRecorderRound(){
        return game.isNextRoundRecorderRound();
    }

    public void addPlayersToGame(ArrayList<String> playerNames){
        game.addAllPlayers(playerNames);
    }

    public String getCurrentPlayerName(){
        return game.getCurrentPlayerName();
    }

    /*
     * ToDo add multiple methods to get information about players.
     *  See Game
     */
}
