package ultrapoulet.wifeygame.screens;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.text.TextPaint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ultrapoulet.androidgame.framework.Game;
import ultrapoulet.androidgame.framework.Graphics;
import ultrapoulet.androidgame.framework.Image;
import ultrapoulet.androidgame.framework.Input;
import ultrapoulet.androidgame.framework.Screen;
import ultrapoulet.androidgame.framework.helpers.Button;
import ultrapoulet.androidgame.framework.helpers.ButtonList;
import ultrapoulet.wifeygame.Assets;
import ultrapoulet.wifeygame.battle.BattleWifey;
import ultrapoulet.wifeygame.character.Element;
import ultrapoulet.wifeygame.character.SkillsEnum;
import ultrapoulet.wifeygame.character.TransformWifey;
import ultrapoulet.wifeygame.character.UniqueSkillsEnum;
import ultrapoulet.wifeygame.character.Weapon;
import ultrapoulet.wifeygame.character.WeaponSkillsEnum;
import ultrapoulet.wifeygame.character.WifeyCharacter;

/**
 * Created by John on 5/19/2016.
 */
public class CharacterInfoScreen extends AbsCharacterInfoScreen {

    private WifeyCharacter displayChar;
    private List<TransformWifey> transformations;

    private Image displayImage;
    private String displayName;
    private String displayExp;
    private int displayStrength;
    private int displayMagic;
    private ArrayList<SkillsEnum> displaySkills = new ArrayList<>();
    private UniqueSkillsEnum displayUniqueSkill;
    private WeaponSkillsEnum displayWeaponSkill;
    private Weapon displayWeapon;
    private Element displayAttackElement;
    private Element displayStrongElement;
    private Element displayWeakElement;

    private ButtonList uniqueButtons;
    private Button prevTransform;
    private Button nextTransform;
    private Button favoriteButton;
    private int transformPage;
    private int maxTransformPage;
    private static final int TRANSFORM_PAGE_WIDTH = 50;
    private static final int TRANSFORM_PREV_PAGE_LEFT_X = 40 + BG_X;
    private static final int TRANSFORM_PREV_PAGE_RIGHT_X = TRANSFORM_PREV_PAGE_LEFT_X + TRANSFORM_PAGE_WIDTH;
    private static final int TRANSFORM_NEXT_PAGE_LEFT_X = TRANSFORM_PREV_PAGE_LEFT_X + 270;
    private static final int TRANSFORM_NEXT_PAGE_RIGHT_X = TRANSFORM_NEXT_PAGE_LEFT_X + TRANSFORM_PAGE_WIDTH;
    private static final int TRANSFORM_PAGE_TOP_Y = 100 + BG_Y;
    private static final int TRANSFORM_PAGE_BOT_Y = TRANSFORM_PAGE_TOP_Y + 320;
    private static final int FAVORITE_LEFT_X = BG_X + 610;
    private static final int FAVORITE_RIGHT_X = FAVORITE_LEFT_X + 50;
    private static final int FAVORITE_TOP_Y = BG_Y + 115;
    private static final int FAVORITE_BOT_Y = FAVORITE_TOP_Y + 50;

    private static final int SKILLS_DESC_SIZE = 30;
    private static final int MAX_NAME_SIZE = 191;

    private String displayTitle;
    private TextPaint titlePaint;
    private static final int MAX_TITLE_FONT = 40;
    private static final int MIN_TITLE_FONT = 20;
    private static final int MAX_TITLE_SIZE = 236;
    private static final int TWO_LINE_TITLE_FONT = 20;
    private static final int TITLE_X = 412 + BG_X;
    private static final int MAX_TITLE_Y = 240 + BG_Y;
    private static final int TWO_LINE_TITLE_Y = 200 + BG_Y;

    private Paint levelPaint;
    private static final int LEVEL_SIZE = 34;
    private static final int LEVEL_X = 435 + BG_X;
    private static final int LEVEL_Y = 322 + BG_Y;

    private Paint expPaint;
    private static final int MAX_EXP_FONT = 34;
    private static final int MAX_EXP_SIZE = 195;
    private static final int EXP_X = 562 + BG_X;
    private static final int EXP_Y = 322 + BG_Y;

