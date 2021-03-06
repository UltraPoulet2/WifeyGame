package ultrapoulet.wifeygame.battle.requirements;

import java.util.ArrayList;
import java.util.List;

import ultrapoulet.androidgame.framework.Game;
import ultrapoulet.androidgame.framework.Screen;
import ultrapoulet.wifeygame.character.WifeyCharacter;
import ultrapoulet.wifeygame.gamestate.Characters;
import ultrapoulet.wifeygame.screens.dialogs.RequirementWifeyInfoDialog;

/**
 * Created by John on 10/9/2016.
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
        WifeyCharacter wifey = Characters.get(input);
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
    public boolean validateParty(List<WifeyCharacter> party){
        for(int i = 0; i < requiredWifeys.size(); i++){
            if(!party.contains(requiredWifeys.get(i))){
                return false;
            }
        }
        return true;
    }

    public ArrayList<WifeyCharacter> getRequiredList(){
        return requiredWifeys;
    }

    @Override
    public Screen getRequirementDialog(Game game, Screen prevScreen) {
        return new RequirementWifeyInfoDialog(game, prevScreen, "The following wifeys are required:",  requiredWifeys);
    }

    @Override
    public String getTitle() {
        return "Required Wifeys";
    }
}
