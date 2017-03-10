package ultrapoulet.wifeygame.screens;

import java.util.ArrayList;
import java.util.List;

import ultrapoulet.androidgame.framework.Game;
import ultrapoulet.androidgame.framework.Graphics;
import ultrapoulet.androidgame.framework.Input;
import ultrapoulet.androidgame.framework.Input.TouchEvent;
import ultrapoulet.androidgame.framework.Screen;
import ultrapoulet.androidgame.framework.helpers.Button;
import ultrapoulet.androidgame.framework.helpers.ButtonList;
import ultrapoulet.androidgame.framework.helpers.NumberPrinter;
import ultrapoulet.androidgame.framework.helpers.NumberPrinter.Align;
import ultrapoulet.wifeygame.Assets;
import ultrapoulet.wifeygame.battle.BattleCharacter;
import ultrapoulet.wifeygame.battle.BattleInfo;
import ultrapoulet.wifeygame.gamestate.PlayerInfo;

/**
 * Created by John on 1/18/2017.
 */

public class BattleResultScreen extends Screen{

    private static final int RESULT_X = 50;
    private static final int RESULT_Y = 50;
    private static final int EXP_Y = 250;
    private static final int GOLD_Y = 310;
    private static final int CENTER_X = 400;
    private static final int RESULT_OBJECT_WIDTH = 150;
    private static final int TEXT_SPACING = 10;
    private static final int TEXT_WIDTH = 30;
    private static final int TEXT_HEIGHT = 60;
    private static final int TEXT_OFFSET = 0;

    private static final int PARTY_HEIGHT = 160;
    private static final int PARTY_WIDTH = 160;
    private static final int PARTY_SPACING = 10;
    private static final int PARTY_ROW_1_Y = 750;
    private static final int PARTY_ROW_2_Y = 920;

    private static final int BONUS_EXP_OFFSET_Y = 0;
    private static final int BONUS_GOLD_OFFSET_Y = 30;
    private static final int BONUS_TEXT_WIDTH = 15;
    private static final int BONUS_TEXT_HEIGHT = 30;
    private static final int BONUS_TEXT_OFFSET = 0;

    private static final int CONTINUE_LEFT_X = 500;
    private static final int CONTINUE_RIGHT_X = 750;
    private static final int CONTINUE_TOP_Y = 1130;
    private static final int CONTINUE_BOTTOM_Y = 1230;
    private static final String CONTINUE_STRING = "Continue";

    private BattleInfo info;
    private List<BattleCharacter> party;
    private List<BattleCharacter> enemies;
    private ButtonList buttons;
    private Button continueButton;
    private Button pressed;

    private boolean victory;
    //Calculate actual values later
    private int baseExp;
    private int bonusExp;
    private int baseGold;
    private int bonusGold;

    private boolean printed = false;

    private class BonusGains{
        private int gold;
        private int exp;

        protected BonusGains(int gold, int exp){
            this.gold = gold;
            this.exp = exp;
        }

        protected int getGold(){
            return this.gold;
        }

        protected int getExp(){
            return this.exp;
        }
    }
    private List<BonusGains> gains;

    public BattleResultScreen(Game game, BattleInfo info, List<BattleCharacter> party, List<BattleCharacter> enemies){
        super(game);
        this.info = info;
        this.party = party;
        this.enemies = enemies;

        buttons = new ButtonList();
        continueButton = new Button(CONTINUE_LEFT_X, CONTINUE_RIGHT_X, CONTINUE_TOP_Y, CONTINUE_BOTTOM_Y, true, CONTINUE_STRING);
        buttons.addButton(continueButton);

        victory = enemies.get(enemies.size() - 1).getCurrentHP() == 0;

        for(int i = 0; i < enemies.size(); i++){
            BattleCharacter enemy = enemies.get(i);
            double healthPer = (enemy.getMaxHP() - enemy.getCurrentHP()) / (1.0 * enemy.getMaxHP());
            baseGold += (int) (enemy.getGold() * healthPer);
            baseExp += (int) (enemy.getExperience() * healthPer);
        }
        gains = new ArrayList<>();
        for(int i = 0; i < party.size(); i++){
            int gold = party.get(i).getGold();
            int exp = party.get(i).getExperience();
            bonusGold += gold;
            bonusExp += exp;
            gains.add(new BonusGains(gold, exp));
        }
        boolean playerLevelUp  = PlayerInfo.addExperience(baseExp + bonusExp);
        if(playerLevelUp){
            System.out.println("For now, just printing out level up");
        }
        PlayerInfo.addGold(baseGold + bonusGold);
    }

