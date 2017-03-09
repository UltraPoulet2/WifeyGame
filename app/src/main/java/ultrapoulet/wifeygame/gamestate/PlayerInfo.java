package ultrapoulet.wifeygame.gamestate;

import ultrapoulet.androidgame.framework.Graphics;
import ultrapoulet.androidgame.framework.helpers.NumberPrinter;
import ultrapoulet.androidgame.framework.helpers.NumberPrinter.Align;
import ultrapoulet.wifeygame.Assets;

/**
 * Created by John on 1/9/2017.
 */

public class PlayerInfo {

    private static int gold = 0;
    private static int level = 999;
    private static int experience;
    private static int currentEnergy = 99;
    private static int maxEnergy = 999;
    //private int(?) timeLeft;

    public static int getGold() {
        return gold;
    }

    public static void addGold(int addedGold){
        gold += addedGold;
        if(gold > 999999999){
            gold = 999999999;
        }
    }

    public static int getLevel() {
        return level;
    }

    public static int getExperience() {
        return experience;
    }

    public static int getLevelPercentage() {
        return 100;
    }

    public static void addExperience(int addedExperience){
        experience += addedExperience;
    }

    public static int getCurrentEnergy() {
        return currentEnergy;
    }

    public static void decrementEnergy(int decrementedEnergy){
        currentEnergy -= decrementedEnergy;
        //Do things with time here.
    }

    public static int getMaxEnergy() {
        return maxEnergy;
    }

    //Objects for printing the holder
    private static final int EXP_BAR_WIDTH = 160;
    private static final int EXP_BAR_HEIGHT = 10;

    public static void drawHeader(Graphics g) {
        g.drawImage(Assets.StatusHolder, 0, 0);
        NumberPrinter.drawNumber(g, gold, 60, 0, 30, 60, 0, Assets.YellowNumbers, Align.LEFT);
        NumberPrinter.drawNumber(g, level, 340, 24, 15, 30, 0, Assets.WhiteNumbers, Align.LEFT);
        NumberPrinter.drawNumber(g, currentEnergy, 635, 0, 20, 40, 0, Assets.WhiteNumbers, Align.RIGHT);
        NumberPrinter.drawNumber(g, maxEnergy, 655, 0, 20, 40, 0, Assets.WhiteNumbers, Align.LEFT);

        g.drawScaledImage(Assets.pHealthG, 390, 35, (int) (EXP_BAR_WIDTH * 0.8), EXP_BAR_HEIGHT);

        if(currentEnergy != maxEnergy) {
            //Minutes
            g.drawImage(Assets.Hourglass, 715, 0);
            NumberPrinter.drawNumber(g, 1, 730, 0, 15, 30, 0, Assets.WhiteNumbers, Align.LEFT);
            g.drawImage(Assets.Colon, 747, 10);
            NumberPrinter.drawNumberPadded(g, 1, 2, 750, 0, 15, 30, 0, Assets.WhiteNumbers, Align.LEFT);
        }
    }
}
