package ultrapoulet.wifeygame.battle.skills.implementations;

import ultrapoulet.wifeygame.battle.BattleCharacter;
import ultrapoulet.wifeygame.battle.skills.AbsSkill;

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
    public Multipliers getMultipliers(BattleCharacter enemy){
        Multipliers returnValue = new Multipliers();
        returnValue.setPhysAtk(getMult(enemy));
        returnValue.setMagAtk(getMult(enemy));
        returnValue.setSpecAtk(getMult(enemy));
        return returnValue;
    }

    @Override
    public String getDescription(BattleCharacter enemy) {
        StringBuilder desc = new StringBuilder();
        desc.append("Attack Multiplier: " + String.format("%1$.2f", getMult(enemy)) + "x\n\n");
        desc.append("Multiplies damage dealt by 0.75x. Each percentage point below 75% the enemy's health is at increases the multiplier by 0.40x.");
        return desc.toString();
    }
}
