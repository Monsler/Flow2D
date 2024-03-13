package org.flow;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;

public class graphics extends ZeroArgFunction {
    private static Graphics2D drawer;
    public static HashMap<String, Image> imageBuffer = new HashMap<>();

    public static void setDrawer(Graphics2D newton){
        drawer = newton;
    }
    @Override
    public LuaValue call() {
        LuaValue lib = tableOf();
        lib.set("fillRect", new fillRect());
        lib.set("setColor", new setColor());
        lib.set("width", new width());
        lib.set("height", new height());
        lib.set("repaint", new repaint());
        lib.set("drawText", new newText());
        lib.set("drawImage", new drawImage());
        lib.set("setBackground", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue luaValue) {
                Runner.pane.setColor(Color.decode(luaValue.tojstring()));
                return null;
            }
        });
        lib.set("setFont", new TwoArgFunction() {
            @Override
            public LuaValue call(LuaValue luaValue, LuaValue luaValue1) {
                drawer.setFont(new Font(luaValue.tojstring(), Font.PLAIN, luaValue1.toint()));
                return null;
            }
        });
        lib.set("fps", new ZeroArgFunction() {
            @Override
            public LuaValue call() {
                return valueOf(Runner.pane.fps);
            }
        });
        lib.set("loadFont", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue luaValue) {
                GraphicsEnvironment GE = GraphicsEnvironment.getLocalGraphicsEnvironment();
                Font font;
                try {
                    font = Font.createFont(Font.TRUETYPE_FONT, new File(luaValue.tojstring()));
                } catch (FontFormatException | IOException e) {
                    throw new RuntimeException(e);
                }
                GE.registerFont(font);
                drawer.setFont(font);
                return null;
            }
        });
        lib.set("fillOval", new fillOval());
        lib.set("getMouseX", new ZeroArgFunction() {
            @Override
            public LuaValue call() {
                return valueOf(Runner.pane.cursorX);
            }
        });
        lib.set("getMouseY", new ZeroArgFunction() {
            @Override
            public LuaValue call() {
                return valueOf(Runner.pane.cursorY);
            }
        });
        lib.set("mousePressed", new ZeroArgFunction() {
            @Override
            public LuaValue call() {
                if(system.pressedMouse == 1){
                    return valueOf(true);
                }else{
                    return valueOf(false);
                }
            }
        });
        return lib;
    }


    private static class fillOval extends OneArgFunction {

        @Override
        public LuaValue call(LuaValue luaValue) {
            drawer.fillOval(luaValue.get(1).toint(), luaValue.get(2).toint(), luaValue.get(3).toint(), luaValue.get(4).toint());
            return null;
        }
    }

    private static class drawImage extends TwoArgFunction {

        @Override
        public LuaValue call(LuaValue luaValue, LuaValue luaValue1) {
            Image out;
            if(!imageBuffer.containsKey(luaValue.tojstring())) {
                byte[] img = Base64.getDecoder().decode(luaValue.tojstring());
                BufferedImage image;
                try {
                    image = ImageIO.read(new ByteArrayInputStream(img));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                out = image.getScaledInstance(luaValue1.get(3).toint(), luaValue1.get(4).toint(), Image.SCALE_SMOOTH);
                imageBuffer.put(luaValue.tojstring(), out);
            }else{
                if(imageBuffer.get(luaValue.tojstring()).getWidth(null) != luaValue1.get(3).toint() || imageBuffer.get(luaValue.tojstring()).getHeight(null) != luaValue1.get(4).toint()){
                    byte[] img = Base64.getDecoder().decode(luaValue.tojstring());
                    BufferedImage image;
                    try {
                        image = ImageIO.read(new ByteArrayInputStream(img));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    out = image.getScaledInstance(luaValue1.get(3).toint(), luaValue1.get(4).toint(), Image.SCALE_SMOOTH);
                    imageBuffer.put(luaValue.tojstring(), out);
                }else{
                    out = imageBuffer.get(luaValue.tojstring());
                }
            }
            drawer.drawImage(out, luaValue1.get(1).toint(), luaValue1.get(2).toint(), null, null);

            return null;
        }
    }

    private static class newText extends TwoArgFunction {

        @Override
        public LuaValue call(LuaValue luaValue, LuaValue luaValue1) {
            drawer.drawString(luaValue.tojstring(), luaValue1.get(1).toint(), luaValue1.get(2).toint());
            return null;
        }
    }

    private static class repaint extends ZeroArgFunction {

        @Override
        public LuaValue call() {
            Runner.pane.repaint();
            return null;
        }
    }


    private static class width extends ZeroArgFunction {

        @Override
        public LuaValue call() {
            return graphics.valueOf(Runner.pane.getWidth());
        }
    }

    private static class height extends ZeroArgFunction {

        @Override
        public LuaValue call() {
            return graphics.valueOf(Runner.pane.getHeight());
        }
    }

    private static class setColor extends OneArgFunction {

        @Override
        public LuaValue call(LuaValue luaValue) {
            drawer.setColor(Color.decode(luaValue.tojstring()));
            return null;
        }
    }

    private static class fillRect extends OneArgFunction {

        @Override
        public LuaValue call(LuaValue luaValue) {
            drawer.fillRect(luaValue.get(1).toint(), luaValue.get(2).toint(), luaValue.get(3).toint(), luaValue.get(4).toint());
            return null;
        }
    }
}
