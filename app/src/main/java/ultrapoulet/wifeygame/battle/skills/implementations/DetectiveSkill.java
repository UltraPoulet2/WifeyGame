package ultrapoulet.wifeygame.battle.skills.implementations;

import ultrapoulet.wifeygame.battle.BattleCharacter;
import ultrapoulet.wifeygame.battle.skills.AbsSkill;

/**
 * Created by John on 8/10/2016.
 */
public class DetectiveSkill extends AbsSkill {

    private double startMultiplier = 0.5;
    private double perTurn = 0.25;
    private double maxMultiplier = 5.0;
    private double multiplier;

    public DetectiveSkill(BattleCharacter owner) {
        super(owner);
        this.skillName = "Detective";
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

    @Override
    public String getDescription(BattleCharacter enemy) {
        StringBuilder desc = new StringBuilder();
        desc.append("Attack Multiplier: " + multiplier + "\n\n");
        desc.append("Multiplies damage dealt by 0.5x at the start of a wave. Multiplier increases by 0.25x at the end of each turn, up to a maximum of 5.0x.");
        return desc.toString();
    }
}