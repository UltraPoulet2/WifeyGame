package ultrapoulet.wifeygame.recruiting;

/**
 * Created by John on 5/7/2017.
 */

public class RecruitRequirementBattle extends RecruitRequirementAbsBattle {

    public String getDescription(){
        return "Complete story battle: '" + requiredBattle.getName() + "'";
    }

}
