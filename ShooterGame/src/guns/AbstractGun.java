package guns;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

public abstract class AbstractGun implements Serializable {

    private int ammoCapacity;
    private int ammoInMagazine;
    private int shootPerMin;
    private int range;
    private int speed;
    private int damage;
    private int piercing;

    protected File imageFile;

    private transient BufferedImage inventoryImage;

    public AbstractGun(int ammoCapacity, int shootPerMin, int range, int speed, int damage, int piercing) {
        this.ammoCapacity = ammoCapacity;
        this.shootPerMin = shootPerMin;
        ammoInMagazine = ammoCapacity;
        this.range = range;
        this.speed = speed;
        this.damage = damage;
        this.piercing = piercing;
    }

    public abstract void shoot(int wPosX, int wPosY, Point point);

    public void reload() {
        ammoInMagazine = ammoCapacity;
    }

    public void reload(int amount) {
        ammoInMagazine = amount;
    }

    public int getAmmoInMagazine() {
        return ammoInMagazine;
    }

    public int getAmmoCapacity() {
        return ammoCapacity;
    }

    public int getRange() {
        return range;
    }

    public int getSpeed() {
        return speed;
    }

    public int getDamage() {
        return damage;
    }

    public int getPiercing() {
        return piercing;
    }

    public BufferedImage getImage() {
        return inventoryImage;
    }

    public File getInventoryImageFile() {
        return imageFile;
    }

    public int getShootPerMin() {
        return shootPerMin;
    }

    public void spendAmmo() {
        ammoInMagazine--;
    }

    public void spendAmmo(int amount) {
        ammoInMagazine -= amount;
    }

    public void setImage(File file) {
        try {
            inventoryImage = ImageIO.read(file);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

}
