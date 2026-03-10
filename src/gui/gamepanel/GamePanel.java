package gui.gamepanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.player.Player;
import gamehandlers.CollisionHandler;
import gamehandlers.ItemBuilder;
import gamehandlers.MapBuilder;
import gamehandlers.ProjectileHandler;
import gamehandlers.SpitDrawer;
import gamehandlers.WaveController;
import gamehandlers.ZombieSpawner;
import inputhandlers.GameStateKeyboardHandler;
import main.GameState;
import main.GameThread;

public class GamePanel extends JPanel {

    private Thread mainThread;
    private Player player;

    private MapBuilder mapBuilder;
    private ItemBuilder itemBuilder;
    private ProjectileHandler projectileHandler;
    private ZombieSpawner zombieSpawner;
    private SpitDrawer spitDrawer;
    private WaveController waveController;
    private CollisionHandler collisionHandler;

    private GameStateKeyboardHandler gameStateHandler;

    private InventoryPanel inventoryPanel;
    private GameState gameState;
    private PauseUI pauseUI;

    private final static int tileSize = 48;

    public GamePanel(Player player, InventoryPanel inventoryPanel) {

        setBackground(Color.BLACK);
        setDoubleBuffered(true);

        this.player = player;

        mapBuilder = new MapBuilder(this);
        itemBuilder = new ItemBuilder(this);
        projectileHandler = new ProjectileHandler();
        zombieSpawner = new ZombieSpawner(this, mapBuilder);
        waveController = new WaveController(zombieSpawner, player, inventoryPanel);
        collisionHandler = new CollisionHandler(this, mapBuilder);
        spitDrawer = new SpitDrawer();

        pauseUI = new PauseUI(this);
        gameStateHandler = new GameStateKeyboardHandler(this, pauseUI);

        this.inventoryPanel = inventoryPanel;
        gameState = player.getGameState();

        addKeyListener(player.keyHandler);
        addKeyListener(gameStateHandler);
        addMouseListener(player.mouseInputHandler);

        setFocusable(true);
        setVisible(true);

    }

    public Thread getMainThread() {
        return mainThread;
    }

    public Player getPlayer() {
        return player;
    }

    public Player getPlayerInstance() {
        return new Player(player);
    }

    public WaveController getWaveController() {
        return waveController;
    }

    public CollisionHandler getCollisionHandler() {
        return collisionHandler;
    }

    public InventoryPanel getInventoryPanel() {
        return inventoryPanel;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        player.setGameState(gameState);
        this.gameState = gameState;
    }

    public void startGameThread() {
        mainThread = new Thread(new GameThread(this));
        mainThread.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // ------------------------------------------
        mapBuilder.drawTiles(g2);
        // ------------------------------------------
        zombieSpawner.drawZombies(g2, tileSize);
        // ------------------------------------------
        itemBuilder.spawnItems(g2);
        // ------------------------------------------
        projectileHandler.drawBullets(g2);
        // ------------------------------------------
        spitDrawer.drawPond(g2);
        // ------------------------------------------
        player.drawPlayer(g2, tileSize);
        // ------------------------------------------
        pauseUI.drawPanel(g2);
        // ------------------------------------------

    }

}
