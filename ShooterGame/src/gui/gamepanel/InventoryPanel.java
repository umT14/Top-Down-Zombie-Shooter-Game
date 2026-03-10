package gui.gamepanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import entity.player.Player;
import gamehandlers.WaveController;
import guns.Pistol;

public class InventoryPanel extends JPanel {

    private Player player;
    private JLabel slot1;
    private JLabel slot2;
    private JLabel slot3;
    private JLabel slot4;
    private JLabel slot5;

    private static JLabel medical;
    private static JLabel gunAmmoInfo;

    private BufferedImage med_icon;

    public InventoryPanel(Player player) {
        this.player = player;
        setBackground(Color.LIGHT_GRAY);
        setIcons();
        setSlotImages();
        setInventoryInfoLabels();

        setPreferredSize(new Dimension(616, 64));
        setLayout(new GridLayout());
        setLayout(new FlowLayout(SwingConstants.CENTER, 16, 8));

        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();

        panel1.setBackground(new Color(50, 50, 50));
        panel1.setLayout(new BorderLayout());

        panel2.setBackground(new Color(50, 50, 50));
        panel2.setLayout(new GridLayout());

        add(slot1);
        add(slot2);
        add(slot3);
        add(slot4);
        add(slot5);

        panel1.setPreferredSize(new Dimension(128, 48));
        panel1.add(medical, BorderLayout.CENTER);

        panel2.setPreferredSize(new Dimension(128, 48));
        panel2.add(gunAmmoInfo);

        add(panel1);
        add(panel2);

    }

    public void setSlotImages() {
        slot1 = new JLabel(new ImageIcon(player.getInventoryItemImage(0)));
        slot2 = new JLabel(new ImageIcon(player.getInventoryItemImage(1)));
        slot3 = new JLabel(new ImageIcon(player.getInventoryItemImage(2)));
        slot4 = new JLabel(new ImageIcon(player.getInventoryItemImage(3)));
        slot5 = new JLabel(new ImageIcon(player.getInventoryItemImage(4)));
    }

    public void setInventoryInfoLabels() {
        medical = new JLabel("Medical:  " + player.getMedicalAmount(), SwingConstants.CENTER);
        medical.setIcon(new ImageIcon(med_icon));
        medical.setIconTextGap(8);
        medical.setForeground(Color.LIGHT_GRAY);

        gunAmmoInfo = new JLabel(player.getGunInHand().getAmmoInMagazine() + " / ∞",
                SwingConstants.CENTER);
        gunAmmoInfo.setForeground(Color.LIGHT_GRAY);
        gunAmmoInfo.setFont(new Font("name", 0, 32));
    }

    public void setIcons() {
        try {
            med_icon = ImageIO.read(new File("src\\assets\\sprite\\gui\\inventory_icons\\med_icon.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeSlotImages() {
        switch (WaveController.getWaveNumber()) {
            case 2:
                slot2.setIcon(new ImageIcon(player.getInventoryItemImage(1)));
            case 4:
                slot3.setIcon(new ImageIcon(player.getInventoryItemImage(2)));
            case 6:
                slot4.setIcon(new ImageIcon(player.getInventoryItemImage(3)));
            case 11:
                slot5.setIcon(new ImageIcon(player.getInventoryItemImage(4)));
        }
    }

    public static void changeMedicalAmountInfo(Player player) {
        medical.setText("Medical:  " + player.getMedicalAmount());
    }

    public static void changeGunAmmunitionInfo(Player player) {
        if (player.getGunInHand() != null) {
            if (player.getGunInHand() instanceof Pistol) {
                gunAmmoInfo.setText(player.getGunInHand().getAmmoInMagazine() + " / ∞");
            } else {
                gunAmmoInfo.setText(player.getGunInHand().getAmmoInMagazine() + " / " + player.getInventoryAmmoInfo());
            }

        } else {
            gunAmmoInfo.setText("0 / 0");
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        player.moveInventoryFrame(g2, player.getHandIndex());
    }
}
