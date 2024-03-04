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

