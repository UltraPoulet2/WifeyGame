package ultrapoulet.wifeygame.screens;

import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.Log;

import java.util.List;

import ultrapoulet.androidgame.framework.Game;
import ultrapoulet.androidgame.framework.Graphics;
import ultrapoulet.androidgame.framework.Image;
import ultrapoulet.androidgame.framework.Input;
import ultrapoulet.androidgame.framework.Screen;
import ultrapoulet.androidgame.framework.helpers.Button;
import ultrapoulet.androidgame.framework.helpers.ButtonList;
import ultrapoulet.androidgame.framework.helpers.NumberPrinter;
import ultrapoulet.wifeygame.Assets;
import ultrapoulet.wifeygame.battle.BattleCharacter;
import ultrapoulet.wifeygame.battle.BattleEnemy;
import ultrapoulet.wifeygame.battle.skills.AbsSkill;
import ultrapoulet.wifeygame.battle.skills.AbsSkill.Multipliers;

/**
 * Created by John on 7/11/2017.
 */

public class EnemyInfoScreen extends Screen {
    private Screen prevScreen;

    private static final int BG_X = 50;
    private int topY;
    private int skillsY;

    private static final int TOP_HEIGHT = Assets.EnemyInfoScreenTop.getHeight();
    private static final int MID_HEIGHT = Assets.EnemyInfoScreenMid.getHeight();
    private static final int BOT_HEIGHT = Assets.EnemyInfoScreenBot.getHeight();
    private int totalHeight;

    private List<BattleCharacter> party;
    private BattleEnemy displayEnemy;

    private int displayText = -1;
    private boolean bDisplayUnique = false;
    private boolean bDisplayWeaponSkill = false;

    private ButtonList basicButtonList;

    private static final int CLOSE_LEFT_X = BG_X + 600;
    private static final int CLOSE_RIGHT_X = CLOSE_LEFT_X + 75;
    private static final int CLOSE_TOP_Y = 10; //topY will need to be added
    private static final int CLOSE_BOT_Y = CLOSE_TOP_Y + 75; //topY will need to be added
    private static final String CLOSE_STRING = "Close";

    private Button uniqueSkillButton;
    private Button weaponSkillButton;
    private static final int UNIQUE_SKILL_LEFT_X = BG_X + 40;
    private static final int UNIQUE_SKILL_RIGHT_X = UNIQUE_SKILL_LEFT_X + 307;
    private static final int WEAPON_SKILL_LEFT_X = BG_X + 353;
    private static final int WEAPON_SKILL_RIGHT_X = BG_X + 540;
    private static final int TOP_SKILLS_TOP_Y = 30; //skillsY will need to be added
    private static final int TOP_SKILLS_BOT_Y = TOP_SKILLS_TOP_Y + 50;
    private static final String UNIQUE_STRING = "Unique";
    private static final String WEAPON_STRING = "Weapon";

    private ButtonList skillsButtonList;
    private static final int SKILLS_BUTTON_LEFT_X = 40 + BG_X;
    private static final int SKILLS_BUTTON_WIDTH = 307;
    private static final int SKILLS_BUTTON_RIGHT_X = SKILLS_BUTTON_LEFT_X + SKILLS_BUTTON_WIDTH;
    private static final int SKILLS_BUTTON_OFFSET_X = 6;
    private static final int SKILLS_BUTTON_TOP_Y = 115; //skillsY will need to be added
    private static final int SKILLS_BUTTON_HEIGHT = 50;
    private static final int SKILLS_BUTTON_BOT_Y = SKILLS_BUTTON_TOP_Y + SKILLS_BUTTON_HEIGHT; //topY will need to be added
    private static final int SKILLS_BUTTON_OFFSET_Y = 5;

    protected static final int SKILL_HIGHLIGHT_COLOR = 0xfff6f0ab;

