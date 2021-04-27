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
    public float[] getxRotation(){
        return this.xRotation;
    }
    public float[] getyRotation(){
        return this.yRotation;
    }
    public float[] getzRotation(){
        return this.zRotation;
    }
    public float[] getxAcc(){
        return this.xAcc;
    }
    public float[] getyAcc(){
        return this.yAcc;
    }
    public float[] getzAcc(){
        return this.zAcc;
    }

    /**
     * Compares this move to the other move and returns false if they
     * are too far apart in any of the rotational axis.
     * @param otherMove - the other move you want to compare.
     * @return true if moves are close enough, false otherwise.
     */
    public boolean isSimpleMoveCloseEnough(Move otherMove){
        boolean returnValue = true;
        int distanceValue = 3;
        if(Math.abs(this.xRotation[0]-otherMove.getxRotation()[0])>distanceValue)
            returnValue=false;
        if(Math.abs(this.yRotation[0]-otherMove.getyRotation()[0])>distanceValue)
            returnValue=false;
        if(Math.abs(this.zRotation[0]-otherMove.getzRotation()[0])>distanceValue)
            returnValue=false;

        return returnValue;
    }

    public boolean isComplexMoveCloseEnough(Move otherMove){

        return false;
    }

}
