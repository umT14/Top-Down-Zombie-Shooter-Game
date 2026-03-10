package gamehandlers;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import entity.zombies.AbstractZombie;
import entity.zombies.AcidSpitterZombie;
import entity.zombies.JumpingZombie;
import entity.zombies.NormalZombie;
import entity.zombies.TankZombie;
import gui.gamepanel.GamePanel;
import gui.gamepanel.HealthInfoPanel;

public class ZombieSpawner {

    private static List<AbstractZombie> zombies;
    private static List<AbstractZombie> deadZombies;
    private GamePanel panel;
    // private MapBuilder mapBuilder;

    public ZombieSpawner(GamePanel panel, MapBuilder mapBuilder) {
        this.panel = panel;
        // this.mapBuilder = mapBuilder;
        zombies = new ArrayList<>();
        deadZombies = new ArrayList<>();
    }

    public void drawZombies(Graphics2D g2, int tileSize) {
        if (!zombies.isEmpty()) {
            for (AbstractZombie zombie : zombies) {
                zombie.drawZombie(g2, tileSize);
            }
            removeZombies();
        }
    }

    public void removeZombies() {
        if (!deadZombies.isEmpty()) {
            for (AbstractZombie zombie : deadZombies) {
                if (zombie instanceof NormalZombie) {
                    zombie.getTarget().addPoints(15);
                } else if (zombie instanceof JumpingZombie) {
                    zombie.getTarget().addPoints(35);
                } else if (zombie instanceof AcidSpitterZombie) {
                    zombie.getTarget().addPoints(40);
                } else if (zombie instanceof TankZombie) {
                    zombie.getTarget().addPoints(70);
                }
                HealthInfoPanel.changePoints(zombie.getTarget());
                zombies.remove(zombie);
            }
            deadZombies.clear();
        }
    }

    public static void addDeadZombie(AbstractZombie deadZombie) {
        deadZombies.add(deadZombie);
    }

    public void spawnZombies(int waveNum) {
        Random random = new Random();

        int upperBound = waveNum * 3;
        int lowerBound = waveNum * 2;

        int zombieCount = random.nextInt(upperBound - lowerBound) + lowerBound;

        for (int i = 0; i < zombieCount; i++) {
            Point point = locationRandomizer(random);
            Class<? extends AbstractZombie> type = zombieDecider(random, waveNum);
            if (type == NormalZombie.class) {
                zombies.add(new NormalZombie((int) point.getX(), (int) point.getY(), panel.getPlayer()));
            } else if (type == JumpingZombie.class) {
                zombies.add(new JumpingZombie((int) point.getX(), (int) point.getY(), panel.getPlayer()));
            } else if (type == AcidSpitterZombie.class) {
                zombies.add(new AcidSpitterZombie((int) point.getX(), (int) point.getY(), panel.getPlayer()));
            } else if (type == TankZombie.class) {
                zombies.add(new TankZombie((int) point.getX(), (int) point.getY(), panel.getPlayer()));
            } else {
                System.out.println("ZombieSpawner: NULL");
            }

        }
    }

    public static List<AbstractZombie> getZombieList() {
        return zombies;
    }

    public Class<? extends AbstractZombie> zombieDecider(Random random, int waveNum) {
        double zombieType = random.nextDouble(100);

        double normalZBound = 80 - ((waveNum - 1) * 2.5);
        double jockeyZBound = 90 - ((waveNum - 1) * 2);
        double spitterZBound = 99 - ((waveNum - 1));
        double tankZBound = 100;

        if (zombieType <= normalZBound) { // Normal
            return NormalZombie.class;
        } else if (normalZBound < zombieType && zombieType <= jockeyZBound && waveNum > 4) { // Jockey&&
            return JumpingZombie.class;
        } else if (jockeyZBound < zombieType && zombieType <= spitterZBound && waveNum > 9) { // Spitter&&
            return AcidSpitterZombie.class;
        } else if (spitterZBound < zombieType && zombieType <= tankZBound && waveNum > 15) { // Tank
            return TankZombie.class;
        } else {
            return null;
        }
    }

    public Point locationRandomizer(Random random) {
        // int lowerBoundX = panel.getPlayer().getWPosX() -
        // panel.getPlayer().getSPosX();
        // int lowerBoundY = panel.getPlayer().getWPosY() -
        // panel.getPlayer().getSPosY();

        // int upperBoundX = panel.getPlayer().getWPosX() + lowerBoundX;
        // int upperBoundY = panel.getPlayer().getWPosY() + lowerBoundY;

        int worldX = random.nextInt(MapBuilder.getMapRow() * MapBuilder.getTileSize());
        int worldY = random.nextInt(MapBuilder.getMapCol() * MapBuilder.getTileSize());

        Tile tile1 = MapBuilder.getTile(worldX / 48, worldY / 48);
        Tile tile2 = MapBuilder.getTile((worldX - (MapBuilder.getTileSize() / 2)) / 48,
                (worldY - (MapBuilder.getTileSize() / 2)) / 48);
        // Tile tile3 = MapBuilder.getTile((worldX + (MapBuilder.getTileSize() / 2)) /
        // 48, (worldY + (MapBuilder.getTileSize() / 2)) / 48);

        if (tile1.hasCollision || tile2.hasCollision) { // || tile3.hasCollision
            return locationRandomizer(random);
        }

        // boolean cond1 = lowerBoundX < worldX && upperBoundX > worldX;
        // boolean cond2 = lowerBoundY < worldY && upperBoundY > worldY;
        return new Point(worldX, worldY);

    }

}
