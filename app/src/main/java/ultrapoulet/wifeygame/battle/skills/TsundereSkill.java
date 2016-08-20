package ultrapoulet.wifeygame.battle.skills;

import ultrapoulet.wifeygame.battle.BattleCharacter;

/**
 * Created by John on 7/19/2016.
 */
public class TsundereSkill extends AbsSkill {

    private double multiplier;
    private double maxMultiplier = 4.0;

    public TsundereSkill(BattleCharacter owner){
        super(owner);
        this.skillName = "Tsundere";
        this.description = "Desc";
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
}
