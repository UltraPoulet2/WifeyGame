package ultrapoulet.wifeygame.screens;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;

import java.util.ArrayList;
import java.util.List;

import ultrapoulet.androidgame.framework.Game;
import ultrapoulet.androidgame.framework.Graphics;
import ultrapoulet.androidgame.framework.Input.TouchEvent;
import ultrapoulet.androidgame.framework.Screen;
import ultrapoulet.androidgame.framework.helpers.Button;
import ultrapoulet.androidgame.framework.helpers.ButtonList;
import ultrapoulet.wifeygame.Assets;
import ultrapoulet.wifeygame.battle.BattleInfo;
import ultrapoulet.wifeygame.character.WifeyCharacter;
import ultrapoulet.wifeygame.gamestate.Party;
import ultrapoulet.wifeygame.gamestate.PlayerInfo;
import ultrapoulet.wifeygame.gamestate.StoryArea;
import ultrapoulet.wifeygame.gamestate.StoryBattles;

/**
 * Created by John on 4/26/2016.
 */
public class BattleSelectScreen extends Screen {

    private static final int HEADER_OFFSET = 60;

    private static final int STORY_LEFT_X = 42;
    private static final int STORY_RIGHT_X = 272;
    private static final int RECRUIT_LEFT_X = 272;
    private static final int RECRUIT_RIGHT_X = 528;
    private static final int SPECIAL_LEFT_X = 528;
    private static final int SPECIAL_RIGHT_X = 758;
    private static final int TAB_TOP_Y = HEADER_OFFSET + 20;
    private static final int TAB_BOT_Y = TAB_TOP_Y + 60;

    private static final int AREA_LEFT_X = 52;
    private static final int AREA_RIGHT_X = AREA_LEFT_X + 340;
    private static final int STORY_BATTLE_LEFT_X = 408;
    private static final int STORY_BATTLE_RIGHT_X = STORY_BATTLE_LEFT_X + 340;
    private static final int BATTLES_TOP_Y = HEADER_OFFSET + 105;
    private static final int BATTLES_BOT_Y = BATTLES_TOP_Y + 100;
    private static final int BATTLES_OFFSET_Y = BATTLES_BOT_Y - BATTLES_TOP_Y + 5;

    private static final int PARTY_LEFT_X = 45;
    private static final int PARTY_RIGHT_X = 265;
    private static final int UPGRADE_LEFT_X = 290;
    private static final int UPGRADE_RIGHT_X = 510;
    private static final int INFO_LEFT_X = 535;
    private static final int INFO_RIGHT_X = 755;
    private static final int BUTTONS_TOP_Y = HEADER_OFFSET + 1090;
    private static final int BUTTONS_BOT_Y = BUTTONS_TOP_Y + 100;

    private static final int DIVIDER_X = 397;
    private static final int DIVIDER_Y = HEADER_OFFSET + 95;

    private ButtonList buttonList;
    private Button storyButton;
    private static final String STORY_BUTTON_STRING = "STORY";
    private Button recruitButton;
    private static final String RECRUIT_BUTTON_STRING = "RECRUIT";
    private Button specialButton;
    private static final String SPECIAL_BUTTON_STRING = "SPECIAL";

    private ButtonList storyAreaList;
    private ButtonList storyBattleList;
    private Paint buttonPaint;
    private List<StoryArea> unlockedAreas;
    private static int selectedArea = -1;
    private int lastPressedArea = -1;
    private int lastPressedBattle = -1;

    private Button partyButton;
    private static final String PARTY_BUTTON_STRING = "PARTY";
    private Button upgradeButton;
    private static final String UPGRADE_BUTTON_STRING = "UPGRADE";
    private Button infoButton;
    private static final String INFO_BUTTON_STRING = "INFO";

    private Button lastPressedGeneral;
    private int selectedChar;

    private CharacterInfoScreen cis;

