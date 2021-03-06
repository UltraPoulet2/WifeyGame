package ultrapoulet.wifeygame.battle.skills.basicskills;

import ultrapoulet.wifeygame.battle.BattleCharacter;
import ultrapoulet.wifeygame.battle.skills.AbsSkill;

/**
 * Created by John on 7/25/2016.
 */
public class ProtagonistSkill extends AbsSkill {

    boolean revived = false;

    public ProtagonistSkill(BattleCharacter owner) {
        super(owner);
        this.skillName = "Protagonist";
    }

    @Override
    public boolean canPreventDeath() {
        return !revived;
    }

    @Override
    public int preventDeath() {
        if(owner.getCurrentHP() == 0 && !revived){
            owner.setCurrentHP(0);
            revived = true;
            return owner.healDamage(owner.HealAmount(owner), owner);
        }
        return 0;
    }

    @Override
    public String getDescription(BattleCharacter enemy) {
        String desc = "Lethal Damage Prevented: " +
                (this.revived ? "Yes\n\n" : "No\n\n") +
                "When this wifey suffers lethal damage the first time, prevent the death and heal self.";
        return desc;
    }
}
