package ultrapoulet.wifeygame.battle.skills.implementations;

import ultrapoulet.wifeygame.battle.BattleCharacter;
import ultrapoulet.wifeygame.battle.skills.AbsSkill;

/**
 * Created by John on 7/28/2016.
 */
public class NurseSkill extends AbsSkill {

    public NurseSkill(BattleCharacter owner) {
        super(owner);
        this.skillName = "Nurse";
    }

    @Override
    public double healPercentage(BattleCharacter partyMember) {
        return 2.0;
    }

    @Override
    public int startRound() {
        int baseHeal = owner.HealAmount(owner) / 10;
        //Multiply by -1 to indicate heal
        return -1 * owner.healDamage(baseHeal, owner);
    }

    @Override
    public String getDescription(BattleCharacter enemy) {
        StringBuilder desc = new StringBuilder();
        desc.append("Healing Multiplier: 2.0x\n\n");
        desc.append("Multiplies healing by 2.0x. Gains health at the start of every round.");
        return desc.toString();
    }
}
