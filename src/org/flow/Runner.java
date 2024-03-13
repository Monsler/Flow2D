package org.flow;


import org.luaj.vm2.LuaValue;
import org.luaj.vm2.script.LuaScriptEngine;

import javax.script.ScriptEngine;
import javax.script.ScriptException;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Flow;

public class Runner {
    public static ScriptEngine engine;
    public static JFrame base;
    static Pane pane = new Pane();
    public static void runFromFile(String file, String[] args){
        engine = new LuaScriptEngine();
        base = new JFrame();
        pane.setLayout(null);
        base.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        base.setSize(500, 400);
        base.setLocationRelativeTo(null);
        LuaValue value = LuaValue.tableOf();
        for(String arg: args){
            value.add(LuaValue.valueOf(arg));
        }
        base.setTitle("Flow2D Simulator");
        base.setVisible(true);
        pane.setBackground(Color.BLACK);
        base.add(pane);
        String main;
        try {
            main = Files.readString(Path.of(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Flow2D.setPanel(pane);
        try {
            engine.eval("image = require('org.flow.image')\naudio = require('org.flow.audio')\ngraphics = require('org.flow.graphics')\nsystem = require('org.flow.system')\nflow = {}\n"+main+"\nflow.start("+value.touserdata()+")");
        } catch (ScriptException e) {
            throw new RuntimeException(e);
        }
        pane.start = 1;
        pane.dtcount();
    }

    public static void runFromString(String code, String[] args){
        engine = new LuaScriptEngine();
        base = new JFrame();
        base.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        base.setSize(500, 400);
        base.setLocationRelativeTo(null);
        LuaValue value = LuaValue.tableOf();
        for(String arg: args){
            value.add(LuaValue.valueOf(arg));
        }
        base.setTitle("Flow2D Simulator");
        base.setVisible(true);
        pane.setBackground(Color.BLACK);
        pane.setLayout(null);
        base.add(pane);
        String main;
        main = code;
        Flow2D.setPanel(pane);
        try {
            engine.eval("image = require('org.flow.image')\naudio = require('org.flow.audio')\ngraphics = require('org.flow.graphics')\nsystem = require('org.flow.system')\nflow = {}\n"+main+"\nflow.start("+value.touserdata()+")");
        } catch (ScriptException e) {
            throw new RuntimeException(e);
        }
        pane.start = 1;
        pane.dtcount();
    }

    public static String getVersion(){
        return "2024.0313";
    }
}
