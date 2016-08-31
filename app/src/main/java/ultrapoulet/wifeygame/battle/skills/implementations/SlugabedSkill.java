package ultrapoulet.wifeygame.battle.skills.implementations;

import ultrapoulet.wifeygame.battle.BattleCharacter;
import ultrapoulet.wifeygame.battle.skills.AbsSkill;

/**
 * Created by John on 7/29/2016.
 */
public class SlugabedSkill extends AbsSkill {

    public SlugabedSkill(BattleCharacter owner) {
        super(owner);
        this.skillName = "Slugabed";
    }

    private double multiplier = 6.0;
    private double minMultiplier = 0.25;
    private double perTurn = 0.25;

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
    public void endRound(){
        multiplier -= perTurn;
        if(multiplier < minMultiplier){
            multiplier = minMultiplier;
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
        desc.append("Attack Multiplier: " + multiplier + "x\n\n");
        desc.append("Multiplies damage dealt by 6.0x. Reduces multiplier by 0.25x for each turn, to a minimum of 0.25x.");
        return desc.toString();
    }
}
