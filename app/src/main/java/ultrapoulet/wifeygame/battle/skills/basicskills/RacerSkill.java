package ultrapoulet.wifeygame.battle.skills.basicskills;

import ultrapoulet.wifeygame.battle.BattleCharacter;
import ultrapoulet.wifeygame.battle.skills.AbsSkill;

/**
 * Created by John on 7/29/2016.
 */
public class RacerSkill extends AbsSkill {

    public RacerSkill(BattleCharacter owner){
        super(owner);
        this.skillName = "Racer";
    }

    private double startMultiplier = 4.0;
    private double perTurn = 0.5;
    private double minMultiplier = 0.5;
    private double multiplier;

    @Override
    public void startWave() {
        multiplier = startMultiplier;
    }

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
        multiplier -= perTurn;
        if(multiplier < minMultiplier){
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
        desc.append("Multiplies damage dealt by 4.00x at the start of a wave. Multiplier decreases by 0.50x for each turn the wave lasts, to a minimum of 0.50x.");
        return desc.toString();
    }
}
