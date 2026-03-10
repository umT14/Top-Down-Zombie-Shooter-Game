package entity.projectile;

import java.awt.Point;

import entity.zombies.AbstractZombie;
import gamehandlers.ProjectileHandler;
import gamehandlers.ZombieSpawner;
import guns.AbstractGun;

public class Bullet extends Projectile {

    private int damage;
    private int piercing;

    public Bullet(int wPosX, int wPosY, AbstractGun gun, Point point, double deflection) {
        super(true, wPosX, wPosY, gun.getRange(), gun.getSpeed(), point, deflection);
        this.damage = gun.getDamage();
        this.piercing = gun.getPiercing();
    }

    public int getDamage() {
        return damage;
    }

    @Override
    public void checkForCollision() {
        for (AbstractZombie zombie : ZombieSpawner.getZombieList())
            if (collision.getBounds().intersects(zombie.getCollision().getBounds())) {
                zombie.zombieLoseHealth(damage);
                piercing--;
            }
        if (piercing == 0) {
            ProjectileHandler.addRemoveList(this);
        }
    }
}
