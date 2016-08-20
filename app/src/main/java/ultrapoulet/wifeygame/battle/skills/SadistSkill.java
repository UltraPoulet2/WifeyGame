package ultrapoulet.wifeygame.battle.skills;

import ultrapoulet.wifeygame.battle.BattleCharacter;

/**
 * Created by John on 7/15/2016.
 */
public class SadistSkill extends AbsSkill {

    private final double hitMultiplier = 0.05;
    private int numHits = 0;

    public SadistSkill(BattleCharacter owner){
        super(owner);
        this.skillName = "Sadist";
        this.description = "Desc";
    }

    @Override
    public double physicalAttackPercentage(BattleCharacter enemy){
        return 1.0 + hitMultiplier * numHits;
    }

    @Override
    public double magicalAttackPercentage(BattleCharacter enemy) {
        return 1.0 + hitMultiplier * numHits;
    }

    @Override
    public double specialAttackPercentage(BattleCharacter enemy) {
        return 1.0 + hitMultiplier * numHits;
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
    @Override
    public double[] getMultipliers(BattleCharacter enemy) {

        double multipliers[] = new double[6];
        multipliers[PHYS_ATK] = 1.0 + hitMultiplier * numHits;
        multipliers[MAG_ATK] = 1.0 + hitMultiplier * numHits;
        multipliers[SPEC_ATK] = 1.0 + hitMultiplier * numHits;
        multipliers[PHYS_DEF] = 0.0;
        multipliers[MAG_DEF] = 0.0;
        multipliers[SPEC_DEF] = 0.0;

        return multipliers;
    }

}
