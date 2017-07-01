package ultrapoulet.wifeygame.battle.skills;

import ultrapoulet.wifeygame.battle.BattleCharacter;

/**
 * Created by John on 6/3/2017.
 */

public abstract class AbsWeaponSkill extends AbsSkill {

    //I want to differentiate AbsWeaponSkill from AbsSkill, even though they are functionally similar

    public AbsWeaponSkill(BattleCharacter owner){
        super(owner);
    }

}
