package org.flow;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.ast.Str;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

import static org.flow.Pane.transform;

public class graphics extends ZeroArgFunction {
    private static Graphics2D drawer;
    public static HashMap<String, Image> imageBuffer = new HashMap<>();
    private static String LastColor = "";
    private static int opacity = 255;
    private DecimalFormat df = new DecimalFormat("0.##");

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
                drawer.setColor(Color.decode(luaValue.tojstring()));
                drawer.fillRect(0, 0, Runner.pane.getWidth(), Runner.pane.getHeight());
                if (!LastColor.isEmpty()) {
                    drawer.setColor(Color.decode(LastColor));
                }
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
                return valueOf(df.format(Runner.pane.fps));
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

        lib.set("fillRoundedRect", new fillRoundedRect());
        lib.set("rotate", new rotate());
        lib.set("setGradient", new setGradientPaint());
        lib.set("drawLine", new drawLine());
        lib.set("drawPolygon", new drawPolygon());
        lib.set("fillRectStroke", new drawRectStroke());
        lib.set("fillOvalStroke", new drawRoundStroke());
        lib.set("fillPolygonStroke", new drawPolygonStroke());
        lib.set("fillRoundedRectStroke", new fillRoundedRectStroke());
        lib.set("crop", new clip());
        lib.set("setOpacity", new setOpacity());
        lib.set("copyArea", new copyArea());
        return lib;
    }

    private static class copyArea extends OneArgFunction {

        @Override
        public LuaValue call(LuaValue luaValue) {
            drawer.copyArea(luaValue.get(1).toint(), luaValue.get(2).toint(), luaValue.get(3).toint(), luaValue.get(4).toint(), luaValue.get(5).toint(), luaValue.get(6).toint());
            return null;
        }
    }

    private static class setOpacity extends OneArgFunction {

        @Override
        public LuaValue call(LuaValue luaValue) {
            opacity = luaValue.toint();
            drawer.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity/255f));
            return null;
        }
    }

    private static class clip extends OneArgFunction {

        @Override
        public LuaValue call(LuaValue luaValue) {
            drawer.clip(new Rectangle(luaValue.get(1).toint(), luaValue.get(2).toint(), luaValue.get(3).toint(), luaValue.get(4).toint()));
            return null;
        }
    }

    private static class fillRoundedRectStroke extends OneArgFunction {

        @Override
        public LuaValue call(LuaValue luaValue) {
            drawer.drawRoundRect(luaValue.get(1).toint(), luaValue.get(2).toint(), luaValue.get(3).toint(), luaValue.get(4).toint(), luaValue.get(5).toint(), luaValue.get(5).toint());
            return null;
        }
    }

    private static class drawPolygonStroke extends OneArgFunction {

        @Override
        public LuaValue call(LuaValue luaValue) {
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < luaValue.length(); i++) {
                list.add(luaValue.get(i + 1).toint());
            }

            Polygon polygon = new Polygon();
            for (int i = 0; i < list.size() - 1; i++) {
                polygon.addPoint(list.get(i), list.get(i + 1));
            }

            drawer.drawPolygon(polygon);
            return null;
        }
    }

    private static class drawRoundStroke extends OneArgFunction {

        @Override
        public LuaValue call(LuaValue luaValue) {
            drawer.drawOval(luaValue.get(1).toint(), luaValue.get(2).toint(), luaValue.get(3).toint(), luaValue.get(4).toint());
            return null;
        }
    }

    private static class drawRectStroke extends OneArgFunction {

        @Override
        public LuaValue call(LuaValue luaValue) {
            drawer.drawRect(luaValue.get(1).toint(), luaValue.get(2).toint(), luaValue.get(3).toint(), luaValue.get(4).toint());
            return null;
        }
    }

    private static class drawPolygon extends OneArgFunction {

        @Override
        public LuaValue call(LuaValue luaValue) {
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < luaValue.length(); i++) {
                list.add(luaValue.get(i + 1).toint());
            }

            Polygon polygon = new Polygon();
            for (int i = 0; i < list.size() - 1; i++) {
                polygon.addPoint(list.get(i), list.get(i + 1));
            }

            drawer.fillPolygon(polygon);
            return null;
        }

    }

    private static class drawLine extends OneArgFunction {

        @Override
        public LuaValue call(LuaValue luaValue) {
            drawer.drawLine(luaValue.get(1).toint(), luaValue.get(2).toint(), luaValue.get(3).toint(), luaValue.get(4).toint());
            return null;
        }
    }

    private static class setGradientPaint extends OneArgFunction {

        @Override
        public LuaValue call(LuaValue luaValue) {
            drawer.setPaint(new GradientPaint(luaValue.get(1).toint(), luaValue.get(2).toint(), Color.decode(luaValue.get(3).tojstring()), luaValue.get(4).toint(), luaValue.get(5).toint(), Color.decode(luaValue.get(6).tojstring())));
            return null;
        }
    }

    private static class rotate extends OneArgFunction {

        @Override
        public LuaValue call(LuaValue luaValue) {
            if(luaValue.toint() != 0){
                drawer.rotate(Math.toRadians(luaValue.toint()));
            }else{
                drawer.setTransform(transform);
            }
            return null;
        }
    }

    private static class fillRoundedRect extends OneArgFunction {

        @Override
        public LuaValue call(LuaValue luaValue) {
            drawer.fillRoundRect(luaValue.get(1).toint(), luaValue.get(2).toint(), luaValue.get(3).toint(), luaValue.get(4).toint(), luaValue.get(5).toint(), luaValue.get(5).toint());
            return null;
        }
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
            graphics.LastColor = luaValue.tojstring();
            drawer.setPaint(null);
            Color set = Color.decode(luaValue.tojstring());
            drawer.setColor(set);
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
