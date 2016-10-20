package ultrapoulet.wifeygame.battle.requirements;

import java.util.ArrayList;

import ultrapoulet.wifeygame.character.WifeyCharacter;
import ultrapoulet.wifeygame.gamestate.RecruitedCharacters;

/**
 * Created by John on 10/9/2016.
 */

public class BannedCharacterRequirement extends AbsRequirement {

    //Banned Wifey list contains Wifeys that are not allowed to participate in a battle
    private ArrayList<WifeyCharacter> bannedWifeys;

    public BannedCharacterRequirement(){
        bannedWifeys = new ArrayList<>();
    }

    //Returns true if the value was successfully added, false otherwise
    public boolean addValue(String input){
        //Change this to all instead of recruited
        WifeyCharacter wifey = RecruitedCharacters.get(input);
        if(wifey == null){
            return false;
        }
        if(bannedWifeys.contains(wifey)){
            return false;
        }
        bannedWifeys.add(wifey);
        return true;
    }

    //Returns true if the character is valid for this requirement
    @Override
    public boolean validateCharacter(WifeyCharacter character){
        return !bannedWifeys.contains(character);
    }

    public String getDescription(){
        return "Certain Wifeys banned";
    }
}
