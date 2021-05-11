package com.example.venux.model;

public class Move implements Comparable<Move> {
    
    private float xRotation;
    private float yRotation;
    private float zRotation;

    public Move(float xRotation, float yRotation, float zRotation) {
        this.xRotation = xRotation;
        this.yRotation = yRotation;
        this.zRotation = zRotation;

    }

    public float getxRotation() {
        return this.xRotation;
    }

    public float getyRotation() {
        return this.yRotation;
    }

    public float getzRotation() {
        return this.zRotation;
    }

    @Override
    public int compareTo(Move otherMove) {
        int distanceValue = 3;
        if (Math.abs(this.xRotation - otherMove.getxRotation()) > distanceValue)
            return -1;
        if (Math.abs(this.yRotation - otherMove.getyRotation()) > distanceValue)
            return -1;
        if (Math.abs(this.zRotation - otherMove.getzRotation()) > distanceValue)
            return -1;
        return 0;
    }
}
