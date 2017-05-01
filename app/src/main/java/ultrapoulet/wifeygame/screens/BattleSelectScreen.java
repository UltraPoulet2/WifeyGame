package ultrapoulet.wifeygame.screens;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ultrapoulet.androidgame.framework.Game;
import ultrapoulet.androidgame.framework.Graphics;
import ultrapoulet.androidgame.framework.Image;
import ultrapoulet.androidgame.framework.Input.TouchEvent;
import ultrapoulet.androidgame.framework.Screen;
import ultrapoulet.androidgame.framework.helpers.Button;
import ultrapoulet.androidgame.framework.helpers.ButtonList;
import ultrapoulet.androidgame.framework.helpers.NumberPrinter;
import ultrapoulet.wifeygame.Assets;
import ultrapoulet.wifeygame.battle.BattleInfo;
import ultrapoulet.wifeygame.character.WifeyCharacter;
import ultrapoulet.wifeygame.gamestate.Party;
import ultrapoulet.wifeygame.gamestate.PlayerInfo;
import ultrapoulet.wifeygame.gamestate.RecruitableCharacters;
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
    private static final int BATTLES_TOP_Y = HEADER_OFFSET + 160;
    private static final int BATTLES_BOT_Y = BATTLES_TOP_Y + 100;
    private static final int BATTLES_OFFSET_Y = BATTLES_BOT_Y - BATTLES_TOP_Y + 10;

    private static final int BATTLE_TITLE_OFFSET_Y = -25;
    private static final int BATTLE_ENERGY_IMAGE_OFFSET_X = 5;
    private static final int BATTLE_ENERGY_NUMBER_OFFSET_X = 25;
    private static final int BATTLE_ENERGY_OFFSET_Y = 50;
    private static final int BATTLE_ENERGY_WIDTH = 20;
    private static final int BATTLE_ENERGY_HEIGHT = 40;
    private static final int BATTLE_ENERGY_OFFSET = 0;
    private static final int BATTLE_INDICATOR_OFFSET_X = 85;

    private static final int BATTLE_PAGE_UP_TOP_Y = HEADER_OFFSET + 110;
    private static final int BATTLE_PAGE_UP_BOT_Y = BATTLE_PAGE_UP_TOP_Y + 40;
    private static final int BATTLE_PAGE_DOWN_TOP_Y = HEADER_OFFSET + 820;
    private static final int BATTLE_PAGE_DOWN_BOT_Y = BATTLE_PAGE_DOWN_TOP_Y + 40;

    private static final int RECRUIT_BUTTON_LEFT_X = 52;
    private static final int RECRUIT_BUTTON_RIGHT_X = 747;
    private static final int RECRUIT_BUTTON_TOP_Y = HEADER_OFFSET + 160;
    private static final int RECRUIT_BUTTON_BOT_Y = RECRUIT_BUTTON_TOP_Y + 100;
    private static final int RECRUIT_OFFSET_Y = BATTLES_OFFSET_Y;
    private static final int RECRUIT_PAGE_UP_TOP_Y = HEADER_OFFSET + 110;
    private static final int RECRUIT_PAGE_UP_BOT_Y = RECRUIT_PAGE_UP_TOP_Y + 40;
    private static final int RECRUIT_PAGE_DOWN_TOP_Y = HEADER_OFFSET + 820;
    private static final int RECRUIT_PAGE_DOWN_BOT_Y = RECRUIT_PAGE_DOWN_TOP_Y + 40;
    private static final int RECRUIT_BUTTON_IMAGE_OFFSET_X = 10;
    private static final int RECRUIT_BUTTON_IMAGE_OFFSET_Y = 10;
    private static final int RECRUIT_BUTTON_IMAGE_SIZE = 80;
    private static final String RECRUIT_UP_STRING = "RECRUIT_UP";
    private static final String RECRUIT_DOWN_STRING = "RECRUIT_DOWN";

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
    private Button areaPageUpButton;
    private Button areaPageDownButton;
    private Button battlePageUpButton;
    private Button battlePageDownButton;
    private Paint buttonPaint;
    private List<StoryArea> unlockedAreas;
    private static int selectedArea = -1;
    private static int selectedAreaPage = 0;
    private static int selectedBattlePage = 0;
    private static String selectedTab = STORY_BUTTON_STRING;
    private int lastPressedArea = -1;
    private int lastPressedBattle = -1;
    private static final int AREA_PAGE_SIZE = 6;

    //For now, this will be a list of the Recruitable Wifeys
    //Later on, this will be a list of the Recruit Info
    private List<WifeyCharacter> recruitableWifeys;
    private List<Image> recruitableWifeyImages;
    private ButtonList recruitButtonList;
    private Button recruitPageUpButton;
    private Button recruitPageDownButton;
    private static int selectedRecruitPage = 0;
    private int lastPressedRecruit = -1;

    private Button partyButton;
    private static final String PARTY_BUTTON_STRING = "PARTY";
    private Button upgradeButton;
    private static final String UPGRADE_BUTTON_STRING = "UPGRADE";
    private Button infoButton;
    private static final String INFO_BUTTON_STRING = "INFO";

    private static final String AREA_UP_STRING = "AREA_UP";
    private static final String AREA_DOWN_STRING = "AREA_DOWN";
    private static final String BATTLE_UP_STRING = "BATTLE_UP";
    private static final String BATTLE_DOWN_STRING = "BATTLE_DOWN";

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

        areaPageUpButton = new Button(AREA_LEFT_X, AREA_RIGHT_X, BATTLE_PAGE_UP_TOP_Y, BATTLE_PAGE_UP_BOT_Y, false, AREA_UP_STRING, Assets.BattleSelectPageUpEnabled, Assets.BattleSelectPageUpDisabled);
        areaPageDownButton = new Button(AREA_LEFT_X, AREA_RIGHT_X, BATTLE_PAGE_DOWN_TOP_Y, BATTLE_PAGE_DOWN_BOT_Y, false, AREA_DOWN_STRING, Assets.BattleSelectPageDownEnabled, Assets.BattleSelectPageDownDisabled);
        battlePageUpButton = new Button(STORY_BATTLE_LEFT_X, STORY_BATTLE_RIGHT_X, BATTLE_PAGE_UP_TOP_Y, BATTLE_PAGE_UP_BOT_Y, false, BATTLE_UP_STRING, Assets.BattleSelectPageUpEnabled, Assets.BattleSelectPageUpDisabled);
        battlePageDownButton = new Button(STORY_BATTLE_LEFT_X, STORY_BATTLE_RIGHT_X, BATTLE_PAGE_DOWN_TOP_Y, BATTLE_PAGE_DOWN_BOT_Y, false, BATTLE_DOWN_STRING, Assets.BattleSelectPageDownEnabled, Assets.BattleSelectPageDownDisabled);
        buttonList.addButton(areaPageUpButton);
        buttonList.addButton(areaPageDownButton);
        buttonList.addButton(battlePageUpButton);
        buttonList.addButton(battlePageDownButton);

        recruitPageUpButton = new Button(RECRUIT_BUTTON_LEFT_X, RECRUIT_BUTTON_RIGHT_X, RECRUIT_PAGE_UP_TOP_Y, RECRUIT_PAGE_UP_BOT_Y, false, RECRUIT_UP_STRING, Assets.RecruitPageUpEnabled, Assets.RecruitPageUpDisabled);
        recruitPageDownButton = new Button(RECRUIT_BUTTON_LEFT_X, RECRUIT_BUTTON_RIGHT_X, RECRUIT_PAGE_DOWN_TOP_Y, RECRUIT_PAGE_DOWN_BOT_Y, false, RECRUIT_DOWN_STRING, Assets.RecruitPageDownEnabled, Assets.RecruitPageDownDisabled);
        buttonList.addButton(recruitPageUpButton);
        buttonList.addButton(recruitPageDownButton);

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
                int topY = BATTLES_TOP_Y + BATTLES_OFFSET_Y * (unlockedAreas.size() % AREA_PAGE_SIZE);
                int botY = BATTLES_BOT_Y + BATTLES_OFFSET_Y * (unlockedAreas.size() % AREA_PAGE_SIZE);
                if(selectedArea == i){
                    storyAreaList.addButton(new Button(leftX, rightX, topY, botY, false, area.get(i).getAreaName(), Assets.StoryBattleSelected));
                }
                else {
                    storyAreaList.addButton(new Button(leftX, rightX, topY, botY, false, area.get(i).getAreaName(), Assets.StoryBattleEnabled));
                }
                unlockedAreas.add(area.get(i));
           }
        }

        createBattleButtons();
        createRecruitButtons();
        /*
        setAreaPageVisible(true);
        setBattlePageVisible(selectedArea != -1);
        activatePageAndBattleButtons();
        */
        changeTab();

        buttonPaint = new Paint();
        buttonPaint.setTextSize(40);
        buttonPaint.setTextAlign(Align.CENTER);
        buttonPaint.setColor(Color.BLACK);
    }

    private void deactivatePageAndBattleButtons(){
        for(int i = 0; i < storyAreaList.size(); i++){
            storyAreaList.get(i).setActive(false);
        }
        for(int i = 0; i < storyBattleList.size(); i++){
            storyBattleList.get(i).setActive(false);
        }
    }

    private void activatePageAndBattleButtons(){
        int maxAreaPage = (unlockedAreas.size() - 1)/ AREA_PAGE_SIZE;
        areaPageUpButton.setActive(selectedAreaPage > 0);
        areaPageDownButton.setActive(selectedAreaPage < maxAreaPage);
        for(int i = 0; i < storyAreaList.size(); i++){
            storyAreaList.get(i).setActive(selectedAreaPage == i / AREA_PAGE_SIZE);
        }
        if(selectedArea == -1){
            battlePageUpButton.setActive(false);
            battlePageDownButton.setActive(false);
        }
        else{
            int maxBattlePage = (unlockedAreas.get(selectedArea).getBattles().size() - 1) / AREA_PAGE_SIZE;
            battlePageUpButton.setActive(selectedBattlePage > 0);
            battlePageDownButton.setActive(selectedBattlePage < maxBattlePage);
        }
        for(int i = 0; i < storyBattleList.size(); i++){
            storyBattleList.get(i).setActive(selectedBattlePage == i / AREA_PAGE_SIZE);
        }
    }

    private void setAreaPageVisible(boolean state){
        areaPageUpButton.setHidden(!state);
        areaPageDownButton.setHidden(!state);
    }

    private void setBattlePageVisible(boolean state){
        battlePageUpButton.setHidden(!state);
        battlePageDownButton.setHidden(!state);
    }

    private void activateRecruitButtons(){
        int maxRecruitPage = (recruitableWifeys.size() - 1) / AREA_PAGE_SIZE;
        recruitPageUpButton.setActive(selectedRecruitPage > 0);
        recruitPageDownButton.setActive(selectedRecruitPage < maxRecruitPage);
        for(int i = 0; i < recruitButtonList.size(); i++){
            recruitButtonList.get(i).setActive(selectedRecruitPage == i / AREA_PAGE_SIZE);
        }
    }

    private void deactivateRecruitButtons(){
        for(int i = 0; i < recruitButtonList.size(); i++){
            recruitButtonList.get(i).setActive(false);
        }
    }

    private void setRecruitPageVisible(boolean state){
        recruitPageUpButton.setHidden(!state);
        recruitPageDownButton.setHidden(!state);
    }

    private void createBattleButtons(){
        storyBattleList = new ButtonList();
        if(selectedArea != -1 && selectedArea < unlockedAreas.size()){
            StoryArea area = unlockedAreas.get(selectedArea);
            selectedBattlePage = 0;
            for(int i = 0; i < area.getBattles().size(); i++){
                //An additional check later will need to be added to make sure the battle is unlocked
                int leftX = STORY_BATTLE_LEFT_X;
                int rightX = STORY_BATTLE_RIGHT_X;
                int topY = BATTLES_TOP_Y + BATTLES_OFFSET_Y * (i % AREA_PAGE_SIZE);
                int botY = BATTLES_BOT_Y + BATTLES_OFFSET_Y * (i % AREA_PAGE_SIZE);
                storyBattleList.addButton(new Button(leftX, rightX, topY, botY, true, area.getBattle(i).getName(), Assets.StoryBattleEnabled));
            }
        }
    }

    private void createRecruitButtons(){
        //We set the current page to 0 to not have any issues
        selectedRecruitPage = 0;
        recruitableWifeys = RecruitableCharacters.getArray();
        recruitableWifeyImages = new ArrayList<>();
        //Sort should be added to make the list consistent
        Collections.sort(recruitableWifeys, WifeyCharacter.getNameComparator());
        recruitButtonList = new ButtonList();
        for(int i = 0; i < recruitableWifeys.size(); i++){
            int leftX = RECRUIT_BUTTON_LEFT_X;
            int rightX = RECRUIT_BUTTON_RIGHT_X;
            int topY = RECRUIT_BUTTON_TOP_Y + RECRUIT_OFFSET_Y * (i % AREA_PAGE_SIZE);
            int botY = RECRUIT_BUTTON_BOT_Y + RECRUIT_OFFSET_Y * (i % AREA_PAGE_SIZE);
            recruitButtonList.addButton(new Button(leftX, rightX, topY, botY, true, recruitableWifeys.get(i).getName(), Assets.RecruitBattleButton));
            recruitableWifeyImages.add(recruitableWifeys.get(i).getImage(game.getGraphics()));
        }
    }

    private void changeTab(){
        switch(selectedTab){
            case STORY_BUTTON_STRING:
                storyButton.setActive(false);
                activatePageAndBattleButtons();
                setAreaPageVisible(true);
                setBattlePageVisible(selectedArea != -1);
                deactivateRecruitButtons();
                setRecruitPageVisible(false);

                recruitButton.setActive(true);
                specialButton.setActive(true);
                break;
            case RECRUIT_BUTTON_STRING:
                storyButton.setActive(true);
                deactivatePageAndBattleButtons();
                setAreaPageVisible(false);
                setBattlePageVisible(false);
                activateRecruitButtons();
                setRecruitPageVisible(true);

                recruitButton.setActive(false);
                specialButton.setActive(true);
                break;
            case SPECIAL_BUTTON_STRING:
                storyButton.setActive(true);
                deactivatePageAndBattleButtons();
                setAreaPageVisible(false);
                setBattlePageVisible(false);
                deactivateRecruitButtons();
                setRecruitPageVisible(false);

                recruitButton.setActive(true);
                specialButton.setActive(false);
                break;
        }
        System.out.println("Tab has been changed to: " + selectedTab);
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
                lastPressedRecruit = recruitButtonList.getIndexPressed(t.x, t.y);
                selectedChar = partyList.getIndexPressed(t.x, t.y);
                continue;
            }
            else if(t.type == TouchEvent.TOUCH_UP) {
                if(lastPressedGeneral != null && lastPressedGeneral == buttonList.getButtonPressed(t.x, t.y)){
                    switch(lastPressedGeneral.getName()){
                        case STORY_BUTTON_STRING:
                            selectedTab = STORY_BUTTON_STRING;
                            changeTab();
                            break;
                        case RECRUIT_BUTTON_STRING:
                            selectedTab = RECRUIT_BUTTON_STRING;
                            changeTab();
                            break;
                        case SPECIAL_BUTTON_STRING:
                            selectedTab = SPECIAL_BUTTON_STRING;
                            changeTab();
                            break;
                        case PARTY_BUTTON_STRING:
                            game.setScreen(new PartySelectScreen(game, this));
                            break;
                        case AREA_UP_STRING:
                            selectedAreaPage--;
                            activatePageAndBattleButtons();
                            break;
                        case AREA_DOWN_STRING:
                            selectedAreaPage++;
                            activatePageAndBattleButtons();
                            break;
                        case BATTLE_UP_STRING:
                            selectedBattlePage--;
                            activatePageAndBattleButtons();
                            break;
                        case BATTLE_DOWN_STRING:
                            selectedBattlePage++;
                            activatePageAndBattleButtons();
                            break;
                        case RECRUIT_UP_STRING:
                            selectedRecruitPage--;
                            activateRecruitButtons();
                            break;
                        case RECRUIT_DOWN_STRING:
                            selectedRecruitPage++;
                            activateRecruitButtons();
                            break;
                        default:
                            System.out.println("Not yet implemented");
                    }
                }
                else if(lastPressedArea == storyAreaList.getIndexPressed(t.x, t.y) && lastPressedArea != -1){
                    if(selectedArea != -1) {
                        storyAreaList.get(selectedArea).setActiveImage(Assets.StoryBattleEnabled);
                    }
                    selectedArea = (selectedArea == lastPressedArea) ? -1 : lastPressedArea;
                    createBattleButtons();
                    setBattlePageVisible(selectedArea != -1);
                    activatePageAndBattleButtons();
                    if(selectedArea != -1) {
                        storyAreaList.get(selectedArea).setActiveImage(Assets.StoryBattleSelected);
                    }
                }
                else if(lastPressedBattle == storyBattleList.getIndexPressed(t.x, t.y) && lastPressedBattle != -1){
                    BattleInfo battle = unlockedAreas.get(selectedArea).getBattle(lastPressedBattle);
                    game.setScreen(new BattleInfoScreen(game, this, battle));
                }
                else if(selectedChar == partyList.getIndexPressed(t.x, t.y) && selectedChar != -1){
                    cis.setChar(party.get(selectedChar));
                    game.setScreen(cis);
                } else if (lastPressedRecruit == recruitButtonList.getIndexPressed(t.x, t.y) && lastPressedRecruit != -1){
                    //For now, we're just going to recruit the character immediately and recreate the recruit buttons
                    recruitableWifeys.get(lastPressedRecruit).recruit();
                    createRecruitButtons();
                    activateRecruitButtons();
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
        storyBattleList.drawString(g, buttonPaint, 0, BATTLE_TITLE_OFFSET_Y);
        recruitButtonList.drawImage(g);

        if(selectedTab == STORY_BUTTON_STRING){
            g.drawImage(Assets.BattleDivider, DIVIDER_X, DIVIDER_Y);
        }

        partyList.drawImage(g);

        if(selectedTab == STORY_BUTTON_STRING && selectedArea != -1){
            for(int i = 0; i < storyBattleList.size(); i++){
                if(storyBattleList.get(i).isActive()){
                    int imageX = STORY_BATTLE_LEFT_X + BATTLE_ENERGY_IMAGE_OFFSET_X;
                    int numberX = STORY_BATTLE_LEFT_X + BATTLE_ENERGY_NUMBER_OFFSET_X;
                    int imageY =  BATTLES_TOP_Y + BATTLES_OFFSET_Y * (i % AREA_PAGE_SIZE) + BATTLE_ENERGY_OFFSET_Y;
                    g.drawImage(Assets.EnergyImage, imageX, imageY);
                    List<Image> numberList;
                    BattleInfo battle = unlockedAreas.get(selectedArea).getBattle(i);
                    if(PlayerInfo.getCurrentEnergy() >= battle.getEnergyRequirement()){
                        numberList = Assets.WhiteNumbers;
                    }
                    else{
                        numberList = Assets.RedNumbers;
                    }
                    NumberPrinter.drawNumber(g, battle.getEnergyRequirement(), numberX, imageY, BATTLE_ENERGY_WIDTH, BATTLE_ENERGY_HEIGHT, BATTLE_ENERGY_OFFSET, numberList, NumberPrinter.Align.LEFT);
                    int indicatorX = STORY_BATTLE_LEFT_X + BATTLE_INDICATOR_OFFSET_X;
                    if(battle.getNumAttempts() == 0){
                        g.drawImage(Assets.NewBattleIndicator, indicatorX, imageY);
                    }
                    else if(battle.getNumComplete() > 0){
                        g.drawImage(Assets.CompletedBattleIndicator, indicatorX, imageY);
                    }
                }
            }
        }
        else if(selectedTab == RECRUIT_BUTTON_STRING){
            for(int i = 0; i < recruitableWifeys.size(); i++){
                if(recruitButtonList.get(i).isActive()){
                    int imageX = RECRUIT_BUTTON_LEFT_X + RECRUIT_BUTTON_IMAGE_OFFSET_X;
                    int imageY = RECRUIT_BUTTON_TOP_Y + BATTLES_OFFSET_Y * (i % AREA_PAGE_SIZE) + RECRUIT_BUTTON_IMAGE_OFFSET_Y;
                    g.drawScaledImage(recruitableWifeyImages.get(i), imageX, imageY, RECRUIT_BUTTON_IMAGE_SIZE, RECRUIT_BUTTON_IMAGE_SIZE);
                }
            }
        }
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
