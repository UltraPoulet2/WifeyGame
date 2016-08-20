package ultrapoulet.wifeygame.battle.skills;

import ultrapoulet.wifeygame.battle.BattleCharacter;

/**
 * Created by John on 7/29/2016.
 */
public class RacerSkill extends AbsSkill {

    public RacerSkill(BattleCharacter owner){
        super(owner);
        this.skillName = "Racer";
        this.description = "Desc";
    }

    private double startMultiplier = 4.0;
    private double perTurn = 0.5;
    private double minMultiplier = 0.5;
    private double multiplier;

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
