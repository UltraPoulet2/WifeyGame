package ultrapoulet.wifeygame.recruiting;

import ultrapoulet.wifeygame.battle.BattleInfo;

/**
 * Created by John on 5/7/2017.
 */

public class RecruitRequirementBattle extends RecruitRequirement {

    private BattleInfo requiredBattle;

    public RecruitRequirementBattle(BattleInfo input){
        this.requiredBattle = input;
    }

    public BattleInfo getRequiredBattle(){
        return requiredBattle;
    }

    public String getDescription(){
        return "Complete battle: " + requiredBattle.getName() + ".";
    }
}
