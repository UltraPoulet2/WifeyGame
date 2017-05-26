package ultrapoulet.wifeygame.recruiting;

import ultrapoulet.androidgame.framework.Game;
import ultrapoulet.androidgame.framework.Screen;
import ultrapoulet.wifeygame.battle.BattleInfo;
import ultrapoulet.wifeygame.character.WifeyCharacter;
import ultrapoulet.wifeygame.screens.RecruitingBattleInfoScreen;

/**
 * Created by John on 5/25/2017.
 */

public class RecruitRequirementRecruitBattle extends RecruitRequirement {

    private BattleInfo requiredBattle;

    public void setRequiredBattle(BattleInfo input){
        this.requiredBattle = input;
    }

    public BattleInfo getRequiredBattle(){
        return requiredBattle;
    }

    @Override
    public boolean isComplete() {
        return requiredBattle.getNumComplete() > 0;
    }

    @Override
    public String getDescription() {
        return "Complete recruiting battle: '" + requiredBattle.getName() + "'";
    }

    @Override
    public boolean validate() {
        return requiredBattle != null;
    }

    public Screen getScreen(Game game, Screen prevScreen, WifeyCharacter recruit){
        if(this.isComplete()){
            return null;
        }
        return new RecruitingBattleInfoScreen(game, prevScreen, requiredBattle, recruit);
    }
}
