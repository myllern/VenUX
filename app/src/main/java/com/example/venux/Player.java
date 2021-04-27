package com.example.venux;

import java.lang.Object;
// import java.awt.Color;
import java.util.UUID;

public class Player {

    private String name;
    private String color; //Finns det en bättre grej? Typ Java.colour... Jag har använt string iaf
    private int playerId; //behövs kanske inte men finns här nu.
    private int score; //Tänkte array så man kan hålla koll på varje runda, men kan vara onödigt
    private boolean inGame;


    public Player (String name, int ID){
        this.name = name;
        this.playerId = ID;
        this.score = 0;
        this.inGame = true;

    }

    public void addScore() {
        this.score++;
    }
    public void playerLost(){
        inGame = false;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public int getPlayerId() {
        return playerId;
    }

    public int getScore() {
        return score;
    }

    private void setColor(int ID){
        switch(ID) {
            case 1:
                this.color = "Green";
                break;
            case 2:
                this.color = "Blue";
                break;
            case 3:
                this.color = "red";
                break;
            default:
                this.color = "Yellow";
        }
    }



}
