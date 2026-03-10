package inputhandlers.buttonhandler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import gui.MainFrame;

public class GameStartListener implements ActionListener {
    JFrame frame;

    public GameStartListener(JFrame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (frame.getClass() == MainFrame.class) {
            MainFrame f = (MainFrame) frame;
            f.changeToGamePanel();
        }
    }
}
