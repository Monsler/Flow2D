package org.flow;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class system extends ZeroArgFunction {
    private int lastPressedKey = 0;
    private int pressedMouse = 0;
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
        lib.set("engineVersion", new ZeroArgFunction() {
            @Override
            public LuaValue call() {
                return valueOf(Runner.getVersion());
            }
        });
        lib.set("hideCursor", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue luaValue) {
                boolean ifs = luaValue.toboolean();
                if (ifs) {
                    byte[]imageByte=new byte[0];
                    Image cursorImage=Toolkit.getDefaultToolkit().createImage(imageByte);
                    Cursor empty = Toolkit.getDefaultToolkit().createCustomCursor(cursorImage, new Point(0, 0), "Empty");
                    Runner.pane.setCursor(empty);
                }else{
                    Runner.pane.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                }
                return null;
            }
        });
        lib.set("setMaximized", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue luaValue) {
                boolean ifs = luaValue.toboolean();
                Runner.base.setVisible(false);
                Runner.base.dispose();
                if (ifs) {
                    Runner.base.setExtendedState(JFrame.MAXIMIZED_BOTH);
                    Runner.base.setUndecorated(true);
                }else {
                    Runner.base.setExtendedState(JFrame.NORMAL);
                    Runner.base.setUndecorated(false);
                }
                Runner.base.setVisible(true);
                return null;
            }
        });
        Runner.pane.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                pressedMouse = 1;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                pressedMouse = 0;
            }
        });
        lib.set("mousePressed", new ZeroArgFunction() {
            @Override
            public LuaValue call() {
                if(pressedMouse == 1){
                    return valueOf(true);
                }else{
                    return valueOf(false);
                }
            }
        });
        return lib;
    }
}
