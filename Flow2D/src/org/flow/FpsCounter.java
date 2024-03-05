package org.flow;

public class FpsCounter extends Thread{

    private int frames = 0;
    private int currFps = 0;
    private long lastFps = 0;

    public void run(){
        Thread t = new Thread(() -> {
            while (true) {
                frames++;
                if(System.nanoTime() > lastFps + 1000000000){
                    lastFps = System.nanoTime();
                    currFps = frames;
                    frames = 0;
                }
            }
        });
        t.start();

    }
    public double fps(){
        return (double) currFps / 100000;
    }
}