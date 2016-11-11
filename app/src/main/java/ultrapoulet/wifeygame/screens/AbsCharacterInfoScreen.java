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

/**
 * Created by John on 11/10/2016.
 */

public abstract class AbsCharacterInfoScreen extends Screen {

    protected Screen previousScreen;

    protected Image background;
    protected static final int BG_X = 50;
    protected static final int BG_Y = 75;

    protected static final int CLOSE_LEFT_X = BG_X + 600;
    protected static final int CLOSE_RIGHT_X = CLOSE_LEFT_X + 75;
    protected static final int CLOSE_TOP_Y = BG_Y + 10;
    protected static final int CLOSE_BOT_Y = CLOSE_TOP_Y + 75;

    protected static final int CHAR_X = 30 + BG_X;
    protected static final int CHAR_Y = 100 + BG_Y;
    protected static final int DOUBLE_SCALE = 200;

    protected static final int ELEMENT_X = 357 + BG_X;
    protected static final int ATK_ELEMENT_Y = 158 + BG_Y;
    protected static final int RES_ELEMENT_Y = 269 + BG_Y;
    protected static final int WEAK_ELEMENT_Y = 380 + BG_Y;
    protected static final int QUARTER_SCALE = 25;

    protected Paint namePaint;
    protected int nameFontSize;
    protected int nameY;
    protected static final int MAX_NAME_FONT = 40;
    protected static final int MAX_NAME_SIZE = 240;
    protected static final int NAME_X = 402 + BG_X;
    protected static final int MAX_NAME_Y = 155 + BG_Y;

    protected Paint skillsPaint;
    protected static final int SKILLS_SIZE = 40;
    protected static final int SKILLS_PER_PAGE = 14;
    protected static final int SKILLS_LEFT_X = 32 + BG_X;
    protected static final int SKILLS_RIGHT_X = SKILLS_LEFT_X + 285;
    protected static final int SKILLS_BASE_Y = 495 + BG_Y;
    protected static final int SKILLS_OFFSET_Y = 55;

    protected static final int SKILLS_BUTTON_LEFT_X = 30 + BG_X;
    protected static final int SKILLS_BUTTON_OFFSET_X = 280;
    protected static final int SKILLS_BUTTON_TOP_Y = 455 + BG_Y;
    protected static final int SKILLS_BUTTON_OFFSET_Y = 55;

    protected static final int SKILLS_DESC_X = 32 + BG_X;
    protected static final int SKILLS_DESC_Y = 872 + BG_Y;
    protected static final int SKILLS_DESC_SIZE = 30;
    protected static final int SKILLS_DESC_WIDTH = 616;
    protected TextPaint descPaint;

    protected int skillsPage = 0;
    protected int displayText = -1;
    protected int maxPage;
    protected static final int SKILLS_PAGE_X = 600 + BG_X;
    protected static final int SKILLS_PREV_Y = 455 + BG_Y;
    protected static final int SKILLS_NEXT_Y = 730 + BG_Y;
    protected static final int SKILLS_PAGE_WIDTH = 50;
    protected static final int SKILLS_PAGE_HEIGHT = 105;
    protected static final int SKILLS_SCROLL_X = 600 + BG_X;
    protected static final int SKILLS_SCROLL_TOP_Y = 565 + BG_Y;
    protected static final int SKILLS_SCROLL_MAX_Y = 645 + BG_Y;

    private enum ButtonPressed{
        CLOSE,
        SKILLS0,
        SKILLS1,
        SKILLS2,
        SKILLS3,
        SKILLS4,
        SKILLS5,
        SKILLS6,
        SKILLS7,
        SKILLS8,
        SKILLS9,
        SKILLS10,
        SKILLS11,
        SKILLS12,
        SKILLS13,
        SKILLS14,
        SKILLS15,
        SKILLSPREV,
        SKILLSNEXT
        //Fill with skill buttons later
    }

    private ButtonPressed[] SkillButtons = {
            ButtonPressed.SKILLS0,
            ButtonPressed.SKILLS1,
            ButtonPressed.SKILLS2,
            ButtonPressed.SKILLS3,
            ButtonPressed.SKILLS4,
            ButtonPressed.SKILLS5,
            ButtonPressed.SKILLS6,
            ButtonPressed.SKILLS7,
            ButtonPressed.SKILLS8,
            ButtonPressed.SKILLS9,
            ButtonPressed.SKILLS10,
            ButtonPressed.SKILLS11,
            ButtonPressed.SKILLS12,
            ButtonPressed.SKILLS13,
            ButtonPressed.SKILLS14,
            ButtonPressed.SKILLS15
    };

