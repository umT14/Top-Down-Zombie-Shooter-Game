package gamehandlers;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import gui.gamepanel.GamePanel;

public class Item {
    private BufferedImage image;
    private Rectangle itemCol;
    private ItemType itemType;
    private int x;
    private int y;
    private int tS = 40;
    private int amount;

    public Item(int amount, ItemType itemType) {
        spawnItem();
        itemCol = new Rectangle(x, y, tS, tS);
        this.amount = amount;
        this.itemType = itemType;
    }

    public Item(ItemType itemType) {
        spawnItem();
        itemCol = new Rectangle(x, y, tS, tS);
        this.amount = 1;
        this.itemType = itemType;
    }

    public Item(BufferedImage image, ItemType itemType) {
        spawnItem();
        itemCol = new Rectangle(x, y, tS, tS);
        this.amount = 1;
        this.image = image;
        this.itemType = itemType;
    }

    public Item(Item item) {
        spawnItem();
        this.itemCol = item.getItemCollision();
        this.amount = item.getAmount();
        this.image = item.getImage();
        this.itemType = item.getItemType();
    }

    public Item(Item item, int worldX, int worldY) {
        spawnItem(worldX, worldY);
        this.itemCol = item.getItemCollision();
        this.amount = item.getAmount();
        this.image = item.getImage();
        this.itemType = item.getItemType();
    }

    public void drawItem(Graphics2D g2, GamePanel panel) {
        int screenX = x - panel.getPlayer().getWPosX() + panel.getPlayer().getSPosX();
        int screenY = y - panel.getPlayer().getWPosY() + panel.getPlayer().getSPosY();
        itemCol = new Rectangle(screenX, screenY, tS, tS);
        g2.draw(itemCol);
        g2.drawImage(image, screenX, screenY, tS, tS, null);
    }

    public void spawnItem() {
        Random random = new Random();
        x = random.nextInt(31) * tS;
        y = random.nextInt(20) * tS;
    }

    public void spawnItem(int worldX, int worldY) {
        x = worldX;
        y = worldY;
    }

    public BufferedImage getImage() {
        return image;
    }

    public Rectangle getItemCollision() {
        return itemCol;
    }

    public int getAmount() {
        return amount;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

}
