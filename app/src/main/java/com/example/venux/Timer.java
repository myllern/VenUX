package com.example.venux;

import java.util.Observable;
import java.util.Observer;

public class Timer extends Observable implements Runnable {

    private int startTime;
    private boolean runs;

    public Timer(Observer observer) {
        startTime = 3;
        this.addObserver(observer);
        runs = true;
    }

    @Override
    public void run() {
        if(!runs)return;
        long t0 = System.currentTimeMillis();
        for (int i = 0; i <= startTime; i++) {
            long now = System.currentTimeMillis();
            long d = (t0 +(i+1)*1000) - now;

            try {
                Thread.sleep(d);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            setChanged();
            notifyObservers(startTime-i);
        }
    }

    public void start(){
        runs = true;
    }

    public void stop(){
        runs = false;
    }
}
