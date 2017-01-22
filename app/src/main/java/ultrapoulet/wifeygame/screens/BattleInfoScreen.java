package ultrapoulet.wifeygame.screens;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.text.TextPaint;

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
import ultrapoulet.wifeygame.Assets;
import ultrapoulet.wifeygame.battle.BattleInfo;
import ultrapoulet.wifeygame.battle.requirements.AbsRequirement;
import ultrapoulet.wifeygame.character.WifeyCharacter;
import ultrapoulet.wifeygame.gamestate.Party;
import ultrapoulet.wifeygame.gamestate.PlayerInfo;

/**
 * Created by John on 10/12/2016.
 */

public class BattleInfoScreen extends Screen{

    private Screen prevScreen;

    private BattleInfo battleInfo;
    private PartySelectScreen partySelect;

    private CharacterInfoScreen charInfo;
    private int selectedChar;

    private Image background = Assets.BattleInfoScreen;

    private List<WifeyCharacter> party;

    private static final int BACKGROUND_OFFSET = 60;

    private static final int BATTLE_NAME_X = 400;
    private static final int BATTLE_NAME_Y = 137;
    private Paint battlePaint;

    private static final int NUMBER_WAVES_X = 228;
    private static final int NUMBER_WAVES_Y = 357;
    private Paint wavesPaint;

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

    private ArrayList<Image> partyImages;

    private ButtonList requirementList;

    public BattleInfoScreen(Game game, Screen previousScreen, BattleInfo info){
        super(game);

        battlePaint = new Paint();
        battlePaint.setColor(Color.BLACK);
        battlePaint.setTextAlign(Align.CENTER);
        battlePaint.setTextSize(50);

        wavesPaint = new Paint();
        wavesPaint.setColor(Color.BLACK);
        wavesPaint.setTextAlign(Align.CENTER);
        wavesPaint.setTextSize(50);

        requirementPaint = new TextPaint();
        requirementPaint.setColor(Color.BLACK);
        requirementPaint.setTextAlign(Align.CENTER);
        requirementPaint.setTextSize(45);

        partySelect = new PartySelectScreen(game, this);

        charInfo = new CharacterInfoScreen(game, this);

        buttonList = new ButtonList();
        buttonList.addButton(new Button(BACK_BUTTON_LEFT_X, BACK_BUTTON_RIGHT_X, BACK_BUTTON_TOP_Y, BACK_BUTTON_BOTTOM_Y, true, BACK_BUTTON_STRING));
        buttonList.addButton(new Button(PARTY_BUTTON_LEFT_X, PARTY_BUTTON_RIGHT_X, PARTY_BUTTON_TOP_Y, PARTY_BUTTON_BOTTOM_Y, true, PARTY_BUTTON_STRING));
        startButton = new Button(START_BUTTON_LEFT_X, START_BUTTON_RIGHT_X, START_BUTTON_TOP_Y, START_BUTTON_BOTTOM_Y, false, START_BUTTON_STRING, Assets.BattleEnable, Assets.BattleDisable);
        buttonList.addButton(startButton);

        partyImages = new ArrayList<>(7);
        for(int i = 0; i < 7; i++){
            partyImages.add(null);
        }
        setBattleInfo(info);
        setPreviousScreen(previousScreen);
    }

    private void setBattleInfo(BattleInfo info){
        this.battleInfo = info;

        partyList = new ButtonList();
        for(int i = 0; i < battleInfo.getPartyMax(); i++){
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
            int yTop = REQUIREMENT_BASE_TOP_Y + REQUIREMENT_OFFSET_Y * i;
            int yBot = REQUIREMENT_BASE_BOT_Y + REQUIREMENT_OFFSET_Y * i;
            requirementList.addButton(new Button(xLeft, xRight, yTop, yBot, true, "REQ_" + i));
        }
    }

    private void setPreviousScreen(Screen prevScreen){
        this.prevScreen = prevScreen;
    }

