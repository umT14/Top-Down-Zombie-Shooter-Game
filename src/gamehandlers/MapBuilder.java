package gamehandlers;

import java.awt.Graphics2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

import gui.gamepanel.GamePanel;

public class MapBuilder {
    private static Tile[] tileTypes;
    private static int[][] mapMatrix;

    private static final int tS = 48;
    private static final int mapCol = 21;
    private static final int mapRow = 32;

    private File mapPath;
    private GamePanel panel;

    private boolean cond1;
    private boolean cond2;
    private boolean cond3;
    private boolean cond4;

    public MapBuilder(GamePanel panel) {
        this.panel = panel;
        mapPath = new File("src\\dataholder\\worldMap.txt");
        tileTypes = new Tile[13];
        mapMatrix = new int[mapCol][mapRow];
        fillMatrix();
        setTiles();
    }

    public void setTiles() {

        try {
            tileTypes[0] = new Tile();
            tileTypes[0].image = ImageIO.read(new File("src\\assets\\sprite\\tiles\\green_stone.png"));

            tileTypes[1] = new Tile(true);
            tileTypes[1].image = ImageIO.read(new File("src\\assets\\sprite\\tiles\\stone_wall_intersect_4.png"));

            tileTypes[2] = new Tile(true);
            tileTypes[2].image = ImageIO.read(new File("src\\assets\\sprite\\tiles\\stone_wall_2.png"));

            tileTypes[3] = new Tile(true);
            tileTypes[3].image = ImageIO.read(new File("src\\assets\\sprite\\tiles\\stone_wall_middle_1.png"));

            tileTypes[4] = new Tile(true);
            tileTypes[4].image = ImageIO.read(new File("src\\assets\\sprite\\tiles\\stone_wall_middle_2.png"));

            tileTypes[5] = new Tile(true);
            tileTypes[5].image = ImageIO.read(new File("src\\assets\\sprite\\tiles\\stone_wall_1.png"));

            tileTypes[6] = new Tile(true);
            tileTypes[6].image = ImageIO.read(new File("src\\assets\\sprite\\tiles\\stone_wall_middle_4.png"));

            tileTypes[7] = new Tile(true);
            tileTypes[7].image = ImageIO.read(new File("src\\assets\\sprite\\tiles\\stone_wall_4.png"));

            tileTypes[8] = new Tile(true);
            tileTypes[8].image = ImageIO.read(new File("src\\assets\\sprite\\tiles\\stone_wall_middle_3.png"));

            tileTypes[9] = new Tile(true);
            tileTypes[9].image = ImageIO.read(new File("src\\assets\\sprite\\tiles\\stone_wall_intersect_1.png"));

            tileTypes[10] = new Tile(true);
            tileTypes[10].image = ImageIO.read(new File("src\\assets\\sprite\\tiles\\stone_wall_intersect_2.png"));

            tileTypes[11] = new Tile(true);
            tileTypes[11].image = ImageIO.read(new File("src\\assets\\sprite\\tiles\\stone_wall_intersect_3.png"));

            tileTypes[12] = new Tile(true);
            tileTypes[12].image = ImageIO.read(new File("src\\assets\\sprite\\tiles\\stone_wall_3.png"));

        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    public void drawTiles(Graphics2D g2) { // 520, 400 / 13, 10 / 20...

        for (int i = 0; i < 21; i++) {

            for (int j = 0; j < 32; j++) {

                int tileWorldX = (j * tS);
                int tileWorldY = (i * tS);

                int tileScreenX = tileWorldX - panel.getPlayer().getWPosX()
                        + panel.getPlayer().getSPosX();
                int tileScreenY = tileWorldY - panel.getPlayer().getWPosY()
                        + panel.getPlayer().getSPosY();

                cond1 = tileWorldX + tS > panel.getPlayer().getWPosX()
                        - panel.getPlayer().getSPosX();
                cond2 = tileWorldX - tS < panel.getPlayer().getWPosX()
                        + panel.getPlayer().getSPosX();
                cond3 = tileWorldY + tS > panel.getPlayer().getWPosY()
                        - panel.getPlayer().getSPosY();
                cond4 = tileWorldY - tS < panel.getPlayer().getWPosY()
                        + panel.getPlayer().getSPosY();

                int num = mapMatrix[i][j];

                if (cond1 && cond2 && cond3 && cond4) {
                    g2.drawImage(tileTypes[num].image, tileScreenX, tileScreenY, tS, tS, null);
                }
            }
        }

    }

    public void fillMatrix() {
        Scanner columnReader = null;
        Scanner lineReader = null;
        try {
            columnReader = new Scanner(new FileInputStream(mapPath));

            for (int i = 0; i < mapCol; i++) {

                String line = columnReader.nextLine();
                lineReader = new Scanner(line);

                for (int j = 0; j < mapRow; j++) {
                    mapMatrix[i][j] = Integer.parseInt(lineReader.next());
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            columnReader.close();
            lineReader.close();
        }

    }

    public static int getMapCol() {
        return mapCol;
    }

    public static int getMapRow() {
        return mapRow;
    }

    public static int getTileSize() {
        return tS;
    }

    public static Tile getTile(int mapCol, int mapRow) {
        return tileTypes[mapMatrix[mapRow][mapCol]];
    }

}