    private TextPaint namePaint;
    private static final int MAX_NAME_FONT = 40;
    private static final int MIN_NAME_FONT = 20;
    private static final int MAX_NAME_SIZE = 236;
    private static final int TWO_LINE_NAME_FONT = 20;
    private static final int NAME_X = 42 + BG_X;
    private static final int MAX_NAME_Y = 155; //topY will need to be added
    private static final int TWO_LINE_NAME_Y = 115; //topY will need to be added

    private Paint skillsPaint;
    private static final int SKILLS_TEXT_FONT = 40;
    private static final int SKILLS_TEXT_SIZE = 303;
    private static final int SKILLS_TEXT_LEFT_X = 42 + BG_X;
    private static final int SKILLS_TEXT_RIGHT_X = SKILLS_TEXT_LEFT_X + 313;
    private static final int SKILLS_TEXT_BASE_Y = 155; //skillsY will need to be added
    private static final int SKILLS_TEXT_OFFSET_Y = 55;

    private static final int SKILLS_DESC_X = 42 + BG_X;
    private static final int SKILLS_DESC_Y = 255; //skillsY will need to be added
    private static final int SKILLS_DESC_WIDTH = 616;
    private static final int SKILLS_DESC_SIZE = 25;
    private TextPaint descPaint;

    private Paint weaponPaint;
    private static final int MAX_WEAPON_FONT = 40;
    private static final int MAX_WEAPON_SIZE = 197;
    private static final int WEAPON_X = 355 + BG_X;
    private static final int MAX_WEAPON_Y = 70; //skillsY will need to be added
    //Unique Skills will use same info as Weapon
    private static final int UNIQUE_X = 42 + BG_X;
    private static final int MAX_UNIQUE_SIZE = 303;


    private static final int HEALTH_BAR_X = 295 + BG_X;
    private static final int HEALTH_BAR_Y = 115; //topY will need to be added
    private static final double HEALTH_BAR_SCALE_X = 228.125;
    private static final int HEALTH_BAR_SCALE_Y = 250;

    private static final int HEALTH_Y = HEALTH_BAR_Y + 5; //topY will need to be added
    private static final int CUR_HEALTH_X = HEALTH_BAR_X + 176;
    private static final int HEALTH_SLASH_X = HEALTH_BAR_X + 172;
    private static final int MAX_HEALTH_X = HEALTH_BAR_X + 188;
    private static final int HEALTH_NUMBER_WIDTH = 20;
    private static final int HEALTH_NUMBER_HEIGHT = 40;
    private static final int HEALTH_OFFSET_X = -4;

    private static final int IMAGE_OFFSET_X = 70;
    private static final int IMAGE_OFFSET_Y = 20;
    private static final int IMAGE_SIZE = 50;

    private Paint multPaint;
    private static final int STAT_SIZE = 34;
    private static final int PHYS_ATK_X = 165 + BG_X;
    private static final int MAG_ATK_X = 85 + PHYS_ATK_X;
    private static final int SPEC_ATK_X = 85 + MAG_ATK_X;
    private static final int PHYS_DEF_X = 85 + SPEC_ATK_X;
    private static final int MAG_DEF_X = 85 + PHYS_DEF_X;
    private static final int SPEC_DEF_X = 85 + MAG_DEF_X;
    private static final int MULT_OFFSET_Y = 57;


