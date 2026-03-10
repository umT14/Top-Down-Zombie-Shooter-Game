package entity.player;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import guns.AbstractGun;
import guns.Pistol;
import inputhandlers.KeyInputHandler;
import inputhandlers.MouseInputHandler;
import main.GameSaver;
import main.GameState;
import entity.Direction;
import entity.Entity;
import entity.player.inventory.Inventory;
import entity.player.inventory.InventorySlot;
import exceptions.EmptyHandException;
import exceptions.EmptySlotException;
import exceptions.FullHealthException;
import gamehandlers.CollisionHandler;
import gamehandlers.Item;
import gamehandlers.ItemBuilder;
import gamehandlers.ItemType;
import gamehandlers.ProjectileHandler;
import gui.gamepanel.HealthInfoPanel;
import gui.gamepanel.InventoryPanel;

public class Player extends Entity {

    private Inventory inventory;
    private AbstractGun gunInHand;
    private int playerHand;

    public KeyInputHandler keyHandler;
    public MouseInputHandler mouseInputHandler;

    private GameState gameState;

    private static final int speed = 3;
    private static final int health = 100;
    private static final int aniSprite = 4;
    private int points;

    private File st;
    private File w2;
    private File w3;
    private File w4;

    public Player(int pos_X, int pos_Y) {
        super(pos_X, pos_Y, health, speed, aniSprite);
        inventory = new Inventory();

        keyHandler = new KeyInputHandler();
        mouseInputHandler = new MouseInputHandler(this);

        screenPos_X = 512;
        screenPos_Y = 360;

        playerHand = 0;
        canMove = true;
        points = 0;

        inventory.addItem(new InventorySlot(new Pistol()));
        // inventory.addItem(new InventorySlot(new Sniper()));
        // inventory.addItem(new InventorySlot(new InfantryRifle()));
        // inventory.addItem(new InventorySlot(new Shotgun()));
        // inventory.addItem(new InventorySlot(new RocketLauncher()));

        gunInHand = inventory.getItem(playerHand);
        inventory.listInventory();

        collision = new Rectangle(screenPos_X - 15, screenPos_Y - 15, 30, 30);

        st = new File("src\\assets\\sprite\\player\\stable3.png");
        w2 = new File("src\\assets\\sprite\\player\\w22.png");
        w3 = new File("src\\assets\\sprite\\player\\w33.png");
        w4 = new File("src\\assets\\sprite\\player\\w44.png");

        try {
            setEntitySprites();
        } catch (IOException e) {
            System.out.println(e);
        }

        gameState = GameState.IN_GAME;
    }