    private List<WifeyCharacter> party;
    private ButtonList partyList;
    private static final int PARTY_IMAGE_BASE_LEFT_X = 55;
    private static final int PARTY_IMAGE_BASE_RIGHT_X = 145;
    private static final int PARTY_IMAGE_OFFSET_X = 100;
    private static final int PARTY_IMAGE_TOP_Y = 1010;
    private static final int PARTY_IMAGE_BOT_Y = 1100;

    public BattleSelectScreen(Game game){
        super(game);

        buttonList = new ButtonList();
        storyButton = new Button(STORY_LEFT_X, STORY_RIGHT_X, TAB_TOP_Y, TAB_BOT_Y, false, STORY_BUTTON_STRING, Assets.StoryButtonInactive, Assets.StoryButtonActive);
        recruitButton = new Button(RECRUIT_LEFT_X, RECRUIT_RIGHT_X, TAB_TOP_Y, TAB_BOT_Y, true, RECRUIT_BUTTON_STRING, Assets.RecruitButtonInactive, Assets.RecruitButtonActive);
        specialButton = new Button(SPECIAL_LEFT_X, SPECIAL_RIGHT_X, TAB_TOP_Y, TAB_BOT_Y, true, SPECIAL_BUTTON_STRING, Assets.SpecialButtonInactive, Assets.SpecialButtonActive);
        buttonList.addButton(storyButton);
        buttonList.addButton(recruitButton);
        buttonList.addButton(specialButton);

        partyButton = new Button(PARTY_LEFT_X, PARTY_RIGHT_X, BUTTONS_TOP_Y, BUTTONS_BOT_Y, true, PARTY_BUTTON_STRING, Assets.PartyButton);
        upgradeButton = new Button(UPGRADE_LEFT_X, UPGRADE_RIGHT_X, BUTTONS_TOP_Y, BUTTONS_BOT_Y, true, UPGRADE_BUTTON_STRING, Assets.UpgradeButton);
        infoButton = new Button(INFO_LEFT_X, INFO_RIGHT_X, BUTTONS_TOP_Y, BUTTONS_BOT_Y, true, INFO_BUTTON_STRING, Assets.InfoButton);
        buttonList.addButton(partyButton);
        buttonList.addButton(upgradeButton);
        buttonList.addButton(infoButton);

        cis = new CharacterInfoScreen(game, this);

        partyList = new ButtonList();
        for(int i = 0; i < 7; i++){
            int leftX = PARTY_IMAGE_BASE_LEFT_X + PARTY_IMAGE_OFFSET_X * i;
            int rightX = PARTY_IMAGE_BASE_RIGHT_X + PARTY_IMAGE_OFFSET_X * i;
            int topY = PARTY_IMAGE_TOP_Y;
            int botY = PARTY_IMAGE_BOT_Y;
            //Active is not initially set to have an image (This is done in resume)
            //Inactive does not have an image
            partyList.addButton(new Button(leftX, rightX, topY, botY, false, "PARTY_" + i));
        }

        storyAreaList = new ButtonList();
        List<StoryArea> area = StoryBattles.getBattles();
        unlockedAreas = new ArrayList<>();
        for(int i = 0; i < area.size(); i++){
            if(area.get(i).isUnlocked()){
                int leftX = AREA_LEFT_X;
                int rightX = AREA_RIGHT_X;
                int topY = BATTLES_TOP_Y + BATTLES_OFFSET_Y * unlockedAreas.size();
                int botY = BATTLES_BOT_Y + BATTLES_OFFSET_Y * unlockedAreas.size();
                System.out.println(leftX + " " + rightX + " " + topY + " " + botY);
                storyAreaList.addButton(new Button(leftX, rightX, topY, botY, false, area.get(i).getAreaName(), Assets.pHealthY));
                unlockedAreas.add(area.get(i));
           }
        }

        createBattleButtons();
        setAreaButtons(true);

        buttonPaint = new Paint();
        buttonPaint.setTextSize(50);
        buttonPaint.setTextAlign(Align.CENTER);
        buttonPaint.setColor(Color.BLACK);
    }

    private void setAreaButtons(boolean state){
        for(int i = 0; i < storyAreaList.size(); i++){
            storyAreaList.get(i).setActive(state);
        }
        for(int i = 0; i < storyBattleList.size(); i++){
            storyBattleList.get(i).setActive(state);
        }
    }

