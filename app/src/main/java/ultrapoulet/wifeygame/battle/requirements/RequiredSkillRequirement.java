package ultrapoulet.wifeygame.battle.requirements;

import java.util.ArrayList;
import java.util.List;

import ultrapoulet.androidgame.framework.Game;
import ultrapoulet.androidgame.framework.Screen;
import ultrapoulet.wifeygame.character.SkillsEnum;
import ultrapoulet.wifeygame.character.WifeyCharacter;
import ultrapoulet.wifeygame.screens.dialogs.AdjustableTextInfoDialog;

/**
 * Created by John on 10/8/2016.
 */

public class RequiredSkillRequirement extends AbsRequirement {

    //Required Skill list a list of several skills that a character must have at least one of
    private ArrayList<SkillsEnum> requiredSkills;

    public RequiredSkillRequirement(){
        requiredSkills = new ArrayList<>();
    }

    public boolean addValue(String input){
        SkillsEnum skill;
        try {
            skill = SkillsEnum.valueOf(input);
        }
        catch(IllegalArgumentException e){
            skill = null;
        }
        if(skill == null) {
            return false;
        }
        if(requiredSkills.contains(skill)){
            return false;
        }
        requiredSkills.add(skill);
        return true;
    }

    @Override
    public boolean validateParty(List<WifeyCharacter> party) {
        for(WifeyCharacter wifey: party){
            if(!validateCharacter(wifey)){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean validateCharacter(WifeyCharacter character){
        for(int i = 0; i < requiredSkills.size(); i++){
            if(character.getSkills().contains(requiredSkills.get(i))){
                return true;
            }
        }
        return false;
    }

    @Override
    public Screen getRequirementDialog(Game game, Screen prevScreen) {
        StringBuilder desc = new StringBuilder();
        desc.append("All Wifeys required to have one of the following skills:");
        for(SkillsEnum skill : requiredSkills){
            desc.append("\n");
            desc.append(skill.getSkillName());
        }
        return new AdjustableTextInfoDialog(game, prevScreen, desc.toString());
    }

    public String getTitle(){
        return "Required Skills";
    }
}
