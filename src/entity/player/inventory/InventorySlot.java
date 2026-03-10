package entity.player.inventory;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

import guns.AbstractGun;

public class InventorySlot implements Serializable {

    private AbstractGun gun;
    private int magazine;

    private File frameFile;
    private File emptySlotFile;

    private static transient BufferedImage emptySlot;
    private static transient BufferedImage frame;

    public InventorySlot(AbstractGun gun) {
        this.gun = gun;
        this.magazine = gun.getAmmoInMagazine();
        frameFile = new File("src\\assets\\sprite\\gui\\inventory_icons\\frame.png");
        emptySlotFile = new File("src\\assets\\sprite\\gui\\inventory_icons\\guns\\empty_slot.png");
        setEmptySlot();
    }

    public AbstractGun getGun() {
        return gun;
    }

    public int getMagazine() {
        return magazine;
    }

    public BufferedImage getSlotImage() {
        return gun.getImage();
    }

    public static BufferedImage getEmptySlotImage() {
        return emptySlot;
    }

    public static BufferedImage getFrameImage() {
        return frame;
    }

    public void setEmptySlot() {
        try {
            emptySlot = ImageIO.read(emptySlotFile);
            frame = ImageIO.read(frameFile);

        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
