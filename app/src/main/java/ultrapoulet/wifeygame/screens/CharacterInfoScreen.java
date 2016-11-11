package ultrapoulet.wifeygame.screens;

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
import ultrapoulet.wifeygame.Assets;
import ultrapoulet.wifeygame.character.SkillsEnum;
import ultrapoulet.wifeygame.character.Weapon;
import ultrapoulet.wifeygame.character.WifeyCharacter;

/**
 * Created by John on 5/19/2016.
 */
public class CharacterInfoScreen extends AbsCharacterInfoScreen {

    private WifeyCharacter displayChar;

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

    public CharacterInfoScreen(Game game) {
        super(game);
        background = Assets.CharacterInfoScreen;
    }

    protected void createUniquePaints(){
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

    protected void drawPortrait(Graphics g){
        g.drawPercentageImage(displayChar.getImage(),CHAR_X, CHAR_Y, DOUBLE_SCALE, DOUBLE_SCALE);
    }

    protected void drawElements(Graphics g){
        g.drawPercentageImage(displayChar.getAttackElement().getElementImage(), ELEMENT_X, ATK_ELEMENT_Y, QUARTER_SCALE, QUARTER_SCALE);
        g.drawPercentageImage(displayChar.getStrongElement().getElementImage(), ELEMENT_X, RES_ELEMENT_Y, QUARTER_SCALE, QUARTER_SCALE);
        g.drawPercentageImage(displayChar.getWeakElement().getElementImage(), ELEMENT_X, WEAK_ELEMENT_Y, QUARTER_SCALE, QUARTER_SCALE);
    }

    protected void drawName(Graphics g){
        g.drawString(displayChar.getName(), NAME_X, nameY, namePaint);
    }

    protected void drawTopRows(Graphics g){
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
    }

    protected void drawSkills(Graphics g){
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
        if(maxPage == 0){
            g.drawImage(Assets.ScrollBarFull, SKILLS_SCROLL_X, SKILLS_SCROLL_TOP_Y);
        }
        else{
            int offsetPerPage = (SKILLS_SCROLL_MAX_Y - SKILLS_SCROLL_TOP_Y) / maxPage;
            g.drawImage(Assets.ScrollBarShort, SKILLS_SCROLL_X, SKILLS_SCROLL_TOP_Y + (offsetPerPage * skillsPage));
        }
    }

    protected void drawDescription(Graphics g){
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
