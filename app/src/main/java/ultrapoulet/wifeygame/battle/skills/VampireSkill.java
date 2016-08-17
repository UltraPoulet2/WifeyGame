package ultrapoulet.wifeygame.battle.skills;

import ultrapoulet.wifeygame.battle.BattleCharacter;

/**
 * Created by John on 8/2/2016.
 */
public class VampireSkill extends AbsSkill {

    public VampireSkill(BattleCharacter owner){
        super(owner);
        this.skillName = "Vampire";
        this.description = "Desc";
    }

    @Override
    public void onDamageDealt(int damage) {
        int healing = damage / 10;
        owner.healDamage(healing, owner);
    }
}
