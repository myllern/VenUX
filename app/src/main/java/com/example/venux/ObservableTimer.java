package com.example.venux;

import java.util.Observable;
import java.util.Observer;

public class ObservableTimer extends Observable implements Runnable  {

    private boolean exit = false;

    public ObservableTimer(Observer observer) {
        this.addObserver(observer);
    }

    public int getI() {
        return 5;
    }


    @Override
    public void run() {
        long t0 = System.currentTimeMillis();
        while (!exit) {
            long now = System.currentTimeMillis();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            notifyObservers("UPDATED");

        }
    }


    public void exit() {
        this.exit = true;
    }

}
