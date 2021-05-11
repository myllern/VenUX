package com.example.venux.model;

import java.lang.Object;
// import java.awt.Color;
import java.util.UUID;

public class Player {

    private String name;
    private String uuid; //behövs kanske inte men finns här nu.
    private int score; //Tänkte array så man kan hålla koll på varje runda, men kan vara onödigt


    public Player(String name) {
        this.name = name;
        this.uuid = UUID.randomUUID().toString();
        this.score = 0;
    }

    public void incrementScore() {
        score++;
    }

    public String getName() {
        return name;
    }

    public String getPlayerId() {
        return uuid;
    }

    public int getScore() {
        return score;
    }


}
