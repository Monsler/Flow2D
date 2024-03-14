package org.flow;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;

import java.awt.*;

public class physics extends ZeroArgFunction {
    @Override
    public LuaValue call() {
        LuaValue lib = tableOf();
        lib.set("collides", new TwoArgFunction() {
            @Override
            public LuaValue call(LuaValue luaValue, LuaValue luaValue1) {
                Rectangle rect1 = new Rectangle(luaValue.get(1).toint(), luaValue.get(2).toint(), luaValue.get(3).toint(), luaValue.get(4).toint());
                Rectangle rect2 = new Rectangle(luaValue1.get(1).toint(), luaValue1.get(2).toint(), luaValue1.get(3).toint(), luaValue1.get(4).toint());
                return valueOf(rect1.intersects(rect2));
            }
        });

        return lib;
    }
}
