package ultrapoulet.wifeygame.screens;

import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ultrapoulet.androidgame.framework.Game;
import ultrapoulet.androidgame.framework.Graphics;
import ultrapoulet.androidgame.framework.Image;
import ultrapoulet.androidgame.framework.Input;
import ultrapoulet.androidgame.framework.Screen;
import ultrapoulet.androidgame.framework.helpers.Button;
import ultrapoulet.androidgame.framework.helpers.ButtonList;
import ultrapoulet.wifeygame.Assets;
import ultrapoulet.wifeygame.battle.BattleInfo;
import ultrapoulet.wifeygame.battle.requirements.AbsRequirement;
import ultrapoulet.wifeygame.character.EnemyCharacter;
import ultrapoulet.wifeygame.character.WifeyCharacter;
import ultrapoulet.wifeygame.gamestate.Party;
import ultrapoulet.wifeygame.gamestate.PlayerInfo;
import ultrapoulet.wifeygame.gamestate.PlayerInfo.HeaderBar;
import ultrapoulet.wifeygame.parsers.ConfigParser;

/**
 * Created by John on 5/23/2017.
 */

public abstract class AbsBattleInfoScreen extends Screen {

    protected HeaderBar header = new HeaderBar();

    protected Screen prevScreen;

    protected BattleInfo battleInfo;

    private CharacterInfoScreen charInfo;
    private int selectedChar;

    private List<WifeyCharacter> party;

    protected Image background;
    protected static final int BACKGROUND_OFFSET = 60;

    private static final int BATTLE_NAME_X = 400;
    private static final int BATTLE_NAME_Y = 137;
    private static final int MAX_BATTLE_WIDTH = 550;
    private static final int MAX_BATTLE_FONT = 50;
    protected Paint battlePaint;

    protected int goldGain;
    protected int expGain;
    protected String shortGoldGain;
    protected String shortExpGain;

    protected static final int COLUMN_1_X = 227;
    protected static final int COLUMN_2_X = 471;
    protected static final int COLUMN_3_X = 715;
    protected static final int ROW_1_Y = 259;
    protected static final int ROW_2_Y = 357;
    protected static final int GAINS_Y_OFFSET = 10;
    protected Paint energyPaint;
    protected Paint infoPaint;
    protected Paint gainsPaint;

    private static final int PARTY_SCALE = 57;

    private static final int REQUIREMENT_CENTER_X = 400;
    private static final int REQUIREMENT_LEFT_X = 47;
    private static final int REQUIREMENT_RIGHT_X = 752;
    private static final int REQUIREMENT_WIDTH = REQUIREMENT_RIGHT_X - REQUIREMENT_LEFT_X - 200;
    private static final int REQUIREMENT_BASE_TOP_Y = 433;
    private static final int REQUIREMENT_BASE_BOT_Y = 532;
    private static final int REQUIREMENT_BASE_TEXT_Y = 505;
    private static final int REQUIREMENT_OFFSET_Y = 105;
    private TextPaint requirementPaint;

    private Button lastPressed = null;
    private int selectedReq = -1;

    private ButtonList buttonList;

    private static final int BACK_BUTTON_LEFT_X = 45;
    private static final int BACK_BUTTON_RIGHT_X = 215;
    private static final int BACK_BUTTON_TOP_Y = 1150;
    private static final int BACK_BUTTON_BOTTOM_Y = 1250;
    private static final String BACK_BUTTON_STRING = "Back";

    private static final int PARTY_BUTTON_LEFT_X = 250;
    private static final int PARTY_BUTTON_RIGHT_X = 550;
    private static final int PARTY_BUTTON_TOP_Y = 1150;
    private static final int PARTY_BUTTON_BOTTOM_Y = 1250;
    private static final String PARTY_BUTTON_STRING = "Party";

