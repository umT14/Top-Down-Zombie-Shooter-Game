package inputhandlers;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;

import entity.player.Player;

public class MouseInputHandler implements MouseListener, Serializable {

    boolean rightClick, leftClick;
    private Point point = null;

    public MouseInputHandler(Player player) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int b = e.getButton();
        System.out.println("here");
        if (b == MouseEvent.BUTTON1) {
            point = e.getPoint();
            leftClick = true;

        } else if (b == MouseEvent.BUTTON3) {
            rightClick = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int b = e.getButton();

        if (b == MouseEvent.BUTTON1) {
            point = e.getPoint();
            leftClick = false;

        } else if (b == MouseEvent.BUTTON3) {
            rightClick = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public Point getPoint() {
        return point;
    }

    public boolean getLeftButton() {
        return leftClick;
    }

}
