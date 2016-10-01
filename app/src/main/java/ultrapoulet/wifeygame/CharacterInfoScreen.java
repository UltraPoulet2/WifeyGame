package ultrapoulet.wifeygame;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;

import java.util.List;

import ultrapoulet.androidgame.framework.Game;
import ultrapoulet.androidgame.framework.Graphics;
import ultrapoulet.androidgame.framework.Image;
import ultrapoulet.androidgame.framework.Input;
import ultrapoulet.androidgame.framework.Input.TouchEvent;
import ultrapoulet.androidgame.framework.Screen;
import ultrapoulet.wifeygame.character.SkillsEnum;
import ultrapoulet.wifeygame.character.Weapon;
import ultrapoulet.wifeygame.character.WifeyCharacter;

/**
 * Created by John on 5/19/2016.
 */
public class CharacterInfoScreen extends Screen {

    private Screen previousScreen;

    private Image background;
    private static final int BG_X = 50;
    private static final int BG_Y = 75;

    private static final int CLOSE_LEFT_X = BG_X + 600;
    private static final int CLOSE_RIGHT_X = CLOSE_LEFT_X + 75;
    private static final int CLOSE_TOP_Y = BG_Y + 10;
    private static final int CLOSE_BOT_Y = CLOSE_TOP_Y + 75;

    private WifeyCharacter displayChar;
    private static final int CHAR_X = 30 + BG_X;
    private static final int CHAR_Y = 100 + BG_Y;
    private static final int DOUBLE_SCALE = 200;

    private static final int ELEMENT_X = 357 + BG_X;
    private static final int ATK_ELEMENT_Y = 158 + BG_Y;
    private static final int RES_ELEMENT_Y = 269 + BG_Y;
    private static final int WEAK_ELEMENT_Y = 380 + BG_Y;
    private static final int QUARTER_SCALE = 25;

    private Paint namePaint;
    private int nameFontSize;
    private int nameY;
    private static final int MAX_NAME_FONT = 40;
    private static final int MAX_NAME_SIZE = 240;
    private static final int NAME_X = 402 + BG_X;
    private static final int MAX_NAME_Y = 155 + BG_Y;

    private Paint levelPaint;
    private static final int LEVEL_SIZE = 34;
    private static final int LEVEL_X = 425 + BG_X;
    private static final int LEVEL_Y = 237 + BG_Y;

    private Paint statPaint;
    private static final int STAT_SIZE = 34;
    private static final int HP_X = 440 + BG_X;
    private static final int STR_X = 85 + HP_X;
    private static final int MAG_X = 85 + STR_X;
    private static final int STAT_Y = 322 + BG_Y;

    private Paint weaponPaint;
    private int weaponFontSize;
    private int weaponY;
    private static final int MAX_WEAPON_FONT = 34;
    private static final int MAX_WEAPON_SIZE = 140;
    private static final int WEAPON_X = 525 + BG_X;
    private static final int MAX_WEAPON_Y = 407 + BG_Y;

    //These will be removed when hits images are added
    private Paint hitsPaint;
    private static final int HITS_SIZE = 34;
    private static final int HITS_X = 100 + WEAPON_X;
    private static final int HITS_Y = 407 + BG_Y;

    private Paint skillsPaint;
    private static final int SKILLS_SIZE = 40;
    private static final int SKILLS_PER_PAGE = 14;
    private static final int SKILLS_LEFT_X = 32 + BG_X;
    private static final int SKILLS_RIGHT_X = SKILLS_LEFT_X + 285;
    private static final int SKILLS_BASE_Y = 495 + BG_Y;
    private static final int SKILLS_OFFSET_Y = 55;

    private static final int SKILLS_BUTTON_LEFT_X = 30 + BG_X;
    private static final int SKILLS_BUTTON_OFFSET_X = 280;
    private static final int SKILLS_BUTTON_TOP_Y = 455 + BG_Y;
    private static final int SKILLS_BUTTON_OFFSET_Y = 55;

    private static final int SKILLS_DESC_X = 32 + BG_X;
    private static final int SKILLS_DESC_Y = 872 + BG_Y;
    private static final int SKILLS_DESC_SIZE = 30;
    private static final int SKILLS_DESC_WIDTH = 616;
    private TextPaint descPaint;

    private int displayText = -1;
    private int skillsPage;
    private int maxPage;
    private static final int SKILLS_PAGE_X = 600 + BG_X;
    private static final int SKILLS_PREV_Y = 455 + BG_Y;
    private static final int SKILLS_NEXT_Y = 730 + BG_Y;
    private static final int SKILLS_PAGE_WIDTH = 50;
    private static final int SKILLS_PAGE_HEIGHT = 105;

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

    public CharacterInfoScreen(Game game) {
        super(game);
        background = Assets.CharacterInfoScreen;

        createPaints();
    }

