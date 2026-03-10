package inputhandlers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import gamehandlers.WaveController;
import gui.gamepanel.GamePanel;
import gui.gamepanel.PauseUI;
import main.GameSaver;
import main.GameState;

public class GameStateKeyboardHandler implements KeyListener {

    private GamePanel panel;
    private PauseUI pauseScreen;

    public GameStateKeyboardHandler(GamePanel panel, PauseUI pauseScreen) {
        this.panel = panel;
        this.pauseScreen = pauseScreen;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_ESCAPE) {
            System.out.println("gamestate");
            if (panel.getGameState().equals(GameState.IN_GAME)) {
                panel.setGameState(GameState.PAUSED);
            } else if (panel.getGameState().equals(GameState.PAUSED)) {
                panel.setGameState(GameState.IN_GAME);
            }
        }
        if (panel.getGameState().equals(GameState.PAUSED)) {
            if (code == KeyEvent.VK_W) {
                pauseScreen.setButtonIndex(0);
            }
            if (code == KeyEvent.VK_S) {
                pauseScreen.setButtonIndex(1);
            }

            if (code == KeyEvent.VK_ENTER) {
                if (pauseScreen.getButtonIndex() == 0) {
                    panel.setGameState(GameState.IN_GAME);
                }
                if (pauseScreen.getButtonIndex() == 1) {
                    panel.getPlayer().setPoints(WaveController.getWaveStartPoints());
                    GameSaver.savePlayer(panel.getPlayer());
                    GameSaver.saveOtherData(panel.getPlayer());
                    System.exit(0);
                }
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}
