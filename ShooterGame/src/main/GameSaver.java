package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

import entity.player.Player;
import gamehandlers.WaveController;

public class GameSaver {

    private static File playerFile = new File("src\\dataholder\\player.txt");
    private static File wavescoreFile = new File("src\\dataholder\\other.txt");

    private static ObjectInputStream ois = null;
    private static ObjectOutputStream oos = null;

    private static PrintWriter pw = null;
    private static Scanner scanner = null;

    private static String totalPoints;

    public GameSaver() {
        playerFile = new File("src\\dataholder\\player.txt");
        ois = null;
    }

    public static Player loadPlayer() {
        try {
            if (playerFile.length() == 0) {
                return new Player(250, 200);
            } else {
                System.out.println("LOADED");
                ois = new ObjectInputStream(new FileInputStream(playerFile));

                Player player = (Player) ois.readObject();

                player.setGameState(GameState.IN_GAME);
                return player;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new Player(250, 200);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return new Player(250, 200);
        }
    }

    public static void savePlayer(Player player) {
        try {
            oos = new ObjectOutputStream(new FileOutputStream(playerFile));
            oos.writeObject(player);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveOtherData(Player player) {
        try {
            scanner = new Scanner(new FileInputStream(wavescoreFile));

            String wave = "" + (WaveController.getWaveNumber() - 1);
            String score = "" + player.getPoints();

            String waveString = scanner.nextLine().substring(12, 22 - wave.length()) + wave;
            String scoreString = scanner.nextLine().substring(12, 22);

            if (player.getPoints() > Integer.parseInt(scoreString)) {
                scoreString = scoreString.substring(0, 10 - score.length()) + score;
            }
            pw = new PrintWriter(new FileOutputStream(wavescoreFile));

            pw.write("wavenumber: " + waveString + "\ntotalscore: " + scoreString);
            pw.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadOtherData() {
        try {
            scanner = new Scanner(new FileInputStream(wavescoreFile));

            String waveString = scanner.nextLine().substring(12, 22);
            totalPoints = scanner.nextLine().substring(12, 22);

            WaveController.setWaveNumber(Integer.parseInt(waveString));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getTotalPoints() {
        return totalPoints;
    }

    public static void setTotalPoints(int points) {
        if (points > Integer.parseInt(totalPoints)) {
            String pts = "" + points;
            totalPoints = totalPoints.substring(0, 10 - pts.length()) + pts;
        }
    }

    public static void emptyFile() {
        try {
            pw = new PrintWriter(new FileOutputStream(playerFile));
            pw.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void resetWave(Player player) {
        try {

            scanner = new Scanner(new FileInputStream(wavescoreFile));

            String score = "" + player.getPoints();
            scanner.nextLine();
            String scoreString = scanner.nextLine().substring(12, 22);

            if (player.getPoints() > Integer.parseInt(scoreString)) {
                scoreString = scoreString.substring(0, 10 - score.length()) + score;
            }
            pw = new PrintWriter(new FileOutputStream(wavescoreFile));

            pw.write("wavenumber: " + "0000000000" + "\ntotalscore: " + scoreString);
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
