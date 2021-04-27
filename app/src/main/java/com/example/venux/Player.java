package com.example.venux;

import java.util.UUID;

public class Player {

    private String name;
    private String colour; //Finns det en bättre grej? Typ Java.Colour... Jag har använt string iaf
    private String playerId; //behövs kanske inte men finns här nu.
    private int[] score; //Tänkte array så man kan hålla koll på varje runda, men kan vara onödigt
    private int[] penalty; //Vet inte om vi behöver, men kan ju vara bra


    public Player (){
        this.name = "newPlayer";
        this.colour = "FFFFFF"; //Detta är vit i hexadecimalt
        this.playerId = UUID.randomUUID().toString();
        this.score = new int[10];
        this.penalty = new int[10];
    }

}
