package gamehandlers;

import entity.player.Player;
import gui.gamepanel.HealthInfoPanel;
import gui.gamepanel.InventoryPanel;
import guns.InfantryRifle;
import guns.RocketLauncher;
import guns.Shotgun;
import guns.Sniper;

public class WaveController {

    private static int waveNumber;
    private static boolean changeInventory;
    private static int waveStartPoints;

    private ZombieSpawner spawner;
    private InventoryPanel inventoryPanel;

    private Player player;
    private boolean itemAdded;

    public WaveController(ZombieSpawner spawner, Player player, InventoryPanel inventoryPanel) {
        this.spawner = spawner;
        this.inventoryPanel = inventoryPanel;
        this.player = player;
        waveStartPoints = player.getPoints();
        itemAdded = false;
    }

    public void checkZombieAmount() {
        if (ZombieSpawner.getZombieList().isEmpty()) {
            waveStartPoints = player.getPoints();
            waveNumber++;
            spawner.spawnZombies(waveNumber);
            System.out.println("zombies spawned");
            itemAdded = false;
            System.out.println(waveNumber);
            HealthInfoPanel.changeWaveInfo();
            if (waveNumber > 1) {
                player.addPoints(waveNumber * 50);
                HealthInfoPanel.changePoints(player);
            }
        }
    }

    public void givePlayerWeapon() {
        switch (waveNumber) {
            case 2:
                if (!itemAdded && !player.inventoryHasItem(new InfantryRifle())) {
                    player.addItemToInventory(new InfantryRifle());
                    itemAdded = true;
                    System.out.println("gun Added");
                    repaintInventory();

                }
                break;
            case 4:
                if (!itemAdded && !player.inventoryHasItem(new Shotgun())) {
                    player.addItemToInventory(new Shotgun());
                    itemAdded = true;
                    System.out.println("gun Added");
                    repaintInventory();

                }
                break;
            case 6:
                if (!itemAdded && !player.inventoryHasItem(new Sniper())) {
                    player.addItemToInventory(new Sniper());
                    itemAdded = true;
                    System.out.println("gun Added");
                    repaintInventory();

                }
                break;

            case 11:
                if (!itemAdded && !player.inventoryHasItem(new RocketLauncher())) {
                    player.addItemToInventory(new RocketLauncher());
                    itemAdded = true;
                    System.out.println("gun Added");
                    repaintInventory();
                }
                break;
        }
    }

    public void repaintInventory() {
        inventoryPanel.changeSlotImages();
    }

    public static int getWaveNumber() {
        return waveNumber;
    }

    public static void setWaveNumber(int num) {
        waveNumber = num;
    }

    public static boolean getChangeInventory() {
        return changeInventory;
    }

    public static void setChangeInventory() {
        changeInventory = false;
    }

    public static int getWaveStartPoints() {
        return waveStartPoints;
    }
}
