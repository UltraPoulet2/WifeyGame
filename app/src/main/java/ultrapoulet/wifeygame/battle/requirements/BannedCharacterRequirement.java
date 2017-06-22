package ultrapoulet.wifeygame.battle.requirements;

import java.util.ArrayList;

import ultrapoulet.androidgame.framework.Game;
import ultrapoulet.androidgame.framework.Screen;
import ultrapoulet.wifeygame.character.WifeyCharacter;
import ultrapoulet.wifeygame.gamestate.Characters;
import ultrapoulet.wifeygame.screens.dialogs.RequirementWifeyInfoDialog;

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
        WifeyCharacter wifey = Characters.get(input);
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

    @Override
    public Screen getRequirementDialog(Game game, Screen prevScreen) {
        return new RequirementWifeyInfoDialog(game, prevScreen, "The following wifeys are not allowed:",  bannedWifeys);
    }

    public String getDescription(){
        return "Certain Wifeys banned";
    }
}
