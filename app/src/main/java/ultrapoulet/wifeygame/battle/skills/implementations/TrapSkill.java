package ultrapoulet.wifeygame.battle.skills.implementations;

import ultrapoulet.wifeygame.battle.BattleCharacter;
import ultrapoulet.wifeygame.battle.skills.AbsSkill;

/**
 * Created by John on 8/13/2016.
 */
public class TrapSkill extends AbsSkill {

    public TrapSkill(BattleCharacter owner){
        super(owner);
        this.skillName = "Trap";
    }

    @Override
    public double receivePhysicalAttackPercentage(BattleCharacter enemy) {
        return 0.15;
    }

    @Override
    public double receiveMagicalAttackPercentage(BattleCharacter enemy) {
        return 0.15;
    }

    @Override
    public double receiveSpecialAttackPercentage(BattleCharacter enemy) {
        return 0.15;
    }

    @Override
    public double[] getMultipliers(BattleCharacter enemy){
        double multipliers[] = new double[6];
        multipliers[PHYS_ATK] = 1.0;
        multipliers[MAG_ATK] = 1.0;
        multipliers[SPEC_ATK] = 1.0;
        multipliers[PHYS_DEF] = 0.15;
        multipliers[MAG_DEF] = 0.15;
        multipliers[SPEC_DEF] = 0.15;

        return multipliers;
    }

    @Override
    public String getDescription(BattleCharacter enemy) {
        StringBuilder desc = new StringBuilder();
        desc.append("Defense Multiplier: 0.15x\n\n");
        desc.append("Multiplies physical, magical, and special damage taken by 0.15x.");
        return desc.toString();
    }
}
