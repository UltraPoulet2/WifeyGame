package ultrapoulet.wifeygame.battle.requirements;

import java.util.ArrayList;

import ultrapoulet.wifeygame.character.SkillsEnum;
import ultrapoulet.wifeygame.character.WifeyCharacter;

/**
 * Created by John on 10/11/2016.
 */

public class BannedSkillRequirement extends AbsRequirement {

    //Banned Skill list a list of several skills that a character can not have
    private ArrayList<SkillsEnum> bannedSkills;

    public BannedSkillRequirement(){
        bannedSkills = new ArrayList<>();
    }

    public boolean addValue(String input){
        SkillsEnum skill = SkillsEnum.getSkill(input);
        if(skill == null) {
            return false;
        }
        if(bannedSkills.contains(skill)){
            return false;
        }
        bannedSkills.add(skill);
        return true;
    }

    @Override
    public boolean validateCharacter(WifeyCharacter character){
        for(int i = 0; i < bannedSkills.size(); i++){
            if(character.getSkills().contains(bannedSkills.get(i))){
                return false;
            }
        }
        return true;
    }

    public String getDescription(){
        return "Certain skills banned";
    }
}
