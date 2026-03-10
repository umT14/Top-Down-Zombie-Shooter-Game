package gui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;

import entity.player.Player;
import gui.gamepanel.GamePanel;
import gui.gamepanel.HealthInfoPanel;
import gui.gamepanel.InventoryPanel;
import main.GameSaver;

public class InGamePanel extends JPanel {

    private final Color mainPanelColor = Color.LIGHT_GRAY;
    private Player player;

    public InGamePanel() {

        setBackground(mainPanelColor);
        setLayout(new BorderLayout());
        setFocusable(true);

        player = GameSaver.loadPlayer();
        GameSaver.loadOtherData();

        InventoryPanel invPanel = new InventoryPanel(player);
        HealthInfoPanel healthPanel = new HealthInfoPanel(player);
        GamePanel gamePanel = new GamePanel(player, invPanel);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.setBackground(mainPanelColor);
        topPanel.add(healthPanel, BorderLayout.WEST);
        topPanel.add(invPanel, BorderLayout.EAST);

        add(gamePanel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        gamePanel.setVisible(true);
        topPanel.setVisible(true);

        gamePanel.startGameThread();
    }
}
