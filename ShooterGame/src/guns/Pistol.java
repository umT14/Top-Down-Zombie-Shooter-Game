package guns;

import java.awt.Point;
import java.io.File;

import entity.projectile.Bullet;
import gamehandlers.ProjectileHandler;

public class Pistol extends AbstractGun {

    private static final int ammoCapacity = 12;
    private static final int shootPerMin = 12;
    private static final int range = 200;
    private static final int speed = 10;
    private static final int damage = 20;
    private static final int piercing = 1;

    public Pistol() {
        super(ammoCapacity, shootPerMin, range, speed, damage, piercing);
        imageFile = new File("src\\assets\\sprite\\gui\\inventory_icons\\guns\\pistol_slot.png");
        setImage(imageFile);
    }

    @Override
    public void shoot(int wPosX, int wPosY, Point point) {
        if (getAmmoInMagazine() > 0) {
            Bullet bullet = new Bullet(wPosX, wPosY, new Pistol(), point, 0);
            ProjectileHandler.addList(bullet);
            spendAmmo();
        }
    }
}
