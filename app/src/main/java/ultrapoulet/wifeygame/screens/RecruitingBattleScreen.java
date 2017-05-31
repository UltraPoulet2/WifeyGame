package ultrapoulet.wifeygame.screens;

import ultrapoulet.androidgame.framework.Game;
import ultrapoulet.androidgame.framework.Screen;
import ultrapoulet.wifeygame.battle.BattleInfo;

/**
 * Created by John on 5/25/2017.
 */

public class RecruitingBattleScreen extends AbsBattleScreen {

    private Screen previousScreen;

    public RecruitingBattleScreen(Game game, BattleInfo info, Screen previousScreen){
        super(game, info);
        this.previousScreen = previousScreen;
    }
    @Override
    protected Screen getCompletionScreen() {
        return previousScreen;
    }
}
