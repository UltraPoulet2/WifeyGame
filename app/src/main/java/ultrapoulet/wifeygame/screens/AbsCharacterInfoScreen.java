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

/**
 * Created by John on 11/10/2016.
 */

public abstract class AbsCharacterInfoScreen extends Screen {

    protected Screen previousScreen;

    protected Image background;
    protected static final int BG_X = 50;
    protected static final int BG_Y = 75;

    protected static final int CHAR_X = 30 + BG_X;
    protected static final int CHAR_Y = 100 + BG_Y;
    protected static final int DOUBLE_SCALE = 200;

    protected static final int ELEMENT_X = 357 + BG_X;
    protected static final int ATK_ELEMENT_Y = 158 + BG_Y;
    protected static final int RES_ELEMENT_Y = 269 + BG_Y;
    protected static final int WEAK_ELEMENT_Y = 380 + BG_Y;
    protected static final int QUARTER_SCALE = 25;

    protected TextPaint namePaint;
    protected static final int MAX_NAME_FONT = 40;
    protected static final int MIN_NAME_FONT = 20;
    protected static final int TWO_LINE_NAME_FONT = 20;
    protected static final int MAX_NAME_SIZE = 236;
    protected static final int NAME_X = 402 + BG_X;
    protected static final int MAX_NAME_Y = 155 + BG_Y;
    protected static final int TWO_LINE_NAME_Y = 115 + BG_Y;

    protected Paint skillsPaint;
    protected static final int SKILLS_TEXT_FONT = 40;
    protected static final int SKILLS_TEXT_SIZE = 276;
    protected static final int SKILLS_TEXT_PER_PAGE = 14;
    protected static final int SKILLS_TEXT_LEFT_X = 32 + BG_X;
    protected static final int SKILLS_TEXT_RIGHT_X = SKILLS_TEXT_LEFT_X + 285;
    protected static final int SKILLS_TEXT_BASE_Y = 495 + BG_Y;
    protected static final int SKILLS_TEXT_OFFSET_Y = 55;

    protected static final int SKILLS_DESC_X = 32 + BG_X;
    protected static final int SKILLS_DESC_Y = 872 + BG_Y;
    protected static final int SKILLS_DESC_WIDTH = 616;
    protected TextPaint descPaint;

    protected int skillsPage = 0;
    protected int displayText = -1;
    protected int maxPage;

    protected static final int SKILLS_SCROLL_X = 600 + BG_X;
    protected static final int SKILLS_SCROLL_TOP_Y = 565 + BG_Y;
    protected static final int SKILLS_SCROLL_MAX_Y = 645 + BG_Y;

    protected ButtonList basicButtonList;

    protected static final int CLOSE_LEFT_X = BG_X + 600;
    protected static final int CLOSE_RIGHT_X = CLOSE_LEFT_X + 75;
    protected static final int CLOSE_TOP_Y = BG_Y + 10;
    protected static final int CLOSE_BOT_Y = CLOSE_TOP_Y + 75;
    protected static final String CLOSE_STRING = "Close";

    protected static final int SKILLS_PAGE_LEFT_X = 600 + BG_X;
    protected static final int SKILLS_PAGE_RIGHT_X = SKILLS_PAGE_LEFT_X + 50;
    protected static final int SKILLS_PAGE_HEIGHT = 105;
    protected static final int SKILLS_PREV_TOP_Y = 455 + BG_Y;
    protected static final int SKILLS_PREV_BOT_Y = SKILLS_PREV_TOP_Y + SKILLS_PAGE_HEIGHT;
    protected static final int SKILLS_NEXT_TOP_Y = 730 + BG_Y;
    protected static final int SKILLS_NEXT_BOT_Y = SKILLS_NEXT_TOP_Y + SKILLS_PAGE_HEIGHT;
    protected static final String PREV_STRING = "Prev";
    protected static final String NEXT_STRING = "Next";

    protected ButtonList skillsButtonList;
    protected static final int SKILLS_BUTTON_LEFT_X = 30 + BG_X;
    protected static final int SKILLS_BUTTON_OFFSET_X = 280;
    protected static final int SKILLS_BUTTON_TOP_Y = 455 + BG_Y;
    protected static final int SKILLS_BUTTON_OFFSET_Y = 55;

