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
    public double[] getMultipliers(BattleCharacter enemy) {

        double multipliers[] = new double[6];
        multipliers[PHYS_ATK] = getMult();
        multipliers[MAG_ATK] = getMult();
        multipliers[SPEC_ATK] = getMult();
        multipliers[PHYS_DEF] = 0.0;
        multipliers[MAG_DEF] = 0.0;
        multipliers[SPEC_DEF] = 0.0;

        return multipliers;
    }

    @Override
    public String getDescription(BattleCharacter enemy) {
        StringBuilder desc = new StringBuilder();
        desc.append("Attack Multiplier: " + getMult() + "x\n\n");
        desc.append("Increases damage dealt multiplier by 0.05x for each hit. The number of hits resets to 0 if an enemy is defeated by this wifey.");
        return desc.toString();
    }
}
