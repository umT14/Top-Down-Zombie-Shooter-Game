package gamehandlers;

import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import entity.player.Player;
import gui.gamepanel.GamePanel;

public class ItemBuilder {
    private static Item[] itemTypes = new Item[5];

    private static List<Item> items = new ArrayList<>();
    private static List<Item> deadItems = new ArrayList<>();

    GamePanel panel;

    public ItemBuilder(GamePanel panel) {
        this.panel = panel;
        instantiateItems();
        createItems();
    }

    public void instantiateItems() {
        try {
            itemTypes[0] = new Item(5, ItemType.SNIPER_AMMO);
            itemTypes[0].setImage(ImageIO.read(new File("src\\assets\\sprite\\items\\sniper_ammo.png")));

            itemTypes[1] = new Item(ItemType.MEDICAL);
            itemTypes[1].setImage(ImageIO.read(new File("src\\assets\\sprite\\items\\med.png")));

            itemTypes[2] = new Item(90, ItemType.RIFLE_AMMO);
            itemTypes[2].setImage(ImageIO.read(new File("src\\assets\\sprite\\items\\infantry_ammo.png")));

            itemTypes[3] = new Item(90, ItemType.SHOTGUN_AMMO);
            itemTypes[3].setImage(ImageIO.read(new File("src\\assets\\sprite\\items\\shotgun_ammo.png")));

            itemTypes[4] = new Item(3, ItemType.ROCKET);
            itemTypes[4].setImage(ImageIO.read(new File("src\\assets\\sprite\\items\\rocket.png")));

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void spawnItems(Graphics2D g2) {
        for (int i = 0; i < items.size(); i++) {
            items.get(i).drawItem(g2, panel);
        }
    }

    public void createItems() {
        Random random = new Random();

        for (int i = 0; i < 5; i++) {
            int itemType = random.nextInt(itemTypes.length);
            items.add(new Item(itemTypes[itemType]));
        }

    }

    public static void dropItem(int worldX, int worldY) {
        Random random = new Random();
        int itemRatio = random.nextInt(100);

        if (itemRatio <= 20) {
            if (itemRatio > 16 && itemRatio <= 20) {
                items.add(new Item(itemTypes[1], worldX, worldY));
            } else if (itemRatio > 12 && itemRatio <= 16 && WaveController.getWaveNumber() >= 2) {
                items.add(new Item(itemTypes[0], worldX, worldY));
            } else if (itemRatio > 8 && itemRatio <= 12 && WaveController.getWaveNumber() >= 4) {
                items.add(new Item(itemTypes[2], worldX, worldY));
            } else if (itemRatio > 4 && itemRatio <= 8 && WaveController.getWaveNumber() >= 6) {
                items.add(new Item(itemTypes[3], worldX, worldY));
            } else if (itemRatio >= 0 && itemRatio <= 4 && WaveController.getWaveNumber() >= 11) {
                items.add(new Item(itemTypes[4], worldX, worldY));
            }
        }
    }

    public static Item checkPlayerCollision(Player player) {
        System.out.println("pressed g key");
        Item res = null;
        if (!items.isEmpty()) {
            for (Item item : items) {
                if (player.getCollision().getBounds().intersects(item.getItemCollision().getBounds())) {
                    deadItems.add(item);
                    System.out.println("Item has collision");
                    res = item;
                }
            }
            for (Item item : deadItems) {
                items.remove(item);
            }
            deadItems.clear();
        }
        if (res != null) {
            return new Item(res);
        } else {
            return null;
        }

    }
}