    private Button startButton;
    private static final int START_BUTTON_LEFT_X = 585;
    private static final int START_BUTTON_RIGHT_X = 755;
    private static final int START_BUTTON_TOP_Y = 1150;
    private static final int START_BUTTON_BOTTOM_Y = 1250;
    private static final String START_BUTTON_STRING = "Start";

    private ButtonList partyList;
    private static final int PARTY_IMAGE_BASE_LEFT_X = 55;
    private static final int PARTY_IMAGE_BASE_RIGHT_X = 145;
    private static final int PARTY_IMAGE_OFFSET_X = 100;
    private static final int PARTY_IMAGE_TOP_Y = 1010;
    private static final int PARTY_IMAGE_BOT_Y = 1100;

    private ButtonList requirementList;

    //specialScreen is used to hide the header before displaying a screen with a transparency
    private Screen specialScreen = null;

    public AbsBattleInfoScreen(Game game, Screen previousScreen, BattleInfo info){
        super(game);

        battlePaint = new Paint();
        battlePaint.setColor(Color.BLACK);
        battlePaint.setTextAlign(Paint.Align.CENTER);

        energyPaint = new Paint();
        energyPaint.setTextAlign(Paint.Align.CENTER);
        energyPaint.setTextSize(50);

        infoPaint = new Paint();
        infoPaint.setColor(Color.BLACK);
        infoPaint.setTextAlign(Paint.Align.CENTER);
        infoPaint.setTextSize(50);

        gainsPaint = new Paint();
        gainsPaint.setColor(Color.BLACK);
        gainsPaint.setTextAlign(Paint.Align.CENTER);
        gainsPaint.setTextSize(30);

        requirementPaint = new TextPaint();
        requirementPaint.setColor(Color.BLACK);
        requirementPaint.setTextAlign(Paint.Align.CENTER);
        requirementPaint.setTextSize(45);

        charInfo = new CharacterInfoScreen(game, this);

        buttonList = new ButtonList();
        buttonList.addButton(new Button(BACK_BUTTON_LEFT_X, BACK_BUTTON_RIGHT_X, BACK_BUTTON_TOP_Y, BACK_BUTTON_BOTTOM_Y, true, BACK_BUTTON_STRING));
        buttonList.addButton(new Button(PARTY_BUTTON_LEFT_X, PARTY_BUTTON_RIGHT_X, PARTY_BUTTON_TOP_Y, PARTY_BUTTON_BOTTOM_Y, true, PARTY_BUTTON_STRING));
        startButton = new Button(START_BUTTON_LEFT_X, START_BUTTON_RIGHT_X, START_BUTTON_TOP_Y, START_BUTTON_BOTTOM_Y, false, START_BUTTON_STRING, Assets.BattleEnable, Assets.BattleDisable);
        buttonList.addButton(startButton);

        setBattleInfo(info);
        setPreviousScreen(previousScreen);
        setBackground();
    }

    private void setBattleInfo(BattleInfo info){
        this.battleInfo = info;

        partyList = new ButtonList();
        for(int i = 0; i < Party.MAX_PARTY_SIZE; i++){
            int xLeft = PARTY_IMAGE_BASE_LEFT_X + PARTY_IMAGE_OFFSET_X * i;
            int xRight = PARTY_IMAGE_BASE_RIGHT_X + PARTY_IMAGE_OFFSET_X * i;
            int yTop = PARTY_IMAGE_TOP_Y;
            int yBot = PARTY_IMAGE_BOT_Y;
            partyList.addButton(new Button(xLeft, xRight, yTop, yBot, false, "PARTY_" + i));
        }

        requirementList = new ButtonList();
        ArrayList<AbsRequirement> tempList = battleInfo.getRequirements();
        for(int i = 0; i < tempList.size(); i++){
            int xLeft = REQUIREMENT_LEFT_X;
            int xRight = REQUIREMENT_RIGHT_X;
            int yTop = REQUIREMENT_BASE_TOP_Y + REQUIREMENT_OFFSET_Y * (i + 1);
            int yBot = REQUIREMENT_BASE_BOT_Y + REQUIREMENT_OFFSET_Y * (i + 1);
            requirementList.addButton(new Button(xLeft, xRight, yTop, yBot, tempList.get(i) != null, "REQ_" + i));
        }

        goldGain = 0;
        expGain = 0;
        List<EnemyCharacter> enemies = battleInfo.getCharacterEnemies();
        for(int i = 0; i < enemies.size(); i++){
            goldGain += enemies.get(i).getGold();
            expGain += enemies.get(i).getExperience();
        }
        shortGoldGain = shortenGains(goldGain);
        shortExpGain = shortenGains(expGain);
    }

