package ultrapoulet.wifeygame.battle.requirements;

import java.util.ArrayList;
import java.util.List;

import ultrapoulet.androidgame.framework.Game;
import ultrapoulet.androidgame.framework.Screen;
import ultrapoulet.wifeygame.character.WifeyCharacter;
import ultrapoulet.wifeygame.gamestate.Characters;
import ultrapoulet.wifeygame.screens.dialogs.RequirementWifeyInfoDialog;

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
        WifeyCharacter wifey = Characters.get(input);
        if(wifey == null){
            return false;
        }
        if(allowedWifeys.contains(wifey)){
            return false;
        }
        allowedWifeys.add(wifey);
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

    //Returns true if the character is valid for this requirement
    @Override
    public boolean validateCharacter(WifeyCharacter character){
        return allowedWifeys.contains(character);
    }

    @Override
    public Screen getRequirementDialog(Game game, Screen prevScreen) {
        return new RequirementWifeyInfoDialog(game, prevScreen, "The following wifeys are allowed:",  allowedWifeys);
    }

    public String getTitle(){
        return "Restricted Wifeys";
    }
}
