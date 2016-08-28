package ultrapoulet.wifeygame.battle.skills.implementations;

import ultrapoulet.wifeygame.battle.BattleCharacter;
import ultrapoulet.wifeygame.battle.skills.AbsSkill;

/**
 * Created by John on 7/31/2016.
 */
public class ChuuniSkill extends AbsSkill {

    public ChuuniSkill(BattleCharacter owner){
        super(owner);
        this.skillName = "Chuunibyou";
    }

    @Override
    public double specialAttackPercentage(BattleCharacter enemy) {
        return 5.0;
    }

    @Override
    public double[] getMultipliers(BattleCharacter enemy) {
        double multipliers[] = new double[6];
        multipliers[PHYS_ATK] = 1.0;
        multipliers[MAG_ATK] = 1.0;
        multipliers[SPEC_ATK] = 5.0;
        multipliers[PHYS_DEF] = 0.0;
        multipliers[MAG_DEF] = 0.0;
        multipliers[SPEC_DEF] = 0.0;

        return multipliers;
    }

    @Override
    public String getDescription(BattleCharacter enemy){
        StringBuilder desc = new StringBuilder();
        desc.append("Special Attack Multiplier: 5.0x\n\n");
        desc.append("Multiplies special attack damage dealt by 5.0x.");
        return desc.toString();
    }
}
