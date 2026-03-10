package gui.gamepanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import entity.player.Player;
import gamehandlers.WaveController;

public class HealthInfoPanel extends JPanel {

    private static JLabel health;
    private static JLabel score;
    private static JLabel wave;

    private Image heart;

    public HealthInfoPanel(Player player) {

        setBackground(Color.LIGHT_GRAY);
        setLayout(new GridLayout());
        setPreferredSize(new Dimension(256, 64));

        setIcons();

        health = new JLabel("" + player.getHealth(), SwingConstants.CENTER);
        score = new JLabel("Score:  " + player.getPoints(), SwingConstants.CENTER);
        wave = new JLabel("Wave:  " + 1, SwingConstants.CENTER);

        health.setIcon(new ImageIcon(heart));
        health.setIconTextGap(5);

        add(health);
        add(score);
        add(wave);
    }

    public void setIcons() {
        try {
            Image heart2 = ImageIO.read(new File("src\\assets\\sprite\\gui\\inventory_icons\\heart_icon_2.png"));
            heart = heart2.getScaledInstance(24, 24, Image.SCALE_FAST);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void changeHealth(Player player) {
        health.setText("" + player.getHealth());
    }

    public static void changeWaveInfo() {
        wave.setText("Wave:  " + WaveController.getWaveNumber());
    }

    public static void changePoints(Player player) {
        score.setText("Score:  " + player.getPoints());
    }
}
