package ultrapoulet.wifeygame.battle.skills;

import ultrapoulet.wifeygame.battle.BattleCharacter;

/**
 * Created by John on 7/25/2016.
 */
public class ProtagonistSkill extends AbsSkill {

    boolean revived = false;

    public ProtagonistSkill(BattleCharacter owner) { super(owner); }

    @Override
    public void onDamageReceived(int damage) {
        if(owner.getCurrentHP() <= 0 && !revived){
            owner.setCurrentHP(0);
            owner.healDamage(owner.HealAmount(owner), owner);
            revived = true;
        }
    }
}
