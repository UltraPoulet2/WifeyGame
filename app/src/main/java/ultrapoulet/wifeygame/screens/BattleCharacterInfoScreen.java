package ultrapoulet.wifeygame.screens;

import android.graphics.Color;
import android.graphics.Paint;

import ultrapoulet.androidgame.framework.Game;
import ultrapoulet.androidgame.framework.Graphics;
import ultrapoulet.androidgame.framework.Image;
import ultrapoulet.androidgame.framework.Screen;
import ultrapoulet.androidgame.framework.helpers.NumberPrinter;
import ultrapoulet.wifeygame.Assets;
import ultrapoulet.wifeygame.battle.BattleEnemy;
import ultrapoulet.wifeygame.battle.BattleWifey;
import ultrapoulet.wifeygame.battle.skills.AbsSkill;
import ultrapoulet.wifeygame.battle.skills.AbsSkill.Multipliers;

/**
 * Created by John on 8/17/2016.
 */
public class BattleCharacterInfoScreen extends AbsCharacterInfoScreen {

    private BattleWifey displayChar;

    private BattleEnemy displayEnemy;

    private Multipliers multipliers;

    private static final int SKILLS_DESC_SIZE = 25;
    private static final int MAX_NAME_SIZE = 236;

    private static final int TRANSFORM_HOLDER_X = 35 + BG_X;
    private static final int TRANSFORM_HOLDER_Y = 95 + BG_Y;
    private static final int TRANSFORM_NUMBER_LEFT_X = CHAR_X;
    private static final int TRANSFORM_NUMBER_RIGHT_X = TRANSFORM_NUMBER_LEFT_X + 40;
    private static final int TRANSFORM_NUMBER_Y = CHAR_Y;
    private static final int TRANSFORM_WIDTH = 20;
    private static final int TRANSFORM_HEIGHT = 40;

    private static final int HEALTH_BAR_X = 410 + BG_X;
    private static final int HEALTH_BAR_Y = 200 + BG_Y;
    private static final double HEALTH_BAR_SCALE_X = 156.25;
    private static final int HEALTH_BAR_SCALE_Y = 250;

    private static final int HEALTH_Y = HEALTH_BAR_Y + 5;
    private static final int CUR_HEALTH_X = HEALTH_BAR_X + 119;
    private static final int HEALTH_SLASH_X = HEALTH_BAR_X + 115;
    private static final int MAX_HEALTH_X = HEALTH_BAR_X + 131;
    private static final int HEALTH_NUMBER_WIDTH = 20;
    private static final int HEALTH_NUMBER_HEIGHT = 40;
    private static final int HEALTH_OFFSET_X = -4;

    private Paint multPaint;
    private static final int STAT_SIZE = 34;
    private static final int PHYS_X = 450 + BG_X;
    private static final int MAG_X = 85 + PHYS_X;
    private static final int SPEC_X = 85 + MAG_X;
    private static final int ATK_Y = 322 + BG_Y;
    private static final int DEF_Y = 85 + ATK_Y;

    public BattleCharacterInfoScreen(Game game, Screen previousScreen) {
        super(game, previousScreen);
        background = Assets.BattleCharacterInfoScreen;
    }


    protected void createUniquePaints(){
        multPaint = new Paint();
        multPaint.setTextAlign(Paint.Align.CENTER);
        multPaint.setColor(Color.BLACK);
        multPaint.setTextSize(STAT_SIZE);

        descPaint.setTextSize(SKILLS_DESC_SIZE);
    }

    protected String getWeaponType(){
        return displayChar.getWeapon().getWeaponType();
    }

    public void setChars(BattleWifey input, BattleEnemy enemy){
        displayChar = input;
        displayEnemy = enemy;

        displayText = -1;
        bDisplayUnique = false;
        bDisplayWeaponSkill = false;
        multipliers = displayChar.getMultipliers(enemy);
    }

    private Image getPlayerHealthBar(int currentHealth, int maxHealth){
        Double percent = (100.0 * currentHealth)/maxHealth;
        if(percent >= 50.0){
            return Assets.SmallGreenBar;
        }
        else if(percent >= 25.0){
            return Assets.SmallYellowBar;
        }
        else{
            return Assets.SmallRedBar;
        }
    }

    protected void drawPortrait(Graphics g){
        g.drawPercentageImage(displayChar.getImage(),CHAR_X, CHAR_Y, DOUBLE_SCALE, DOUBLE_SCALE);
        if(displayChar.getMaxTransformNumber() != 1) {
            g.drawImage(Assets.TransformHolder, TRANSFORM_HOLDER_X, TRANSFORM_HOLDER_Y);
            NumberPrinter.drawNumber(g, displayChar.getTransformNumber(), TRANSFORM_NUMBER_LEFT_X, TRANSFORM_NUMBER_Y, TRANSFORM_WIDTH, TRANSFORM_HEIGHT, 0, Assets.WhiteNumbers, NumberPrinter.Align.LEFT);
            NumberPrinter.drawNumber(g, displayChar.getMaxTransformNumber(), TRANSFORM_NUMBER_RIGHT_X, TRANSFORM_NUMBER_Y, TRANSFORM_WIDTH, TRANSFORM_HEIGHT, 0, Assets.WhiteNumbers, NumberPrinter.Align.LEFT);
        }
    }

    protected void drawElements(Graphics g){
        g.drawPercentageImage(displayChar.getAttackElement().getElementImage(), ELEMENT_X, ATK_ELEMENT_Y, QUARTER_SCALE, QUARTER_SCALE);
        g.drawPercentageImage(displayChar.getStrongElement().getElementImage(), ELEMENT_X, RES_ELEMENT_Y, QUARTER_SCALE, QUARTER_SCALE);
        g.drawPercentageImage(displayChar.getWeakElement().getElementImage(), ELEMENT_X, WEAK_ELEMENT_Y, QUARTER_SCALE, QUARTER_SCALE);
    }

