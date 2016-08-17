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
        if (enemyPercent >= 0.75) {
            return 0.5;
        }
        else {
            return 0.5 + ((0.75 - enemyPercent) * 4);
        }
    }

    @Override
    public double magicalAttackPercentage(BattleCharacter enemy) {
        double enemyPercent = (enemy.getCurrentHP() * 1.0 / enemy.getMaxHP());
        if (enemyPercent >= 0.75) {
            return 0.5;
        }
        else {
            return 0.5 + ((0.75 - enemyPercent) * 4);
        }
    }

    @Override
    public double specialAttackPercentage(BattleCharacter enemy) {
        double enemyPercent = (enemy.getCurrentHP() * 1.0 / enemy.getMaxHP());
        if (enemyPercent >= 0.75) {
            return 0.5;
        }
        else {
            return 0.5 + ((0.75 - enemyPercent) * 4);
        }
    }
}
