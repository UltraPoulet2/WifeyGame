package ultrapoulet.wifeygame.battle.requirements;

/**
 * Created by John on 10/4/2016.
 */

public class RequirementFactory {

    public static AbsRequirement getRequirement(String input){
        if(input.equalsIgnoreCase("RequiredSkill")){
            return new RequiredSkillRequirement();
        }
        return null;
    }
}
