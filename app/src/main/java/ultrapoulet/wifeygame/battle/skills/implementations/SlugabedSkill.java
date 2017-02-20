package ultrapoulet.wifeygame.battle.skills.implementations;

import ultrapoulet.wifeygame.battle.BattleCharacter;
import ultrapoulet.wifeygame.battle.skills.AbsSkill;

/**
 * Created by John on 7/29/2016.
 */
public class SlugabedSkill extends AbsSkill {

    public SlugabedSkill(BattleCharacter owner) {
        super(owner);
        this.skillName = "Slugabed";
    }

    private double multiplier = 6.0;
    private double minMultiplier = 0.25;
    private double perTurn = 0.25;

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
        decrementMult();
    }

    @Override
    public void endWave(){
        decrementMult();
    }

    private void decrementMult() {
        multiplier -= perTurn;
        if(multiplier < minMultiplier) {
            multiplier = minMultiplier;
        }
    }

    @Override
    public Multipliers getMultipliers(BattleCharacter enemy) {
        Multipliers returnValue = new Multipliers();
        returnValue.setPhysAtk(multiplier);
        returnValue.setMagAtk(multiplier);
        returnValue.setSpecAtk(multiplier);
        return returnValue;
    }

    @Override
    public String getDescription(BattleCharacter enemy) {
        StringBuilder desc = new StringBuilder();
        desc.append("Attack Multiplier: " + String.format("%1$.2f", multiplier) + "x\n\n");
        desc.append("Multiplies damage dealt by 6.00x. Reduces multiplier by 0.25x for each turn, to a minimum of 0.25x.");
        return desc.toString();
    }
}