    public AbsCharacterInfoScreen(Game game) {
        super(game);

        createPaints();
    }

    protected void createPaints(){
        namePaint = new Paint();
        namePaint.setTextAlign(Paint.Align.LEFT);
        namePaint.setColor(Color.BLACK);

        skillsPaint = new Paint();
        skillsPaint.setTextAlign(Paint.Align.LEFT);
        skillsPaint.setColor(Color.BLACK);
        skillsPaint.setTextSize(SKILLS_SIZE);

        descPaint = new TextPaint();
        descPaint.setTextAlign(Paint.Align.LEFT);
        descPaint.setColor(Color.BLACK);
        descPaint.setTextSize(SKILLS_DESC_SIZE);

        createUniquePaints();
    }

    public void setPreviousScreen(Screen screen){
        previousScreen = screen;
    }

    abstract protected void createUniquePaints();

    //This will be replaced later
    private ButtonPressed getButtonPressed(int x, int y){
        if(x >= CLOSE_LEFT_X && x <= CLOSE_RIGHT_X && y >= CLOSE_TOP_Y && y <= CLOSE_BOT_Y){
            return ButtonPressed.CLOSE;
        }
        if(x >= SKILLS_PAGE_X && x <= SKILLS_PAGE_X + SKILLS_PAGE_WIDTH && y >= SKILLS_PREV_Y && y <= SKILLS_PREV_Y + SKILLS_PAGE_HEIGHT){
            return ButtonPressed.SKILLSPREV;
        }
        if(x >= SKILLS_PAGE_X && x <= SKILLS_PAGE_X + SKILLS_PAGE_WIDTH && y >= SKILLS_NEXT_Y && y <= SKILLS_NEXT_Y + SKILLS_PAGE_HEIGHT){
            return ButtonPressed.SKILLSNEXT;
        }
        for(int i = 0; i < SKILLS_PER_PAGE; i++){
            int baseX = SKILLS_BUTTON_LEFT_X + (SKILLS_BUTTON_OFFSET_X * (i % 2));
            int baseY = SKILLS_BUTTON_TOP_Y + (SKILLS_BUTTON_OFFSET_Y * (i / 2));
            if(x >= baseX && x <= baseX + SKILLS_BUTTON_OFFSET_X &&
                    y >= baseY && y <= baseY + SKILLS_BUTTON_OFFSET_Y){
                return SkillButtons[i + (SKILLS_PER_PAGE * skillsPage)];
            }
        }
        return null;
    }

    @Override
    public void update(float deltaTime) {
        List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();
        for(int i = 0; i < touchEvents.size(); i++) {
            Input.TouchEvent t = touchEvents.get(i);
            if (t.type == Input.TouchEvent.TOUCH_UP) {
                ButtonPressed press = getButtonPressed(t.x, t.y);
                if(press == null){
                    continue;
                }
                int skillNum = 0;
                switch (press) {
                    case CLOSE:
                        backButton();
                        break;
                    case SKILLSPREV:
                        if(skillsPage > 0){
                            skillsPage--;
                        }
                        break;
                    case SKILLSNEXT:
                        if(skillsPage < maxPage){
                            skillsPage++;
                        }
                        break;
                    case SKILLS15:
                        skillNum++;
                    case SKILLS14:
                        skillNum++;
                    case SKILLS13:
                        skillNum++;
                    case SKILLS12:
                        skillNum++;
                    case SKILLS11:
                        skillNum++;
                    case SKILLS10:
                        skillNum++;
                    case SKILLS9:
                        skillNum++;
                    case SKILLS8:
                        skillNum++;
                    case SKILLS7:
                        skillNum++;
                    case SKILLS6:
                        skillNum++;
                    case SKILLS5:
                        skillNum++;
                    case SKILLS4:
                        skillNum++;
                    case SKILLS3:
                        skillNum++;
                    case SKILLS2:
                        skillNum++;
                    case SKILLS1:
                        skillNum++;
                    case SKILLS0:
                        displayText = (displayText == skillNum) ? -1 : skillNum + SKILLS_PER_PAGE * skillsPage;
                        break;
                    default:
                        break;
                }
            }
        }
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
