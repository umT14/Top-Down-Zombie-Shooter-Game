package entity.zombies;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.player.Player;

public class TankZombie extends AbstractZombie {

    public static final int maxHealth = 1000;
    public static final int damage = 50;
    public static final int speed = 2;
    public static final int aniSprite = 4;

    public TankZombie(int pos_X, int pos_Y, Player target) {
        super(pos_X, pos_Y, maxHealth, speed, damage, aniSprite, target);
        setEntitySprites();
    }

    @Override
    public void setEntitySprites() {
        try {
            baseSprite = ImageIO.read(new File("src\\assets\\sprite\\zombies\\tank_zombie.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void drawZombie(Graphics2D g2, int tileSize) {
        screenPos_X = getWPosX() - getTarget().getWPosX() + getTarget().getSPosX();
        screenPos_Y = getWPosY() - getTarget().getWPosY() + getTarget().getSPosY();

        double radian = Math.atan2(getTarget().getWPosY() - getWPosY(), getTarget().getWPosX() - getWPosX());

        setSpeedX(Math.cos(radian) * getSpeed());
        setSpeedY(Math.sin(radian) * getSpeed());

        zombieSetDirection();
        zombieMove(1);

        collision = new Rectangle(screenPos_X - (32), screenPos_Y - (32), 64, 64);
        g2.drawImage(baseSprite, screenPos_X - (32), screenPos_Y - (32), 64, 64, null);

    }

}
