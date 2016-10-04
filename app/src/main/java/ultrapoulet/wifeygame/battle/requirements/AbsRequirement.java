package ultrapoulet.wifeygame.battle.requirements;

import ultrapoulet.wifeygame.character.WifeyCharacter;

/**
 * Created by John on 10/4/2016.
 */

public abstract class AbsRequirement {

    //Returns true if the value was successfully added, false otherwise
    public abstract boolean addValue(String input);

    //Returns true if the character is valid for this requirement
    public abstract boolean validateCharacter(WifeyCharacter character);

    public abstract String getDescription();
}
