package guns;

import java.awt.Point;
import java.io.File;

import entity.projectile.Rocket;
import gamehandlers.ProjectileHandler;

public class RocketLauncher extends AbstractGun {

    private static final int ammoCapacity = 1;
    private static final int shootPerMin = 25;
    private static final int range = 350;
    private static final int speed = 10;
    private static final int damage = 300;
    private static final int piercing = 1;

    public RocketLauncher() {
        super(ammoCapacity, shootPerMin, range, speed, damage, piercing);
        imageFile = new File("src\\assets\\sprite\\gui\\inventory_icons\\guns\\rocketlauncher_slot.png");
        setImage(imageFile);
    }

    @Override
    public void shoot(int wPosX, int wPosY, Point point) {
        if (getAmmoInMagazine() > 0) {
            Rocket rocket = new Rocket(wPosX, wPosY, getRange(), getSpeed(), point, 0);
            ProjectileHandler.addList(rocket);
            spendAmmo();
        }
    }
}
