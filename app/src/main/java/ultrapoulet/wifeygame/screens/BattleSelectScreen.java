package ultrapoulet.wifeygame.screens;

import android.graphics.Color;
import android.graphics.Paint;

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
import ultrapoulet.wifeygame.gamestate.Battles;
import ultrapoulet.wifeygame.gamestate.Party;
import ultrapoulet.wifeygame.gamestate.PlayerInfo;

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

    private static final int PARTY_LEFT_X = 45;
    private static final int PARTY_RIGHT_X = 265;
    private static final int UPGRADE_LEFT_X = 290;
    private static final int UPGRADE_RIGHT_X = 510;
    private static final int INFO_LEFT_X = 535;
    private static final int INFO_RIGHT_X = 755;
    private static final int BUTTONS_TOP_Y = HEADER_OFFSET + 1090;
    private static final int BUTTONS_BOT_Y = BUTTONS_TOP_Y + 100;

    private ButtonList buttonList;
    private Button storyButton;
    private static final String STORY_BUTTON_STRING = "STORY";
    private Button recruitButton;
    private static final String RECRUIT_BUTTON_STRING = "RECRUIT";
    private Button specialButton;
    private static final String SPECIAL_BUTTON_STRING = "SPECIAL";

    private Button partyButton;
    private static final String PARTY_BUTTON_STRING = "PARTY";
    private Button upgradeButton;
    private static final String UPGRADE_BUTTON_STRING = "UPGRADE";
    private Button infoButton;
    private static final String INFO_BUTTON_STRING = "INFO";

    private Button lastPressedGeneral;
    private int selectedChar;

    private PartySelectScreen pss;
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

        partyButton = new Button(PARTY_LEFT_X, PARTY_RIGHT_X, BUTTONS_TOP_Y, BUTTONS_BOT_Y, true, PARTY_BUTTON_STRING, Assets.PartyButton, null);
        upgradeButton = new Button(UPGRADE_LEFT_X, UPGRADE_RIGHT_X, BUTTONS_TOP_Y, BUTTONS_BOT_Y, true, UPGRADE_BUTTON_STRING, Assets.UpgradeButton, null);
        infoButton = new Button(INFO_LEFT_X, INFO_RIGHT_X, BUTTONS_TOP_Y, BUTTONS_BOT_Y, true, INFO_BUTTON_STRING, Assets.InfoButton, null);
        buttonList.addButton(partyButton);
        buttonList.addButton(upgradeButton);
        buttonList.addButton(infoButton);

        pss = new PartySelectScreen(game, this);
        cis = new CharacterInfoScreen(game, this);

        partyList = new ButtonList();
        for(int i = 0; i < 7; i++){
            int xLeft = PARTY_IMAGE_BASE_LEFT_X + PARTY_IMAGE_OFFSET_X * i;
            int xRight = PARTY_IMAGE_BASE_RIGHT_X + PARTY_IMAGE_OFFSET_X * i;
            int yTop = PARTY_IMAGE_TOP_Y;
            int yBot = PARTY_IMAGE_BOT_Y;
            //Active is not initially set to have an image (This is done in resume)
            //Inactive does not have an image
            partyList.addButton(new Button(xLeft, xRight, yTop, yBot, false, "PARTY_" + i));
        }
    }

    @Override
    public void update(float deltaTime){

        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        for(int i = 0; i < touchEvents.size(); i++){
            TouchEvent t = touchEvents.get(i);
            if(t.type == TouchEvent.TOUCH_DOWN){
                lastPressedGeneral = buttonList.getButtonPressed(t.x, t.y);
                selectedChar = partyList.getIndexPressed(t.x, t.y);
                continue;
            }
            else if(t.type == TouchEvent.TOUCH_UP) {
                if(lastPressedGeneral != null && lastPressedGeneral == buttonList.getButtonPressed(t.x, t.y)){
                    switch(lastPressedGeneral.getName()){
                        case STORY_BUTTON_STRING:
                            storyButton.setActive(false);
                            recruitButton.setActive(true);
                            specialButton.setActive(true);
                            break;
                        case RECRUIT_BUTTON_STRING:
                            storyButton.setActive(true);
                            recruitButton.setActive(false);
                            specialButton.setActive(true);
                            break;
                        case SPECIAL_BUTTON_STRING:
                            storyButton.setActive(true);
                            recruitButton.setActive(true);
                            specialButton.setActive(false);
                            break;
                        case PARTY_BUTTON_STRING:
                            game.setScreen(pss);
                            break;
                        default:
                            System.out.println("Not yet implemented");
                    }
                }
                else if(selectedChar == partyList.getIndexPressed(t.x, t.y) && selectedChar != -1){
                    cis.setChar(party.get(selectedChar));
                    game.setScreen(cis);
                }
                if (t.x < 100 || t.x > 700) {
                    continue;
                }
                BattleInfo testInfo = null;
                if (t.y >= 160 && t.y <= 260) {
                    testInfo = Battles.get("TEST-BATL");
                }
                if (t.y >= 310 && t.y <= 410) {
                    testInfo = Battles.get("TEST-BTWO");
                }
                if (t.y >= 460 && t.y <= 560) {
                    testInfo = Battles.get("TEST-PHYS");
                }
                if (t.y >= 610 && t.y <= 710) {
                    testInfo = Battles.get("TEST-MAGI");
                }
                if (testInfo != null) {
                    BattleInfoScreen bis = new BattleInfoScreen(game, this, testInfo);
                    game.setScreen(bis);
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

        // Temporary way of making battle list
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(50);
        g.drawRect(100, 160, 600, 100, Color.DKGRAY);
        BattleInfo testInfo = Battles.get("TEST-BATL");
        g.drawString(testInfo.getName(), 400, 230, paint);

        g.drawRect(100, 310, 600, 100, Color.DKGRAY);
        BattleInfo test2 = Battles.get("TEST-BTWO");
        g.drawString(test2.getName(), 400, 380, paint);

        g.drawRect(100, 460, 600, 100, Color.DKGRAY);
        BattleInfo test3 = Battles.get("TEST-PHYS");
        g.drawString(test3.getName(), 400, 530, paint);

        g.drawRect(100, 610, 600, 100, Color.DKGRAY);
        BattleInfo test4 = Battles.get("TEST-MAGI");
        g.drawString(test4.getName(), 400, 680, paint);

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
