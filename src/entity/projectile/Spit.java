package entity.projectile;

import java.awt.Graphics2D;
import java.awt.Point;

import entity.player.Player;
import gamehandlers.ProjectileHandler;

public class Spit extends Projectile {

    private Player target;
    private static int spitSpeed = 5;

    public Spit(int sPosX, int sPosY, int wPosX, int wPosY, int range, Point point, Player target) {
        super(false, sPosX, sPosY, range, spitSpeed, point, 0);
        this.target = target;
        this.wPosX = wPosX;
        this.wPosY = wPosY;
        System.out.println("spit created");
    }

    @Override
    public void drawProjectile(Graphics2D g2) {
        double distance = Math.sqrt(Math.pow(sPosX - startX, 2) + Math.pow(sPosY - startY, 2));
        if (distance < range) {

            wPosX += speedX;
            wPosY += speedY;
            sPosX = wPosX - target.getWPosY() + target.getSPosY();
            sPosY = wPosY - target.getWPosY() + target.getSPosY();

            g2.fillOval((int) sPosX, (int) sPosY, 10, 10);
        } else if (!addedList) {
            createSpitPond(g2);
            ProjectileHandler.addRemoveList(this);
            addedList = true;
        }
    }

    public void createSpitPond(Graphics2D g2) {
        SpitPond spitPond = new SpitPond(target.getWPosX(), target.getWPosY(), target);
        spitPond.drawSpit(g2);
    }

    @Override
    public void checkForCollision() {
        if (collision.getBounds().intersects(target.getCollision().getBounds())) {
            target.playerLoseHealth(5);
        }
    }

}
