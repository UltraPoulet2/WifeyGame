package ultrapoulet.wifeygame.screens;

import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;

import java.util.List;

import ultrapoulet.androidgame.framework.Game;
import ultrapoulet.androidgame.framework.Graphics;
import ultrapoulet.androidgame.framework.Image;
import ultrapoulet.androidgame.framework.Input;
import ultrapoulet.androidgame.framework.Screen;
import ultrapoulet.androidgame.framework.helpers.Button;
import ultrapoulet.androidgame.framework.helpers.ButtonList;
import ultrapoulet.wifeygame.Assets;
import ultrapoulet.wifeygame.character.WifeyCharacter;
import ultrapoulet.wifeygame.gamestate.PlayerInfo;
import ultrapoulet.wifeygame.recruiting.RecruitInfo;

/**
 * Created by John on 5/2/2017.
 */

public class RecruitingScreen extends Screen {

    private static final int HEADER_OFFSET = 60;

    private static final int IMAGE_X = 30;
    private static final int IMAGE_Y = HEADER_OFFSET + 150;
    private static final int IMAGE_SIZE = 160;

    private static final int BACK_BUTTON_LEFT_X = 45;
    private static final int BACK_BUTTON_RIGHT_X = 215;
    private static final int BACK_BUTTON_TOP_Y = HEADER_OFFSET + 1090;
    private static final int BACK_BUTTON_BOT_Y = HEADER_OFFSET + 1190;
    private static final String BACK_STRING = "Back";

    private static final int RECRUIT_BUTTON_LEFT_X = 235;
    private static final int RECRUIT_BUTTON_RIGHT_X = 755;
    private static final int RECRUIT_BUTTON_TOP_Y = HEADER_OFFSET + 930;
    private static final int RECRUIT_BUTTON_BOT_Y = HEADER_OFFSET + 1190;
    private static final String RECRUIT_STRING = "Recruit";

    private Screen previousScreen;

    private ButtonList basicButtons;
    private Button backButton;
    private Button recruitButton;

    private Button lastPressed;

    private WifeyCharacter recruit;
    private Image displayImage;

    private static final int TITLE_NAME_X = 400;
    private static final int TITLE_NAME_Y = 137;
    private static final int TITLE_TEXT_MAX_WIDTH = 550;
    private static final int TITLE_TEXT_MAX_FONT = 50;
    private Paint titlePaint;

    private TextPaint quotePaint;
    private static final int QUOTE_X = 220;
    private static final int QUOTE_Y = HEADER_OFFSET + 150;
    private static final int QUOTE_MAX_WIDTH = 550;
    private static final int QUOTE_TEXT_SIZE = 25;

    public RecruitingScreen(Game game, Screen previousScreen, WifeyCharacter inputRecruit){
        super(game);
        this.previousScreen = previousScreen;

        this.basicButtons = new ButtonList();
        backButton = new Button(BACK_BUTTON_LEFT_X, BACK_BUTTON_RIGHT_X, BACK_BUTTON_TOP_Y, BACK_BUTTON_BOT_Y, true, BACK_STRING);
        recruitButton = new Button(RECRUIT_BUTTON_LEFT_X, RECRUIT_BUTTON_RIGHT_X, RECRUIT_BUTTON_TOP_Y, RECRUIT_BUTTON_BOT_Y, false, RECRUIT_STRING, Assets.RecruitingButtonEnable, Assets.RecruitingButtonDisable);
        this.basicButtons.addButton(backButton);
        this.basicButtons.addButton(recruitButton);

        this.recruit = inputRecruit;
        this.displayImage = recruit.getImage(game.getGraphics());

        titlePaint = new Paint();
        titlePaint.setColor(Color.BLACK);
        titlePaint.setTextAlign(Paint.Align.CENTER);

        quotePaint = new TextPaint();
        quotePaint.setColor(Color.BLACK);
        quotePaint.setTextSize(QUOTE_TEXT_SIZE);
    }

    private void checkRecruitAvailable(){
        RecruitInfo info = recruit.getRecruitingInfo();
        for(int i = 0; i < info.getRequirements().size(); i++){
            //We'll set up images indicating whether or not a requirement is complete or not
        }
        recruitButton.setActive(info.isRecruitable());
    }

    @Override
    public void update(float deltaTime){
        List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();
        for(int i = 0; i < touchEvents.size(); i++) {
            Input.TouchEvent t = touchEvents.get(i);
            if (t.type == Input.TouchEvent.TOUCH_DOWN) {
                lastPressed = basicButtons.getButtonPressed(t.x, t.y);
                continue;
            } else if (t.type == Input.TouchEvent.TOUCH_UP) {
                if(lastPressed == basicButtons.getButtonPressed(t.x, t.y)){
                    if(lastPressed == backButton){
                        backButton();
                        break;
                    }
                    else if(lastPressed == recruitButton){
                        recruit.recruit();
                        backButton();
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void paint(float deltaTime) {
        Graphics g = game.getGraphics();
        PlayerInfo.drawHeader(g);
        g.drawImage(Assets.RecruitingScreen, 0, HEADER_OFFSET);

        g.drawString(recruit.getName(), TITLE_NAME_X, TITLE_NAME_Y, titlePaint, TITLE_TEXT_MAX_WIDTH, TITLE_TEXT_MAX_FONT);
        g.drawScaledImage(displayImage, IMAGE_X, IMAGE_Y, IMAGE_SIZE, IMAGE_SIZE);

        if(recruit.getRecruitingInfo() != null){
            g.drawMultiLineString(recruit.getRecruitingInfo().getQuote(), QUOTE_X, QUOTE_Y, QUOTE_MAX_WIDTH, quotePaint);
        }

        basicButtons.drawImage(g);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {
        checkRecruitAvailable();
    }

    @Override
    public void dispose() {

    }

    @Override
    public void backButton() {
        game.setScreen(previousScreen);
    }
}
