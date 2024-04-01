package org.flow;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.script.LuaScriptEngine;

import javax.imageio.ImageIO;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;


public class Runner {
    public static ScriptEngine engine;
    public static JFrame base;
    static Pane pane = new Pane();
    private final static String des = "image = require('org.flow.image')\naudio = require('org.flow.audio')\ngraphics = require('org.flow.graphics')\nsystem = require('org.flow.system')\nflow = {}\n";
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
        base.setResizable(false);
        base.add(pane);
        try {
            base.setIconImage(ImageIO.read(Objects.requireNonNull(Runner.class.getResource("flow2d-logo.png"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String main;
        try {
            main = Files.readString(Path.of(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Flow2D.setPanel(pane);
        Flow2D.setEngine(engine);
        try {
            engine.eval(des +main+"\nflow.start("+value.touserdata()+")");
        } catch (ScriptException e) {
            throw new RuntimeException(e);
        }
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }
        pane.grabFocus();
        pane.start = 1;
        pane.dtcount();
    }

    public static void runFromResources(Class<?> cls, String filename, String[] args){
        URL url = cls.getClassLoader().getResource(filename);
        assert url != null;
        InputStream inputStream;
        try {
            inputStream = url.openStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        List<String> list = reader.lines().toList();
        StringBuilder bld = new StringBuilder();
        for(String of: list) {
            bld.append(of).append("\n");
        }
        engine = new LuaScriptEngine();
        base = new JFrame();
        pane.setLayout(null);
        base.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        base.setSize(500, 400);
        base.setResizable(false);
        base.setLocationRelativeTo(null);
        LuaValue value = LuaValue.tableOf();
        for(String arg: args){
            value.add(LuaValue.valueOf(arg));
        }
        base.setTitle("Flow2D Simulator");
        base.setVisible(true);
        pane.setBackground(Color.BLACK);
        base.add(pane);
        try {
            base.setIconImage(ImageIO.read(Objects.requireNonNull(Runner.class.getResource("flow2d-logo.png"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Flow2D.setPanel(pane);
        Flow2D.setEngine(engine);
        try {
            engine.eval(des +bld+"\nflow.start("+value.touserdata()+")");
        } catch (ScriptException e) {
            throw new RuntimeException(e);
        }
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }
        pane.grabFocus();
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
        base.setResizable(false);
        pane.setLayout(null);
        base.add(pane);
        try {
            base.setIconImage(ImageIO.read(Objects.requireNonNull(Runner.class.getResource("flow2d-logo.png"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String main;
        main = code;
        Flow2D.setPanel(pane);
        try {
            engine.eval(des +main+"\nflow.start("+value.touserdata()+")");
        } catch (ScriptException e) {
            throw new RuntimeException(e);
        }
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }
        Flow2D.setEngine(engine);
        pane.grabFocus();
        pane.start = 1;
        pane.dtcount();
    }


    public static String getVersion(){
        return "2024.0401";
    }
}
