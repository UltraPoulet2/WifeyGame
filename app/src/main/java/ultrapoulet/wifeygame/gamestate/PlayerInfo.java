package ultrapoulet.wifeygame.gamestate;

import android.content.SharedPreferences;
import android.util.Log;

import ultrapoulet.androidgame.framework.Graphics;
import ultrapoulet.androidgame.framework.Input.TouchEvent;
import ultrapoulet.androidgame.framework.helpers.Button;
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
    private static int currentEnergy = 10;
    private static int maxEnergy = 10;

    private static int nextLevelExp = 1000;
    private static final int NEXT_LEVEL_RATE = 1000;

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
            Log.e("PlayerInfo", "Gold became negative. Setting to 0.");
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

    public static int getNextLevelExp() {
        return nextLevelExp;
    }

    public static double getLevelPercentage() {
        return (1.0 * experience) / nextLevelExp;
    }

    //Returns true if Player Levels-up
    public static boolean addExperience(int addedExperience){
        boolean leveled = false;
        experience += addedExperience;
        while(experience >= nextLevelExp){
            leveled = true;
            level++;
            experience -= nextLevelExp;
            //I've got math for this as well
            nextLevelExp = (NEXT_LEVEL_RATE / 2 * level * level) + ((NEXT_LEVEL_RATE / 2) * level);
            Log.i("PlayerInfo", "Player Level Up. Level " + level + " Next exp: " + nextLevelExp);

            //Increase max energy on level up
            maxEnergy++;
            //Refill energy
            currentEnergy = maxEnergy;
            nextEnergyTime = 0;
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

    public static void setLevel(int newLevel) {
        level = newLevel;
        nextLevelExp = (NEXT_LEVEL_RATE / 2 * level * level) + ((NEXT_LEVEL_RATE / 2) * level);
        //10 at level 1, 11 at level 2, etc.
        maxEnergy = level + 9;
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
        editor.apply();
    }

    public static int getMaxEnergy() {
        return maxEnergy;
    }

    public static class HeaderBar {
        //Objects for printing the holder
        private static final int EXP_BAR_WIDTH = 160;
        private static final int EXP_BAR_HEIGHT = 10;

        private static final int EXP_BUTTON_LEFT_X = 338;
        private static final int EXP_BUTTON_RIGHT_X = 554;
        private static final int EXP_BUTTON_TOP_Y = 0;
        private static final int EXP_BUTTON_BOT_Y = 60;
        private static final String EXP_STRING = "EXP";
        private Button expButton;

        private boolean displayExp = false;
        private boolean buttonPressed = false;

        private static final int EXP_CENTER = EXP_BUTTON_RIGHT_X - ((EXP_BUTTON_RIGHT_X - EXP_BUTTON_LEFT_X) / 2);
        private static final int EXP_CENTER_MIN_SIZE = Assets.NextLevel.getWidth();
        private static final int EXP_DIALOG_Y = 0;
        private static final int EXP_DIALOG_HEIGHT = Assets.NextLevelDialogCenter.getHeight();
        private static final int EXP_DIALOG_SIDE_WIDTH = Assets.NextLevelDialogLeft.getWidth();
        private static final int NEXT_LEVEL_X_OFFSET = Assets.NextLevel.getWidth() / 2;
        private static final int NEXT_LEVEL_Y_OFFSET = 19;
        private static final int EXP_WIDTH = 30;
        private static final int EXP_HEIGHT = 60;
        private static final int EXP_SPACING = 0;
        private static final int EXP_SLASH = EXP_CENTER - 15;
        private static final int EXP_LEFT_NUMBER = EXP_SLASH;
        private static final int EXP_RIGHT_NUMBER = EXP_SLASH + 15;
        private static final int EXP_OFFSET_Y = 70;

        private int centerWidth;

        public HeaderBar(){
            expButton = new Button(EXP_BUTTON_LEFT_X, EXP_BUTTON_RIGHT_X, EXP_BUTTON_TOP_Y, EXP_BUTTON_BOT_Y, true, EXP_STRING);

            int nextLevelLength = String.valueOf(PlayerInfo.getNextLevelExp()).length();
            int numberWidth = ((nextLevelLength * 2) + 1) * EXP_WIDTH;
            centerWidth = (numberWidth > EXP_CENTER_MIN_SIZE) ? numberWidth : EXP_CENTER_MIN_SIZE;
        }

        public void draw(Graphics g) {
            g.drawImage(Assets.StatusHolder, 0, 0);
            NumberPrinter.drawNumber(g, PlayerInfo.getGold(), 60, 0, 30, 60, 0, Assets.YellowNumbers, Align.LEFT);
            NumberPrinter.drawNumber(g, PlayerInfo.getLevel(), 340, 24, 15, 30, 0, Assets.WhiteNumbers, Align.LEFT);
            NumberPrinter.drawNumber(g, PlayerInfo.getCurrentEnergy(), 635, 0, 20, 40, 0, Assets.WhiteNumbers, Align.RIGHT);
            NumberPrinter.drawNumber(g, PlayerInfo.getMaxEnergy(), 655, 0, 20, 40, 0, Assets.WhiteNumbers, Align.LEFT);

            g.drawScaledImage(Assets.SmallGreenBar, 390, 35, (int) (EXP_BAR_WIDTH * PlayerInfo.getLevelPercentage()), EXP_BAR_HEIGHT);

            if (PlayerInfo.getCurrentEnergy() != PlayerInfo.getMaxEnergy()) {
                //Minutes
                g.drawImage(Assets.Hourglass, 715, 0);
                NumberPrinter.drawNumber(g, PlayerInfo.getNextEnergyMinutes(), 730, 0, 15, 30, 0, Assets.YellowNumbers, Align.LEFT);
                //Seconds
                g.drawImage(Assets.Colon, 747, 10);
                NumberPrinter.drawNumberPadded(g, PlayerInfo.getNextEnergySeconds(), 2, 750, 0, 15, 30, 0, Assets.YellowNumbers, Align.LEFT);
            }

            if(displayExp){
                int centerLeftX = EXP_CENTER - (centerWidth/2);
                g.drawScaledImage(Assets.NextLevelDialogCenter, centerLeftX, EXP_DIALOG_Y, centerWidth, EXP_DIALOG_HEIGHT);
                g.drawImage(Assets.NextLevelDialogLeft, centerLeftX - EXP_DIALOG_SIDE_WIDTH, EXP_DIALOG_Y);
                g.drawImage(Assets.NextLevelDialogRight, centerLeftX + centerWidth, EXP_DIALOG_Y);
                g.drawImage(Assets.NextLevel, EXP_CENTER - NEXT_LEVEL_X_OFFSET, EXP_DIALOG_Y + NEXT_LEVEL_Y_OFFSET);
                g.drawScaledImage(Assets.HPSlash, EXP_SLASH, EXP_OFFSET_Y, EXP_WIDTH, EXP_HEIGHT);
                NumberPrinter.drawNumber(g, PlayerInfo.getExperience(), EXP_LEFT_NUMBER, EXP_OFFSET_Y, EXP_WIDTH, EXP_HEIGHT, EXP_SPACING, Assets.WhiteNumbers, Align.RIGHT);
                NumberPrinter.drawNumber(g, PlayerInfo.getNextLevelExp(), EXP_RIGHT_NUMBER, EXP_OFFSET_Y, EXP_WIDTH, EXP_HEIGHT, EXP_SPACING, Assets.WhiteNumbers, Align.LEFT);
            }
        }

        public void update(TouchEvent t){
            //For now, nothing.
            if (t.type == TouchEvent.TOUCH_DOWN) {
                buttonPressed = expButton.isPressed(t.x, t.y);
            }
            else if(t.type == TouchEvent.TOUCH_UP){
                displayExp = buttonPressed && expButton.isPressed(t.x, t.y) && !displayExp;
            }
        }
    }
}
