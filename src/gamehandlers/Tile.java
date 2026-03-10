package gamehandlers;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Tile {
    BufferedImage image;
    boolean hasCollision;
    Rectangle collision;

    public Tile(boolean hasCollision) {
        this.hasCollision = hasCollision;
    }

    public Tile() {
        this.hasCollision = false;
    }

    public void setCollision(Rectangle collision) {
        this.collision = collision;
    }
}
