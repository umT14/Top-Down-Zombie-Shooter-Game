package entity.zombies;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.player.Player;

public class JumpingZombie extends AbstractZombie {
    public static final int maxHealth = 60;
    public static final int damage = 15;
    public static final int speed = 4;
    public static final int aniSprite = 4;

    public static final int jumpDistanceUpper = 100;
    public static final int jumpDistanceLower = 60;

    public JumpingZombie(int pos_X, int pos_Y, Player target) {
        super(pos_X, pos_Y, maxHealth, speed, damage, aniSprite, target);
        setEntitySprites();
    }

    @Override
    public void setEntitySprites() {
        try {
            baseSprite = ImageIO.read(new File("src\\assets\\sprite\\zombies\\jockey.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void drawZombie(Graphics2D g2, int tileSize) {

        double distance = Math.sqrt(
                Math.pow(getWPosX() - getTarget().getWPosX(), 2) + Math.pow(getWPosY() - getTarget().getWPosY(), 2));

        screenPos_X = getWPosX() - getTarget().getWPosX() + getTarget().getSPosX();
        screenPos_Y = getWPosY() - getTarget().getWPosY() + getTarget().getSPosY();

        double radian = Math.atan2(getTarget().getWPosY() - getWPosY(), getTarget().getWPosX() - getWPosX());

        setSpeedX(Math.cos(radian) * getSpeed());
        setSpeedY(Math.sin(radian) * getSpeed());

        double distance2 = Math
                .sqrt(Math.pow(getWPosX() - getTarget().getWPosX(), 2)
                        + Math.pow(getWPosY() - getTarget().getWPosY(), 2));

        if (distance2 <= FOV && !sawPlayer) {
            sawPlayer = true;
        }

        if (sawPlayer) {
            if (distance > jumpDistanceLower && distance < jumpDistanceUpper) {
                zombieSetDirection();
                zombieMove(6);
            } else {
                zombieSetDirection();
                zombieMove(1);
            }
        }

        collision = new Rectangle(screenPos_X - (16), screenPos_Y - (16), 32, 32);
        g2.drawImage(baseSprite, screenPos_X - 16, screenPos_Y - 16, 32, 32, null);

    }
}
