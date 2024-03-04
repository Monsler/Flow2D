# Flow2D
Flow2D Are free and open source game engine (framework), that allows you to create beautiful 2d games using lua.
# Getting started
This code will draw rectange inside the window.
```lua
flow.start = function(args)
    -- Function that will be invoked on start
end

flow.draw = function()
    graphics.setColor("#FF0000")
    graphics.fillRect({25, 25, 50, 50})
end
```
result:<br>
<div style="text-align: center;">
<img width="50%" src="https://github.com/Monsler/Flow2D/assets/105060825/106c5610-f069-403f-a6b1-6e998c750b83">
</div>

# Drawing images
Now, lets draw image. for example, i'll take this one called 'table.png'.
![table](https://github.com/Monsler/Flow2D/assets/105060825/621e002a-9de1-4088-ba9d-d54fd5100df3)
<br>
```lua
flow.start = function(args)
    graphics.setBackground("#FFFFFF")
    -- Function that will be invoked on start
end

flow.draw = function()
    graphics.drawImage("table.png", {50, 50, 100, 100})
end
```
result:<br>
![image](https://github.com/Monsler/Flow2D/assets/105060825/ee70747f-7c1e-4153-83b7-14139ac43a48)
