package com.example.venux;

public class Move {

    //Borde man egentligen ha ArrayLists med Floats?
    //Lite flexiblare, men vet inte hur det skulle hjälpa eller hur det påverkar prestandan.
    private float[] xAcc;
    private float[] yAcc;
    private float[] zAcc;
    private float[] xRotation;
    private float[] yRotation;
    private float[] zRotation;

    public Move(){
        this.xAcc = new float[1];
        this.yAcc = new float[1];
        this.zAcc = new float[1];
        this.xRotation = new float[1];
        this.yRotation = new float[1];
        this.zRotation = new float[1];

    }

}
