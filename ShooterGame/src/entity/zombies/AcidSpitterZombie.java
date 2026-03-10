package entity.zombies;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.player.Player;
import entity.projectile.Spit;
import gamehandlers.ProjectileHandler;

public class AcidSpitterZombie extends AbstractZombie {
    public static final int maxHealth = 200;
    public static final int damage = 15;
    public static final int speed = 3;
    public static final int aniSprite = 4;

    public static final int jumpDistanceUpper = 150;
    public static final int jumpDistanceLower = 100;
    public int spitC = 1;

    public AcidSpitterZombie(int pos_X, int pos_Y, Player target) {
        super(pos_X, pos_Y, maxHealth, speed, damage, aniSprite, target);
        setEntitySprites();
    }

    @Override
    public void setEntitySprites() {
        try {
            baseSprite = ImageIO.read(new File("src\\assets\\sprite\\zombies\\spitter.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void drawZombie(Graphics2D g2, int tileSize) {

        int distance = (int) Math.sqrt(
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
                if (spitC != 0) {
                    Point playerPoint = new Point(getTarget().getSPosX(), getTarget().getSPosY());
                    ProjectileHandler.addList(
                            new Spit(getSPosX(), getSPosY(), getWPosX(), getWPosY(), distance, playerPoint,
                                    getTarget()));
                    spitC--;
                }
            } else {
                zombieSetDirection();
                zombieMove(1);
            }
        }

        collision = new Rectangle(screenPos_X - (tileSize / 2), screenPos_Y - (tileSize / 2), tileSize, tileSize);
        g2.drawImage(baseSprite, screenPos_X - (tileSize / 2), screenPos_Y - (tileSize / 2), tileSize, tileSize, null);

    }
}
