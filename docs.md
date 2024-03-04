# Setup project
Add library to your java project. Now you can use Runner class. Methods:
```java
Runner.runFromString(code, args)
Runner.runFromFile(filename, args)
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
flow.start = function(args)

end

flow.draw = function()

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

# Displaying image
Syntax:
```lua
graphics.drawImage(filename, {x, y, width, height})
```

Example:
```lua
flow.start = function(args)
    -- Function that will be invoked on start
end

flow.draw = function()
    graphics.setBackground('#FFFFFF')
    graphics.drawImage('table.png', {0, 0, 70, 70})
end
```
![image](https://github.com/Monsler/Flow2D/assets/105060825/2918d7a9-fdfe-4fd9-b59f-a5c90df30b0b)

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

# Audio
Example:
```lua
local sound1 = audio.load('beep.wav')
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
