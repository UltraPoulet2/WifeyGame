package ultrapoulet.wifeygame.battle.skills;

import ultrapoulet.wifeygame.battle.BattleCharacter;

/**
 * Created by John on 8/10/2016.
 */
public class DetectiveSkill extends AbsSkill {

    private double startMultiplier = 0.5;
    private double perTurn = 0.5;
    private double maxMultiplier = 5.0;
    private double multiplier;

    public DetectiveSkill(BattleCharacter owner) {
        super(owner);
        this.skillName = "Detective";
        this.description = "Desc";
    }

    @Override
    public void startWave() {
        multiplier = startMultiplier;
    }

    @Override
    public double physicalAttackPercentage(BattleCharacter enemy) {
        return multiplier;
    }

    @Override
    public double magicalAttackPercentage(BattleCharacter enemy) {
        return multiplier;
    }

    @Override
    public double specialAttackPercentage(BattleCharacter enemy) {
        return multiplier;
    }

    @Override
    public void endRound() {
        if(multiplier < maxMultiplier) {
            multiplier += perTurn;
        }
    }

    @Override
    public double[] getMultipliers(BattleCharacter enemy) {
        double multipliers[] = new double[6];
        multipliers[PHYS_ATK] = multiplier;
        multipliers[MAG_ATK] = multiplier;
        multipliers[SPEC_ATK] = multiplier;
        multipliers[PHYS_DEF] = 0.0;
        multipliers[MAG_DEF] = 0.0;
        multipliers[SPEC_DEF] = 0.0;

        return multipliers;
    }
}
