# Flow2D
<img src="https://github.com/Monsler/Flow2D/assets/105060825/8d6b7ca1-ba81-4035-bcbc-b17a9816cabe" style="width: 40%">
<br>Flow2D Are free and open source game engine (framework), that allows you to create beautiful 2d games using lua.

You can read documentation [here](https://github.com/Monsler/Flow2D/blob/main/docs.md).
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

<img width="50%" src="https://github.com/Monsler/Flow2D/assets/105060825/128515bc-cb5a-4a09-8bbf-87e7f5b4f787">
</div>

# Drawing images
Now, lets draw an image. for example, i'll take this one called 'table.png'.
<img src="https://github.com/Monsler/Flow2D/assets/105060825/621e002a-9de1-4088-ba9d-d54fd5100df3" width="40%">
<br>
```lua
local img
flow.start = function(args)
    -- Function that will be invoked on start
    img = image.read('table.png')
end

flow.draw = function()
    graphics.setBackground("#FFFFFF")
    graphics.drawImage(img.getImage(), {50, 50, 100, 100})
end
```
result:<br>
![image](https://github.com/Monsler/Flow2D/assets/105060825/ee70747f-7c1e-4153-83b7-14139ac43a48)

# Keycodes
```
Escape - 1
1 - 2
2 - 3
3 - 4
4 - 5
5 - 6
6 - 7
7 - 8
8 - 9
9 - 10
0 - 11
minus - 12
equals - 13
backspace - 14
tab - 15
q - 16
w - 17
e - 18
r - 19
t - 20
y - 21
u - 22
i - 23
o - 24
p - 25
a - 30
s - 31
d - 32
f - 33
g - 34
h - 35
j - 36
k - 37
l - 38
z - 44
x - 45
c - 46
v - 47
b - 48
n - 49
m - 50
space - 57
ctrl - 29
alt - 56
left arrow - 57419
right arrow - 57421
down arrow - 57424
up arrow - 57416
insert - 3666
F1 - 59
F2 - 60
F3 - 61
F4 - 62
F5 - 63
F6 - 64
F7 - 65
F8 - 67
F9 - 68
F10 - 69
F11 - 70
```
