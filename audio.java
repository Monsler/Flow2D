package org.flow;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;

import javax.print.attribute.standard.Media;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class audio extends ZeroArgFunction {
    @Override
    public LuaValue call() {
        LuaValue table = tableOf();
        table.set("read", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue luaValue) {
                LuaValue lib = tableOf();
                AudioInputStream audioInputStream;
                try {
                    audioInputStream = AudioSystem.getAudioInputStream(new File(luaValue.tojstring()));
                } catch (UnsupportedAudioFileException | IOException e) {
                    throw new RuntimeException(e);
                }
                Clip clip;
                try {
                    clip = AudioSystem.getClip();
                } catch (LineUnavailableException e) {
                    throw new RuntimeException(e);
                }
                try {
                    clip.open(audioInputStream);
                } catch (LineUnavailableException | IOException e) {
                    throw new RuntimeException(e);
                }
                Clip finalClip = clip;
                lib.set("play", new ZeroArgFunction() {
                    @Override
                    public LuaValue call() {
                        finalClip.setMicrosecondPosition(0);
                        finalClip.start();
                        return null;
                    }
                });
                lib.set("stop", new ZeroArgFunction() {
                    @Override
                    public LuaValue call() {
                        finalClip.stop();
                        return null;
                    }
                });
                lib.set("loop", new OneArgFunction() {
                    @Override
                    public LuaValue call(LuaValue luaValue) {
                        finalClip.loop(luaValue.toint());
                        finalClip.stop();
                        return null;
                    }
                });
                return lib;
            }
        });
        return table;
    }
}
