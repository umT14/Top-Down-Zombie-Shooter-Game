package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import inputhandlers.buttonhandler.GameStartListener;

public class OpeningPanel extends JPanel {

    private JPanel leftPanel;

    private final static int buttonWidth = 70;
    private final static int buttonHeight = 50;
    private final Color panelColor = Color.DARK_GRAY;
    private final Color buttonColor = Color.LIGHT_GRAY;

    public OpeningPanel(JFrame frame) {

        setBackground(panelColor);

        leftPanel = new JPanel();
        leftPanel.setLayout(new FlowLayout());

        JButton button = new JButton();
        button.setName("Start");
        button.addActionListener(new GameStartListener(frame));
        button.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        button.setBackground(buttonColor);

        leftPanel.add(button);

        setLayout(new BorderLayout());
        add(leftPanel, BorderLayout.WEST);

        leftPanel.setVisible(true);
        setVisible(true);
    }
}
