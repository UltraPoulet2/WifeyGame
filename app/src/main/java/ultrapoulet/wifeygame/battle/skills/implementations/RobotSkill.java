package ultrapoulet.wifeygame.battle.skills.implementations;

import ultrapoulet.wifeygame.battle.BattleCharacter;
import ultrapoulet.wifeygame.battle.skills.AbsSkill;

/**
 * Created by John on 7/22/2016.
 */
public class RobotSkill extends AbsSkill {

    public RobotSkill(BattleCharacter owner) {
        super(owner);
        this.skillName = "Robot";
    }

    @Override
    public double receivePhysicalAttackPercentage(BattleCharacter enemy) {
        return 0.2;
    }

    @Override
    public double receiveMagicalAttackPercentage(BattleCharacter enemy) {
        return -0.2;
    }

    @Override
    public double[] getMultipliers(BattleCharacter enemy){
        double multipliers[] = new double[6];
        multipliers[PHYS_ATK] = 1.0;
        multipliers[MAG_ATK] = 1.0;
        multipliers[SPEC_ATK] = 1.0;
        multipliers[PHYS_DEF] = 0.2;
        multipliers[MAG_DEF] = -0.2;
        multipliers[SPEC_DEF] = 0.0;

        return multipliers;
    }

    @Override
    public String getDescription(BattleCharacter enemy) {
        StringBuilder desc = new StringBuilder();
        desc.append("Physical Defense Multiplier: 0.8x\n");
        desc.append("Magical Defense Multiplier: 1.2x\n\n");
        desc.append("Multiplies physical damage taken by 0.8x. Multiplies magical damage taken by 1.2x.");
        return desc.toString();
    }
}
