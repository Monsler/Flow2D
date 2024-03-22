package org.flow;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;

public class color extends ZeroArgFunction {
    @Override
    public LuaValue call() {
        LuaValue lib = tableOf();
        lib.set("White", "#FFFFFF");
        lib.set("Red", "#FF0000");
        lib.set("Bloody", "#8B0000");
        lib.set("Green", "#00FF00");
        lib.set("Blue", "#0000FF");
        lib.set("OceanBlue", "#003366");
        lib.set("BabyBlue", "#89cff0");
        lib.set("CoffeeBlack", "#3b2f2f");
        lib.set("SkyBlue", "#8cbed6");
        lib.set("Yellow", "#ffff31");
        lib.set("Leafy", "#BCEE68");
        lib.set("SeaGreen", "#c1ffc1");
        lib.set("DarkViolet", "#9400d3");
        lib.set("Denim", "#1560bd");
        lib.set("ForestGreen", "#228b22");
        lib.set("Iris", "#5a4fcf");
        lib.set("Salmon", "#ffa07a");
        lib.set("GoGreen", "#00ab66");
        lib.set("LightSlateBlue", "#8470ff");
        lib.set("LimeGreen", "#32cd32");
        lib.set("NeonBlue", "#4666ff");
        lib.set("RoyalBlue", "#4169e1");
        lib.set("RussianViolet", "#32174d");
        lib.set("Orange", "#e69138");
        lib.set("LaserLemon", "#ffff66");
        lib.set("AcidGreen", "#b0bf1a");
        lib.set("Aqua", "#00ffff");
        lib.set("JungleGreen", "#1a2421");
        lib.set("Cinnabar", "#e34234");
        lib.set("Corn", "#fbec5d");
        lib.set("DarkOrchid", "#9932cc");
        lib.set("Black", "#000000");
        lib.set("Gray", "#505050");
        lib.set("DarkGray", "#303030");
        lib.set("TeaGreen", "#d0f0c0");
        lib.set("Vanilla", "#f3e5ab");
        lib.set("Wisteria", "#c9a0dc");
        lib.set("Wine", "#722f37");
        lib.set("Smoke", "#f5f5f5");
        lib.set("Ultramarine", "#4166f5");
        lib.set("Tan", "#ffa54f");
        lib.set("SmokyBlack", "#100c08");
        lib.set("ShamrockGreen", "#009e60");
        lib.set("Rose", "#ff007f");
        lib.set("Plum", "#8e4585");
        lib.set("PaleGreen", "#98fb98");
        lib.set("PacificBlue", "#1ca9c9");
        lib.set("Mint", "#3eb489");
        lib.set("MediumPurple", "#9370db");
        lib.set("CandyAppleRed", "#e2062c");
        lib.set("MayaBlue", "#73c2fb");
        lib.set("Mango", "#fdbe02");
        lib.set("MangoTango", "#ff8243");
        lib.set("Mantis", "#74c365");
        lib.set("Mahogany", "#c04000");
        lib.set("Mandarin", "#f37a48");
        lib.set("Light", "#eedd82");
        lib.set("Indigo", "#4b0082");
        lib.set("IndianRed", "#cd5c5c");
        lib.set("ImperialRed", "#ed2939");
        lib.set("Iceberg", "#71a6d2");
        lib.set("Emerald", "#319177");
        lib.set("HunterGreen", "#355e3b");
        lib.set("HonoluluBlue", "#006db0");
        lib.set("Gunmetal", "#2a3439");
        lib.set("Ginger", "#b06500");
        lib.set("RGB", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue luaValue) {
                String hex = String.format("#%02x%02x%02x", luaValue.get(1).toint(), luaValue.get(2).toint(), luaValue.get(3).toint());
                return valueOf(hex);
            }
        });
        return lib;
    }
}
