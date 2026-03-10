package entity.player.inventory;

import java.awt.image.BufferedImage;
import java.io.Serializable;

import exceptions.EmptySlotException;
import gamehandlers.Item;
import gamehandlers.ItemType;
import guns.AbstractGun;
import guns.InfantryRifle;
import guns.RocketLauncher;
import guns.Shotgun;
import guns.Sniper;

public class Inventory implements Serializable {

    InventorySlot[] inventory = new InventorySlot[5];

    private int rocketAmmo;
    private int sniperAmmo;
    private int shotgunAmmo;
    private int infantryAmmo;
    private int medical;

    public void addItem(InventorySlot slot) {
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] == null) {
                inventory[i] = slot;
                break;
            }
        }
    }

    public int getSniperAmmo() {
        return sniperAmmo;
    }

    public int getShotgunAmmo() {
        return shotgunAmmo;
    }

    public int getRocketAmmo() {
        return rocketAmmo;
    }

    public int getInfantryAmmo() {
        return infantryAmmo;
    }

    public int getMedical() {
        return medical;
    }

    public int getAmmoInfo(int playerHand) {
        System.out.println(playerHand);
        AbstractGun gun = inventory[playerHand].getGun();
        if (gun instanceof Sniper) {
            return sniperAmmo;
        } else if (gun instanceof Shotgun) {
            return shotgunAmmo;
        } else if (gun instanceof InfantryRifle) {
            return infantryAmmo;
        } else if (gun instanceof RocketLauncher) {
            return rocketAmmo;
        } else {
            return 0;
        }
    }

    public void addAmmo(Item item) {

        if (item != null) {
            ItemType itemType = item.getItemType();

            if (itemType.equals(ItemType.SNIPER_AMMO)) {
                sniperAmmo += item.getAmount();
            } else if (itemType.equals(ItemType.SHOTGUN_AMMO)) {
                shotgunAmmo += item.getAmount();
            } else if (itemType.equals(ItemType.RIFLE_AMMO)) {
                infantryAmmo += item.getAmount();
            } else if (itemType.equals(ItemType.ROCKET)) {
                rocketAmmo += item.getAmount();
            } else if (itemType.equals(ItemType.MEDICAL)) {
                medical += item.getAmount();
            }
        }
    }

    public void spendAmmo(int playerHand, boolean hasEnough) {
        AbstractGun gun = inventory[playerHand].getGun();
        if (gun instanceof Sniper) {
            if (hasEnough) {
                sniperAmmo -= gun.getAmmoCapacity();
            } else {
                sniperAmmo = 0;
            }
        } else if (gun instanceof Shotgun) {
            if (hasEnough) {
                shotgunAmmo -= gun.getAmmoCapacity();
            } else {
                shotgunAmmo = 0;
            }
        } else if (gun instanceof InfantryRifle) {
            if (hasEnough) {
                infantryAmmo -= gun.getAmmoCapacity();
            } else {
                infantryAmmo = 0;
            }
        } else if (gun instanceof RocketLauncher) {
            if (hasEnough) {
                rocketAmmo -= gun.getAmmoCapacity();
            } else {
                rocketAmmo = 0;
            }
        }
    }

    public AbstractGun getItem(int index) {
        if (inventory[index] != null) {
            return inventory[index].getGun();
        } else {
            return null;
        }

    }

    public void listInventory() {
        for (InventorySlot slot : inventory) {
            if (slot != null) {
                System.out.print(slot.getGun() + "   ");
            } else {
                System.out.print("null         ");
            }

        }
    }

    public void useMedical() throws EmptySlotException {
        if (medical > 0) {
            medical--;
        } else {
            throw new EmptySlotException(ItemType.MEDICAL);
        }
    }

    public BufferedImage returnSlotImage(int i) {
        if (inventory[i] != null) {
            inventory[i].setEmptySlot();
            return inventory[i].getSlotImage();
        } else {
            return InventorySlot.getEmptySlotImage();
        }
    }

    public boolean hasItem(AbstractGun gun) {
        for (InventorySlot slot : inventory) {
            if (slot != null) {
                if (slot.getGun().getClass() == gun.getClass()) {
                    return true;
                }
            }
        }
        return false;
    }

}
