package ultrapoulet.wifeygame.recruiting;

import ultrapoulet.wifeygame.battle.BattleInfo;

/**
 * Created by John on 5/26/2017.
 */

public abstract class RecruitRequirementAbsBattle extends RecruitRequirement{

    protected BattleInfo requiredBattle;

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

    public boolean validate(){
        return requiredBattle != null;
    }

}
