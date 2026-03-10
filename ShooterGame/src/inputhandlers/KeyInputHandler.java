package inputhandlers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Serializable;

public class KeyInputHandler implements KeyListener, Serializable {

    private boolean north, south, east, west, grab, one, two, three, four, five, health, reload;
    int i = 0;

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        playerMovementPressed(code);
        gunChangePressed(code);

        if (code == KeyEvent.VK_G) {
            grab = true;
        }

        if (code == KeyEvent.VK_H) {
            health = true;
        }

        if (code == KeyEvent.VK_R) {
            reload = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        playerMovementReleased(code);
        gunChangeRemoved(code);

        if (code == KeyEvent.VK_G) {
            grab = false;
        }
        if (code == KeyEvent.VK_H) {
            health = false;
        }
        if (code == KeyEvent.VK_R) {
            reload = false;
        }
    }

    public void playerMovementPressed(int code) {
        if (code == KeyEvent.VK_W) {
            north = true;
        }
        if (code == KeyEvent.VK_A) {
            west = true;
        }
        if (code == KeyEvent.VK_S) {
            south = true;
        }
        if (code == KeyEvent.VK_D) {
            east = true;
        }
    }

    public void playerMovementReleased(int code) {
        if (code == KeyEvent.VK_W) {
            north = false;
        }
        if (code == KeyEvent.VK_A) {
            west = false;
        }
        if (code == KeyEvent.VK_S) {
            south = false;
        }
        if (code == KeyEvent.VK_D) {
            east = false;
        }
    }

    public void gunChangePressed(int code) {
        if (code == KeyEvent.VK_1) {
            one = true;
        }
        if (code == KeyEvent.VK_2) {
            two = true;
        }
        if (code == KeyEvent.VK_3) {
            three = true;
        }
        if (code == KeyEvent.VK_4) {
            four = true;
        }
        if (code == KeyEvent.VK_5) {
            five = true;
        }
    }

    public void gunChangeRemoved(int code) {
        if (code == KeyEvent.VK_1) {
            one = false;
        }
        if (code == KeyEvent.VK_2) {
            two = false;
        }
        if (code == KeyEvent.VK_3) {
            three = false;
        }
        if (code == KeyEvent.VK_4) {
            four = false;
        }
        if (code == KeyEvent.VK_5) {
            five = false;
        }
    }

    public boolean getNorth() {
        return north;
    }

    public boolean getWest() {
        return west;
    }

    public boolean getSouth() {
        return south;
    }

    public boolean getEast() {
        return east;
    }

    public boolean getGrapInfo() {
        return grab;
    }

    public boolean getOne() {
        return one;
    }

    public boolean getTwo() {
        return two;
    }

    public boolean getThree() {
        return three;
    }

    public boolean getFour() {
        return four;
    }

    public boolean getFive() {
        return five;
    }

    public boolean getRegenInfo() {
        return health;
    }

    public boolean getReloadInfo() {
        return reload;
    }

    public void setRegenInfo(boolean info) {
        health = info;
    }
}
