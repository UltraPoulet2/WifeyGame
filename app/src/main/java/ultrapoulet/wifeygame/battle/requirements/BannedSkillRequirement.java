package ultrapoulet.wifeygame.battle.requirements;

import java.util.ArrayList;
import java.util.List;

import ultrapoulet.androidgame.framework.Game;
import ultrapoulet.androidgame.framework.Screen;
import ultrapoulet.wifeygame.character.SkillsEnum;
import ultrapoulet.wifeygame.character.WifeyCharacter;
import ultrapoulet.wifeygame.screens.dialogs.RequirementTextInfoDialog;

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
        if(bannedSkills.contains(skill)){
            return false;
        }
        bannedSkills.add(skill);
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
        for(int i = 0; i < bannedSkills.size(); i++){
            if(character.getSkills().contains(bannedSkills.get(i))){
                return false;
            }
        }
        return true;
    }

    @Override
    public Screen getRequirementDialog(Game game, Screen prevScreen) {
        StringBuilder desc = new StringBuilder();
        desc.append("Wifeys are not allowed to have any of the following skills:");
        for(SkillsEnum skill : bannedSkills){
            desc.append("\n");
            desc.append(skill.getSkillName());
        }
        return new RequirementTextInfoDialog(game, prevScreen, desc.toString());
    }

    public String getTitle(){
        return "Banned Skills";
    }
}
