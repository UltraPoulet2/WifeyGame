package ultrapoulet.wifeygame.recruiting;

import ultrapoulet.wifeygame.battle.BattleInfo;

/**
 * Created by John on 5/7/2017.
 */

public class RecruitRequirementBattle extends RecruitRequirement {

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

    public String getDescription(){
        return "Complete battle: " + requiredBattle.getName();
    }

    public boolean validate(){
        return requiredBattle != null;
    }
}
