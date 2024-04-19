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
            final double physicist = (double) 1000000000 / 140;

            while (true) {
                long now = System.nanoTime();
                delta += (float) ((now - lastlong) / physicist);
                long lastrender;
                lastlong = now;
                while (delta >= 1) {
                    delta--;
                }
                lastrender = now;

                while (now - lastrender < (1000000000 / 60)) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException ignored) {}
                    now = System.nanoTime();
                }
            }}}.start();
    }

    protected void paintComponent(Graphics g){
        Graphics2D graphics2D = (Graphics2D) g.create();
        AffineTransform transform = graphics2D.getTransform();
        graphics.setDrawer(graphics2D);
        long currentTime = System.nanoTime();
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setColor(bgcolor);
        graphics2D.fillRect(0, 0, getWidth(), getHeight());
        graphics2D.setColor(Color.WHITE);
        try {
            if (start == 1) {
                Runner.engine.eval("flow.draw(" + dt + ")");
            }
        } catch (ScriptException e) {
            throw new RuntimeException(e);
        }
        repaint();
        if (getMousePosition() != null) {
            cursorX = getMousePosition().x;
            cursorY = getMousePosition().y;
        }
        fps = (int) counter.fps();
        dt = (float) ((currentTime - lastTime) / 1000000000.0); // Calculate delta time in seconds
        lastTime = currentTime;
    }
}
