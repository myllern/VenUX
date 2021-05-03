package com.example.venux;

public class Timer implements Runnable {

    @Override
    public void run() {
        long t0 = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            long now = System.currentTimeMillis();
            System.out.println(now - t0);
            long d = (t0 +(i+1)*1000) - now;

            try {
                Thread.sleep(d);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