    @Override
    public void update(float deltaTime) {
        List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();
        for(int i = 0; i < touchEvents.size(); i++) {
            Input.TouchEvent t = touchEvents.get(i);
            if(t.type == TouchEvent.TOUCH_DOWN){
                lastPressed = buttonList.getButtonPressed(t.x, t.y);
                selectedChar = partyList.getIndexPressed(t.x, t.y);
                selectedReq = requirementList.getIndexPressed(t.x, t.y);
                continue;
            }
            else if(t.type == TouchEvent.TOUCH_UP){
                if(lastPressed == buttonList.getButtonPressed(t.x, t.y) && lastPressed != null){
                    switch(lastPressed.getName()){
                        case BACK_BUTTON_STRING:
                            backButton();
                            break;
                        case PARTY_BUTTON_STRING:
                            partySelect.setBattleInfo(battleInfo);
                            game.setScreen(partySelect);
                            break;
                        case START_BUTTON_STRING:
                            BattleScreen bs = new BattleScreen(game, battleInfo);
                            game.setScreen(bs);
                            break;
                    }
                }
                else if(selectedChar == partyList.getIndexPressed(t.x, t.y) && selectedChar != -1){
                    charInfo.setChar(party.get(selectedChar));
                    game.setScreen(charInfo);
                }
                else if(selectedReq == requirementList.getIndexPressed(t.x, t.y) && selectedReq != -1){
                    //Set up displaying a requirement
                    System.out.println("Displaying requirement: " + selectedReq + " " + battleInfo.getRequirements().get(selectedReq).getDescription());
                }
            }
        }
    }

    @Override
    public void paint(float deltaTime) {
        Graphics g = game.getGraphics();
        g.clearScreen(0xFFFFFFFF);

        PlayerInfo.drawHeader(g);

        g.drawImage(background, 0, BACKGROUND_OFFSET);

        g.drawString(battleInfo.getName(), BATTLE_NAME_X, BATTLE_NAME_Y, battlePaint);

        g.drawString(String.valueOf(battleInfo.getCharacterEnemies().size()), NUMBER_WAVES_X, NUMBER_WAVES_Y, wavesPaint);

        for(int i = 0; i < battleInfo.getRequirements().size(); i++){
            String desc = battleInfo.getRequirements().get(i).getDescription();
            if(requirementPaint.breakText(desc, true, REQUIREMENT_WIDTH, null) == desc.length() ){
                g.drawString(desc, REQUIREMENT_CENTER_X, REQUIREMENT_BASE_TEXT_Y + i * REQUIREMENT_OFFSET_Y, requirementPaint);
            }
            else {
                g.drawMultiLineString(desc, REQUIREMENT_CENTER_X, REQUIREMENT_BASE_TOP_Y + i * REQUIREMENT_OFFSET_Y, REQUIREMENT_WIDTH, requirementPaint);
            }
        }

        for(int i = 0; i < battleInfo.getPartyMax() && party.get(i) != null; i++){
            g.drawPercentageImage(partyImages.get(i), PARTY_IMAGE_OFFSET_X * i + PARTY_IMAGE_BASE_LEFT_X, PARTY_IMAGE_TOP_Y, PARTY_SCALE, PARTY_SCALE);
            if(!battleInfo.allowCharacter(party.get(i))){
                g.drawPercentageImage(Assets.InvalidChar, PARTY_IMAGE_OFFSET_X * i + PARTY_IMAGE_BASE_LEFT_X, PARTY_IMAGE_TOP_Y, PARTY_SCALE, PARTY_SCALE);
            }
        }
        for(int i = battleInfo.getPartyMax(); i < 7; i++){
            g.drawImage(Assets.LockSelection, PARTY_IMAGE_OFFSET_X * i + PARTY_IMAGE_BASE_LEFT_X, PARTY_IMAGE_TOP_Y);
        }

        buttonList.drawImage(g);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {
        party = Party.getParty(battleInfo.getPartyMax());
        if(party.get(0) != null && battleInfo.validParty(party)){
            startButton.setActive(true);
        }
        else{
            startButton.setActive(false);
        }
        for(int i = 0; i < battleInfo.getPartyMax(); i++){
            //Set the button to active if the party member exists
            partyList.setIndexActive(i, party.get(i) != null);
            if(party.get(i) != null){
                partyImages.set(i, party.get(i).getImage(game.getGraphics()));
            }
            else {
                partyImages.set(i, null);
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
