package com.example.venux;

import java.lang.Object;
// import java.awt.Color;
import java.util.UUID;

public class Player {

    private String name;
    private String color; //Finns det en bättre grej? Typ Java.colour... Jag har använt string iaf
    private String playerId; //behövs kanske inte men finns här nu.
    private int[] score; //Tänkte array så man kan hålla koll på varje runda, men kan vara onödigt


    public Player (String name){
        this.name = name;
        this.playerId = UUID.randomUUID().toString();
        this.score = new int[10];
        setColor(1); //Just nu blir alla gröna men detta kan vi ändra.
    }

    public void addScore(int round) { this.score[round]++; }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public String getPlayerId() {
        return playerId;
    }

    public int getScoreInRound(int round) {
        return score[round];
    }

    public int getPlayerTotalScore(){
        int totalScore =0;
        for (int i = 0; i < 10; i++) {
            totalScore += score[i];
        }
        return totalScore;
    }

    private void setColor(int colour){
        switch(colour) {
            case 1:
                this.color = "Green";
                break;
            case 2:
                this.color = "Blue";
                break;
            case 3:
                this.color = "Red";
                break;
            default:
                this.color = "Yellow";
        }
    }
}
