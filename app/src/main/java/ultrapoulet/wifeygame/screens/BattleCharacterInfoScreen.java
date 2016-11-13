package ultrapoulet.wifeygame.screens;

import android.graphics.Color;
import android.graphics.Paint;

import ultrapoulet.androidgame.framework.Game;
import ultrapoulet.androidgame.framework.Graphics;
import ultrapoulet.androidgame.framework.Image;
import ultrapoulet.wifeygame.Assets;
import ultrapoulet.wifeygame.battle.BattleEnemy;
import ultrapoulet.wifeygame.battle.BattleWifey;
import ultrapoulet.wifeygame.battle.skills.AbsSkill;

/**
 * Created by John on 8/17/2016.
 */
public class BattleCharacterInfoScreen extends AbsCharacterInfoScreen {

    private BattleWifey displayChar;

    private BattleEnemy displayEnemy;

    private double[] multipliers;

    private static final int HEALTH_BAR_X = 400 + BG_X;
    private static final int HEALTH_BAR_Y = 200 + BG_Y;
    private static final double HEALTH_BAR_SCALE_X = 156.25;
    private static final int HEALTH_BAR_SCALE_Y = 250;

    private static final int HEALTH_START_X = HEALTH_BAR_X + 53;
    private static final int HEALTH_START_Y = HEALTH_BAR_Y + 5;
    private static final int HEALTH_OFFSET_X = 16;
    private static final int SLASH = 10;

    private Paint multPaint;
    private static final int STAT_SIZE = 34;
    private static final int PHYS_X = 440 + BG_X;
    private static final int MAG_X = 85 + PHYS_X;
    private static final int SPEC_X = 85 + MAG_X;
    private static final int ATK_Y = 322 + BG_Y;
    private static final int DEF_Y = 85 + ATK_Y;

    public BattleCharacterInfoScreen(Game game) {
        super(game);
        background = Assets.BattleCharacterInfoScreen;
    }


    protected void createUniquePaints(){
        multPaint = new Paint();
        multPaint.setTextAlign(Paint.Align.CENTER);
        multPaint.setColor(Color.BLACK);
        multPaint.setTextSize(STAT_SIZE);
    }

    public void setChars(BattleWifey input, BattleEnemy enemy){
        displayChar = input;
        displayEnemy = enemy;
        nameFontSize = MAX_NAME_FONT;
        namePaint.setTextSize(nameFontSize);
        while(namePaint.measureText(displayChar.getName()) > MAX_NAME_SIZE){
            nameFontSize--;
            namePaint.setTextSize(nameFontSize);
        }
        nameY = MAX_NAME_Y - ((MAX_NAME_FONT - nameFontSize) / 2);

        displayText = -1;
        skillsPage = 0;
        maxPage = displayChar.getSkills().size() / SKILLS_TEXT_PER_PAGE;
        multipliers = displayChar.getMultipliers(enemy);
    }

    private Image getPlayerHealthBar(int currentHealth, int maxHealth){
        Double percent = (100.0 * currentHealth)/maxHealth;
        if(percent >= 50.0){
            return Assets.pHealthG;
        }
        else if(percent >= 25.0){
            return Assets.pHealthY;
        }
        else{
            return Assets.pHealthR;
        }
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
        Image healthBar = getPlayerHealthBar(displayChar.getCurrentHP(), displayChar.getMaxHP());
        double perHealth = 1.0 * displayChar.getCurrentHP() / displayChar.getMaxHP();
        int healthSize = (int) (HEALTH_BAR_SCALE_X * perHealth);
        g.drawPercentageImage(healthBar, HEALTH_BAR_X, HEALTH_BAR_Y, healthSize, HEALTH_BAR_SCALE_Y);

        boolean healthStart = false;
        int divisor = 1000;
        for(int i = 0; i < 4; i++){
            int numberPart = (displayChar.getCurrentHP() / divisor % 10);
            healthStart = healthStart || (numberPart > 0);
            if(healthStart){
                g.drawImage(Assets.HPNumbers[numberPart], HEALTH_START_X + HEALTH_OFFSET_X * i, HEALTH_START_Y);
            }
            divisor = divisor / 10;
        }
        g.drawImage(Assets.HPNumbers[SLASH], HEALTH_START_X + HEALTH_OFFSET_X * 4, HEALTH_START_Y);
        int i = 5;
        divisor = 1000;
        healthStart = false;
        while(divisor > 0){
            int numberPart = (displayChar.getMaxHP() / divisor % 10);
            healthStart = healthStart || (numberPart > 0);
            if(healthStart){
                g.drawImage(Assets.HPNumbers[numberPart], HEALTH_START_X + HEALTH_OFFSET_X * i, HEALTH_START_Y);
                i++;
            }
            divisor = divisor / 10;
        }

        g.drawString(format(multipliers[0]) + "x", PHYS_X, ATK_Y, multPaint);
        g.drawString(format(multipliers[1]) + "x", MAG_X, ATK_Y, multPaint);
        g.drawString(format(multipliers[2]) + "x", SPEC_X, ATK_Y, multPaint);
        g.drawString(format(multipliers[3]) + "x", PHYS_X, DEF_Y, multPaint);
        g.drawString(format(multipliers[4]) + "x", MAG_X, DEF_Y, multPaint);
        g.drawString(format(multipliers[5]) + "x", SPEC_X, DEF_Y, multPaint);
    }

    protected void drawSkills(Graphics g){
        //List out names for the skills
        for(int i = SKILLS_TEXT_PER_PAGE * skillsPage; i < SKILLS_TEXT_PER_PAGE * skillsPage + SKILLS_TEXT_PER_PAGE && i < displayChar.getSkills().size(); i++){
            AbsSkill skill = displayChar.getSkills().get(i);
            int xOffset;
            int yOffset = SKILLS_TEXT_BASE_Y + ((i % SKILLS_TEXT_PER_PAGE) / 2) * SKILLS_TEXT_OFFSET_Y;
            if(i % 2 == 0) {
                xOffset = SKILLS_TEXT_LEFT_X;
            }
            else{
                xOffset = SKILLS_TEXT_RIGHT_X;
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
            String desc = displayChar.getSkills().get(displayText).getDescription(displayEnemy);
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