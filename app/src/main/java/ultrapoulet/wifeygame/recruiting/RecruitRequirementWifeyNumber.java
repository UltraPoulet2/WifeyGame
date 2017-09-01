package ultrapoulet.wifeygame.recruiting;

import android.util.Log;

import ultrapoulet.androidgame.framework.Game;
import ultrapoulet.androidgame.framework.Screen;
import ultrapoulet.wifeygame.character.SkillsEnum;
import ultrapoulet.wifeygame.character.WifeyCharacter;
import ultrapoulet.wifeygame.gamestate.RecruitedCharacters;
import ultrapoulet.wifeygame.screens.dialogs.InfoDialog;

/**
 * Created by John on 5/12/2017.
 */

public class RecruitRequirementWifeyNumber extends RecruitRequirement {

    private SkillsEnum skill;
    private int number;
    private boolean error = false;

    public RecruitRequirementWifeyNumber(String inSkill){
        if(inSkill != null){
            try {
                skill = SkillsEnum.valueOf(inSkill);
            }
            catch(IllegalArgumentException e){
                skill = null;
            }
            if(inSkill != null && skill  == null){
                //System.out.println("RecruitRequirementWifeyNumber(): Could not find skill: " + skill);
                Log.e("RecruitRe...WifeyNumber", "Could not find skill: " + skill);
                error = true;
            }
        }

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
        return number > 0 && !error;
    }

    public Screen getScreen(Game game, Screen prevScreen, WifeyCharacter recruit) {
        if(this.isComplete()){
            return null;
        }
        else {
            int numFound;
            if(skill == null){
                numFound = RecruitedCharacters.getNumberRecruited();
            }
            else {
                numFound = RecruitedCharacters.getNumberRecruited(skill);
            }
            String detail = (skill != null) ? " " + skill.getSkillName() : "";
            String wifey = (number > 1) ? " Wifeys" : " Wifey";
            String info = "Recruited " + numFound + "/" + number + detail + wifey;
            return new InfoDialog(game, prevScreen, info);
        }
    }
}
