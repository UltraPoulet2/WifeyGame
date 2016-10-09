package ultrapoulet.wifeygame.battle.requirements;

import java.util.ArrayList;

import ultrapoulet.wifeygame.character.SkillsEnum;
import ultrapoulet.wifeygame.character.WifeyCharacter;

/**
 * Created by John on 10/8/2016.
 */

public class RequiredSkillRequirement extends AbsRequirement {

    private ArrayList<SkillsEnum> requiredSkills;

    public RequiredSkillRequirement(){
        requiredSkills = new ArrayList<>();
    }

    public boolean addValue(String input){
        SkillsEnum skill = SkillsEnum.getSkill(input);
        if(skill == null) {
            return false;
        }
        if(requiredSkills.contains(skill)){
            return false;
        }
        requiredSkills.add(skill);
        return true;
    }

    public boolean validateCharacter(WifeyCharacter character){
        for(int i = 0; i < requiredSkills.size(); i++){
            if(character.getSkills().contains(requiredSkills.get(i))){
                return true;
            }
        }
        return false;
    }

    public String getDescription(){
        return "Determine later";
    }
}
