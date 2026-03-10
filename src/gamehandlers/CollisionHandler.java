package gamehandlers;

import java.awt.Graphics2D;

import entity.Direction;
import entity.Entity;
import entity.player.Player;
import entity.projectile.Projectile;
import entity.projectile.Rocket;
import entity.zombies.AbstractZombie;
import gui.gamepanel.GamePanel;
import gui.gamepanel.HealthInfoPanel;

public class CollisionHandler {

    private Player player;
    GamePanel panel;

    private int time;

    public CollisionHandler(GamePanel panel, MapBuilder mapBuilder) {
        this.panel = panel;
        this.player = panel.getPlayer();
        time = 39;
    }

    public void checkPlayerToZombieCollision() {
        for (AbstractZombie zombie : ZombieSpawner.getZombieList()) {
            if (player.getCollision().intersects(zombie.getCollision())) {
                time++;
                if (time % 40 == 0) {
                    player.playerLoseHealth(zombie.getDamage());
                    HealthInfoPanel.changeHealth(player);
                    time = 0;
                }
            }
        }
    }

    public static void checkZombieCollision() {

        for (AbstractZombie zombie1 : ZombieSpawner.getZombieList()) {
            checkTileCollision(zombie1);
        }
    }

    public static void checkZombieCollision(AbstractZombie zombie) {
        checkTileCollision(zombie);

    }

    public static void checkTileCollision(Entity entity) {

        entity.setCanMove(true);

        int topRow = (entity.getWPosY() - 15) / 48;
        int bottomRow = (entity.getWPosY() + 15) / 48;
        int leftCol = (entity.getWPosX() - 15) / 48;
        int rightCol = (entity.getWPosX() + 15) / 48;

        if (entity.getDirection() == Direction.NORTH) {
            topRow = (entity.getWPosY() - 15 - entity.getSpeed()) / 48;
            Tile tile1 = MapBuilder.getTile(leftCol, topRow);
            Tile tile2 = MapBuilder.getTile(rightCol, topRow);

            if (tile1.hasCollision || tile2.hasCollision) {
                entity.setCanMove(false);
            }
        } else if (entity.getDirection() == Direction.WEST) {
            leftCol = (entity.getWPosX() - 15 - entity.getSpeed()) / 48;
            Tile tile1 = MapBuilder.getTile(leftCol, topRow);
            Tile tile2 = MapBuilder.getTile(leftCol, bottomRow);

            if (tile1.hasCollision || tile2.hasCollision) {
                entity.setCanMove(false);
            }
        } else if (entity.getDirection() == Direction.SOUTH) {
            bottomRow = (entity.getWPosY() + 15 + entity.getSpeed()) / 48;
            Tile tile1 = MapBuilder.getTile(leftCol, bottomRow);
            Tile tile2 = MapBuilder.getTile(rightCol, bottomRow);

            if (tile1.hasCollision || tile2.hasCollision) {
                entity.setCanMove(false);
            }
        } else if (entity.getDirection() == Direction.EAST) {
            rightCol = (entity.getWPosX() + 15 + entity.getSpeed()) / 48;
            Tile tile1 = MapBuilder.getTile(rightCol, bottomRow);
            Tile tile2 = MapBuilder.getTile(rightCol, topRow);

            if (tile1.hasCollision || tile2.hasCollision) {
                entity.setCanMove(false);
            }
        } else if (entity.getDirection() == Direction.NORTH_WEST) {
            topRow = (entity.getWPosY() - 15 - entity.getSpeed()) / 48;
            leftCol = (entity.getWPosX() - 15 - entity.getSpeed()) / 48;
            Tile tile1 = MapBuilder.getTile(leftCol, topRow);
            Tile tile2 = MapBuilder.getTile(leftCol, bottomRow);
            Tile tile3 = MapBuilder.getTile(leftCol, topRow);
            Tile tile4 = MapBuilder.getTile(rightCol, topRow);

            if (tile1.hasCollision || tile2.hasCollision || tile3.hasCollision || tile4.hasCollision) {
                entity.setCanMove(false);
            }
        } else if (entity.getDirection() == Direction.NORTH_EAST) {
            topRow = (entity.getWPosY() - 15 - entity.getSpeed()) / 48;
            rightCol = (entity.getWPosX() + 15 + entity.getSpeed()) / 48;
            Tile tile1 = MapBuilder.getTile(rightCol, bottomRow);
            Tile tile2 = MapBuilder.getTile(rightCol, topRow);
            Tile tile3 = MapBuilder.getTile(leftCol, topRow);
            Tile tile4 = MapBuilder.getTile(rightCol, topRow);

            if (tile1.hasCollision || tile2.hasCollision || tile3.hasCollision || tile4.hasCollision) {
                entity.setCanMove(false);
            }
        } else if (entity.getDirection() == Direction.SOUTH_WEST) {
            bottomRow = (entity.getWPosY() + 15 + entity.getSpeed()) / 48;
            leftCol = (entity.getWPosX() - 15 - entity.getSpeed()) / 48;
            Tile tile1 = MapBuilder.getTile(leftCol, topRow);
            Tile tile2 = MapBuilder.getTile(leftCol, bottomRow);
            Tile tile3 = MapBuilder.getTile(leftCol, bottomRow);
            Tile tile4 = MapBuilder.getTile(rightCol, bottomRow);

            if (tile1.hasCollision || tile2.hasCollision || tile3.hasCollision || tile4.hasCollision) {
                entity.setCanMove(false);
            }
        } else if (entity.getDirection() == Direction.SOUTH_EAST) {
            bottomRow = (entity.getWPosY() + 15 + entity.getSpeed()) / 48;
            rightCol = (entity.getWPosX() + 15 + entity.getSpeed()) / 48;
            Tile tile1 = MapBuilder.getTile(rightCol, bottomRow);
            Tile tile2 = MapBuilder.getTile(rightCol, topRow);
            Tile tile3 = MapBuilder.getTile(leftCol, bottomRow);
            Tile tile4 = MapBuilder.getTile(rightCol, bottomRow);

            if (tile1.hasCollision || tile2.hasCollision || tile3.hasCollision || tile4.hasCollision) {
                entity.setCanMove(false);
            }
        }
    }

    public void bulletCollision() {
        for (Projectile projectile : ProjectileHandler.getList()) {
            int bWorldX = (int) projectile.getCollision().getX() + (player.getWPosX() - player.getSPosX());
            int bWorldY = (int) projectile.getCollision().getY() + (player.getWPosY() - player.getSPosY());

            Tile tile = MapBuilder.getTile(bWorldX / 48, bWorldY / 48);
            if (tile.hasCollision) {
                if (projectile instanceof Rocket) {
                    Rocket rocket = (Rocket) projectile;
                    rocket.createExplosion((Graphics2D) panel.getGraphics());
                }
                ProjectileHandler.addRemoveList(projectile);
            }
        }
    }
}
