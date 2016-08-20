package ultrapoulet.wifeygame;

import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;

import java.util.List;

import ultrapoulet.androidgame.framework.Game;
import ultrapoulet.androidgame.framework.Graphics;
import ultrapoulet.androidgame.framework.Image;
import ultrapoulet.androidgame.framework.Input;
import ultrapoulet.androidgame.framework.Screen;
import ultrapoulet.wifeygame.battle.BattleEnemy;
import ultrapoulet.wifeygame.battle.BattleWifey;
import ultrapoulet.wifeygame.battle.skills.AbsSkill;
import ultrapoulet.wifeygame.character.SkillsEnum;
import ultrapoulet.wifeygame.character.Weapon;
import ultrapoulet.wifeygame.character.WifeyCharacter;

/**
 * Created by John on 8/17/2016.
 */
public class BattleCharacterInfoScreen extends Screen {

    private Screen previousScreen;

    private Image background;
    private static final int BG_X = 50;
    private static final int BG_Y = 75;

    private static final int CLOSE_LEFT_X = BG_X + 600;
    private static final int CLOSE_RIGHT_X = CLOSE_LEFT_X + 75;
    private static final int CLOSE_TOP_Y = BG_Y + 10;
    private static final int CLOSE_BOT_Y = CLOSE_TOP_Y + 75;

    private BattleWifey displayChar;
    private static final int CHAR_X = 30 + BG_X;
    private static final int CHAR_Y = 100 + BG_Y;
    private static final int DOUBLE_SCALE = 200;

    private double[] multipliers;

    private Paint namePaint;
    private int nameFontSize;
    private int nameY;
    private static final int MAX_NAME_FONT = 40;
    private static final int MAX_NAME_SIZE = 240;
    private static final int NAME_X = 402 + BG_X;
    private static final int MAX_NAME_Y = 155 + BG_Y;

    private static final int HEALTH_BAR_X = 400 + BG_X;
    private static final int HEALTH_BAR_Y = 200 + BG_Y;
    private static final double HEALTH_BAR_SCALE_X = 156.25;
    private static final int HEALTH_BAR_SCALE_Y = 250;

    private Paint levelPaint;
    private static final int LEVEL_SIZE = 34;
    private static final int LEVEL_X = 400 + BG_X;
    private static final int LEVEL_Y = 200 + BG_Y;

    private Paint multPaint;
    private static final int STAT_SIZE = 34;
    private static final int PHYS_X = 440 + BG_X;
    private static final int MAG_X = 85 + PHYS_X;
    private static final int SPEC_X = 85 + MAG_X;
    private static final int ATK_Y = 322 + BG_Y;
    private static final int DEF_Y = 85 + ATK_Y;

    private int skillsPage;
    private Paint skillsPaint;
    private static final int SKILLS_SIZE = 40;
    private static final int SKILLS_PER_PAGE = 14;
    private static final int SKILLS_LEFT_X = 32 + BG_X;
    private static final int SKILLS_RIGHT_X = SKILLS_LEFT_X + 285;
    private static final int SKILLS_BASE_Y = 495 + BG_Y;
    private static final int SKILLS_OFFSET_Y = 55;

    private static final int SKILLS_BUTTON_LEFT_X = 30 + BG_X;
    private static final int SKILLS_BUTTON_OFFSET_X = 280;
    private static final int SKILLS_BUTTON_RIGHT_X = SKILLS_BUTTON_LEFT_X + SKILLS_BUTTON_OFFSET_X;
    private static final int SKILLS_BUTTON_TOP_Y = 455 + BG_Y;
    private static final int SKILLS_BUTTON_OFFSET_Y = 55;
    private static final int SKILLS_BUTTON_BOTTOM_BASE = SKILLS_BUTTON_TOP_Y + SKILLS_OFFSET_Y;

    private static final int SKILLS_DESC_X = 32 + BG_X;
    private static final int SKILLS_DESC_Y = 872 + BG_Y;
    private static final int SKILLS_DESC_SIZE = 30;
    private static final int SKILLS_DESC_WIDTH = 616;
    private TextPaint descPaint;

    private int currentPage = 0;
    private int displayText = -1;

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
        SKILLS15
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

    public BattleCharacterInfoScreen(Game game) {
        super(game);
        background = Assets.BattleCharacterInfoScreen;

        createPaints();
    }