    public AbsCharacterInfoScreen(Game game, Screen previousScreen) {
        super(game);
        setPreviousScreen(previousScreen);

        createPaints();
        createButtons();
    }

    protected void createPaints(){
        namePaint = new TextPaint();
        namePaint.setTextAlign(Paint.Align.LEFT);
        namePaint.setColor(Color.BLACK);

        skillsPaint = new Paint();
        skillsPaint.setTextAlign(Paint.Align.LEFT);
        skillsPaint.setColor(Color.BLACK);

        descPaint = new TextPaint();
        descPaint.setTextAlign(Paint.Align.LEFT);
        descPaint.setColor(Color.BLACK);

        createUniquePaints();
    }

    protected void createButtons(){
        basicButtonList = new ButtonList();
        basicButtonList.addButton(new Button(CLOSE_LEFT_X, CLOSE_RIGHT_X, CLOSE_TOP_Y, CLOSE_BOT_Y, true, CLOSE_STRING));
        basicButtonList.addButton(new Button(SKILLS_PAGE_LEFT_X, SKILLS_PAGE_RIGHT_X, SKILLS_PREV_TOP_Y, SKILLS_PREV_BOT_Y, true, PREV_STRING));
        basicButtonList.addButton(new Button(SKILLS_PAGE_LEFT_X, SKILLS_PAGE_RIGHT_X, SKILLS_NEXT_TOP_Y, SKILLS_NEXT_BOT_Y, true, NEXT_STRING));

        skillsButtonList = new ButtonList();
        for(int i = 0; i < SKILLS_TEXT_PER_PAGE; i++) {
            int leftX = SKILLS_BUTTON_LEFT_X + (SKILLS_BUTTON_OFFSET_X * (i % 2));
            int rightX = leftX + SKILLS_BUTTON_OFFSET_X;
            int topY = SKILLS_BUTTON_TOP_Y + (SKILLS_BUTTON_OFFSET_Y * (i / 2));
            int botY = topY + SKILLS_BUTTON_OFFSET_Y;
            skillsButtonList.addButton(new Button(leftX, rightX, topY, botY, true, "Skill_" + i));
        }
    }

    private void setPreviousScreen(Screen screen){
        previousScreen = screen;
    }

    abstract protected void createUniquePaints();

    @Override
    public void update(float deltaTime) {
        List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();
        for(int i = 0; i < touchEvents.size(); i++) {
            Input.TouchEvent t = touchEvents.get(i);
            if (t.type == Input.TouchEvent.TOUCH_UP) {
                Button basicPressed = basicButtonList.getButtonPressed(t.x, t.y);
                int skillPressed = skillsButtonList.getIndexPressed(t.x, t.y);
                if(basicPressed != null){
                    switch(basicPressed.getName()){
                        case CLOSE_STRING:
                            backButton();
                            break;
                        case PREV_STRING:
                            if(skillsPage > 0){
                                skillsPage--;
                            }
                            break;
                        case NEXT_STRING:
                            if(skillsPage < maxPage){
                                skillsPage++;
                            }
                            break;
                        default:
                            System.out.println("AbsCharacterInfoScreen:update(): Invalid button selection: " + basicPressed.getName());
                    }
                }
                else if(skillPressed != -1){
                    displayText = (displayText == skillPressed) ? -1 : skillPressed + SKILLS_TEXT_PER_PAGE * skillsPage;
                }
            }
            uniqueUpdate(t);
        }
    }

    protected void uniqueUpdate(Input.TouchEvent t){
        //Do nothing. This will be overriden in each class
    }

    @Override
    public void paint(float deltaTime) {
        Graphics g = game.getGraphics();
        g.drawImage(background, BG_X, BG_Y);

        drawPortrait(g);
        drawElements(g);
        drawName(g);
        drawTopRows(g);
        drawSkills(g);
        drawDescription(g);
    }

    abstract protected void drawPortrait(Graphics g);
    abstract protected void drawElements(Graphics g);
    abstract protected void drawName(Graphics g);
    abstract protected void drawTopRows(Graphics g);
    abstract protected void drawSkills(Graphics g);
    abstract protected void drawDescription(Graphics g);

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
        game.setScreen(previousScreen);
    }
}
