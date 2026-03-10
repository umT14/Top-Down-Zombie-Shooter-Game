package gui.gamepanel;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GameSaver;
import main.GameState;

public class PauseUI {

    private GamePanel panel;

    private Rectangle shape;
    private Rectangle button1;
    private Rectangle button2;

    private int index;
    private Image selection;

    public PauseUI(GamePanel panel) {
        this.panel = panel;
        index = 0;
        shape = new Rectangle(128, 148, 768, 360);
        button1 = new Rectangle(448, 341, 128, 64);
        button2 = new Rectangle(448, 418, 128, 64);
        setSelectionImage();
    }

    public void drawPanel(Graphics2D g2) {
        if (panel.getGameState().equals(GameState.PAUSED)) {

            GameSaver.setTotalPoints(panel.getPlayer().getPoints());

            g2.setColor(Color.DARK_GRAY);
            g2.fill(shape);

            g2.setColor(Color.LIGHT_GRAY);

            g2.drawString("Total Score : " + GameSaver.getTotalPoints(), 448, 238);

            g2.fill(button1);
            g2.fill(button2);

            g2.drawImage(selection, 448, 341 + (index * 77), null);
        }
    }

    public void setButtonIndex(int index) {
        this.index = index;
    }

    public int getButtonIndex() {
        return index;
    }

    public void setSelectionImage() {
        try {
            selection = ImageIO.read(new File("src\\assets\\sprite\\gui\\inventory_icons\\frame.png"));
            selection = selection.getScaledInstance(128, 64, Image.SCALE_FAST);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
