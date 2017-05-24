package ultrapoulet.wifeygame.screens;

import ultrapoulet.androidgame.framework.Game;
import ultrapoulet.androidgame.framework.Graphics;
import ultrapoulet.androidgame.framework.Screen;
import ultrapoulet.wifeygame.Assets;
import ultrapoulet.wifeygame.battle.BattleInfo;

/**
 * Created by John on 10/12/2016.
 */

public class BattleInfoScreen extends AbsBattleInfoScreen{

    public BattleInfoScreen(Game game, Screen previousScreen, BattleInfo info){
        super(game, previousScreen, info);
    }

    @Override
    protected void setBackground() {
        this.background = Assets.BattleInfoScreen;
    }

    @Override
    protected AbsBattleScreen getBattleScreen() {
        return new StandardBattleScreen(game, battleInfo);
    }

    @Override
    protected void drawInfo(Graphics g) {
        g.drawString(String.valueOf(battleInfo.getEnergyRequirement()), COLUMN_1_X, ROW_1_Y, energyPaint);
        g.drawString(shortGoldGain, COLUMN_1_X, ROW_2_Y - GAINS_Y_OFFSET, gainsPaint);
        g.drawString(shortExpGain, COLUMN_2_X, ROW_2_Y - GAINS_Y_OFFSET, gainsPaint);
        g.drawString(String.valueOf(battleInfo.getCharacterEnemies().size()), COLUMN_3_X, ROW_1_Y, infoPaint);
        String dropString = (battleInfo.getMaxDrops() == 0) ? "--" : battleInfo.getFoundDrops() + "/" + battleInfo.getMaxDrops();
        g.drawString(dropString, COLUMN_3_X, ROW_2_Y - GAINS_Y_OFFSET, gainsPaint);
    }
}
