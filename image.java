package org.flow;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;

import javax.swing.*;
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

        return lib;
    }
}
