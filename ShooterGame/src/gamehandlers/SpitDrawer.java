package gamehandlers;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import entity.projectile.SpitPond;

public class SpitDrawer {
    private static List<SpitPond> ponds;
    private static List<SpitPond> drainedPonds;

    public SpitDrawer() {
        ponds = new ArrayList<>();
        drainedPonds = new ArrayList<>();
    }

    public void drawPond(Graphics2D g2) {
        if (!ponds.isEmpty()) {
            for (SpitPond pond : ponds) {
                pond.drawSpit(g2);
            }
            removePond();
        }
    }

    public void removePond() {
        if (!drainedPonds.isEmpty()) {
            for (SpitPond pond : drainedPonds) {
                ponds.remove(pond);
            }
            drainedPonds.clear();
        }
    }

    public static List<SpitPond> getSpitList() {
        return ponds;
    }

    public static void addDrainedPond(SpitPond drainedPond) {
        drainedPonds.add(drainedPond);
    }
}
