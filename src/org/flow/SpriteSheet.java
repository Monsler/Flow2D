package org.flow;
import java.awt.image.BufferedImage;
import java.io.IOException;
public class SpriteSheet {
    public BufferedImage sprite;
    public BufferedImage[] sprites;
    int width;
    int height;
    int rows;
    int columns;
    public int size;
    public SpriteSheet(int width, int height, int rows, int columns, BufferedImage ss) throws IOException {
        this.width = width;
        this.height = height;
        this.rows = rows;
        this.columns = columns;
        this.size = 0;
        this.sprites = new BufferedImage[2056];
        this.sprite = ss;
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < columns; j++) {
                assert false;
                sprites[1+(i * columns) + j] = ss.getSubimage(i * width, j * height, width, height);
                size++;
            }
        }
    }

}