    public EnemyInfoScreen(Game game, Screen prevScreen, List<BattleCharacter> party) {
        super(game);
        this.prevScreen = prevScreen;
        this.party = party;
        totalHeight = TOP_HEIGHT + (MID_HEIGHT * this.party.size()) + BOT_HEIGHT;
        topY = (1280 - totalHeight) / 2;
        skillsY = topY + TOP_HEIGHT + (MID_HEIGHT * this.party.size());

        basicButtonList = new ButtonList();
        basicButtonList.addButton(new Button(CLOSE_LEFT_X, CLOSE_RIGHT_X, topY + CLOSE_TOP_Y, topY + CLOSE_BOT_Y, true, CLOSE_STRING));
        uniqueSkillButton = new Button(UNIQUE_SKILL_LEFT_X, UNIQUE_SKILL_RIGHT_X, skillsY + TOP_SKILLS_TOP_Y, skillsY + TOP_SKILLS_BOT_Y, true, UNIQUE_STRING);
        basicButtonList.addButton(uniqueSkillButton);
        weaponSkillButton = new Button(WEAPON_SKILL_LEFT_X, WEAPON_SKILL_RIGHT_X, skillsY + TOP_SKILLS_TOP_Y, skillsY + TOP_SKILLS_BOT_Y, true, WEAPON_STRING);
        basicButtonList.addButton(weaponSkillButton);

        skillsButtonList = new ButtonList();
        for(int i = 0; i < 4; i++) {
            int skillLeftX = SKILLS_BUTTON_LEFT_X + ((SKILLS_BUTTON_WIDTH +SKILLS_BUTTON_OFFSET_X) * (i % 2));
            int skillRightX = SKILLS_BUTTON_RIGHT_X  + ((SKILLS_BUTTON_WIDTH +SKILLS_BUTTON_OFFSET_X) * (i % 2));
            int skillTopY = skillsY + SKILLS_BUTTON_TOP_Y + ((SKILLS_BUTTON_HEIGHT + SKILLS_BUTTON_OFFSET_Y) * (i / 2));
            int skillBotY = skillsY + SKILLS_BUTTON_BOT_Y + ((SKILLS_BUTTON_HEIGHT + SKILLS_BUTTON_OFFSET_Y) * (i / 2));
            skillsButtonList.addButton(new Button(skillLeftX, skillRightX, skillTopY, skillBotY, true, "Skill_" + i));
        }

        namePaint = new TextPaint();
        namePaint.setTextAlign(Paint.Align.LEFT);
        namePaint.setColor(Color.BLACK);

        skillsPaint = new Paint();
        skillsPaint.setTextAlign(Paint.Align.LEFT);
        skillsPaint.setColor(Color.BLACK);

        descPaint = new TextPaint();
        descPaint.setTextAlign(Paint.Align.LEFT);
        descPaint.setColor(Color.BLACK);
        descPaint.setTextSize(SKILLS_DESC_SIZE);

        weaponPaint = new Paint();
        weaponPaint.setTextAlign(Paint.Align.LEFT);
        weaponPaint.setColor(Color.BLACK);

        multPaint = new Paint();
        multPaint.setTextAlign(Paint.Align.CENTER);
        multPaint.setColor(Color.BLACK);
        multPaint.setTextSize(STAT_SIZE);
    }

    public void setEnemy(BattleEnemy enemy){
        this.displayEnemy = enemy;

        displayText = -1;
        bDisplayUnique = false;
        bDisplayWeaponSkill = false;

        updateSkillButtons();
    }

