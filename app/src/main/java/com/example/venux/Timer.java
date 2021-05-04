package com.example.venux;

import java.util.Observable;
import java.util.Observer;

public class Timer extends Observable implements Runnable {

    public Timer(Observer observer) {
        this.addObserver(observer);
    }

    @Override
    public void run() {
        long t0 = System.currentTimeMillis();
        for (int i = 0; i <= 5; i++) {
            long now = System.currentTimeMillis();
            long d = (t0 +(i+1)*1000) - now;

            try {
                Thread.sleep(d);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            setChanged();
            notifyObservers(i);
        }
    }
}
