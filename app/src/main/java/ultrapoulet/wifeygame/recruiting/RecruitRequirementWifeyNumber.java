package ultrapoulet.wifeygame.recruiting;

import ultrapoulet.wifeygame.character.SkillsEnum;
import ultrapoulet.wifeygame.gamestate.RecruitedCharacters;

/**
 * Created by John on 5/12/2017.
 */

public class RecruitRequirementWifeyNumber extends RecruitRequirement {

    private SkillsEnum skill;
    private int number;

    public RecruitRequirementWifeyNumber(SkillsEnum inSkill){
        this.skill = inSkill;
    }

    public void setNumber(int inNumber){
        this.number = inNumber;
    }

    @Override
    public String getDescription() {
        String detail = (skill != null) ? " " + skill.getSkillName() : "";
        String wifey = (number > 1) ? " Wifeys" : " Wifey";
        return "Recruit " + number + detail + wifey;
    }

    @Override
    public boolean isComplete() {
        if(skill == null){
            return RecruitedCharacters.getNumberRecruited() > number;
        }
        else {
            return RecruitedCharacters.getNumberRecruited(skill) > number;
        }
    }

    @Override
    public boolean validate() {
        return number > 0;
    }
}
