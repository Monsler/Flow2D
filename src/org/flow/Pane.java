package org.flow;

import javax.script.ScriptException;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class Pane extends JPanel {
    private Color bgcolor;
    public int start = 0;
    public int fps = 0;
    public float dt = 0;
    private float lastTime;
    public static AffineTransform transform;
    long last_time = System.nanoTime();
    FpsCounter counter;
    public int cursorX, cursorY;
    public Pane(){
        bgcolor = Color.black;
        counter = new FpsCounter();
        counter.start();
        cursorX = 0;
        cursorY = 0;
    }

    public void dtcount(){
        new Thread() { public void run() {
            long lastlong = System.nanoTime();
            float delta = 0;
            long timer = System.currentTimeMillis();
            final double physicist = (double) 1000000000 / 140;

            while (true) {
                long now = System.nanoTime();
                delta += (float) ((now - lastlong) / physicist);
                long lastrender = System.nanoTime();
                lastlong = now;
                while (delta >= 1) {
                    delta--;
                    //physics code 8% CPU, is that normal?
                }
                lastrender = now;

                while (now - lastrender < (1000000000 / 60)) {
                    try {
                        Thread.sleep(1);
                        //Without sleeping there is a 100% CPU usage CRAZY!
                    } catch (InterruptedException ie) {
                    }
                    now = System.nanoTime();
                }
            }}}.start();
    }

    protected void paintComponent(Graphics g){
        Graphics2D ext = (Graphics2D) g.create();
        transform = ext.getTransform();
        graphics.setDrawer(ext);
        long currentTime = System.nanoTime();
        ext.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        ext.setColor(bgcolor);
        ext.fillRect(0, 0, getWidth(), getHeight());
        ext.setColor(Color.WHITE);
        try {
            if(start == 1) {
                Runner.engine.eval("flow.draw("+dt+")");
            }
        } catch (ScriptException e) {
            throw new RuntimeException(e);
        }
        repaint();
        if(getMousePosition() != null) {
            cursorX = getMousePosition() != null ? getMousePosition().x : 0;
            cursorY = getMousePosition() != null ? getMousePosition().y : 0;
        }else{
            cursorX = 0;
            cursorY = 0;
        }
        fps = (int) counter.fps();
        dt = (float) ((currentTime - lastTime) / 1000000000.0); // Calculate delta time in seconds
        lastTime = currentTime;

    }

    public void setColor(Color one){
        bgcolor = one;
    }
}
