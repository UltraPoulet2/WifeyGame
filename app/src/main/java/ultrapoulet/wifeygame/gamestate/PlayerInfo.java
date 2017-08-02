package ultrapoulet.wifeygame.gamestate;

import android.content.SharedPreferences;

import ultrapoulet.androidgame.framework.Graphics;
import ultrapoulet.androidgame.framework.Input.TouchEvent;
import ultrapoulet.androidgame.framework.helpers.NumberPrinter;
import ultrapoulet.androidgame.framework.helpers.NumberPrinter.Align;
import ultrapoulet.wifeygame.Assets;

/**
 * Created by John on 1/9/2017.
 */

public class PlayerInfo {

    private static int gold = 0;
    private static int level = 1;
    private static int experience;
    private static int currentEnergy = 5;
    private static int maxEnergy = 5;

    private static int nextLevelExp = 100;
    private static final double NEXT_LEVEL_MULT = 1.25;

    //This is the time (in milliseconds) that the player will get their next energy
    private static long nextEnergyTime;
    private static final int SECONDS_PER_ENERGY = 120;
    private static final int MILLISECONDS = 1000;
    private static final int MINUTE = 60;

    private static SharedPreferences prefs;

    public static void init(SharedPreferences inPrefs){
        prefs = inPrefs;
    }


    public static int getGold() {
        return gold;
    }

    public static void addGold(int addedGold){
        gold += addedGold;
        if(gold > 999999999){
            gold = 999999999;
        }
    }

    public static void payGold(int paidGold){
        if(paidGold > gold){
            gold = 0;
            System.out.println("PlayerInfo:payGold(): Spent too much gold.");
        }
        else {
            gold -= paidGold;
        }
    }

    public static int getLevel() {
        return level;
    }

    public static int getExperience() {
        return experience;
    }

    public static double getLevelPercentage() {
        return (1.0 * experience) / nextLevelExp;
    }

    //Returns true if Player Levels-up
    public static boolean addExperience(int addedExperience){
        boolean leveled = false;
        experience += addedExperience;
        while(experience > nextLevelExp){
            leveled = true;
            level++;
            System.out.println("Level " + level + " Next exp: " + nextLevelExp);
            experience -= nextLevelExp;
            nextLevelExp *= NEXT_LEVEL_MULT;
        }
        return leveled;
    }

    public static int getCurrentEnergy() {
        updateTimer();
        return currentEnergy;
    }

    public static void setCurrentEnergy(int energy){
        currentEnergy = energy;
    }

    public static void decrementEnergy(int decrementedEnergy){
        currentEnergy -= decrementedEnergy;
        //Do things with time here.
        if(nextEnergyTime == 0){
            nextEnergyTime = System.currentTimeMillis() + (SECONDS_PER_ENERGY * MILLISECONDS);
        }
        saveEnergy();
    }

    public static int getNextEnergyMinutes(){
        updateTimer();
        int totalSecondsLeft = (int) ((nextEnergyTime - System.currentTimeMillis()) / MILLISECONDS);
        //The plus 1 is to make the range 2:00 - 0:01
        int result = (totalSecondsLeft + 1) / MINUTE;
        return (result >= 0) ? result : 0;
    }

    public static int getNextEnergySeconds(){
        updateTimer();
        int totalSecondsLeft = (int) ((nextEnergyTime - System.currentTimeMillis()) / MILLISECONDS);
        //The plus 1 is to make the range 2:00 - 0:01
        int result =  (totalSecondsLeft + 1) % MINUTE;
        return (result >= 0) ? result : 0;
    }

    public static void setEnergyTimers(long nextEnergy){
        nextEnergyTime = nextEnergy;
        updateTimer();
    }

    //This will update the timer, increase currentEnergy
    private static void updateTimer(){
        while(System.currentTimeMillis() >= nextEnergyTime && currentEnergy < maxEnergy){
            currentEnergy++;
            nextEnergyTime += SECONDS_PER_ENERGY * MILLISECONDS;
            if(currentEnergy == maxEnergy){
                nextEnergyTime = 0;
            }
            saveEnergy();
        }
    }

    private static void saveEnergy() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong("next_energy", nextEnergyTime);
        editor.putInt("current_energy", currentEnergy);
        editor.commit();
    }

    public static int getMaxEnergy() {
        return maxEnergy;
    }

    public static class HeaderBar {
        //Objects for printing the holder
        private static final int EXP_BAR_WIDTH = 160;
        private static final int EXP_BAR_HEIGHT = 10;

        public void draw(Graphics g) {
            g.drawImage(Assets.StatusHolder, 0, 0);
            NumberPrinter.drawNumber(g, PlayerInfo.getGold(), 60, 0, 30, 60, 0, Assets.YellowNumbers, Align.LEFT);
            NumberPrinter.drawNumber(g, PlayerInfo.getLevel(), 340, 24, 15, 30, 0, Assets.WhiteNumbers, Align.LEFT);
            NumberPrinter.drawNumber(g, PlayerInfo.getCurrentEnergy(), 635, 0, 20, 40, 0, Assets.WhiteNumbers, Align.RIGHT);
            NumberPrinter.drawNumber(g, PlayerInfo.getMaxEnergy(), 655, 0, 20, 40, 0, Assets.WhiteNumbers, Align.LEFT);

            g.drawScaledImage(Assets.pHealthG, 390, 35, (int) (EXP_BAR_WIDTH * PlayerInfo.getLevelPercentage()), EXP_BAR_HEIGHT);

            if (PlayerInfo.getCurrentEnergy() != PlayerInfo.getMaxEnergy()) {
                //Minutes
                g.drawImage(Assets.Hourglass, 715, 0);
                NumberPrinter.drawNumber(g, PlayerInfo.getNextEnergyMinutes(), 730, 0, 15, 30, 0, Assets.WhiteNumbers, Align.LEFT);
                g.drawImage(Assets.Colon, 747, 10);
                NumberPrinter.drawNumberPadded(g, PlayerInfo.getNextEnergySeconds(), 2, 750, 0, 15, 30, 0, Assets.WhiteNumbers, Align.LEFT);
            }
        }

        public void update(TouchEvent t){
            //For now, nothing.
        }
    }
}
