package ultrapoulet.wifeygame.battle.skills;

import ultrapoulet.wifeygame.battle.BattleCharacter;

/**
 * Created by John on 7/29/2016.
 */
public class SlugabedSkill extends AbsSkill {

    public SlugabedSkill(BattleCharacter owner) {
        super(owner);
        this.skillName = "Slugabed";
        this.description = "Desc";
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
}
