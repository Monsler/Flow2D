local game = {}

flow.start = function(args)
    system.hideCursor(true)
    game.cursor = image.openSpriteSheet('sprite.png', {325, 310, 2, 1})
    system.setIcon(game.cursor.getSprite(1))
end

flow.draw = function()
    graphics.drawImage(game.cursor.getSprite(2), {graphics.getMouseX()-45, graphics.getMouseY()-40, 90, 80})
    system.wait(10)
end