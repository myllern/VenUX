package com.example.venux.util;

import java.util.Observable;
import java.util.Observer;

public class CountdownTimer extends Observable implements Runnable {

    private boolean runs;
    private long interval;
    private int duration;

    /**
     *
     * @param observer is the observing class
     * @param interval is the time between each tick in ms
     * @param duration is the amount of ticks
     */
    public CountdownTimer(Observer observer, long interval, int duration) {
        this.interval = interval;
        this.duration = duration;
        this.addObserver(observer);
    }

    @Override
    public void run() {
        long t0 = System.currentTimeMillis();
        for (int i = 0; i <= duration; i++) {
            long now = System.currentTimeMillis();
            long d = (t0 + (i + 1) * interval) - now;

            try {
                Thread.sleep(d);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            setChanged();
            notifyObservers(duration - i);
        }
    }
}