    private static final int EXP_BAR_X = 465 + BG_X;
    private static final int EXP_BAR_Y = 285 + BG_Y;
    private static final int EXP_BAR_MAX_WIDTH = 195;
    private static final int EXP_BAR_HEIGHT = 50;

    private Paint statPaint;
    private static final int STAT_SIZE = 34;
    private static final int HP_X = 450 + BG_X;
    private static final int STR_X = 85 + HP_X;
    private static final int MAG_X = 85 + STR_X;
    private static final int STAT_Y = 407 + BG_Y;



    public CharacterInfoScreen(Game game, Screen previousScreen) {
        super(game, previousScreen);
        background = Assets.CharacterInfoScreen;
        createUniqueButtons();
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

        descPaint.setTextSize(SKILLS_DESC_SIZE);

        expPaint = new Paint();
        expPaint.setTextAlign(Align.CENTER);
        expPaint.setColor(Color.WHITE);

        titlePaint = new TextPaint();
        namePaint.setTextAlign(Align.LEFT);
        namePaint.setColor(Color.BLACK);
    }

    protected String getWeaponType(){
        return displayWeapon.getWeaponType();
    }

    public void createUniqueButtons(){
        uniqueButtons = new ButtonList();
        prevTransform = new Button(TRANSFORM_PREV_PAGE_LEFT_X, TRANSFORM_PREV_PAGE_RIGHT_X, TRANSFORM_PAGE_TOP_Y, TRANSFORM_PAGE_BOT_Y, false, "TRANS_PREV", Assets.TransformPrevEnable, Assets.TransformPrevDisable);
        nextTransform = new Button(TRANSFORM_NEXT_PAGE_LEFT_X, TRANSFORM_NEXT_PAGE_RIGHT_X, TRANSFORM_PAGE_TOP_Y, TRANSFORM_PAGE_BOT_Y, false, "TRANS_NEXT", Assets.TransformNextEnable, Assets.TransformNextDisable);
        favoriteButton = new Button(FAVORITE_LEFT_X, FAVORITE_RIGHT_X, FAVORITE_TOP_Y, FAVORITE_BOT_Y, true, "FAVORITE", Assets.Favorite);
        uniqueButtons.addButton(prevTransform);
        uniqueButtons.addButton(nextTransform);
        uniqueButtons.addButton(favoriteButton);
    }

    public void setChar(WifeyCharacter input){
        displayChar = input;
        transformations = displayChar.getTransformations();

        resetSkillDisplay();

        displayExp = displayChar.getExperienceString();

        transformPage = 0;
        maxTransformPage = transformations.size();
        prevTransform.setActive(false);
        nextTransform.setActive(maxTransformPage != transformPage);
        prevTransform.setHidden(maxTransformPage == 0);
        nextTransform.setHidden(maxTransformPage == 0);

        setDefaultDisplayInfo();
        updateFavoriteButton();
        updateSkillButtons();
    }

    private void updateFavoriteButton(){
        if(displayChar != null && displayChar.isFavorite()) {
            favoriteButton.setHidden(false);
        }
        else {
            favoriteButton.setHidden(true);
        }
    }

    private void resetSkillDisplay() {
        displayText = -1;
        bDisplayUnique = false;
        bDisplayWeaponSkill = false;
    }

    private void setDefaultDisplayInfo() {
        displayName = displayChar.getName();
        displayImage = displayChar.getImage(game.getGraphics());
        displayStrength = displayChar.getStrength();
        displayMagic = displayChar.getMagic();
        displaySkills.clear();
        displaySkills.addAll(displayChar.getSkills());
        displayWeapon = displayChar.getWeapon();
        displayAttackElement = displayChar.getAttackElement();
        displayStrongElement = displayChar.getStrongElement();
        displayWeakElement = displayChar.getWeakElement();
        if(displayChar.getTitle() != null){
            displayTitle = displayChar.getTitle() + " Wifey";
        }
        else {
            displayTitle = "Wifey";
        }
        displayUniqueSkill = displayChar.getUniqueSkill();
        displayWeaponSkill = displayChar.getWeaponSkill();
    }

