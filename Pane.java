package org.flow;

import javax.script.ScriptException;
import javax.swing.*;
import java.awt.*;

public class Pane extends JPanel {
    private Color bgcolor;
    public int start = 0;
    public int fps = 0;
    FpsCounter counter;
    public int cursorX, cursorY;
    public Pane(){
        bgcolor = Color.black;
        counter = new FpsCounter();
        counter.start();
        cursorX = 0;
        cursorY = 0;
    }

    protected void paintComponent(Graphics g){
        Graphics2D ext = (Graphics2D) g.create();
        graphics.setDrawer(ext);

        ext.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        ext.setColor(bgcolor);
        ext.fillRect(0, 0, getWidth(), getHeight());
        ext.setColor(Color.WHITE);
        //ext.drawString("Test", 25, 30);
        try {
            if(start == 1) {
                Runner.engine.eval("flow.draw()");
            }
        } catch (ScriptException e) {
            throw new RuntimeException(e);
        }
        repaint();
        if(getMousePosition() != null) {
            cursorX = getMousePosition().x;
            cursorY = getMousePosition().y;
        }
        fps = (int) counter.fps();
    }
    public void setColor(Color one){
        bgcolor = one;
    }
}
