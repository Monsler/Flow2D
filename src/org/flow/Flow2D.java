package org.flow;


import javax.script.ScriptEngine;
import javax.swing.*;

public class Flow2D {

    private static JPanel pane;
    private static ScriptEngine engine;
    public static JPanel getPanel(){
        return pane;
    }
    public static void setEngine(ScriptEngine repl){
        engine = repl;
    }

    public static ScriptEngine getEngine(){
        return engine;
    }

    public static void setPanel(JPanel one){
        pane = one;
    }
}