    private void updateSkillButtons() {
        for(int i = 0; i < 4; i++) {
            skillsButtonList.get(i).setActive(displaySkills.size() > i);
        }
        uniqueSkillButton.setActive(displayUniqueSkill != null);
        weaponSkillButton.setActive(displayWeaponSkill != null);
    }

    private void incrementDisplayInfo() {
        TransformWifey displayForm = transformations.get(transformPage - 1);
        displayName = displayForm.getName();
        displayImage = displayForm.getImage(game.getGraphics());
        displayStrength = displayForm.getStrength();
        displayMagic = displayForm.getMagic();
        if(displayForm.getWeapon() != null){
            displayWeapon = displayForm.getWeapon();
        }
        if(displayForm.getAttackElement() != null){
            displayAttackElement = displayForm.getAttackElement();
        }
        if(displayForm.getStrongElement() != null){
            displayStrongElement = displayForm.getStrongElement();
        }
        if(displayForm.getWeakElement() != null){
            displayWeakElement = displayForm.getWeakElement();
        }
        displaySkills.addAll(displayForm.getAddSkills());
        displaySkills.removeAll(displayForm.getRemoveSkills());
        if(displayForm.getUniqueSkill() != null){
            displayUniqueSkill = displayForm.getUniqueSkill();
        }
        if(displayForm.getWeaponSkill() != null){
            displayWeaponSkill = displayForm.getWeaponSkill();
        }
        Collections.sort(displaySkills, SkillsEnum.SKILLS_ENUM_COMPARATOR);
        resetSkillDisplay();
        updateSkillButtons();
    }

    private void decrementDisplayInfo() {
        if(transformPage == 0){
            setDefaultDisplayInfo();
        }
        else {
            TransformWifey displayForm = transformations.get(transformPage - 1);
            displayName = displayForm.getName();
            displayImage = displayForm.getImage(game.getGraphics());
            displayStrength = displayForm.getStrength();
            displayMagic = displayForm.getMagic();

            boolean weaponFound = false;
            boolean attackElementFound = false;
            boolean strongElementFound = false;
            boolean weakElementFound = false;
            boolean uniqueSkillFound = false;
            boolean weaponSkillFound = false;
            for (int i = transformPage - 1; i >= 0; i--) {
                TransformWifey temp = transformations.get(i);
                if (!weaponFound && temp.getWeapon() != null) {
                    displayWeapon = temp.getWeapon();
                    weaponFound = true;
                }
                if (!attackElementFound && temp.getAttackElement() != null) {
                    displayAttackElement = temp.getAttackElement();
                    attackElementFound = true;
                }
                if (!strongElementFound && temp.getStrongElement() != null) {
                    displayStrongElement = temp.getStrongElement();
                    strongElementFound = true;
                }
                if (!weakElementFound && temp.getWeakElement() != null) {
                    displayWeakElement = temp.getWeakElement();
                    weakElementFound = true;
                }
                if(!uniqueSkillFound && temp.getUniqueSkill() != null) {
                    displayUniqueSkill = temp.getUniqueSkill();
                    uniqueSkillFound = true;
                }
                if(!weaponSkillFound && temp.getWeaponSkill() != null) {
                    displayWeaponSkill = temp.getWeaponSkill();
                    weaponSkillFound = true;
                }
            }
            if(!weaponFound){
                displayWeapon = displayChar.getWeapon();
            }
            if(!attackElementFound){
                displayAttackElement = displayChar.getAttackElement();
            }
            if(!strongElementFound){
                displayStrongElement = displayChar.getStrongElement();
            }
            if(!weakElementFound){
                displayWeakElement = displayChar.getWeakElement();
            }

            TransformWifey prevForm = transformations.get(transformPage);
            //Since decrementing, remove the skills previously added
            displaySkills.removeAll(prevForm.getAddSkills());
            //Since decrementing, add skills previously removed
            displaySkills.addAll(prevForm.getRemoveSkills());
            Collections.sort(displaySkills, SkillsEnum.SKILLS_ENUM_COMPARATOR);
        }
        resetSkillDisplay();
        updateSkillButtons();
    }