    private void createPaints(){
        namePaint = new Paint();
        namePaint.setTextAlign(Align.LEFT);
        namePaint.setColor(Color.BLACK);

        levelPaint = new Paint();
        levelPaint.setTextAlign(Align.CENTER);
        levelPaint.setColor(Color.BLACK);
        levelPaint.setTextSize(LEVEL_SIZE);

        statPaint = new Paint();
        statPaint.setTextAlign(Align.CENTER);
        statPaint.setColor(Color.BLACK);
        statPaint.setTextSize(STAT_SIZE);

        weaponPaint = new Paint();
        weaponPaint.setTextAlign(Align.CENTER);
        weaponPaint.setColor(Color.BLACK);

        hitsPaint = new Paint();
        hitsPaint.setTextAlign(Align.CENTER);
        hitsPaint.setColor(Color.BLACK);
        hitsPaint.setTextSize(HITS_SIZE);

        skillsPaint = new Paint();
        skillsPaint.setTextAlign(Align.LEFT);
        skillsPaint.setColor(Color.BLACK);
        skillsPaint.setTextSize(SKILLS_SIZE);

        descPaint = new TextPaint();
        descPaint.setTextAlign(Align.LEFT);
        descPaint.setColor(Color.BLACK);
        descPaint.setTextSize(SKILLS_DESC_SIZE);
    }

    public void setPreviousScreen(Screen screen){
        previousScreen = screen;
    }

    public void setChar(WifeyCharacter input){
        displayChar = input;
        nameFontSize = MAX_NAME_FONT;
        namePaint.setTextSize(nameFontSize);
        while(namePaint.measureText(displayChar.getName()) > MAX_NAME_SIZE){
            nameFontSize--;
            namePaint.setTextSize(nameFontSize);
        }
        nameY = MAX_NAME_Y - ((MAX_NAME_FONT - nameFontSize) / 2);

        //Using weapon type as name for now
        String weaponName = displayChar.getWeapon().getWeaponType();
        weaponFontSize = MAX_WEAPON_FONT;
        weaponPaint.setTextSize(weaponFontSize);
        while(weaponPaint.measureText(weaponName) > MAX_WEAPON_SIZE){
            weaponFontSize--;
            weaponPaint.setTextSize(weaponFontSize);
        }
        weaponY = MAX_WEAPON_Y - ((MAX_WEAPON_FONT - weaponFontSize) / 2);

        displayText = -1;

        maxPage = (displayChar.getSkills().size() / SKILLS_PER_PAGE);
        skillsPage = 0;
    }

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
            if (t.type == TouchEvent.TOUCH_UP) {
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
        Graphics g= game.getGraphics();
        g.drawImage(background, BG_X, BG_Y);
        g.drawPercentageImage(displayChar.getImage(),CHAR_X, CHAR_Y, DOUBLE_SCALE, DOUBLE_SCALE);

        g.drawPercentageImage(displayChar.getAttackElement().getElementImage(), ELEMENT_X, ATK_ELEMENT_Y, QUARTER_SCALE, QUARTER_SCALE);
        g.drawPercentageImage(displayChar.getStrongElement().getElementImage(), ELEMENT_X, RES_ELEMENT_Y, QUARTER_SCALE, QUARTER_SCALE);
        g.drawPercentageImage(displayChar.getWeakElement().getElementImage(), ELEMENT_X, WEAK_ELEMENT_Y, QUARTER_SCALE, QUARTER_SCALE);

        g.drawString(displayChar.getName(), NAME_X, nameY, namePaint);

        g.drawString("1", LEVEL_X, LEVEL_Y, levelPaint);

        g.drawString(String.valueOf(displayChar.getHP()), HP_X, STAT_Y, statPaint);
        g.drawString(String.valueOf(displayChar.getStrength()), STR_X, STAT_Y, statPaint);
        g.drawString(String.valueOf(displayChar.getMagic()), MAG_X, STAT_Y, statPaint);

        //Draw image for weapon category

        Weapon charWeapon = displayChar.getWeapon();
        //Draw string for weapon name
        g.drawString(charWeapon.getWeaponType(), WEAPON_X, weaponY, weaponPaint);
        //Draw image for number hits
        g.drawString(String.valueOf(charWeapon.getNumHits()), HITS_X, HITS_Y, hitsPaint);

        //List out names for the skills
        for(int i = SKILLS_PER_PAGE * skillsPage; i < SKILLS_PER_PAGE * skillsPage + SKILLS_PER_PAGE && i < displayChar.getSkills().size(); i++){
            SkillsEnum skill = displayChar.getSkills().get(i);
            int xOffset;
            int yOffset = SKILLS_BASE_Y + ((i % SKILLS_PER_PAGE) / 2) * SKILLS_OFFSET_Y;
            if(i % 2 == 0) {
                xOffset = SKILLS_LEFT_X;
            }
            else{
                xOffset = SKILLS_RIGHT_X;
            }
            g.drawString(skill.getSkillName(), xOffset, yOffset, skillsPaint);
        }

        if(displayText != -1 && displayChar.getSkills().size() > displayText){
            String desc = displayChar.getSkills().get(displayText).getSkillDesc();
            g.drawMultiLineString(desc, SKILLS_DESC_X, SKILLS_DESC_Y, SKILLS_DESC_WIDTH, descPaint);
        }
    }

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
