package ultrapoulet.wifeygame.screens;

import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;

import java.util.List;

import ultrapoulet.androidgame.framework.Game;
import ultrapoulet.androidgame.framework.Graphics;
import ultrapoulet.androidgame.framework.Image;
import ultrapoulet.androidgame.framework.Screen;
import ultrapoulet.androidgame.framework.helpers.NumberPrinter;
import ultrapoulet.wifeygame.Assets;
import ultrapoulet.wifeygame.battle.BattleCharacter;
import ultrapoulet.wifeygame.battle.BattleEnemy;
import ultrapoulet.wifeygame.battle.skills.AbsSkill.Multipliers;

/**
 * Created by John on 7/11/2017.
 */

public class EnemyInfoScreen extends Screen {
    private Screen prevScreen;

    private static final int BG_X = 50;
    private int topY;

    private static final int TOP_HEIGHT = Assets.EnemyInfoScreenTop.getHeight();
    private static final int MID_HEIGHT = Assets.EnemyInfoScreenMid.getHeight();
    private static final int BOT_HEIGHT = Assets.EnemyInfoScreenBot.getHeight();
    private int totalHeight;

    private List<BattleCharacter> party;
    private BattleEnemy displayEnemy;

    private TextPaint namePaint;
    private static final int MAX_NAME_FONT = 40;
    private static final int MIN_NAME_FONT = 20;
    private static final int MAX_NAME_SIZE = 236;
    private static final int TWO_LINE_NAME_FONT = 20;
    private static final int NAME_X = 42 + BG_X;
    private static final int MAX_NAME_Y = 155; //topY will need to be added
    private static final int TWO_LINE_NAME_Y = 115; //topY will need to be added

    private static final int HEALTH_BAR_X = 295 + BG_X;
    private static final int HEALTH_BAR_Y = 115; //topY will need to be added
    private static final double HEALTH_BAR_SCALE_X = 228.125; //365
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

        namePaint = new TextPaint();
        namePaint.setTextAlign(Paint.Align.LEFT);
        namePaint.setColor(Color.BLACK);

        multPaint = new Paint();
        multPaint.setTextAlign(Paint.Align.CENTER);
        multPaint.setColor(Color.BLACK);
        multPaint.setTextSize(STAT_SIZE);
    }

    public void setEnemy(BattleEnemy enemy){
        this.displayEnemy = enemy;
    }

    @Override
    public void update(float deltaTime) {

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
    }

    private Image getHealthBar(int currentHealth, int maxHealth){
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
