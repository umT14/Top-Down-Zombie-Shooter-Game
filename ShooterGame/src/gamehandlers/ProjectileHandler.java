package gamehandlers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import entity.projectile.Projectile;

public class ProjectileHandler {

    private static List<Projectile> projectiles;
    private static List<Projectile> projectileToRemove;
    private static int bulletTimeCounter;

    {
        projectiles = new ArrayList<>();
        projectileToRemove = new ArrayList<>();
        bulletTimeCounter = 0;
    }

    public ProjectileHandler() {
    }

    public void drawBullets(Graphics2D g2) {
        if (!projectiles.isEmpty()) {
            for (Projectile projectile : projectiles) {
                g2.setColor(Color.YELLOW);
                projectile.drawProjectile(g2);
            }
            removeBullet();
        }
    }

    public void removeBullet() {
        for (Projectile projectile : projectileToRemove) {
            projectiles.remove(projectile);
        }
        projectileToRemove.clear();
    }

    public static void addRemoveList(Projectile projectile) {
        projectileToRemove.add(projectile);
    }

    public static void addList(Projectile projectile) {
        projectiles.add(projectile);
    }

    public static void increaseBulletCounter() {
        bulletTimeCounter++;
    }

    public static int getBulletTimeCounter() {
        return bulletTimeCounter;
    }

    public static void setBulletTimeCounter(int amount) {
        bulletTimeCounter = amount;
    }

    public static List<Projectile> getList() {
        return projectiles;
    }
}
