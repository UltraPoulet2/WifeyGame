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

/**
 * Created by John on 11/10/2016.
 */

public abstract class AbsCharacterInfoScreen extends Screen {

    protected Screen previousScreen;

    protected Image background;
    protected static final int BG_X = 50;
    protected static final int BG_Y = 170;

    protected static final int CHAR_X = 40 + BG_X;
    protected static final int CHAR_Y = 100 + BG_Y;
    protected static final int DOUBLE_SCALE = 200;

    protected static final int ELEMENT_X = 367 + BG_X;
    protected static final int ATK_ELEMENT_Y = 158 + BG_Y;
    protected static final int RES_ELEMENT_Y = 269 + BG_Y;
    protected static final int WEAK_ELEMENT_Y = 380 + BG_Y;
    protected static final int QUARTER_SCALE = 25;

    protected TextPaint namePaint;
    protected static final int MAX_NAME_FONT = 40;
    protected static final int MIN_NAME_FONT = 20;
    protected static final int TWO_LINE_NAME_FONT = 20;
    protected static final int NAME_X = 412 + BG_X;
    protected static final int MAX_NAME_Y = 155 + BG_Y;
    protected static final int TWO_LINE_NAME_Y = 115 + BG_Y;

    protected Paint skillsPaint;
    protected static final int SKILLS_TEXT_FONT = 40;
    protected static final int SKILLS_TEXT_SIZE = 303;
    protected static final int SKILLS_TEXT_LEFT_X = 42 + BG_X;
    protected static final int SKILLS_TEXT_RIGHT_X = SKILLS_TEXT_LEFT_X + 313;
    protected static final int SKILLS_TEXT_BASE_Y = 580 + BG_Y;
    protected static final int SKILLS_TEXT_OFFSET_Y = 55;

    protected static final int SKILLS_DESC_X = 42 + BG_X;
    protected static final int SKILLS_DESC_Y = 680 + BG_Y;
    protected static final int SKILLS_DESC_WIDTH = 616;
    protected TextPaint descPaint;

    protected int displayText = -1;
    protected boolean bDisplayUnique = false;
    protected boolean bDisplayWeaponSkill = false;

    protected ButtonList basicButtonList;

    protected static final int CLOSE_LEFT_X = BG_X + 600;
    protected static final int CLOSE_RIGHT_X = CLOSE_LEFT_X + 75;
    protected static final int CLOSE_TOP_Y = BG_Y + 10;
    protected static final int CLOSE_BOT_Y = CLOSE_TOP_Y + 75;
    protected static final String CLOSE_STRING = "Close";

    protected static final int UNIQUE_SKILL_LEFT_X = BG_X + 40;
    protected static final int UNIQUE_SKILL_RIGHT_X = UNIQUE_SKILL_LEFT_X + 307;
    protected static final int WEAPON_SKILL_LEFT_X = BG_X + 353;
    protected static final int WEAPON_SKILL_RIGHT_X = BG_X + 540;
    protected static final int TOP_SKILLS_TOP_Y = BG_Y + 455;
    protected static final int TOP_SKILLS_BOT_Y = TOP_SKILLS_TOP_Y + 50;
    protected static final String UNIQUE_STRING = "Unique";
    protected static final String WEAPON_STRING = "Weapon";

    protected ButtonList skillsButtonList;
    protected static final int SKILLS_BUTTON_LEFT_X = 40 + BG_X;
    protected static final int SKILLS_BUTTON_WIDTH = 307;
    protected static final int SKILLS_BUTTON_RIGHT_X = SKILLS_BUTTON_LEFT_X + SKILLS_BUTTON_WIDTH;
    protected static final int SKILLS_BUTTON_OFFSET_X = 6;
    protected static final int SKILLS_BUTTON_TOP_Y = 540 + BG_Y;
    protected static final int SKILLS_BUTTON_HEIGHT = 50;
    protected static final int SKILLS_BUTTON_BOT_Y = SKILLS_BUTTON_TOP_Y + SKILLS_BUTTON_HEIGHT;
    protected static final int SKILLS_BUTTON_OFFSET_Y = 5;

    protected Paint weaponPaint;
    protected static final int MAX_WEAPON_FONT = 40;
    protected static final int MAX_WEAPON_SIZE = 197;
    protected static final int WEAPON_X = 355 + BG_X;
    protected static final int MAX_WEAPON_Y = 495 + BG_Y;
    //Unique Skills will use same info as Weapon
    protected static final int UNIQUE_X = 42 + BG_X;
    protected static final int MAX_UNIQUE_SIZE = 303;

    protected static final int HITS_X = 555 + BG_X;
    protected static final int HITS_Y = 455 + BG_Y;

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

        weaponPaint = new Paint();
        weaponPaint.setTextAlign(Paint.Align.LEFT);
        weaponPaint.setColor(Color.BLACK);

        createUniquePaints();
    }

    protected void createButtons(){
        basicButtonList = new ButtonList();
        basicButtonList.addButton(new Button(CLOSE_LEFT_X, CLOSE_RIGHT_X, CLOSE_TOP_Y, CLOSE_BOT_Y, true, CLOSE_STRING));
        basicButtonList.addButton(new Button(UNIQUE_SKILL_LEFT_X, UNIQUE_SKILL_RIGHT_X, TOP_SKILLS_TOP_Y, TOP_SKILLS_BOT_Y, true, UNIQUE_STRING));
        basicButtonList.addButton(new Button(WEAPON_SKILL_LEFT_X, WEAPON_SKILL_RIGHT_X, TOP_SKILLS_TOP_Y, TOP_SKILLS_BOT_Y, true, WEAPON_STRING));

        skillsButtonList = new ButtonList();
        for(int i = 0; i < 4; i++) {
            int leftX = SKILLS_BUTTON_LEFT_X + ((SKILLS_BUTTON_WIDTH +SKILLS_BUTTON_OFFSET_X) * (i % 2));
            int rightX = SKILLS_BUTTON_RIGHT_X  + ((SKILLS_BUTTON_WIDTH +SKILLS_BUTTON_OFFSET_X) * (i % 2));
            int topY = SKILLS_BUTTON_TOP_Y + ((SKILLS_BUTTON_HEIGHT + SKILLS_BUTTON_OFFSET_Y) * (i / 2));
            int botY = SKILLS_BUTTON_BOT_Y + ((SKILLS_BUTTON_HEIGHT + SKILLS_BUTTON_OFFSET_Y) * (i / 2));
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
                        case UNIQUE_STRING:
                            displayText = -1;
                            bDisplayUnique = !bDisplayUnique;
                            bDisplayWeaponSkill = false;
                            break;
                        case WEAPON_STRING:
                            displayText = -1;
                            bDisplayUnique = false;
                            bDisplayWeaponSkill = !bDisplayWeaponSkill;
                            break;
                        default:
                            System.out.println("AbsCharacterInfoScreen:update(): Invalid button selection: " + basicPressed.getName());
                    }
                }
                else if(skillPressed != -1){
                    displayText = (displayText == skillPressed) ? -1 : skillPressed;
                    bDisplayUnique = false;
                    bDisplayWeaponSkill = false;
                }
            }
            uniqueUpdate(t);
        }
    }

    protected void uniqueUpdate(Input.TouchEvent t){
        //Do nothing. This will be overriden in each class
    }

    protected Image getHitsImage(int i){
        return Assets.NumberHits.get(i-2);
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
