package ultrapoulet.wifeygame.screens;

import java.util.ArrayList;
import java.util.List;

import ultrapoulet.androidgame.framework.Game;
import ultrapoulet.androidgame.framework.Graphics;
import ultrapoulet.androidgame.framework.Image;
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
import ultrapoulet.wifeygame.character.WifeyCharacter;
import ultrapoulet.wifeygame.gamestate.Party;
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
    private static final int LEVEL_WIDTH = 60;

    private static final int DROP_BASE_X = 90;
    private static final int DROP_BASE_Y = 425;
    private static final int DROP_WIDTH = 120;
    private static final int DROP_HEIGHT = 120;
    private static final int DROP_OFFSET = 5;

    private static final int PARTY_HEIGHT = 160;
    private static final int PARTY_WIDTH = 160;
    private static final int PARTY_SPACING = 10;
    private static final int PARTY_ROW_1_Y = 738;
    private static final int PARTY_ROW_2_Y = 932;
    private static final int PARTY_HOLDER_OFFSET_X = -5;
    private static final int PARTY_HOLDER_OFFSET_Y = -2;
    private static final int PARTY_EXP_OFFSET_Y = 162;
    private static final int EXP_BAR_MAX_WIDTH = 195;
    private static final int EXP_BAR_HEIGHT = 20;

    private static final int BONUS_EXP_OFFSET_Y = 0;
    private static final int BONUS_GOLD_OFFSET_Y = 30;
    private static final int BONUS_TEXT_WIDTH = 15;
    private static final int BONUS_TEXT_HEIGHT = 30;
    private static final int BONUS_TEXT_OFFSET = 0;
    private static final int WIFEY_LEVEL_OFFSET_X = 50;
    private static final int WIFEY_LEVEL_OFFSET_Y = 100;

    private static final int CONTINUE_LEFT_X = 500;
    private static final int CONTINUE_RIGHT_X = 750;
    private static final int CONTINUE_TOP_Y = 1130;
    private static final int CONTINUE_BOTTOM_Y = 1230;
    private static final String CONTINUE_STRING = "Continue";

    private static final int DROPS_PER_ROW = 5;

    private BattleInfo info;
    private List<BattleCharacter> party;
    private List<BattleCharacter> enemies;
    private ButtonList buttons;
    private Button continueButton;
    private Button pressed;

    private boolean victory;

    private int baseExp;
    private int bonusExp;
    private int baseGold;
    private int bonusGold;

    private int bonusRecruit;

    private boolean printed = false;

    private boolean playerLevelUp = false;
    private boolean[] wifeyLevelUp = new boolean[7];

    private ArrayList<Image> drops = new ArrayList<>();

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
            bonusRecruit += party.get(i).getBonusRecruiting();
            gains.add(new BonusGains(gold, exp));
        }
        playerLevelUp  = PlayerInfo.addExperience(baseExp + bonusExp);
        List<WifeyCharacter> wifeyList = Party.getCurrentParty(party.size());
        for(int i = 0; i < party.size(); i++){
            //This will be replaced with a boolean list later
            wifeyLevelUp[i] = wifeyList.get(i).addExperience(baseExp + bonusExp);
        }
        PlayerInfo.addGold(baseGold + bonusGold);

        if(victory){
            info.incrementNumComplete();
            ArrayList<WifeyCharacter> droppedWifeys = info.performDrops(bonusRecruit);
            Graphics g = game.getGraphics();
            for(WifeyCharacter wifey : droppedWifeys){
                drops.add(wifey.getImage(g));
            }
        }
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
        if(playerLevelUp){
            expWidth += TEXT_SPACING + LEVEL_WIDTH;
        }
        currentX = CENTER_X - (expWidth / 2);
        g.drawImage(Assets.BattleResultExp, currentX, EXP_Y);
        currentX += RESULT_OBJECT_WIDTH + TEXT_SPACING;
        g.drawImage(Assets.BluePlus, currentX, EXP_Y);
        currentX += TEXT_WIDTH;
        NumberPrinter.drawNumber(g, baseExp, currentX, EXP_Y, TEXT_WIDTH, TEXT_HEIGHT, TEXT_OFFSET, Assets.BlueNumbers, Align.LEFT);
        currentX += baseExpDigits * TEXT_WIDTH + TEXT_SPACING;
        if(bonusExp > 0) {
            g.drawImage(Assets.BluePlus, currentX, EXP_Y);
            currentX += TEXT_WIDTH;
            NumberPrinter.drawNumber(g, bonusExp, currentX, EXP_Y, TEXT_WIDTH, TEXT_HEIGHT, TEXT_OFFSET, Assets.BlueNumbers, Align.LEFT);
            currentX += bonusExpDigits * TEXT_WIDTH + TEXT_SPACING;
        }
        if(playerLevelUp){
            g.drawImage(Assets.LevelUp, currentX, EXP_Y);
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

        //Draw drops
        for(int i = 0; i < drops.size(); i++){
            g.drawScaledImage(drops.get(i), DROP_BASE_X + ((DROP_WIDTH + DROP_OFFSET) * (i % DROPS_PER_ROW)), DROP_BASE_Y + ((DROP_HEIGHT + DROP_OFFSET) * (i / DROPS_PER_ROW)), DROP_WIDTH, DROP_HEIGHT);
        }

        //Draw Party Images
        int topRowSize = party.size() - (party.size() / 2);
        int baseX = CENTER_X - ((PARTY_WIDTH * topRowSize) + (PARTY_SPACING * (topRowSize - 1))) / 2;
        for(int i = 0; i < topRowSize; i++){
            int x = baseX + (PARTY_WIDTH + PARTY_SPACING) * i;
            drawPartyMember(g, i, x, PARTY_ROW_1_Y);
        }
        int botRowSize = party.size() / 2;
        baseX = CENTER_X - ((PARTY_WIDTH * botRowSize) + (PARTY_SPACING * (botRowSize - 1))) / 2;
        for(int i = topRowSize; i < party.size(); i++){
            int x = baseX + (PARTY_WIDTH + PARTY_SPACING) * (i - topRowSize);
            drawPartyMember(g, i, x, PARTY_ROW_2_Y);
        }
    }

    private void drawPartyMember(Graphics g, int partyMember, int baseX, int baseY){
        List<WifeyCharacter> wifeyList = Party.getCurrentParty(party.size());
        g.drawScaledImage(party.get(partyMember).getImage(), baseX, baseY, PARTY_WIDTH, PARTY_HEIGHT);
        g.drawImage(Assets.BattleResultCharHolder, baseX + PARTY_HOLDER_OFFSET_X, baseY + PARTY_HOLDER_OFFSET_Y);
        if(wifeyLevelUp[partyMember]){
            int wifeyExpWidth = (int) (EXP_BAR_MAX_WIDTH * wifeyList.get(partyMember).getExperiencePercent());
            g.drawScaledImage(Assets.SmallYellowBar, baseX, baseY + PARTY_EXP_OFFSET_Y, wifeyExpWidth, EXP_BAR_HEIGHT);
        }
        else{
            int originalExp = wifeyList.get(partyMember).getExp() - (baseExp + bonusExp);
            int nextExp = wifeyList.get(partyMember).getNextLevelExp();
            int originalExpWidth = (int) (EXP_BAR_MAX_WIDTH * (1.0 * originalExp / nextExp));
            int gainedExpWidth = (int) (EXP_BAR_MAX_WIDTH * (1.0 * (baseExp + bonusExp) / nextExp));
            g.drawScaledImage(Assets.SmallGreenBar, baseX, baseY + PARTY_EXP_OFFSET_Y, originalExpWidth, EXP_BAR_HEIGHT);
            g.drawScaledImage(Assets.SmallYellowBar, baseX + originalExpWidth, baseY + PARTY_EXP_OFFSET_Y, gainedExpWidth, EXP_BAR_HEIGHT);
        }
        if(gains.get(partyMember).getExp() > 0){
            int exp = gains.get(partyMember).getExp();
            int numDigits = Integer.toString(exp).length();
            int totalWidth = (numDigits + 1) * BONUS_TEXT_WIDTH;
            int plusX = baseX + ((PARTY_WIDTH - totalWidth) / 2);
            g.drawScaledImage(Assets.BluePlus, plusX, baseY + BONUS_EXP_OFFSET_Y, BONUS_TEXT_WIDTH, BONUS_TEXT_HEIGHT);
            NumberPrinter.drawNumber(g, exp, plusX + BONUS_TEXT_WIDTH, baseY + BONUS_EXP_OFFSET_Y, BONUS_TEXT_WIDTH, BONUS_TEXT_HEIGHT, BONUS_TEXT_OFFSET, Assets.BlueNumbers, Align.LEFT);
        }
        if(gains.get(partyMember).getGold() > 0){
            int gold = gains.get(partyMember).getGold();
            int numDigits = Integer.toString(gold).length();
            int totalWidth = (numDigits + 1) * BONUS_TEXT_WIDTH;
            int plusX = baseX + ((PARTY_WIDTH - totalWidth) / 2);
            g.drawScaledImage(Assets.YellowPlus, plusX, baseY + BONUS_GOLD_OFFSET_Y, BONUS_TEXT_WIDTH, BONUS_TEXT_HEIGHT);
            NumberPrinter.drawNumber(g, gold, plusX + BONUS_TEXT_WIDTH, baseY + BONUS_GOLD_OFFSET_Y, BONUS_TEXT_WIDTH, BONUS_TEXT_HEIGHT, BONUS_TEXT_OFFSET, Assets.YellowNumbers, Align.LEFT);
        }
        if(wifeyLevelUp[partyMember]){
            g.drawImage(Assets.LevelUp, baseX + WIFEY_LEVEL_OFFSET_X, baseY + WIFEY_LEVEL_OFFSET_Y);
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