    private void setPreviousScreen(Screen prevScreen){
        this.prevScreen = prevScreen;
    }

    protected abstract void setBackground();

    private boolean hasEnoughEnergy(){
        return PlayerInfo.getCurrentEnergy() >= battleInfo.getEnergyRequirement();
    }

    private boolean canStartBattle(){
        return party.get(0) != null && battleInfo.validParty(party) && PlayerInfo.getCurrentEnergy() >= battleInfo.getEnergyRequirement();
    }

    @Override
    public void update(float deltaTime) {
        List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();
        if(specialScreen != null) {
            game.setScreen(specialScreen);
            specialScreen = null;
        }
        else {
            for (int i = 0; i < touchEvents.size(); i++) {
                Input.TouchEvent t = touchEvents.get(i);
                //Perform update for HeaderBar
                header.update(t);

                if (t.type == Input.TouchEvent.TOUCH_DOWN) {
                    lastPressed = buttonList.getButtonPressed(t.x, t.y);
                    selectedChar = partyList.getIndexPressed(t.x, t.y);
                    selectedReq = requirementList.getIndexPressed(t.x, t.y);
                    continue;
                } else if (t.type == Input.TouchEvent.TOUCH_UP) {
                    if (lastPressed == buttonList.getButtonPressed(t.x, t.y) && lastPressed != null) {
                        switch (lastPressed.getName()) {
                            case BACK_BUTTON_STRING:
                                backButton();
                                break;
                            case PARTY_BUTTON_STRING:
                                PartySelectScreen partySelect = new PartySelectScreen(game, this, battleInfo);
                                game.setScreen(partySelect);
                                break;
                            case START_BUTTON_STRING:
                                PlayerInfo.decrementEnergy(battleInfo.getEnergyRequirement());
                                AbsBattleScreen abs = getBattleScreen();
                                BattleLoadingScreen bls = new BattleLoadingScreen(this.game, battleInfo, abs);
                                game.setScreen(bls);
                                break;
                        }
                    } else if (selectedChar == partyList.getIndexPressed(t.x, t.y) && selectedChar != -1) {
                        charInfo.setChar(party.get(selectedChar));
                        //game.setScreen(charInfo);
                        specialScreen = charInfo;
                    } else if (selectedReq == requirementList.getIndexPressed(t.x, t.y) && selectedReq != -1) {
                        Screen reqDialog = battleInfo.getRequirements().get(selectedReq).getRequirementDialog(game, this);
                        if (reqDialog != null) {
                            //game.setScreen(reqDialog);
                            specialScreen = reqDialog;
                        }
                    }
                }
            }
            if (hasEnoughEnergy()) {
                energyPaint.setColor(Color.BLACK);
            } else {
                energyPaint.setColor(Color.RED);
            }
            startButton.setActive(canStartBattle());
        }
    }

    protected abstract AbsBattleScreen getBattleScreen();

    //This function is terrible. Please come up with a better way later.
    private String shortenGains(int input){
        if(input < 10000){
            return String.valueOf(input);
        }
        String inputString = Integer.toString(input);
        String digits = inputString.substring(0, 3);
        int decimal = inputString.length() % 3;
        String abbrev;
        switch((inputString.length() - 1)/ 3){
            case(1):
                abbrev = "K";
                break;
            case(2):
                abbrev = "M";
                break;
            case(3):
                abbrev = "B";
                break;
            default:
                abbrev = "";
                break;
        }
        if(decimal != 0){
            return digits.substring(0, decimal) + "." + digits.substring(decimal) + abbrev;
        }
        else{
            return digits + abbrev;
        }
    }

