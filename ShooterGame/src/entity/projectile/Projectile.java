package entity.projectile;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import gamehandlers.ProjectileHandler;

public abstract class Projectile {

    boolean sourceIsPlayer;
    double wPosX;
    double wPosY;

    double sPosX;
    double sPosY;

    double startX;
    double startY;

    int range;
    int speed;

    double speedX;
    double speedY;
    double radian;

    double pointX;
    double pointY;

    boolean addedList;

    Point point;

    Rectangle collision;

    public Projectile(boolean sourceIsPlayer, int sPosX, int sPosY, int range, int speed, Point point,
            double deflection) {
        this.sourceIsPlayer = sourceIsPlayer;
        this.speed = speed;
        this.range = range;
        this.point = point;

        this.sPosX = sPosX;
        this.sPosY = sPosY;
        startX = this.sPosX;
        startY = this.sPosY;

        pointX = point.getX();
        pointY = point.getY();

        radian = Math.atan2(pointY - sPosY, pointX - sPosX) + deflection;

        speedX = Math.cos(radian) * speed;
        speedY = Math.sin(radian) * speed;

        collision = new Rectangle((int) sPosX, (int) sPosY, 5, 5);

        addedList = false;
    }

    public void drawProjectile(Graphics2D g2) {

        double distance = Math.sqrt(Math.pow(sPosX - startX, 2) + Math.pow(sPosY - startY, 2));
        if (distance < range) {

            sPosX += speedX;
            sPosY += speedY;

            collision = new Rectangle((int) sPosX, (int) sPosY, 5, 5);
            checkForCollision();
            g2.fillOval((int) sPosX, (int) sPosY, 10, 10);
        } else if (!addedList) {
            ProjectileHandler.addRemoveList(this);
            addedList = true;
        }
    }

    public Rectangle getCollision() {
        return collision;
    }

    public abstract void checkForCollision();

    public Point getPoint() {
        return point;
    }

}
