package ultrapoulet.wifeygame.battle.skills;

import ultrapoulet.wifeygame.battle.BattleCharacter;

/**
 * Created by John on 7/20/2016.
 */
public class GhostSkill extends AbsSkill {

    public GhostSkill(BattleCharacter owner) {
        super(owner);
        this.skillName = "Ghost";
        this.description = "Desc";
    }

    @Override
    public double receivePhysicalAttackPercentage(BattleCharacter enemy) {
        return 0.5;
    }


    @Override
    public double[] getMultipliers(BattleCharacter enemy) {
        double multipliers[] = new double[6];
        multipliers[PHYS_ATK] = 1.0;
        multipliers[MAG_ATK] = 1.0;
        multipliers[SPEC_ATK] = 1.0;
        multipliers[PHYS_DEF] = 0.5;
        multipliers[MAG_DEF] = 0.0;
        multipliers[SPEC_DEF] = 0.0;

        return multipliers;
    }
}
