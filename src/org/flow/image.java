package org.flow;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

public class image extends ZeroArgFunction {
    @Override
    public LuaValue call() {
        LuaValue lib = tableOf();
        lib.set("open", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue luaValue) {
                File fi = new File(luaValue.tojstring());
                byte[] fileContent;
                try {
                    fileContent = Files.readAllBytes(fi.toPath());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                LuaValue tool = tableOf();
                tool.set("getImage", new ZeroArgFunction() {
                    @Override
                    public LuaValue call() {
                        return valueOf(Base64.getEncoder().encodeToString(fileContent));
                    }
                });
                return tool;
            }
        });
        lib.set("openSpriteSheet", new TwoArgFunction() {
            @Override
            public LuaValue call(LuaValue luaValue, LuaValue pref) {
                BufferedImage ss;
                SpriteSheet sheet;
                try {
                    ss = ImageIO.read(new File(luaValue.tojstring()));
                    sheet = new SpriteSheet(pref.get(1).toint(), pref.get(2).toint(), pref.get(3).toint(), pref.get(4).toint(), ss);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                LuaValue table = tableOf();
                table.set("getSprite", new OneArgFunction() {
                    @Override
                    public LuaValue call(LuaValue luaValue) {
                        BufferedImage sub = sheet.sprites[luaValue.toint()];
                        ByteArrayOutputStream bass = new ByteArrayOutputStream();
                        try {
                            ImageIO.write(sub, "png", bass);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        byte[] bytes = bass.toByteArray();
                        return valueOf(Base64.getEncoder().encodeToString(bytes));
                    }
                });
                table.set("getSize", new ZeroArgFunction() {
                    @Override
                    public LuaValue call() {
                        return valueOf(sheet.size);
                    }
                });
                return table;
            }
        });

        return lib;
    }
}
