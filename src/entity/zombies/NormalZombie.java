package entity.zombies;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.player.Player;

public class NormalZombie extends AbstractZombie {

    public static final int maxHealth = 100;
    public static final int speed = 3;
    public static final int damage = 5;
    public static final int aniSprite = 4;

    public NormalZombie(int pos_X, int pos_Y, Player target) {
        super(pos_X, pos_Y, maxHealth, speed, damage, aniSprite, target);
        setEntitySprites();
    }

    @Override
    public void setEntitySprites() {
        try {
            baseSprite = ImageIO.read(new File("src\\assets\\sprite\\zombies\\normal_zombie.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
