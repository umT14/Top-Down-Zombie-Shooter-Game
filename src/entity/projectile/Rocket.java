package entity.projectile;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import entity.zombies.AbstractZombie;
import gamehandlers.ProjectileHandler;
import gamehandlers.ZombieSpawner;

public class Rocket extends Projectile {

    private static int damage = 75;

    public Rocket(int wPosX, int wPosY, int range, int speed, Point point, double deflection) {
        super(true, wPosX, wPosY, range, speed, point, deflection);
    }

    public int getDamage() {
        return damage;
    }

    @Override
    public void drawProjectile(Graphics2D g2) {
        double distance = Math.sqrt(Math.pow(sPosX - startX, 2) + Math.pow(sPosY - startY, 2));
        if (distance < range) {

            sPosX += speedX;
            sPosY += speedY;

            collision = new Rectangle((int) sPosX, (int) sPosY, 5, 5);
            checkForCollision(g2);
            g2.fillOval((int) sPosX, (int) sPosY, 10, 10);
        } else if (!addedList) {
            createExplosion(g2);
            ProjectileHandler.addRemoveList(this);
            addedList = true;
        }
    }

    public void createExplosion(Graphics2D g2) {
        Rectangle explosion = new Rectangle((int) collision.getX() - 75, (int) collision.getY() - 75, 150, 150);
        for (int i = 0; i < 36; i++) {
            g2.fill(explosion);
        }
        checkExplosionCollision(explosion);
    }

    // @Override
    public void checkForCollision(Graphics2D g2) {
        for (AbstractZombie zombie : ZombieSpawner.getZombieList()) {
            if (collision.getBounds().intersects(zombie.getCollision().getBounds())) {
                createExplosion(g2);
                ProjectileHandler.addRemoveList(this);
            }
        }
    }

    public void checkExplosionCollision(Rectangle explosion) {
        for (AbstractZombie zombie : ZombieSpawner.getZombieList()) {
            if (explosion.getBounds().intersects(zombie.getCollision().getBounds())) {
                zombie.zombieLoseHealth(damage);
                ProjectileHandler.addRemoveList(this);
            }
        }
    }

    @Override
    public void checkForCollision() {
    }
}
