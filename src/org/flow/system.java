package org.flow;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class system extends ZeroArgFunction {
    private int lastPressedKey = 0;
    @Override
    public LuaValue call() {
        LuaValue lib = tableOf();
        lib.set("wait", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue luaValue) {
                try {
                    Thread.sleep(luaValue.toint());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                return null;
            }
        });
        lib.set("setWindowTitle", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue luaValue) {
                Runner.base.setTitle(luaValue.tojstring());
                return null;
            }
        });
        lib.set("setWindowSize", new TwoArgFunction() {
            @Override
            public LuaValue call(LuaValue luaValue, LuaValue luaValue1) {
                Runner.base.setSize(luaValue.toint(), luaValue1.toint());
                return null;
            }
        });
        lib.set("setIcon", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue luaValue) {
                Runner.base.setIconImage(new ImageIcon(luaValue.tojstring()).getImage());
                return null;
            }
        });
        lib.set("setResizable", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue luaValue) {
                Runner.base.setResizable(luaValue.toboolean());
                return null;
            }
        });
        lib.set("centerFrame", new ZeroArgFunction() {
            @Override
            public LuaValue call() {
                Runner.base.setLocationRelativeTo(null);
                return null;
            }
        });
        lib.set("log", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue luaValue) {
                System.out.println("[LOG] "+luaValue.tojstring());
                return null;
            }
        });
        Runner.base.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                lastPressedKey = e.getKeyCode();
            }

            @Override
            public void keyReleased(KeyEvent e){
                super.keyReleased(e);
                lastPressedKey = 0;
            }
        });
        lib.set("getPressedKey", new ZeroArgFunction() {
            @Override
            public LuaValue call() {
                return valueOf(lastPressedKey);
            }
        });
        return lib;
    }
}
