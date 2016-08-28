package ultrapoulet.wifeygame.battle.skills.implementations;

import ultrapoulet.wifeygame.battle.BattleCharacter;
import ultrapoulet.wifeygame.battle.skills.AbsSkill;

/**
 * Created by John on 7/19/2016.
 */
public class TsundereSkill extends AbsSkill {

    private double multiplier;
    private double maxMultiplier = 4.0;

    public TsundereSkill(BattleCharacter owner){
        super(owner);
        this.skillName = "Tsundere";
    }

    @Override
    public void startBattle(BattleCharacter[] party) {
        multiplier = maxMultiplier - (party.length - 1);
        if(multiplier < 1.0){
            multiplier = 1.0;
        }
    }

    @Override
    public double physicalAttackPercentage(BattleCharacter enemy) {
        return multiplier;
    }

    @Override
    public double magicalAttackPercentage(BattleCharacter enemy){
        return multiplier;
    }

    @Override
    public double specialAttackPercentage(BattleCharacter enemy) {
        return multiplier;
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
        desc.append("Damage Multiplier: " + multiplier + "x\n\n");
        desc.append("Multiplies damage dealt by 2.0x if there are only 3 party members, 3.0x if there are only 2, 4.0x if this is the only wifey.");
        return desc.toString();
    }
}
