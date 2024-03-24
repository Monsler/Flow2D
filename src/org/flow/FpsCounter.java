package org.flow;

public class FpsCounter extends Thread{

    private int frames = 0;
    private int currFps = 0;
    private long lastFps = 0;

    public void run(){
        do {
            frames++;
            if (System.nanoTime() > lastFps + 1000000000) {
                lastFps = System.nanoTime();
                currFps = frames;
                frames = 0;
            }
        } while (true);

    }
    public double fps(){
        return (double) currFps / 10000.0;
    }
}