    protected abstract void drawInfo(Graphics g);

    @Override
    public void paint(float deltaTime) {
        Graphics g = game.getGraphics();
        g.clearScreen(0xFFFFFFFF);

        g.drawImage(background, 0, BACKGROUND_OFFSET);

        g.drawString(battleInfo.getName(), BATTLE_NAME_X, BATTLE_NAME_Y, battlePaint, MAX_BATTLE_WIDTH, MAX_BATTLE_FONT);

        drawInfo(g);

        drawMaxPartySize(g, battleInfo.getPartyMax());
        drawRequirement(g, battleInfo.getCharacterRestrictions(), "No Banned Wifeys", 1);
        drawRequirement(g, battleInfo.getCharacterRequirements(), "No Required Wifeys", 2);
        drawRequirement(g, battleInfo.getSkillRestrictions(), "No Restricted Skills", 3);
        drawRequirement(g, battleInfo.getSkillRequirements(), "No Required Skills", 4);

        partyList.drawImage(g);
        for(int i = 0; i < Party.MAX_PARTY_SIZE && party.get(i) != null; i++){
            if(!battleInfo.allowCharacter(party.get(i))){
                g.drawPercentageImage(Assets.InvalidChar, PARTY_IMAGE_OFFSET_X * i + PARTY_IMAGE_BASE_LEFT_X, PARTY_IMAGE_TOP_Y, PARTY_SCALE, PARTY_SCALE);
            }
        }
        for(int i = battleInfo.getPartyMax(); i < Party.MAX_PARTY_SIZE; i++){
            g.drawImage(Assets.LockSelection, PARTY_IMAGE_OFFSET_X * i + PARTY_IMAGE_BASE_LEFT_X, PARTY_IMAGE_TOP_Y);
        }

        buttonList.drawImage(g);

        header.draw(g);
    }

    private void drawMaxPartySize(Graphics g, int maxPartySize) {
        requirementPaint.setColor(Color.GREEN);
        g.drawString("Max Party Size: " + maxPartySize, REQUIREMENT_CENTER_X, REQUIREMENT_BASE_TEXT_Y, requirementPaint);
    }

    private void drawRequirement(Graphics g, AbsRequirement req, String defaultTitle, int index) {
        String desc = req != null ? req.getTitle() : defaultTitle;
        if(req == null || req.validateParty(party)){
            requirementPaint.setColor(Color.GREEN);
        }
        else {
            requirementPaint.setColor(Color.RED);
        }
        if(requirementPaint.breakText(desc, true, REQUIREMENT_WIDTH, null) == desc.length() ){
            g.drawString(desc, REQUIREMENT_CENTER_X, REQUIREMENT_BASE_TEXT_Y + index * REQUIREMENT_OFFSET_Y, requirementPaint);
        }
        else {
            g.drawMultiLineString(desc, REQUIREMENT_CENTER_X, REQUIREMENT_BASE_TOP_Y + index * REQUIREMENT_OFFSET_Y, REQUIREMENT_WIDTH, requirementPaint);
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {
        party = Party.getCurrentParty();
        startButton.setActive(canStartBattle());
        for(int i = 0; i < Party.MAX_PARTY_SIZE; i++){
            //Set the button to active if the party member exists
            partyList.get(i).setActive(party.get(i) != null);
            if(party.get(i) != null){
                partyList.get(i).setActiveImage(party.get(i).getImage(game.getGraphics()));
            }
            else {
                partyList.get(i).setActiveImage(null);
            }
        }
    }

    @Override
    public void dispose() {

    }

    @Override
    public void backButton() {
        game.setScreen(prevScreen);
    }
}
