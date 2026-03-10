package guns;

import java.awt.Point;
import java.io.File;

import entity.projectile.Bullet;
import gamehandlers.ProjectileHandler;

public class Shotgun extends AbstractGun {

    private static final int ammoCapacity = 45;
    private static final int shootPerMin = 15;
    private static final int range = 100;
    private static final int speed = 15;
    private static final int damage = 25;
    private static final int piercing = 1;

    public Shotgun() {
        super(ammoCapacity, shootPerMin, range, speed, damage, piercing);
        imageFile = new File("src\\assets\\sprite\\gui\\inventory_icons\\guns\\shotgun_slot.png");
        setImage(imageFile);
    }

    @Override
    public void shoot(int wPosX, int wPosY, Point point) {

        if (getAmmoInMagazine() > 8) {
            double deflection = -22.5;
            for (int i = 0; i < 9; i++) {

                Bullet bullet = new Bullet(wPosX, wPosY, new Shotgun(), point,
                        Math.toRadians(deflection));

                ProjectileHandler.addList(bullet);
                deflection += 5;
                spendAmmo();
            }
        }
    }
}
