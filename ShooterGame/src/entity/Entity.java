package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;

public abstract class Entity implements Serializable {

    private final int maxHealth;
    private int health;
    private int speed;

    private int worldPos_X;
    private int worldPos_Y;

    protected int screenPos_X;
    protected int screenPos_Y;

    protected int cycle = 0;
    private int aniSprite;

    protected Rectangle collision;
    protected Direction direction;
    protected boolean canMove;

    protected transient BufferedImage baseSprite;
    protected transient BufferedImage[] walkSprites;

    protected int spriteCounter;
    protected static final int maxSpriteCounter = 12;

    public Entity(int pos_X, int pos_Y, int health, int speed, int aniSprite) {
        this.worldPos_X = pos_X;
        this.worldPos_Y = pos_Y;
        this.health = health;
        maxHealth = health;
        this.speed = speed;
        this.aniSprite = aniSprite;

        spriteCounter = 0;
        walkSprites = new BufferedImage[aniSprite];
    }

    public BufferedImage getSprite() {
        return baseSprite;
    }

    public int getSpriteNum() {
        return spriteCounter;
    }

    public int getWPosX() {
        return worldPos_X;
    }

    public int getWPosY() {
        return worldPos_Y;
    }

    public int getSPosX() {
        return screenPos_X;
    }

    public int getSPosY() {
        return screenPos_Y;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getSpeed() {
        return speed;
    }

    public Rectangle getCollision() {
        return collision;
    }

    public Direction getDirection() {
        return direction;
    }

    public void loseHealth(int amount) {
        health -= amount;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public abstract void setEntitySprites() throws IOException;

    public void spriteNumControl() {
        spriteCounter++;
        if (spriteCounter > maxSpriteCounter) {
            cycle++;
            if (cycle > (aniSprite - 1)) {
                cycle = 0;
            }
            spriteCounter = 0;
        }
    }

    public void movePosX(double amount) {
        worldPos_X += amount;
    }

    public void movePosY(double amount) {
        worldPos_Y += amount;
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

}