    @Override
    public void update(float deltaTime) {
        List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();
        for(int i = 0; i < touchEvents.size(); i++) {
            TouchEvent t = touchEvents.get(i);
            if(t.type == TouchEvent.TOUCH_DOWN){
                pressed = buttons.getButtonPressed(t.x, t.y);
            }
            else if(t.type == TouchEvent.TOUCH_UP){
                if(pressed != null && pressed == buttons.getButtonPressed(t.x, t.y)){
                    if(pressed == continueButton) {
                        game.setScreen(new BattleSelectScreen(game));
                    }
                }
            }
        }
    }

    @Override
    public void paint(float deltaTime) {
        Graphics g = game.getGraphics();
        g.drawImage(Assets.BattleResultScreen, 0, 0);

        if(victory){
            g.drawImage(Assets.BattleResultVictory, RESULT_X, RESULT_Y);
        }
        else {
            g.drawImage(Assets.BattleResultDefeat, RESULT_X, RESULT_Y);
        }

        int currentX;

        //Imagery for displaying Experience Gained
        int expWidth = RESULT_OBJECT_WIDTH + TEXT_SPACING + TEXT_WIDTH /*For the plus*/;
        int baseExpDigits = Integer.toString(baseExp).length();
        int bonusExpDigits = Integer.toString(bonusExp).length();
        expWidth += baseExpDigits * TEXT_WIDTH;
        if(bonusExp > 0){
            expWidth += TEXT_SPACING + (bonusExpDigits * TEXT_WIDTH);
        }
        currentX = CENTER_X - (expWidth / 2);
        g.drawImage(Assets.BattleResultExp, currentX, EXP_Y);
        currentX += RESULT_OBJECT_WIDTH + TEXT_SPACING;
        g.drawImage(Assets.BluePlus, currentX, EXP_Y);
        currentX += TEXT_WIDTH;
        NumberPrinter.drawNumber(g, baseExp, currentX, EXP_Y, TEXT_WIDTH, TEXT_HEIGHT, TEXT_OFFSET, Assets.BlueNumbers, Align.LEFT);
        if(bonusExp > 0) {
            currentX += baseExpDigits * TEXT_WIDTH + TEXT_SPACING;
            g.drawImage(Assets.BluePlus, currentX, EXP_Y);
            currentX += TEXT_WIDTH;
            NumberPrinter.drawNumber(g, bonusExp, currentX, EXP_Y, TEXT_WIDTH, TEXT_HEIGHT, TEXT_OFFSET, Assets.BlueNumbers, Align.LEFT);
        }

        //Imagery for displaying Gold Gained
        int goldWidth = RESULT_OBJECT_WIDTH + TEXT_SPACING + TEXT_WIDTH /*For the plus*/;
        int baseGoldDigits = Integer.toString(baseGold).length();
        int bonusGoldDigits = Integer.toString(bonusGold).length();
        goldWidth += baseGoldDigits * TEXT_WIDTH;
        if(bonusGold > 0){
            goldWidth += TEXT_SPACING + (bonusGoldDigits * TEXT_WIDTH);
        }
        currentX = CENTER_X - (goldWidth / 2);
        g.drawImage(Assets.BattleResultGold, currentX, GOLD_Y);
        currentX += RESULT_OBJECT_WIDTH + TEXT_SPACING;
        g.drawImage(Assets.YellowPlus, currentX, GOLD_Y);
        currentX += TEXT_WIDTH;
        NumberPrinter.drawNumber(g, baseGold, currentX, GOLD_Y, TEXT_WIDTH, TEXT_HEIGHT, TEXT_OFFSET, Assets.YellowNumbers, Align.LEFT);
        if(bonusGold > 0) {
            currentX += baseGoldDigits * TEXT_WIDTH + TEXT_SPACING;
            g.drawImage(Assets.YellowPlus, currentX, GOLD_Y);
            currentX += TEXT_WIDTH;
            NumberPrinter.drawNumber(g, bonusGold, currentX, GOLD_Y, TEXT_WIDTH, TEXT_HEIGHT, TEXT_OFFSET, Assets.YellowNumbers, Align.LEFT);
        }

        //Draw Party Images
        int topRowSize = party.size() - (party.size() / 2);
        int baseX = CENTER_X - ((PARTY_WIDTH * topRowSize) + (PARTY_SPACING * (topRowSize - 1))) / 2;
        for(int i = 0; i < topRowSize; i++){
            int x = baseX + (PARTY_WIDTH + PARTY_SPACING) * i;
            g.drawScaledImage(party.get(i).getImage(), x, PARTY_ROW_1_Y, PARTY_WIDTH, PARTY_HEIGHT);
            if(gains.get(i).getExp() > 0){
                int exp = gains.get(i).getExp();
                int numDigits = Integer.toString(exp).length();
                int totalWidth = (numDigits + 1) * BONUS_TEXT_WIDTH;
                int plusX = x + ((PARTY_WIDTH - totalWidth) / 2);
                g.drawScaledImage(Assets.BluePlus, plusX, PARTY_ROW_1_Y + BONUS_EXP_OFFSET_Y, BONUS_TEXT_WIDTH, BONUS_TEXT_HEIGHT);
                NumberPrinter.drawNumber(g, exp, plusX + BONUS_TEXT_WIDTH, PARTY_ROW_1_Y + BONUS_EXP_OFFSET_Y, BONUS_TEXT_WIDTH, BONUS_TEXT_HEIGHT, BONUS_TEXT_OFFSET, Assets.BlueNumbers, Align.LEFT);
            }
            if(gains.get(i).getGold() > 0){
                int gold = gains.get(i).getGold();
                int numDigits = Integer.toString(gold).length();
                int totalWidth = (numDigits + 1) * BONUS_TEXT_WIDTH;
                int plusX = x + ((PARTY_WIDTH - totalWidth) / 2);
                g.drawScaledImage(Assets.YellowPlus, plusX, PARTY_ROW_1_Y + BONUS_GOLD_OFFSET_Y, BONUS_TEXT_WIDTH, BONUS_TEXT_HEIGHT);
                NumberPrinter.drawNumber(g, gold, plusX + BONUS_TEXT_WIDTH, PARTY_ROW_1_Y + BONUS_GOLD_OFFSET_Y, BONUS_TEXT_WIDTH, BONUS_TEXT_HEIGHT, BONUS_TEXT_OFFSET, Assets.YellowNumbers, Align.LEFT);
            }
        }
        int botRowSize = party.size() / 2;
        baseX = CENTER_X - ((PARTY_WIDTH * botRowSize) + (PARTY_SPACING * (botRowSize - 1))) / 2;
        for(int i = topRowSize; i < party.size(); i++){
            int x = baseX + (PARTY_WIDTH + PARTY_SPACING) * (i - topRowSize);
            g.drawScaledImage(party.get(i).getImage(), x, PARTY_ROW_2_Y, PARTY_WIDTH, PARTY_HEIGHT);
            if(gains.get(i).getExp() > 0){
                int exp = gains.get(i).getExp();
                int numDigits = Integer.toString(exp).length();
                int totalWidth = (numDigits + 1) * BONUS_TEXT_WIDTH;
                int plusX = x + ((PARTY_WIDTH - totalWidth) / 2);
                g.drawScaledImage(Assets.BluePlus, plusX, PARTY_ROW_2_Y + BONUS_EXP_OFFSET_Y, BONUS_TEXT_WIDTH, BONUS_TEXT_HEIGHT);
                NumberPrinter.drawNumber(g, exp, plusX + BONUS_TEXT_WIDTH, PARTY_ROW_2_Y + BONUS_EXP_OFFSET_Y, BONUS_TEXT_WIDTH, BONUS_TEXT_HEIGHT, BONUS_TEXT_OFFSET, Assets.BlueNumbers, Align.LEFT);
            }
            if(gains.get(i).getGold() > 0){
                int gold = gains.get(i).getGold();
                int numDigits = Integer.toString(gold).length();
                int totalWidth = (numDigits + 1) * BONUS_TEXT_WIDTH;
                int plusX = x + ((PARTY_WIDTH - totalWidth) / 2);
                g.drawScaledImage(Assets.YellowPlus, plusX, PARTY_ROW_2_Y + BONUS_GOLD_OFFSET_Y, BONUS_TEXT_WIDTH, BONUS_TEXT_HEIGHT);
                NumberPrinter.drawNumber(g, gold, plusX + BONUS_TEXT_WIDTH, PARTY_ROW_2_Y + BONUS_GOLD_OFFSET_Y, BONUS_TEXT_WIDTH, BONUS_TEXT_HEIGHT, BONUS_TEXT_OFFSET, Assets.YellowNumbers, Align.LEFT);
            }
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void backButton() {

    }
}
