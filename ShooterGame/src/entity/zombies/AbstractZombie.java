package entity.zombies;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import entity.Direction;
import entity.Entity;
import entity.player.Player;
import gamehandlers.CollisionHandler;
import gamehandlers.ItemBuilder;
import gamehandlers.ZombieSpawner;
import main.GameState;

public abstract class AbstractZombie extends Entity {

    private int damage;
    private Player target;
    private double speedX;
    private double speedY;
    protected int FOV;
    protected boolean sawPlayer;

    public AbstractZombie(int pos_X, int pos_Y, int health, int speed, int damage, int aniSprite, Player target) {
        super(pos_X, pos_Y, health, speed, aniSprite);
        this.target = target;
        this.damage = damage;
        sawPlayer = true;
        FOV = 500;

        collision = new Rectangle(screenPos_X - 20, screenPos_Y - 20, 40, 40);
    }

    public void drawZombie(Graphics2D g2, int tileSize) {
        screenPos_X = getWPosX() - target.getWPosX() + target.getSPosX();
        screenPos_Y = getWPosY() - target.getWPosY() + target.getSPosY();

        double distance = Math
                .sqrt(Math.pow(getWPosX() - target.getWPosX(), 2) + Math.pow(getWPosY() - target.getWPosY(), 2));

        if (distance <= FOV && !sawPlayer) {
            sawPlayer = true;
        }
        if (sawPlayer) {
            double radian = Math.atan2(target.getWPosY() - getWPosY(), target.getWPosX() - getWPosX());
            speedX = Math.cos(radian) * getSpeed();
            speedY = Math.sin(radian) * getSpeed();
            zombieSetDirection();
            zombieMove(1);
        }

        collision = new Rectangle(screenPos_X - (tileSize / 2), screenPos_Y - (tileSize / 2), tileSize, tileSize);
        g2.drawImage(baseSprite, screenPos_X - (tileSize / 2), screenPos_Y - (tileSize / 2), tileSize, tileSize, null);
    }

    public int getDamage() {
        return damage;
    }

    public Player getTarget() {
        return target;
    }

    public double getSpeedX() {
        return speedX;
    }

    public double getSpeedY() {
        return speedY;
    }

    public void setSpeedX(double speedX) {
        this.speedX = speedX;
    }

    public void setSpeedY(double speedY) {
        this.speedY = speedY;
    }

    public void zombieLoseHealth(int amount) {
        loseHealth(amount);
        if (getHealth() <= 0) {
            ZombieSpawner.addDeadZombie(this);
            ItemBuilder.dropItem(getWPosX(), getWPosY());
        }
    }

    public void zombieMove(int m) {
        CollisionHandler.checkTileCollision(this);
        if (canMove && target.getGameState().equals(GameState.IN_GAME)) {
            if (direction == Direction.NORTH) {
                movePosY(speedY * m);
            }
            if (direction == Direction.SOUTH) {
                movePosY(speedY * m);
            }
            if (direction == Direction.WEST) {
                movePosX(speedX * m);
            }
            if (direction == Direction.EAST) {
                movePosX(speedX * m);
            }

            if (direction == Direction.NORTH_WEST) {
                movePosY(speedY * m);
                movePosX(speedX * m);
            }
            if (direction == Direction.SOUTH_WEST) {
                movePosY(speedY * m);
                movePosX(speedX * m);
            }
            if (direction == Direction.NORTH_EAST) {
                movePosX(speedX * m);
                movePosY(speedY * m);
            }
            if (direction == Direction.SOUTH_EAST) {
                movePosX(speedX * m);
                movePosY(speedY * m);
            }
        }
    }

    public void zombieSetDirection() {
        if (speedX > 0 && Math.abs(speedX / speedY) > 1) {
            direction = Direction.EAST;
        }
        if (speedX < 0 && Math.abs(speedX / speedY) > 1) {
            direction = Direction.WEST;
        }
        if (speedY > 0 && Math.abs(speedY / speedX) > 1) {
            direction = Direction.SOUTH;
        }
        if (speedY < 0 && Math.abs(speedY / speedX) > 1) {
            direction = Direction.NORTH;
        }
        if (speedX > 0 && Math.abs(speedX / speedY) < 1 && speedX / speedY > 0) {
            direction = Direction.SOUTH_EAST;
        }
        if (speedX < 0 && Math.abs(speedX / speedY) < 1 && speedX / speedY > 0) {
            direction = Direction.NORTH_WEST;
        }
        if (speedY > 0 && Math.abs(speedX / speedY) < 1 && speedX / speedY < 0) {
            direction = Direction.SOUTH_WEST;
        }
        if (speedY < 0 && Math.abs(speedY / speedX) < 1 && speedX / speedY < 0) {
            direction = Direction.NORTH_EAST;
        }
    }
}
