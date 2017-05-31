package ultrapoulet.wifeygame.recruiting;

import ultrapoulet.androidgame.framework.Game;
import ultrapoulet.androidgame.framework.Screen;
import ultrapoulet.wifeygame.character.WifeyCharacter;
import ultrapoulet.wifeygame.screens.RecruitingBattleInfoScreen;

/**
 * Created by John on 5/25/2017.
 */

public class RecruitRequirementRecruitBattle extends RecruitRequirementAbsBattle {

    @Override
    public String getDescription() {
        return "Complete recruiting battle: '" + requiredBattle.getName() + "'";
    }

    public Screen getScreen(Game game, Screen prevScreen, WifeyCharacter recruit){
        if(this.isComplete()){
            return null;
        }
        return new RecruitingBattleInfoScreen(game, prevScreen, requiredBattle, recruit);
    }
}
