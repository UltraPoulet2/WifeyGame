package ultrapoulet.wifeygame.battle.skills.implementations;

import ultrapoulet.wifeygame.battle.BattleCharacter;
import ultrapoulet.wifeygame.battle.skills.AbsSkill;

/**
 * Created by John on 7/15/2016.
 */
public class SadistSkill extends AbsSkill {

    private final double hitMultiplier = 0.05;
    private int numHits = 0;

    public SadistSkill(BattleCharacter owner){
        super(owner);
        this.skillName = "Sadist";
    }

    @Override
    public double physicalAttackPercentage(BattleCharacter enemy){
        return getMult();
    }

    @Override
    public double magicalAttackPercentage(BattleCharacter enemy) {
        return getMult();
    }

    @Override
    public double specialAttackPercentage(BattleCharacter enemy) {
        return getMult();
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

    private double getMult(){
        return 1.0 + hitMultiplier * numHits;
    }

    @Override
    public Multipliers getMultipliers(BattleCharacter enemy) {
        Multipliers returnValue = new Multipliers();
        returnValue.setPhysAtk(getMult());
        returnValue.setMagAtk(getMult());
        returnValue.setSpecAtk(getMult());
        return returnValue;
    }

    @Override
    public String getDescription(BattleCharacter enemy) {
        StringBuilder desc = new StringBuilder();
        desc.append("Attack Multiplier: " + String.format("%1$.2f", getMult()) + "x\n\n");
        desc.append("Increases damage dealt multiplier by 0.05x for each hit. The number of hits resets to 0 if an enemy is defeated by this wifey.");
        return desc.toString();
    }
}
