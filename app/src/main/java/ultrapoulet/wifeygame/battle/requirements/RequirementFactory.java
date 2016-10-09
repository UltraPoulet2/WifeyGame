package ultrapoulet.wifeygame.battle.requirements;

/**
 * Created by John on 10/8/2016.
 */

public class RequirementFactory {

    public static AbsRequirement getRequirement(String input){
        if(input.equalsIgnoreCase("RequiredSkill")){
            return new RequiredSkillRequirement();
        }
        else if(input.equalsIgnoreCase("BannedCharacter")){
            return new BannedCharacterRequirement();
        }
        else if(input.equalsIgnoreCase("RequiredCharacter")){
            return new RequiredCharacterRequirement();
        }
        return null;
    }
}