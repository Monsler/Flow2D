package org.flow;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Base64;

public class system extends ZeroArgFunction {
    private int lastPressedKey = 0;
    public static int pressedMouse = 0;

    @Override
    public LuaValue call() {
        LuaValue lib = tableOf();
        lib.set("wait", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue luaValue) {
                int sleepTime = luaValue.toint();
                SwingUtilities.invokeLater(() -> {
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
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
                byte[] img = Base64.getDecoder().decode(luaValue.tojstring());
                BufferedImage image;
                try {
                     image = ImageIO.read(new ByteArrayInputStream(img));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Runner.base.setIconImage(image);
                return valueOf(false);
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
        Runner.pane.addKeyListener(new KeyAdapter() {
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
                boolean isValueTrue = luaValue.toboolean();
                if (isValueTrue) {
                    byte[] imageBytes = new byte[0];
                    Image cursorImage = Toolkit.getDefaultToolkit().createImage(imageBytes);
                    Cursor emptyCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImage, new Point(0, 0), "Empty");
                    Runner.pane.setCursor(emptyCursor);
                } else {
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
                Dimension dim = new Dimension(0, 0);
                Runner.base.dispose();
                if (ifs) {
                    Runner.base.setExtendedState(JFrame.MAXIMIZED_BOTH);
                    dim = Runner.base.getSize();
                    Runner.base.setSize(Toolkit.getDefaultToolkit().getScreenSize());
                    Runner.base.setAlwaysOnTop(true);
                    Runner.base.setUndecorated(true);
                }else {
                    Runner.base.setExtendedState(JFrame.NORMAL);
                    Runner.base.setUndecorated(false);
                    Runner.base.setSize(dim);
                    Runner.base.setAlwaysOnTop(false);
                }
                Runner.base.setVisible(true);
                return null;
            }
        });
        lib.set("loadLibrary", new TwoArgFunction() {
            @Override
            public LuaValue call(LuaValue luaValue, LuaValue address) {
                String path = luaValue.tojstring();
                String addressLib = address.tojstring();
                try {
                    URLClassLoader child = new URLClassLoader(
                            new URL[] { new URL("file:" + path) },
                            this.getClass().getClassLoader()
                    );
                    Class<?> classToLoad = Class.forName(addressLib + ".LibraryEntry", true, child);
                    Method method = classToLoad.getDeclaredMethod("call");
                    Object instance = classToLoad.getDeclaredConstructor().newInstance();
                    return (LuaValue) method.invoke(instance);
                } catch (MalformedURLException | ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        lib.set("getProperty", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue luaValue) {
                return valueOf(System.getProperty(luaValue.tojstring()));
            }
        });

        lib.set("setProperty", new TwoArgFunction() {
            @Override
            public LuaValue call(LuaValue luaValue, LuaValue luaValue1) {
                System.setProperty(luaValue.tojstring(), luaValue1.tojstring());
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
        return lib;
    }
}
