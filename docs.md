# Project setup
Add library to your java project. Now you can use Runner class. Methods:
```java
Runner.runFromString(code, args)
Runner.runFromFile(filename, args)
Runner.runFromResources(Current.class, filename, args)
```
Example:
```java
import org.flow.Runner;

public class Main {
    public static void main(String[] args){
        Runner.runFromFile("main.lua", args);
    }
}

```

# Syntax
Default startup code of Flow2D project looks like:
```lua
flow.start = function(args) -- args is arguments that user passed via terminal

end

flow.draw = function(dt) -- dt is a delta time

end
```
Inside the body of <bold>flow.draw</bold> you have to put some code to draw primitives.
# Basic shapes
> Rectangle

Syntax:
```lua
graphics.fillRect({x, y, width, height}
```

Example:
```lua
flow.start = function(args)
    -- Function that will be invoked on start
end

flow.draw = function()
    graphics.fillRect({0, 0, 100, 100})
end
```
![image](https://github.com/Monsler/Flow2D/assets/105060825/6218d852-f95f-4604-8a1a-eca543790c75)


<br>

> Oval

Syntax:
```lua
graphics.fillOval({x, y, width, height})
```

Example:
```lua
flow.start = function(args)
    -- Function that will be invoked on start
end

flow.draw = function()
    graphics.fillOval({25, 25, 100, 100})
end
```
![image](https://github.com/Monsler/Flow2D/assets/105060825/5769d870-cf09-4c26-a692-d5b3bac171e3)

> Rounded rectangle

Syntax:
```lua
graphics.fillRoundedRect({x, y, width, height, roundValue})
```
Example:
```lua
local game = {}

flow.start = function(args)

end

flow.draw = function()
    graphics.fillRoundedRect({10, 10, graphics.width()-20, 100, 15})
end
```
![image](https://github.com/Monsler/Flow2D/assets/105060825/3a0b007b-8a1a-433d-886f-7ef62650ef6d)


> Setup a color for each shape

Syntax:
```lua
graphics.setColor('HEX color')
```
Example:
```lua
flow.start = function(args)
    -- Function that will be invoked on start
end

flow.draw = function()
    graphics.setColor('#FF0000')
    graphics.fillOval({25, 25, 100, 100})
end
```
![image](https://github.com/Monsler/Flow2D/assets/105060825/895e62d8-a649-46b8-8417-ba44ba1a00f1)

> Rotating shape

Syntax:
```lua
graphics.rotate(degrees)
```
Example:
```lua
local game = {}

flow.start = function(args)

end

flow.draw = function()
    graphics.rotate(20)
    graphics.fillRoundedRect({10, 10, graphics.width()-20, 100, 15})
end
```
![image](https://github.com/Monsler/Flow2D/assets/105060825/042b506b-1854-4217-94f1-51b60a33f5bc)

> Global opacity

Syntax:
```lua
graphics.setOpacity(0-255)
```

> Crop an area

Syntax:
```lua
graphics.crop({x, y, width, height})
```

> Copy an area

Syntax:
```lua
graphics.copyArea({x, y, width, height, x2, y2})
```

> Gradient fill

Syntax:
```lua
graphics.setGradient(startX, startY, color1, endX, endY, color2)
```

Example:
```lua
flow.start = function(args)
    system.setWindowTitle("Gradient")
end

flow.draw = function(dt)
    graphics.setGradient({0, 100, "#FF0000", graphics.width()-20, 100, "#0000FF"})
    graphics.fillRoundedRect({10, 10, graphics.width()-20, 100, 10})
end
```
![image](https://github.com/Monsler/Flow2D/assets/105060825/fa6bd170-41aa-430f-9720-2998e7aaac68)


# Displaying image
Syntax:
```lua
graphics.drawImage(filename, {x, y, width, height})
```

Example:
```lua
local img = 0
flow.start = function(args)
    -- Function that will be invoked on start
    img = image.read('table.png')
    graphics.setBackground('#FFFFFF')
end

flow.draw = function()
    graphics.drawImage(img.getImage(), {graphics.getMouseX()-50 , graphics.getMouseY()-50, 100, 100})
end
```
![image](https://github.com/Monsler/Flow2D/assets/105060825/1aa8ade0-bea1-4bf5-9d39-178e6eed3f64)

# Displaying Sprite Sheet

![sprite](https://github.com/Monsler/Flow2D/assets/105060825/88ec3586-ddaa-40bf-aaa5-9c3d96312968)

Example:
```lua
local game = {}

flow.start = function(args)
    system.hideCursor(true)
    game.cursor = image.readSpriteSheet('sprite.png', {325, 310, 2, 1})
    system.setIcon(game.cursor.getSprite(1))
end

flow.draw = function()
    graphics.drawImage(game.cursor.getSprite(2), {graphics.getMouseX()-45, graphics.getMouseY()-40, 90, 80})
end
```
Result:
![image](https://github.com/Monsler/Flow2D/assets/105060825/6741ddc5-d6bc-452f-bd73-0e900f32ece9)

# Color module
Usage:
```lua
color = require('org.flow.color')
```
Currently available colors:<br>
White<br>
Red<br>
Bloody<br>
Green<br>
Blue<br>
OceanBlue<br>
BabyBlue<br>
CoffeeBlack<br>
SkyBlue<br>
Yellow<br>
Leafy<br>
SeaGreen<br>
DarkViolet<br>
Denim<br>
ForestGreen<br>
Iris<br>
Salmon<br>
GoGreen<br>
LightSlateBlue<br>
LimeGreen<br>
NeonBlue<br>
RoyalBlue<br>
RussianViolet<br>
Orange<br>
LaserLemon<br>
AcidGreen<br>
Aqua<br>
JungleGreen<br>
Cinnabar<br>
Corn<br>
DarkOrchid<br>
Black<br>
Gray<br>
DarkGray<br>
TeaGreen<br>
Vanilla<br>
Wisteria<br>
Wine<br>
Smoke<br>
Ultramarine<br>
Tan<br>
SmokyBlack<br>
ShamrockGreen<br>
Rose<br>
Plum<br>
PaleGreen<br>
PacificBlue<br>
Mint<br>
MediumPurple<br>
CandyAppleRed<br>
MayaBlue<br>
Mango<br>
MangoTango<br>
Mantis<br>
Mahogany<br>
Mandarin<br>
Light<br>
Indigo<br>
IndianRed<br>
ImperialRed<br>
Iceberg<br>
Emerald<br>
HunterGreen<br>
HonoluluBlue<br>
Gunmetal<br>
Ginger<br>

Example:
```lua
local color = require('org.flow.color')

flow.start = function(args)
    system.setWindowTitle('Colors!')
end

flow.draw = function(dt)
    graphics.setBackground(color.Wine)
end
```
<img src="https://github.com/Monsler/Flow2D/assets/105060825/13eff38f-cca7-4f42-8354-46e8a3d3cede">


# Drawing text
Syntax:
```lua
graphics.drawText(text, {x, y})
```

Example with setting a font:
```lua
flow.start = function(args)
    -- Function that will be invoked on start
end

flow.draw = function()
    graphics.setFont('Consolas', 25)
    graphics.drawText('test', {25, 40})
end
```
![image](https://github.com/Monsler/Flow2D/assets/105060825/3b8bc426-5417-4e3c-9f0a-191d453ed6d9)

# Loading font from files
Syntax:
```lua
graphics.loadFont(filename)
```

Example:
```lua
flow.start = function(args)
    -- Function that will be invoked on start
    graphics.loadFont('Montserrat.ttf')
end

flow.draw = function()
    graphics.setFont('Montserrat', 25)
    graphics.drawText('Montserrat font, loaded from file', {25, 40})
    graphics.setFont('Consolas', 25)
    graphics.drawText('Consolas font', {25, 70})
end
```
![image](https://github.com/Monsler/Flow2D/assets/105060825/7668fb27-e7ba-4975-935f-41491ed9933d)

# Mouse info
Methods:
```lua
graphics.mousePressed() -- Returns true or false of mouse pressed state
graphics.getMouseX()
graphics.getMouseY()
```


# Audio
Example:
```lua
local sound1 = audio.read('beep.wav')
sound1.play()
```
There are also
```lua
sound1.stop()
```
method.

# Physics
Methods:
```lua
physics.collides({x, y, width, height}, {x, y, width, height})
```
# TCP
Example:
```lua
tcp = require("org.flow.tcp")
flow.start = function(args)
    system.setWindowTitle("TCP Test")
    local port = 0 -- Use your port and ip here
    game.server = tcp.connectTo("ip", port)
    game.server.write("{\"answer\": \"My message\"}")
    print(game.server.read())
    game.server.close()
end

flow.draw = function(dt)
    -- Empty because i tired of it lol
end
```

Methods:
tcp.connectTo(ip, port) - connects you to server so you can use the methods below<br>
server.write(text) - sends a text to server<br>
server.read() reads data of server's answer<br>
server.close() - closes the socket

# Getting and Setting window params
```lua
graphics.width() -- Returns width of the Window
graphics.height() -- Returns height of the Window
graphics.repaint() -- Repaints window
system.setWindowTitle(text) -- Sets window title
system.setResizable([true or false])
system.centerFrame() -- Centers position of window to center of screen
system.setWindowSize(width, height) -- Sets the size of window
system.setIcon(filename) -- Sets icon of window
system.getProperty(key) -- returns Java call of System.getProperty()
system.setProperty(key, value) -- sets the value to key. for example: system.setProperty("tmp_dir", "./tmp/")
```
# Importing a plugin
There in Flow2D, you can create your own libraries using java.
Load example: 
```lua
local library = system.loadLibrary("Library.jar", "com.monsler.class")
library.method()
```
To create libraries you have to use Flow2D SDK, And Luaj (you can take it out from a lib folder)

# Other system module functions
```lua
system.wait(millis) -- Async wait function
system.getPressedKey() -- Returns the number of pressed key.
system.log(text) -- Log information to output
system.engineVersion() -- Returns the version of engine
```
