package guns;

import java.awt.Point;
import java.io.File;

import entity.projectile.Bullet;
import gamehandlers.ProjectileHandler;

public class Sniper extends AbstractGun {

    private static final int ammoCapacity = 5;
    private static final int shootPerMin = 30;
    private static final int range = 700;
    private static final int speed = 30;
    private static final int damage = 100;
    private static final int piercing = 10;

    public Sniper() {
        super(ammoCapacity, shootPerMin, range, speed, damage, piercing);
        imageFile = new File("src\\assets\\sprite\\gui\\inventory_icons\\guns\\sniper_slot.png");
        setImage(imageFile);
    }

    @Override
    public void shoot(int wPosX, int wPosY, Point point) {

        if (getAmmoInMagazine() > 0) {
            Bullet bullet = new Bullet(wPosX, wPosY, new Sniper(), point, 0);
            ProjectileHandler.addList(bullet);
            spendAmmo();
        }
    }
}