    private void createBattleButtons(){
        storyBattleList = new ButtonList();
        if(selectedArea != -1 && selectedArea < unlockedAreas.size()){
            StoryArea area = unlockedAreas.get(selectedArea);
            for(int i = 0; i < area.getBattles().size(); i++){
                //An additional check later will need to be added to make sure the battle is unlocked
                int leftX = STORY_BATTLE_LEFT_X;
                int rightX = STORY_BATTLE_RIGHT_X;
                int topY = BATTLES_TOP_Y + BATTLES_OFFSET_Y * i;
                int botY = BATTLES_BOT_Y + BATTLES_OFFSET_Y * i;
                storyBattleList.addButton(new Button(leftX, rightX, topY, botY, true, area.getBattle(i).getName(), Assets.pHealthY));
            }
        }
    }

    @Override
    public void update(float deltaTime){

        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        for(int i = 0; i < touchEvents.size(); i++){
            TouchEvent t = touchEvents.get(i);
            if(t.type == TouchEvent.TOUCH_DOWN){
                lastPressedGeneral = buttonList.getButtonPressed(t.x, t.y);
                lastPressedArea = storyAreaList.getIndexPressed(t.x, t.y);
                lastPressedBattle = storyBattleList.getIndexPressed(t.x, t.y);
                selectedChar = partyList.getIndexPressed(t.x, t.y);
                continue;
            }
            else if(t.type == TouchEvent.TOUCH_UP) {
                if(lastPressedGeneral != null && lastPressedGeneral == buttonList.getButtonPressed(t.x, t.y)){
                    switch(lastPressedGeneral.getName()){
                        case STORY_BUTTON_STRING:
                            storyButton.setActive(false);
                            setAreaButtons(true);

                            recruitButton.setActive(true);
                            specialButton.setActive(true);
                            break;
                        case RECRUIT_BUTTON_STRING:
                            storyButton.setActive(true);
                            setAreaButtons(false);

                            recruitButton.setActive(false);
                            specialButton.setActive(true);
                            break;
                        case SPECIAL_BUTTON_STRING:
                            storyButton.setActive(true);
                            setAreaButtons(false);

                            recruitButton.setActive(true);
                            specialButton.setActive(false);
                            break;
                        case PARTY_BUTTON_STRING:
                            game.setScreen(new PartySelectScreen(game, this));
                            break;
                        default:
                            System.out.println("Not yet implemented");
                    }
                }
                else if(lastPressedArea == storyAreaList.getIndexPressed(t.x, t.y) && lastPressedArea != -1){
                    selectedArea = (selectedArea == lastPressedArea) ? -1 : lastPressedArea;
                    createBattleButtons();
                }
                else if(lastPressedBattle == storyBattleList.getIndexPressed(t.x, t.y) && lastPressedBattle != -1){
                    BattleInfo battle = unlockedAreas.get(selectedArea).getBattle(lastPressedBattle);
                    game.setScreen(new BattleInfoScreen(game, this, battle));
                }
                else if(selectedChar == partyList.getIndexPressed(t.x, t.y) && selectedChar != -1){
                    cis.setChar(party.get(selectedChar));
                    game.setScreen(cis);
                }
            }
        }
    }

    @Override
    public void paint(float deltaTime) {
        Graphics g = game.getGraphics();
        PlayerInfo.drawHeader(g);
        g.drawImage(Assets.BattleSelectScreen, 0, HEADER_OFFSET);

        buttonList.drawImage(g);

        storyAreaList.drawImage(g);
        storyAreaList.drawString(g, buttonPaint);
        storyBattleList.drawImage(g);
        storyBattleList.drawString(g, buttonPaint);

        if(!storyButton.isActive()){
            g.drawImage(Assets.BattleDivider, DIVIDER_X, DIVIDER_Y);
        }

        partyList.drawImage(g);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {
        //Do stuff to get the current party
        party = Party.getParty(7);
        for(int i = 0; i < 7; i++){
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

    }
}
