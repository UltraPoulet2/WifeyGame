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
import ultrapoulet.wifeygame.gamestate.Battles;
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
    private Button recruitButton;
    private Button specialButton;

    private Button partyButton;
    private Button upgradeButton;
    private Button infoButton;

    public BattleSelectScreen(Game game){
        super(game);

        buttonList = new ButtonList();
        storyButton = new Button(STORY_LEFT_X, STORY_RIGHT_X, TAB_TOP_Y, TAB_BOT_Y, false, "STORY", Assets.StoryButtonInactive, Assets.StoryButtonActive);
        recruitButton = new Button(RECRUIT_LEFT_X, RECRUIT_RIGHT_X, TAB_TOP_Y, TAB_BOT_Y, true, "RECRUIT", Assets.RecruitButtonInactive, Assets.RecruitButtonActive);
        specialButton = new Button(SPECIAL_LEFT_X, SPECIAL_RIGHT_X, TAB_TOP_Y, TAB_BOT_Y, true, "SPECIAL", Assets.SpecialButtonInactive, Assets.SpecialButtonActive);
        buttonList.addButton(storyButton);
        buttonList.addButton(recruitButton);
        buttonList.addButton(specialButton);

        partyButton = new Button(PARTY_LEFT_X, PARTY_RIGHT_X, BUTTONS_TOP_Y, BUTTONS_BOT_Y, true, "PARTY", Assets.PartyButton, null);
        upgradeButton = new Button(UPGRADE_LEFT_X, UPGRADE_RIGHT_X, BUTTONS_TOP_Y, BUTTONS_BOT_Y, true, "UPGRADE", Assets.UpgradeButton, null);
        infoButton = new Button(INFO_LEFT_X, INFO_RIGHT_X, BUTTONS_TOP_Y, BUTTONS_BOT_Y, true, "INFO", Assets.InfoButton, null);
        buttonList.addButton(partyButton);
        buttonList.addButton(upgradeButton);
        buttonList.addButton(infoButton);
    }

    @Override
    public void update(float deltaTime){

        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        for(int i = 0; i < touchEvents.size(); i++){
            TouchEvent t = touchEvents.get(i);
            if(t.type != TouchEvent.TOUCH_UP){
                continue;
            }
            if(t.x < 100 || t.x > 700){
                continue;
            }
            BattleInfo testInfo = null;
            if(t.y >= 160 && t.y <= 260){
                testInfo = Battles.get("TEST-BATL");
            }
            if(t.y >= 310 && t.y <= 410){
                testInfo = Battles.get("TEST-BTWO");
            }
            if(t.y >= 460 && t.y <= 560){
                testInfo = Battles.get("TEST-PHYS");
            }
            if(t.y >= 610 && t.y <= 710){
                testInfo = Battles.get("TEST-MAGI");
            }
            if(testInfo != null){
                BattleInfoScreen bis = new BattleInfoScreen(game, this, testInfo);
                game.setScreen(bis);
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
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {
        //Do stuff to get the current party
    }

    @Override
    public void dispose() {

    }

    @Override
    public void backButton() {

    }
}
