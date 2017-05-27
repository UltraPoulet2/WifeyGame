package ultrapoulet.wifeygame.screens;

import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;

import ultrapoulet.androidgame.framework.Game;
import ultrapoulet.androidgame.framework.Graphics;
import ultrapoulet.androidgame.framework.Image;
import ultrapoulet.androidgame.framework.Screen;
import ultrapoulet.wifeygame.Assets;
import ultrapoulet.wifeygame.battle.BattleInfo;
import ultrapoulet.wifeygame.character.WifeyCharacter;

/**
 * Created by John on 5/24/2017.
 */

public class RecruitingBattleInfoScreen extends AbsBattleInfoScreen {

    private Image recruitImage;
    private static final int RECRUIT_IMAGE_X = 45;
    private static final int RECRUIT_IMAGE_Y = BACKGROUND_OFFSET + 133;
    private static final int RECRUIT_IMAGE_SIZE = 90;

    private String recruitInfo;
    private static final int NAME_TEXT_X = 450;
    private static final int NAME_TEXT_Y = BACKGROUND_OFFSET + 196;
    private static final int MAX_NAME_WIDTH = 615;
    private static final int MAX_NAME_FONT = 50;
    private static final int MIN_NAME_FONT = 30;
    private static final int TWO_LINE_NAME_SIZE = 30;
    private static final int TWO_LINE_NAME_Y = BACKGROUND_OFFSET + 143;

    private TextPaint namePaint;

    public RecruitingBattleInfoScreen(Game game, Screen previousScreen, BattleInfo info, WifeyCharacter recruit){
        super(game, previousScreen, info);
        recruitImage = recruit.getImage(game.getGraphics());
        recruitInfo = "Recruiting for: " + recruit.getName();

        namePaint = new TextPaint();
        namePaint.setColor(Color.BLACK);
        namePaint.setTextAlign(Paint.Align.CENTER);
    }
    @Override
    protected void setBackground() {
        this.background = Assets.RecruitBattleInfoScreen;
    }

    @Override
    protected AbsBattleScreen getBattleScreen() {
        return new RecruitingBattleScreen(game, battleInfo, prevScreen);
    }

    @Override
    protected void drawInfo(Graphics g) {
        g.drawScaledImage(recruitImage, RECRUIT_IMAGE_X, RECRUIT_IMAGE_Y, RECRUIT_IMAGE_SIZE, RECRUIT_IMAGE_SIZE);

        if(g.canDrawString(recruitInfo, namePaint, MAX_NAME_WIDTH, MIN_NAME_FONT)){
            g.drawString(recruitInfo, NAME_TEXT_X, NAME_TEXT_Y, namePaint, MAX_NAME_WIDTH, MAX_NAME_FONT);
        }
        else {
            namePaint.setTextSize(TWO_LINE_NAME_SIZE);
            g.drawMultiLineString(recruitInfo, NAME_TEXT_X, TWO_LINE_NAME_Y, MAX_NAME_WIDTH, namePaint);
        }
        //g.drawString(recruitInfo, NAME_TEXT_X, NAME_TEXT_Y, namePaint, MAX_NAME_WIDTH, MAX_NAME_FONT);
        g.drawString(String.valueOf(battleInfo.getEnergyRequirement()), COLUMN_1_X, ROW_2_Y, energyPaint);
        g.drawString(String.valueOf(battleInfo.getCharacterEnemies().size()), COLUMN_3_X, ROW_2_Y, infoPaint);
    }
}
