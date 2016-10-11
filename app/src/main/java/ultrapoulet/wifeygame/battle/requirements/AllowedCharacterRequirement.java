package ultrapoulet.wifeygame.battle.requirements;

import java.util.ArrayList;

import ultrapoulet.wifeygame.character.WifeyCharacter;
import ultrapoulet.wifeygame.gamestate.RecruitedCharacters;

/**
 * Created by John on 10/11/2016.
 */

public class AllowedCharacterRequirement extends AbsRequirement {

    //Allowed Wifey list contains Wifeys that are allowed to participate in a battle
    private ArrayList<WifeyCharacter> allowedWifeys;

    public AllowedCharacterRequirement(){
        allowedWifeys = new ArrayList<>();
    }

    //Returns true if the value was successfully added, false otherwise
    public boolean addValue(String input){
        //Change this to all instead of recruited
        WifeyCharacter wifey = RecruitedCharacters.get(input);
        if(wifey == null){
            return false;
        }
        if(allowedWifeys.contains(wifey)){
            return false;
        }
        allowedWifeys.add(wifey);
        return true;
    }

    //Returns true if the character is valid for this requirement
    @Override
    public boolean validateCharacter(WifeyCharacter character){
        return allowedWifeys.contains(character);
    }

    public String getDescription(){
        return "To do";
    }
}
