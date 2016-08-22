package ultrapoulet.wifeygame.battle.skills;

import ultrapoulet.wifeygame.battle.BattleCharacter;

/**
 * Created by John on 7/24/2016.
 */
public class DullahanSkill extends AbsSkill {

    public DullahanSkill(BattleCharacter owner){
        super(owner);
        this.skillName = "Dullahan";
    }

    @Override
    public double physicalAttackPercentage(BattleCharacter enemy) {
        return getMult(enemy);
    }

    @Override
    public double magicalAttackPercentage(BattleCharacter enemy) {
        return getMult(enemy);
    }

    @Override
    public double specialAttackPercentage(BattleCharacter enemy) {
        return getMult(enemy);
    }

    private double getMult(BattleCharacter enemy){
        double enemyPercent = (enemy.getCurrentHP() * 1.0 / enemy.getMaxHP());
        if (enemyPercent >= 0.75) {
            return 0.5;
        }
        else {
            return 0.5 + ((0.75 - enemyPercent) * 4);
        }
    }

    @Override
    public double[] getMultipliers(BattleCharacter enemy) {
        double multipliers[] = new double[6];
        multipliers[PHYS_ATK] = getMult(enemy);
        multipliers[MAG_ATK] = getMult(enemy);
        multipliers[SPEC_ATK] = getMult(enemy);
        multipliers[PHYS_DEF] = 0.0;
        multipliers[MAG_DEF] = 0.0;
        multipliers[SPEC_DEF] = 0.0;

        return multipliers;
    }

    @Override
    public String getDescription(BattleCharacter enemy) {
        StringBuilder desc = new StringBuilder();
        desc.append("Attack Multiplier: " + getMult(enemy) + "\n\n");
        desc.append("Multiplies damage dealt by 0.75x. Each percentage point below 75% the enemy's health is at increases the multiplier by 0.4x.");
        return desc.toString();
    }
}
