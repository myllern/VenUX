package com.example.venux.model;

import java.lang.Object;
// import java.awt.Color;
import java.util.UUID;

public class Player {

    private String name;
    private String uuid; //behövs kanske inte men finns här nu.

    public Player(String name) {
        this.name = name;
        this.uuid = UUID.randomUUID().toString();
    }

    public String getName() {
        return name;
    }

    public String getPlayerId() {
        return uuid;
    }

}