    protected void drawPortrait(Graphics g){
        g.drawPercentageImage(displayImage, CHAR_X, CHAR_Y, DOUBLE_SCALE, DOUBLE_SCALE);

        uniqueButtons.drawImage(g);
    }

    protected void drawElements(Graphics g){
        g.drawPercentageImage(displayAttackElement.getElementImage(), ELEMENT_X, ATK_ELEMENT_Y, QUARTER_SCALE, QUARTER_SCALE);
        g.drawPercentageImage(displayStrongElement.getElementImage(), ELEMENT_X, RES_ELEMENT_Y, QUARTER_SCALE, QUARTER_SCALE);
        g.drawPercentageImage(displayWeakElement.getElementImage(), ELEMENT_X, WEAK_ELEMENT_Y, QUARTER_SCALE, QUARTER_SCALE);
    }

    protected void drawName(Graphics g){
        if(g.canDrawString(displayName, namePaint, MAX_NAME_SIZE, MIN_NAME_FONT)){
            g.drawString(displayName, NAME_X, MAX_NAME_Y, namePaint, MAX_NAME_SIZE, MAX_NAME_FONT);
        }
        else{
            namePaint.setTextSize(TWO_LINE_NAME_FONT);
            g.drawMultiLineString(displayName, NAME_X, TWO_LINE_NAME_Y, MAX_NAME_SIZE, namePaint);
        }
    }

    protected void drawTopRows(Graphics g){
        if(g.canDrawString(displayTitle, titlePaint, MAX_TITLE_SIZE, MIN_TITLE_FONT)){
            g.drawString(displayTitle, TITLE_X, MAX_TITLE_Y, titlePaint, MAX_TITLE_SIZE, MAX_TITLE_FONT);
        }
        else{
            titlePaint.setTextSize(TWO_LINE_TITLE_FONT);
            g.drawMultiLineString(displayTitle, TITLE_X, TWO_LINE_TITLE_Y, MAX_TITLE_SIZE, titlePaint);
        }
        g.drawString(String.valueOf(displayChar.getLevel()), LEVEL_X, LEVEL_Y, levelPaint);
        int expWidth = (int) (EXP_BAR_MAX_WIDTH * displayChar.getExperiencePercent());
        g.drawScaledImage(Assets.SmallYellowBar, EXP_BAR_X, EXP_BAR_Y, expWidth, EXP_BAR_HEIGHT);
        g.drawString(displayExp, EXP_X, EXP_Y, expPaint, MAX_EXP_SIZE, MAX_EXP_FONT);

        g.drawString(String.valueOf(BattleWifey.calculateHP(displayStrength)), HP_X, STAT_Y, statPaint);
        g.drawString(String.valueOf(displayStrength), STR_X, STAT_Y, statPaint);
        g.drawString(String.valueOf(displayMagic), MAG_X, STAT_Y, statPaint);
    }

