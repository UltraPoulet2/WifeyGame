package ultrapoulet.wifeygame.battle.requirements;

import ultrapoulet.wifeygame.character.WifeyCharacter;

/**
 * Created by John on 10/8/2016.
 */

public abstract class AbsRequirement {

    //Returns true if the value was successfully added, false otherwise
    public abstract boolean addValue(String input);

    //Returns true if the character is valid for this requirement
    public boolean validateCharacter(WifeyCharacter character){
        return true;
    }

    //Returns true if the party is valid for this requirement
    public boolean validateParty(WifeyCharacter[] party){
        return true;
    }

    public abstract String getDescription();
}