    public Player(Player player) {
        super(player.getWPosX(), player.getWPosX(), health, speed, aniSprite);
        inventory = new Inventory();
        inventory.addItem(new InventorySlot(new Pistol()));
        keyHandler = new KeyInputHandler();

        screenPos_X = 250;
        screenPos_Y = 200;

        try {
            setEntitySprites();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void playerReload() {
        if (keyHandler.getReloadInfo()) {
            if (gunInHand instanceof Pistol) {
                gunInHand.reload();
                System.out.println("Gun reloaded");
            } else {
                if (inventory.getAmmoInfo(playerHand) != 0) {
                    int capacity = gunInHand.getAmmoCapacity();
                    if (inventory.getAmmoInfo(playerHand) >= capacity && gunInHand.getAmmoInMagazine() != capacity) {
                        inventory.spendAmmo(playerHand, true);
                        gunInHand.reload();
                    } else if (inventory.getAmmoInfo(playerHand) < capacity
                            && gunInHand.getAmmoInMagazine() != capacity) {
                        gunInHand.reload(inventory.getAmmoInfo(playerHand));
                        inventory.spendAmmo(playerHand, false);
                    } else {
                        System.out.println("BOS");
                    }
                }
            }
            InventoryPanel.changeGunAmmunitionInfo(this);
        }
    }

    public void gunReload() {
        if (inventory.getAmmoInfo(playerHand) >= gunInHand.getAmmoCapacity()) {
            inventory.spendAmmo(playerHand, true);
            gunInHand.reload();
        } else {
            inventory.spendAmmo(playerHand, false);
        }
    }

    public void playerGrab() {
        if (keyHandler.getGrapInfo()) {
            if (collision.getBounds().intersects(collision)) {
                Item item = ItemBuilder.checkPlayerCollision(this);
                inventory.addAmmo(item);
                updateItemInfoOnScreen(item);
            }
        }
    }

    public void updateItemInfoOnScreen(Item item) {
        if (item != null) {
            if (item.getItemType().equals(ItemType.MEDICAL)) {
                InventoryPanel.changeMedicalAmountInfo(this);
            } else {
                InventoryPanel.changeGunAmmunitionInfo(this);
            }
        }
    }

    public void playerShoot() throws EmptyHandException {

        if (mouseInputHandler.getLeftButton()) {

            if (gunInHand != null) {
                if (ProjectileHandler.getBulletTimeCounter() > gunInHand.getShootPerMin()) {
                    Point point = mouseInputHandler.getPoint();
                    if (point != null) {

                        gunInHand.shoot(getSPosX(), getSPosY(), point);

                    }
                    ProjectileHandler.setBulletTimeCounter(0);
                    InventoryPanel.changeGunAmmunitionInfo(this);
                }
            } else {
                throw new EmptyHandException();
            }

        }

    }

    public void playerMove() {

        if (keyHandler.getNorth()) {
            direction = Direction.NORTH;
        }
        if (keyHandler.getWest()) {
            direction = Direction.WEST;

        }
        if (keyHandler.getSouth()) {
            direction = Direction.SOUTH;

        }
        if (keyHandler.getEast()) {
            direction = Direction.EAST;
        }
        if (keyHandler.getWest() && keyHandler.getNorth()) {
            direction = Direction.NORTH_WEST;
        }
        if (keyHandler.getWest() && keyHandler.getSouth()) {
            direction = Direction.SOUTH_WEST;
        }
        if (keyHandler.getEast() && keyHandler.getNorth()) {
            direction = Direction.NORTH_EAST;
        }
        if (keyHandler.getEast() && keyHandler.getSouth()) {
            direction = Direction.SOUTH_EAST;
        }

        CollisionHandler.checkTileCollision(this);

        if (canMove) {
            if (direction == Direction.NORTH_WEST && (keyHandler.getWest() || keyHandler.getNorth())) {
                movePosX(-speed);
                movePosY(-speed);
                spriteNumControl();
            }
            if (direction == Direction.NORTH_EAST && (keyHandler.getEast() || keyHandler.getNorth())) {
                movePosX(speed);
                movePosY(-speed);
                spriteNumControl();
            }
            if (direction == Direction.SOUTH_EAST && (keyHandler.getEast() || keyHandler.getNorth())) {
                movePosX(speed);
                movePosY(speed);
                spriteNumControl();
            }
            if (direction == Direction.SOUTH_WEST && (keyHandler.getWest() || keyHandler.getSouth())) {
                movePosX(-speed);
                movePosY(speed);
                spriteNumControl();
            }
            if (direction == Direction.NORTH && keyHandler.getNorth()) {
                movePosY(-speed);
                spriteNumControl();
            }
            if (direction == Direction.WEST && keyHandler.getWest()) {
                movePosX(-speed);
                spriteNumControl();
            }
            if (direction == Direction.SOUTH && keyHandler.getSouth()) {
                movePosY(+speed);
                spriteNumControl();
            }
            if (direction == Direction.EAST && keyHandler.getEast()) {
                movePosX(speed);
                spriteNumControl();
            }

        }

    }

    public void drawPlayer(Graphics2D g2, int tileSize) {

        if (walkSprites == null) {
            try {
                setEntitySprites();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        baseSprite = walkSprites[cycle];
        g2.drawImage(baseSprite, getSPosX() - (tileSize / 2), getSPosY() - (tileSize / 2), tileSize, tileSize, null);
        // g2.draw(collision);
    }

    public void setEntitySprites() throws IOException {
        walkSprites = new BufferedImage[aniSprite];

        walkSprites[0] = ImageIO.read(st);
        walkSprites[1] = ImageIO.read(w2);
        walkSprites[2] = ImageIO.read(w3);
        walkSprites[3] = ImageIO.read(w4);
    }

    public void checkPlayerInventory() {
        if (keyHandler.getOne()) {
            playerHand = 0;
            setGunInHand(playerHand);
            InventoryPanel.changeGunAmmunitionInfo(this);
        }
        if (keyHandler.getTwo()) {
            playerHand = 1;
            setGunInHand(playerHand);
            InventoryPanel.changeGunAmmunitionInfo(this);
        }
        if (keyHandler.getThree()) {
            playerHand = 2;
            setGunInHand(playerHand);
            InventoryPanel.changeGunAmmunitionInfo(this);

        }
        if (keyHandler.getFour()) {
            playerHand = 3;
            setGunInHand(playerHand);
            InventoryPanel.changeGunAmmunitionInfo(this);
        }
        if (keyHandler.getFive()) {
            playerHand = 4;
            setGunInHand(playerHand);
            InventoryPanel.changeGunAmmunitionInfo(this);
        }

    }

    public void setGunInHand(int playerHand) {
        gunInHand = inventory.getItem(playerHand);
    }

    public void playerUseMedical() throws FullHealthException, EmptySlotException {

        if (keyHandler.getRegenInfo()) {
            if (getHealth() < getMaxHealth()) {
                inventory.useMedical();
                restoreHealth();
                InventoryPanel.changeMedicalAmountInfo(this);
                HealthInfoPanel.changeHealth(this);
                keyHandler.setRegenInfo(false);
            } else {
                throw new FullHealthException();
            }
        }

    }

    public void restoreHealth() {
        int health = getHealth();
        int restoreAmount = 30;
        if (restoreAmount + health < getMaxHealth()) {
            setHealth(restoreAmount + health);
        } else {
            setHealth(getMaxHealth());
        }
    }

    public void playerLoseHealth(int amount) {
        System.out.println(getHealth());
        if (getHealth() <= 0) {
            System.out.println("Player dead");
            GameSaver.resetWave(this);
            GameSaver.emptyFile();
            System.exit(0);
        } else {
            loseHealth(amount);
        }
    }

    public void addItemToInventory(AbstractGun gun) {
        inventory.addItem(new InventorySlot(gun));
    }

    public AbstractGun getSpecificInventoryItem(int i) {
        return inventory.getItem(i);
    }

    public BufferedImage getInventoryItemImage(int i) {
        if (inventory.getItem(i) != null) {
            if (inventory.getItem(i).getImage() == null) {
                inventory.getItem(i).setImage(inventory.getItem(i).getInventoryImageFile());
            }
        } else {

        }

        return inventory.returnSlotImage(i);
    }

    public void moveInventoryFrame(Graphics2D g2, int i) {
        g2.drawImage(InventorySlot.getFrameImage(), (i * 64) + 8, 0, null);
    }

    public int getHandIndex() {
        return playerHand;
    }

    public int getPoints() {
        return points;
    }

    public void addPoints(int amount) {
        points += amount;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getMedicalAmount() {
        return inventory.getMedical();
    }

    public int getInventoryAmmoInfo() {
        return inventory.getAmmoInfo(playerHand);
    }

    public AbstractGun getGunInHand() {
        return gunInHand;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public boolean inventoryHasItem(AbstractGun gun) {
        return inventory.hasItem(gun);
    }

}
