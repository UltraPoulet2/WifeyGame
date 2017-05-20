package ultrapoulet.wifeygame.screens;

import ultrapoulet.androidgame.framework.Game;
import ultrapoulet.androidgame.framework.Screen;
import ultrapoulet.wifeygame.battle.BattleInfo;

/**
 * Created by John on 3/5/2016.
 */
public class StandardBattleScreen extends AbsBattleScreen {

    public StandardBattleScreen(Game game, BattleInfo info){
        super(game, info);
    }

    @Override
    protected Screen getCompletionScreen() {
        return new BattleResultScreen(game, battleInfo, party, enemies);
    }
}
