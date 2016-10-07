package ultrapoulet.wifeygame.battle.requirements;

import java.util.ArrayList;

import ultrapoulet.wifeygame.character.WifeyCharacter;
import ultrapoulet.wifeygame.gamestate.RecruitedCharacters;

/**
 * Created by John on 10/7/2016.
 */

public class RequiredCharacterRequirement extends AbsRequirement {

    //Required Wifey list contains a list of characters that the party must contain

    private ArrayList<WifeyCharacter> requiredWifeys;

    public RequiredCharacterRequirement(){
        requiredWifeys = new ArrayList<>();
    }

    @Override
    public boolean addValue(String input) {
        //Change this to all instead of recruited
        WifeyCharacter wifey = RecruitedCharacters.get(input);
        if(wifey == null){
            return false;
        }
        if(requiredWifeys.contains(wifey)){
            return false;
        }
        requiredWifeys.add(wifey);
        return true;
    }

    @Override
    public boolean validateParty(WifeyCharacter[] party){
        //This will be so much simpler after the conversion to ArrayList
        for(int i = 0; i < requiredWifeys.size(); i++){
            boolean found = false;
            for(int j = 0; j < party.length; j++){
                if(party[j] == requiredWifeys.get(i)){
                    found = true;
                }
            }
            if(found == false){
                return false;
            }
        }
        return true;
    }

    @Override
    public String getDescription() {
        return "To do";
    }
}
