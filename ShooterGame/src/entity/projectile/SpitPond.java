package entity.projectile;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.player.Player;
import gamehandlers.SpitDrawer;
import gui.gamepanel.HealthInfoPanel;

public class SpitPond extends Rectangle {

    private Rectangle spitCollision;
    private BufferedImage image;
    private int worldX;
    private int worldY;
    private Player target;

    private static int interval = 400;
    private int time = 0;

    public SpitPond(int worldX, int worldY, Player target) {
        spitCollision = new Rectangle();
        this.worldX = worldX;
        this.worldY = worldY;
        this.target = target;
        setImage();
        SpitDrawer.getSpitList().add(this);
    }

    public void drawSpit(Graphics2D g2) {

        int screenX = worldX - target.getWPosX() + target.getSPosX();
        int screenY = worldY - target.getWPosY() + target.getSPosY();

        spitCollision = new Rectangle(screenX - 40, screenY - 40, 80, 80);

        if (interval > time) {
            time++;
            spitCollisionCheck();
            // g2.fill(spitCollision);
            g2.drawImage(image, screenX - 40, screenY - 40, 80, 80, null);
        } else {
            time = 0;
            SpitDrawer.addDrainedPond(this);
        }
    }

    public void spitCollisionCheck() {
        if (spitCollision.getBounds().intersects(target.getCollision().getBounds())) {
            if (time % 40 == 0) {
                target.playerLoseHealth(4);
                HealthInfoPanel.changeHealth(target);
            }
        }
    }

    public void setImage() {
        try {
            image = ImageIO.read(new File("src\\assets\\sprite\\other\\spitTest.png"));
        } catch (IOException e) {
            System.out.println(e);
        }
    }

}
