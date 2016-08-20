package ultrapoulet.wifeygame.battle.skills;

import ultrapoulet.wifeygame.battle.BattleCharacter;

/**
 * Created by John on 7/24/2016.
 */
public class DullahanSkill extends AbsSkill {

    public DullahanSkill(BattleCharacter owner){
        super(owner);
        this.skillName = "Dullahan";
        this.description = "Desc";
    }

    @Override
    public double physicalAttackPercentage(BattleCharacter enemy) {
        double enemyPercent = (enemy.getCurrentHP() * 1.0 / enemy.getMaxHP());
        return getMult(enemyPercent);
    }

    @Override
    public double magicalAttackPercentage(BattleCharacter enemy) {
        double enemyPercent = (enemy.getCurrentHP() * 1.0 / enemy.getMaxHP());
        return getMult(enemyPercent);
    }

    @Override
    public double specialAttackPercentage(BattleCharacter enemy) {
        double enemyPercent = (enemy.getCurrentHP() * 1.0 / enemy.getMaxHP());
        return getMult(enemyPercent);
    }

    private double getMult(double enemyPercent){
        if (enemyPercent >= 0.75) {
            return 0.5;
        }
        else {
            return 0.5 + ((0.75 - enemyPercent) * 4);
        }
    }

    @Override
    public double[] getMultipliers(BattleCharacter enemy) {
        double multiplier;
        double enemyPercent = (enemy.getCurrentHP() * 1.0 / enemy.getMaxHP());
        multiplier = getMult(enemyPercent);

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
