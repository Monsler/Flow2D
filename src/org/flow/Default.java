package org.flow;

class Default {
    public static void main(String[] args){
        Runner.runFromString("local mode\n" +
                "\n" +
                "flow.start = function(_)\n" +
                "    mode = 1\n" +
                "end\n" +
                "\n" +
                "flow.draw = function()\n" +
                "    graphics.setFont(\"Consolas\", 25)\n" +
                "    graphics.setColor(\"#FFFFFF\")\n" +
                "    if mode == 1 then\n" +
                "        graphics.drawText(\"Wait,\", {graphics.width()/2-50, graphics.height()/2+10})\n" +
                "        graphics.setFont(\"\", 14)" +
                "        graphics.drawText(\"It is a library, not an application.\", {graphics.width()/2-150, graphics.height()/2+50})\n"+
                "        mode = 1\n" +
                "    else\n" +
                "        mode = 1\n" +
                "    end\n" +
                "end", args);
    }
}
