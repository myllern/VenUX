package com.example.venux;

import java.util.Observable;
import java.util.Observer;

public class Metronome extends Observable implements Runnable  {

    private boolean exit = false;

    public Metronome(Observer observer) {
        this.addObserver(observer);
    }

    public int getI() {
        return 5;
    }


    @Override
    public void run() {
        long t0 = System.currentTimeMillis();
        exit = false;
        while (!exit) {
            setChanged();
            notifyObservers("UPDATED");
            long now = System.currentTimeMillis();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }


    public void exit() {
        this.exit = true;
    }

}
