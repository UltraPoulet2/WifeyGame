package ultrapoulet.wifeygame.battle.skills;

import ultrapoulet.wifeygame.battle.BattleCharacter;

/**
 * Created by John on 6/7/2017.
 */

public class AbsUniqueSkill extends AbsSkill {

    //I want to differentiate AbsWeaponSkill from AbsSkill, even though they are functionally similar

    public AbsUniqueSkill(BattleCharacter owner){
        super(owner);
    }
}