    protected void drawSkills(Graphics g){
        if(displayUniqueSkill != null){
            if(bDisplayUnique){
                g.drawRect(UNIQUE_SKILL_LEFT_X, TOP_SKILLS_TOP_Y, UNIQUE_SKILL_RIGHT_X - UNIQUE_SKILL_LEFT_X, TOP_SKILLS_BOT_Y - TOP_SKILLS_TOP_Y, SKILL_HIGHLIGHT_COLOR);
            }
            g.drawString(displayUniqueSkill.getSkillName(), UNIQUE_X, MAX_WEAPON_Y, weaponPaint, MAX_UNIQUE_SIZE, MAX_WEAPON_FONT);
        }
        else {
            g.drawString("--NONE--", UNIQUE_X, MAX_WEAPON_Y, weaponPaint, MAX_UNIQUE_SIZE, MAX_WEAPON_FONT);
        }

        //Draw string for weapon name
        if(displayWeaponSkill != null){
            if(bDisplayWeaponSkill){
                g.drawRect(WEAPON_SKILL_LEFT_X, TOP_SKILLS_TOP_Y, WEAPON_SKILL_RIGHT_X - WEAPON_SKILL_LEFT_X, TOP_SKILLS_BOT_Y - TOP_SKILLS_TOP_Y, SKILL_HIGHLIGHT_COLOR);
            }
            g.drawString(displayWeaponSkill.getSkillName(), WEAPON_X, MAX_WEAPON_Y, weaponPaint, MAX_WEAPON_SIZE, MAX_WEAPON_FONT);
        }
        else {
            //We'll print default weapon and let the image describe the weapon
            g.drawString("--Default Weapon--", WEAPON_X, MAX_WEAPON_Y, weaponPaint, MAX_WEAPON_SIZE, MAX_WEAPON_FONT);
        }
        //Draw image for weapon
        g.drawImage(displayWeapon.getImage(), WEAPONS_IMAGE_LEFT_X, WEAPONS_IMAGE_TOP_Y);

        //Draw image for number hits
        g.drawImage(getHitsImage(displayWeapon.getNumHits()), HITS_X, HITS_Y);

        //List out names for the skills
        for(int i = 0; i < 4 && i < displaySkills.size(); i++){
            if(displayText == i){
                int highlightX = displayText % 2 == 0 ? SKILLS_BUTTON_LEFT_X : SKILLS_BUTTON_LEFT_X + (SKILLS_BUTTON_WIDTH +SKILLS_BUTTON_OFFSET_X);
                int highlightY = displayText / 2 == 0 ? SKILLS_BUTTON_TOP_Y : SKILLS_BUTTON_TOP_Y + (SKILLS_BUTTON_HEIGHT + SKILLS_BUTTON_OFFSET_Y);
                g.drawRect(highlightX, highlightY, SKILLS_BUTTON_WIDTH, SKILLS_BUTTON_HEIGHT, SKILL_HIGHLIGHT_COLOR);
            }
            SkillsEnum skill = displaySkills.get(i);
            int xOffset;
            int yOffset = SKILLS_TEXT_BASE_Y + (i / 2) * SKILLS_TEXT_OFFSET_Y;
            if(i % 2 == 0) {
                xOffset = SKILLS_TEXT_LEFT_X;
            }
            else{
                xOffset = SKILLS_TEXT_RIGHT_X;
            }
            g.drawString(skill.getSkillName(), xOffset, yOffset, skillsPaint, SKILLS_TEXT_SIZE, SKILLS_TEXT_FONT);
        }
    }

    protected void drawDescription(Graphics g){
        if(displayText != -1 && displaySkills.size() > displayText){
            String desc = displaySkills.get(displayText).getSkillDesc();
            g.drawMultiLineString(desc, SKILLS_DESC_X, SKILLS_DESC_Y, SKILLS_DESC_WIDTH, descPaint);
        }
        else if(bDisplayUnique && displayUniqueSkill != null){
            String desc = displayUniqueSkill.getSkillDesc();
            g.drawMultiLineString(desc, SKILLS_DESC_X, SKILLS_DESC_Y, SKILLS_DESC_WIDTH, descPaint);
        }
        else if(bDisplayWeaponSkill && displayWeaponSkill != null){
            String desc = displayWeaponSkill.getSkillDesc();
            g.drawMultiLineString(desc, SKILLS_DESC_X, SKILLS_DESC_Y, SKILLS_DESC_WIDTH, descPaint);
        }
    }

    @Override
    protected void uniqueUpdate(Input.TouchEvent t) {
        if (t.type == Input.TouchEvent.TOUCH_UP) {
            Button buttonPressed = uniqueButtons.getButtonPressed(t.x, t.y);
            if(buttonPressed == prevTransform){
                transformPage--;
                decrementDisplayInfo();
                nextTransform.setActive(true);
                prevTransform.setActive(transformPage != 0);
            }
            else if(buttonPressed == nextTransform) {
                transformPage++;
                incrementDisplayInfo();
                prevTransform.setActive(true);
                nextTransform.setActive(transformPage != maxTransformPage);
            }
            else if(buttonPressed == favoriteButton){
                displayChar.toggleFavorite();
                updateFavoriteButton();
            }
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
