package ultrapoulet.wifeygame.battle.skills;

import ultrapoulet.wifeygame.battle.BattleCharacter;

/**
 * Created by John on 7/15/2016.
 */
public class SadistSkill extends AbsSkill {

    private final double hitMultiplier = 0.01;
    private int numHits = 0;

    public SadistSkill(BattleCharacter owner){ super(owner); }

    @Override
    public double physicalAttackPercentage(BattleCharacter enemy){
        return hitMultiplier * numHits;
    }

    @Override
    public double magicalAttackPercentage(BattleCharacter enemy) {
        return hitMultiplier * numHits;
    }

    @Override
    public double specialAttackPercentage(BattleCharacter enemy) {
        return hitMultiplier * numHits;
    }

    @Override
    public void onDamageDealt(int damage) {
        if(damage > 0){
            numHits++;
        }
    }

    @Override
    public void onEnemyDefeat(BattleCharacter enemy) {
        numHits = 0;
    }
}
