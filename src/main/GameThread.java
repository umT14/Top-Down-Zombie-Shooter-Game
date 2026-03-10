package main;

import gamehandlers.ProjectileHandler;
import gui.gamepanel.GamePanel;

public class GameThread implements Runnable {

    private GamePanel panel;

    private final static double FPS = 60;
    private final static double nanoSec = 1000000000;

    public GameThread(GamePanel panel) {
        this.panel = panel;
    }

    @Override
    public void run() {

        double interval = nanoSec / FPS;
        double nextLoop = System.nanoTime() + interval;

        while (panel.getMainThread() != null) {

            update();

            panel.repaint();
            panel.getInventoryPanel().repaint();

            try {
                double remainingTime = nextLoop - System.nanoTime();
                remainingTime = remainingTime / 1000000;
                if (remainingTime < 0) {
                    remainingTime = 0;
                }
                Thread.sleep((long) (remainingTime));
                nextLoop += interval;
            } catch (InterruptedException e) {
                System.out.println(e);
            }

        }
    }

    public void update() {
        try {

            if (panel.getGameState().equals(GameState.IN_GAME)) {
                ProjectileHandler.increaseBulletCounter();
                panel.requestFocusInWindow();
                panel.getPlayer().playerMove();
                panel.getPlayer().playerShoot();
                panel.getCollisionHandler().bulletCollision();
                panel.getPlayer().checkPlayerInventory();
                panel.getPlayer().playerGrab();
                panel.getPlayer().playerReload();
                panel.getPlayer().playerUseMedical();
                panel.getWaveController().checkZombieAmount();
                panel.getWaveController().givePlayerWeapon();
                panel.getCollisionHandler().checkPlayerToZombieCollision();
            }

        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }

    }

}
