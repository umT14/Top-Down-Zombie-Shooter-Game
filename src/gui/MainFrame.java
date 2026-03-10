package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame {

    private JPanel mainPanel;

    private final static int WIDTH = 1024;
    private final static int HEIGHT = 720;
    private final String title = "Top-Down Shooter Game";
    private BufferedImage iconImage;

    public MainFrame() {
        super();
        setTitle(title);
        setBackground(Color.LIGHT_GRAY);
        setIcon();
        setIconImage(iconImage);
        setSize(WIDTH, HEIGHT);
        setResizable(true);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BorderLayout());
        mainPanel = new OpeningPanel(this);
        add(mainPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }

    public void changeToGamePanel() {
        mainPanel.setVisible(false);
        remove(mainPanel);
        mainPanel = new InGamePanel();
        add(mainPanel);
        mainPanel.setVisible(true);
    }

    public void changeToMainPanel() {
        mainPanel.setVisible(false);
        remove(mainPanel);
        mainPanel = new OpeningPanel(this);
        add(mainPanel);
        mainPanel.setVisible(true);
    }

    public void setIcon() {
        try {
            iconImage = ImageIO.read(new File("src\\assets\\sprite\\gui\\iconTest.png"));
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