    private void createPaints(){
        namePaint = new Paint();
        namePaint.setTextAlign(Paint.Align.LEFT);
        namePaint.setColor(Color.BLACK);

        multPaint = new Paint();
        multPaint.setTextAlign(Paint.Align.CENTER);
        multPaint.setColor(Color.BLACK);
        multPaint.setTextSize(STAT_SIZE);

        skillsPaint = new Paint();
        skillsPaint.setTextAlign(Paint.Align.LEFT);
        skillsPaint.setColor(Color.BLACK);
        skillsPaint.setTextSize(SKILLS_SIZE);

        descPaint = new TextPaint();
        descPaint.setTextAlign(Paint.Align.LEFT);
        descPaint.setColor(Color.BLACK);
        descPaint.setTextSize(SKILLS_DESC_SIZE);
    }

    public void setPreviousScreen(Screen screen){
        previousScreen = screen;
    }

    public void setChars(BattleWifey input, BattleEnemy enemy){
        displayChar = input;
        nameFontSize = MAX_NAME_FONT;
        namePaint.setTextSize(nameFontSize);
        while(namePaint.measureText(displayChar.getName()) > MAX_NAME_SIZE){
            nameFontSize--;
            namePaint.setTextSize(nameFontSize);
        }
        nameY = MAX_NAME_Y - ((MAX_NAME_FONT - nameFontSize) / 2);

        displayText = -1;

        multipliers = displayChar.getMultipliers(enemy);
    }

    private ButtonPressed getButtonPressed(int x, int y){
        if(x >= CLOSE_LEFT_X && x <= CLOSE_RIGHT_X && y >= CLOSE_TOP_Y && y <= CLOSE_BOT_Y){
            return ButtonPressed.CLOSE;
        }
        for(int i = 0; i < SKILLS_PER_PAGE; i++){
            int baseX = SKILLS_BUTTON_LEFT_X + (SKILLS_BUTTON_OFFSET_X * (i % 2));
            int baseY = SKILLS_BUTTON_TOP_Y + (SKILLS_BUTTON_OFFSET_Y * (i / 2));
            if(x >= baseX && x <= baseX + SKILLS_BUTTON_OFFSET_X &&
                    y >= baseY && y <= baseY + SKILLS_BUTTON_OFFSET_Y){
                return SkillButtons[i];
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
                        displayText = (displayText == skillNum) ? -1 : skillNum + SKILLS_PER_PAGE * currentPage;
                        System.out.println(displayText);
                        break;
                    default:
                        break;
                }
            }
        }
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

    @Override
    public void paint(float deltaTime) {
        Graphics g= game.getGraphics();
        g.drawImage(background, BG_X, BG_Y);
        g.drawPercentageImage(displayChar.getImage(),CHAR_X, CHAR_Y, DOUBLE_SCALE, DOUBLE_SCALE);

        g.drawString(displayChar.getName(), NAME_X, nameY, namePaint);

        //List out names for the skills
        for(int i = SKILLS_PER_PAGE * skillsPage; i < 14 * skillsPage + SKILLS_PER_PAGE && i < displayChar.getSkills().size(); i++){
            AbsSkill skill = displayChar.getSkills().get(i);
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
        Image healthBar = getPlayerHealthBar(displayChar.getCurrentHP(), displayChar.getMaxHP());
        double perHealth = 1.0 * displayChar.getCurrentHP() / displayChar.getMaxHP();
        int healthSize = (int) (HEALTH_BAR_SCALE_X * perHealth);
        g.drawPercentageImage(healthBar, HEALTH_BAR_X, HEALTH_BAR_Y, healthSize, HEALTH_BAR_SCALE_Y);

        g.drawString(format(multipliers[0]) + "x", PHYS_X, ATK_Y, multPaint);
        g.drawString(format(multipliers[1]) + "x", MAG_X, ATK_Y, multPaint);
        g.drawString(format(multipliers[2]) + "x", SPEC_X, ATK_Y, multPaint);
        g.drawString(format(multipliers[3]) + "x", PHYS_X, DEF_Y, multPaint);
        g.drawString(format(multipliers[4]) + "x", MAG_X, DEF_Y, multPaint);
        g.drawString(format(multipliers[5]) + "x", SPEC_X, DEF_Y, multPaint);

        if(displayText != -1 && displayChar.getSkills().size() > displayText){
            String desc = displayChar.getSkills().get(displayText).getDescription();
            //String desc = "PLACEHOLDER";
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