    private void updateSkillButtons() {
        for(int i = 0; i < 4; i++) {
            skillsButtonList.get(i).setActive(displayEnemy.getSkills().size() > i);
        }
        uniqueSkillButton.setActive(displayEnemy.getSkills().getUniqueSkill() != null);
        weaponSkillButton.setActive(displayEnemy.getSkills().getWeaponSkill() != null);
    }

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
                            Log.e("EnemyInfoScreen", "Invalid button selection: " + basicPressed.getName());
                    }
                }
                else if(skillPressed != -1){
                    displayText = (displayText == skillPressed) ? -1 : skillPressed;
                    bDisplayUnique = false;
                    bDisplayWeaponSkill = false;
                }
            }
        }
    }

    @Override
    public void paint(float deltaTime) {
        Graphics g = game.getGraphics();
        g.drawImage(Assets.EnemyInfoScreenTop, BG_X, topY);
        for(int i = 0; i < party.size(); i++){
            int baseY = topY + TOP_HEIGHT + (MID_HEIGHT * i);
            g.drawImage(Assets.EnemyInfoScreenMid, BG_X, baseY);
            g.drawScaledImage(party.get(i).getImage(), BG_X + IMAGE_OFFSET_X, baseY + IMAGE_OFFSET_Y, IMAGE_SIZE, IMAGE_SIZE);

            Multipliers multipliers = displayEnemy.getMultipliers(party.get(i));
            g.drawString(format(multipliers.getPhysAtk()) + "x", PHYS_ATK_X, baseY + MULT_OFFSET_Y, multPaint);
            g.drawString(format(multipliers.getMagAtk()) + "x", MAG_ATK_X,  baseY + MULT_OFFSET_Y, multPaint);
            g.drawString(format(multipliers.getSpecAtk()) + "x", SPEC_ATK_X,  baseY + MULT_OFFSET_Y, multPaint);
            g.drawString(format(multipliers.getPhysDef()) + "x", PHYS_DEF_X,  baseY + MULT_OFFSET_Y, multPaint);
            g.drawString(format(multipliers.getMagDef()) + "x", MAG_DEF_X,  baseY + MULT_OFFSET_Y, multPaint);
            g.drawString(format(multipliers.getSpecDef()) + "x", SPEC_DEF_X,  baseY + MULT_OFFSET_Y, multPaint);
        }
        g.drawImage(Assets.EnemyInfoScreenBot, BG_X, topY + TOP_HEIGHT + (MID_HEIGHT * party.size()));

        if(g.canDrawString(displayEnemy.getName(), namePaint, MAX_NAME_SIZE, MIN_NAME_FONT)){
            g.drawString(displayEnemy.getName(), NAME_X, topY + MAX_NAME_Y, namePaint, MAX_NAME_SIZE, MAX_NAME_FONT);
        }
        else{
            namePaint.setTextSize(TWO_LINE_NAME_FONT);
            g.drawMultiLineString(displayEnemy.getName(), NAME_X, topY + TWO_LINE_NAME_Y, MAX_NAME_SIZE, namePaint);
        }

        Image healthBar = getHealthBar(displayEnemy.getCurrentHP(), displayEnemy.getMaxHP());
        double perHealth = 1.0 * displayEnemy.getCurrentHP() / displayEnemy.getMaxHP();
        int healthSize = (int) (HEALTH_BAR_SCALE_X * perHealth);
        g.drawPercentageImage(healthBar, HEALTH_BAR_X, topY + HEALTH_BAR_Y, healthSize, HEALTH_BAR_SCALE_Y);

        NumberPrinter.drawNumber(g, displayEnemy.getCurrentHP(), CUR_HEALTH_X, topY + HEALTH_Y, HEALTH_NUMBER_WIDTH, HEALTH_NUMBER_HEIGHT, HEALTH_OFFSET_X, Assets.WhiteNumbers, NumberPrinter.Align.RIGHT);
        g.drawImage(Assets.HPSlash, HEALTH_SLASH_X, topY + HEALTH_Y);
        NumberPrinter.drawNumber(g, displayEnemy.getMaxHP(), MAX_HEALTH_X, topY + HEALTH_Y, HEALTH_NUMBER_WIDTH, HEALTH_NUMBER_HEIGHT, HEALTH_OFFSET_X, Assets.WhiteNumbers, NumberPrinter.Align.LEFT);

        if(displayEnemy.getSkills().getUniqueSkill() != null){
            if(bDisplayUnique){
                g.drawRect(UNIQUE_SKILL_LEFT_X, skillsY + TOP_SKILLS_TOP_Y, UNIQUE_SKILL_RIGHT_X - UNIQUE_SKILL_LEFT_X, TOP_SKILLS_BOT_Y - TOP_SKILLS_TOP_Y, SKILL_HIGHLIGHT_COLOR);
            }
            g.drawString(displayEnemy.getSkills().getUniqueSkill().getSkillName(), UNIQUE_X, skillsY + MAX_WEAPON_Y, weaponPaint, MAX_UNIQUE_SIZE, MAX_WEAPON_FONT);
        }
        else {
            g.drawString("--NONE--", UNIQUE_X, skillsY + MAX_WEAPON_Y, weaponPaint, MAX_UNIQUE_SIZE, MAX_WEAPON_FONT);
        }

        //Draw string for weapon name
        if(displayEnemy.getSkills().getWeaponSkill() != null){
            if(bDisplayWeaponSkill){
                g.drawRect(WEAPON_SKILL_LEFT_X, skillsY + TOP_SKILLS_TOP_Y, WEAPON_SKILL_RIGHT_X - WEAPON_SKILL_LEFT_X, TOP_SKILLS_BOT_Y - TOP_SKILLS_TOP_Y, SKILL_HIGHLIGHT_COLOR);
            }
            g.drawString(displayEnemy.getSkills().getWeaponSkill().getSkillName(), WEAPON_X, skillsY + MAX_WEAPON_Y, weaponPaint, MAX_WEAPON_SIZE, MAX_WEAPON_FONT);
        }
        else {
            g.drawString("--NONE--", WEAPON_X, skillsY + MAX_WEAPON_Y, weaponPaint, MAX_WEAPON_SIZE, MAX_WEAPON_FONT);
        }

        //List out names for the skills
        for(int i = 0; i < 4 && i < displayEnemy.getSkills().size(); i++){
            if(displayText == i){
                int highlightX = displayText % 2 == 0 ? SKILLS_BUTTON_LEFT_X : SKILLS_BUTTON_LEFT_X + (SKILLS_BUTTON_WIDTH +SKILLS_BUTTON_OFFSET_X);
                int highlightY = displayText / 2 == 0 ? skillsY + SKILLS_BUTTON_TOP_Y : skillsY + SKILLS_BUTTON_TOP_Y + (SKILLS_BUTTON_HEIGHT + SKILLS_BUTTON_OFFSET_Y);
                g.drawRect(highlightX, highlightY, SKILLS_BUTTON_WIDTH, SKILLS_BUTTON_HEIGHT, SKILL_HIGHLIGHT_COLOR);
            }
            AbsSkill skill = displayEnemy.getSkills().get(i);
            int xOffset;
            int yOffset = skillsY + (SKILLS_TEXT_BASE_Y + (i / 2) * SKILLS_TEXT_OFFSET_Y);
            if(i % 2 == 0) {
                xOffset = SKILLS_TEXT_LEFT_X;
            }
            else{
                xOffset = SKILLS_TEXT_RIGHT_X;
            }
            g.drawString(skill.getSkillName(), xOffset, yOffset, skillsPaint, SKILLS_TEXT_SIZE, SKILLS_TEXT_FONT);
        }

        if(displayText != -1 && displayEnemy.getSkills().size() > displayText){
            String desc = displayEnemy.getSkills().get(displayText).getDescription(displayEnemy);
            g.drawMultiLineString(desc, SKILLS_DESC_X, skillsY + SKILLS_DESC_Y, SKILLS_DESC_WIDTH, descPaint);
        }
        else if(bDisplayUnique && displayEnemy.getSkills().getUniqueSkill() != null){
            String desc = displayEnemy.getSkills().getUniqueSkill().getDescription(displayEnemy);
            g.drawMultiLineString(desc, SKILLS_DESC_X, skillsY + SKILLS_DESC_Y, SKILLS_DESC_WIDTH, descPaint);
        }
        else if(bDisplayWeaponSkill && displayEnemy.getSkills().getWeaponSkill() != null){
            String desc = displayEnemy.getSkills().getWeaponSkill().getDescription(displayEnemy);
            g.drawMultiLineString(desc, SKILLS_DESC_X, skillsY + SKILLS_DESC_Y, SKILLS_DESC_WIDTH, descPaint);
        }
    }

    private Image getHealthBar(int currentHealth, int maxHealth){
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
        game.setScreen(prevScreen);
    }
}
