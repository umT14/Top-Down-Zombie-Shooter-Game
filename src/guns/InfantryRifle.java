package guns;

import java.awt.Point;
import java.io.File;

import entity.projectile.Bullet;
import gamehandlers.ProjectileHandler;

public class InfantryRifle extends AbstractGun {

    private static final int ammoCapacity = 30;
    private static final int shootPerMin = 3;
    private static final int range = 300;
    private static final int speed = 20;
    private static final int damage = 30;
    private static final int piercing = 1;

    public InfantryRifle() {
        super(ammoCapacity, shootPerMin, range, speed, damage, piercing);
        imageFile = new File("src\\assets\\sprite\\gui\\inventory_icons\\guns\\infantry_slot.png");
        setImage(imageFile);
    }

    @Override
    public void shoot(int wPosX, int wPosY, Point point) {

        if (getAmmoInMagazine() > 0) {
            double deflection = Math.toRadians((Math.random() * 30) - 15);

            Bullet bullet = new Bullet(wPosX, wPosY, new InfantryRifle(), point, deflection);
            ProjectileHandler.addList(bullet);
            spendAmmo();
        }
    }
}
