package ultrapoulet.wifeygame.battle.skills;

import ultrapoulet.wifeygame.battle.BattleCharacter;

/**
 * Created by John on 7/30/2016.
 */
public class KillerSkill extends AbsSkill {

    public KillerSkill(BattleCharacter owner){
        super(owner);
        this.skillName = "Killer";
        this.description = "Desc";
    }

    private double multiplier = 1.0;

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
    public void onEnemyDefeat(BattleCharacter enemy) {
        multiplier += 1.0;
    }
}
