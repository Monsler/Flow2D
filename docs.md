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

<br>
