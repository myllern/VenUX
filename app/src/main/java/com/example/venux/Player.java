package com.example.venux;

import java.lang.Object;
// import java.awt.Color;
import java.util.UUID;

public class Player {

    private String name;
    private String color; //Finns det en bättre grej? Typ Java.colour... Jag har använt string iaf
    private String playerId; //behövs kanske inte men finns här nu.
    private int[] score; //Tänkte array så man kan hålla koll på varje runda, men kan vara onödigt
    private int life;


    public Player (String name){
        this.name = name;
        this.playerId = UUID.randomUUID().toString();
        this.score = new int[10];
        setColor(1); //Just nu blir alla gröna men detta kan vi ändra.
        this.life =3;
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

    //ToDo: this whole colour thing needs to be looked at again and changed
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

    public int died(){
        this.life = this.life <= 0 ? 0 : this.life-1;
        return this.life;
    }

    public boolean isDead(){
        return this.life==0;
    }

    public int resetPlayerLife(){
        this.life = 3;
        return this.life;
    }

    public int resetPlayerLife(int amountToSet){
        this.life = amountToSet;
        return this.life;
    }

}