    protected void drawName(Graphics g){
        if(g.canDrawString(displayChar.getName(), namePaint, MAX_NAME_SIZE, MIN_NAME_FONT)){
            g.drawString(displayChar.getName(), NAME_X, MAX_NAME_Y, namePaint, MAX_NAME_SIZE, MAX_NAME_FONT);
        }
        else{
            namePaint.setTextSize(TWO_LINE_NAME_FONT);
            g.drawMultiLineString(displayChar.getName(), NAME_X, TWO_LINE_NAME_Y, MAX_NAME_SIZE, namePaint);
        }
    }

    protected void drawTopRows(Graphics g){
        Image healthBar = getPlayerHealthBar(displayChar.getCurrentHP(), displayChar.getMaxHP());
        double perHealth = 1.0 * displayChar.getCurrentHP() / displayChar.getMaxHP();
        int healthSize = (int) (HEALTH_BAR_SCALE_X * perHealth);
        g.drawPercentageImage(healthBar, HEALTH_BAR_X, HEALTH_BAR_Y, healthSize, HEALTH_BAR_SCALE_Y);

        NumberPrinter.drawNumber(g, displayChar.getCurrentHP(), CUR_HEALTH_X, HEALTH_Y, HEALTH_NUMBER_WIDTH, HEALTH_NUMBER_HEIGHT, HEALTH_OFFSET_X, Assets.WhiteNumbers, NumberPrinter.Align.RIGHT);
        g.drawImage(Assets.HPSlash, HEALTH_SLASH_X, HEALTH_Y);
        NumberPrinter.drawNumber(g, displayChar.getMaxHP(), MAX_HEALTH_X, HEALTH_Y, HEALTH_NUMBER_WIDTH, HEALTH_NUMBER_HEIGHT, HEALTH_OFFSET_X, Assets.WhiteNumbers, NumberPrinter.Align.LEFT);

        g.drawString(format(multipliers.getPhysAtk()) + "x", PHYS_X, ATK_Y, multPaint);
        g.drawString(format(multipliers.getMagAtk()) + "x", MAG_X, ATK_Y, multPaint);
        g.drawString(format(multipliers.getSpecAtk()) + "x", SPEC_X, ATK_Y, multPaint);
        g.drawString(format(multipliers.getPhysDef()) + "x", PHYS_X, DEF_Y, multPaint);
        g.drawString(format(multipliers.getMagDef()) + "x", MAG_X, DEF_Y, multPaint);
        g.drawString(format(multipliers.getSpecDef()) + "x", SPEC_X, DEF_Y, multPaint);
    }

    protected void drawSkills(Graphics g){
        if(displayChar.getSkills().getUniqueSkill() != null){
            g.drawString(displayChar.getSkills().getUniqueSkill().getSkillName(), UNIQUE_X, MAX_WEAPON_Y, weaponPaint, MAX_UNIQUE_SIZE, MAX_WEAPON_FONT);
        }
        else {
            g.drawString("--NONE--", UNIQUE_X, MAX_WEAPON_Y, weaponPaint, MAX_UNIQUE_SIZE, MAX_WEAPON_FONT);
        }

        //Draw image for weapon category

        //Draw string for weapon name
        if(displayChar.getSkills().getWeaponSkill() != null){
            g.drawString(displayChar.getSkills().getWeaponSkill().getSkillName(), WEAPON_X, MAX_WEAPON_Y, weaponPaint, MAX_WEAPON_SIZE, MAX_WEAPON_FONT);
        }
        else {
            //We'll print default weapon and let the image describe the weapon
            g.drawString("--Default Weapon--", WEAPON_X, MAX_WEAPON_Y, weaponPaint, MAX_WEAPON_SIZE, MAX_WEAPON_FONT);
        }
        //Draw image for weapon
        g.drawImage(displayChar.getWeapon().getImage(), WEAPONS_IMAGE_LEFT_X, WEAPONS_IMAGE_TOP_Y);

        //Draw image for number hits
        g.drawImage(getHitsImage(displayChar.getNumHits()), HITS_X, HITS_Y);

        //List out names for the skills
        for(int i = 0; i < 4 && i < displayChar.getSkills().size(); i++){
            AbsSkill skill = displayChar.getSkills().get(i);
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
        if(displayText != -1 && displayChar.getSkills().size() > displayText){
            String desc = displayChar.getSkills().get(displayText).getDescription(displayEnemy);
            g.drawMultiLineString(desc, SKILLS_DESC_X, SKILLS_DESC_Y, SKILLS_DESC_WIDTH, descPaint);
        }
        else if(bDisplayUnique && displayChar.getSkills().getUniqueSkill() != null){
            String desc = displayChar.getSkills().getUniqueSkill().getDescription(displayEnemy);
            g.drawMultiLineString(desc, SKILLS_DESC_X, SKILLS_DESC_Y, SKILLS_DESC_WIDTH, descPaint);
        }
        else if(bDisplayWeaponSkill && displayChar.getSkills().getWeaponSkill() != null){
            String desc = displayChar.getSkills().getWeaponSkill().getDescription(displayEnemy);
            g.drawMultiLineString(desc, SKILLS_DESC_X, SKILLS_DESC_Y, SKILLS_DESC_WIDTH, descPaint);
        }
    }

    private String format(double number){
        if(number >= 100.0){
            return String.format("%1$.0f", number);
        }
        else if(number >= 10.0){
            return String.format("%1$.1f", number);
        }
        else{
            return String.format("%1$.2f", number);
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
