package ultrapoulet.wifeygame.battle.skills.basicskills;

import ultrapoulet.wifeygame.battle.BattleCharacter;
import ultrapoulet.wifeygame.battle.skills.AbsSkill;

/**
 * Created by John on 8/10/2016.
 */
public class DetectiveSkill extends AbsSkill {

    private double startMultiplier = 1.0;
    private double perTurn = 0.5;
    private double maxMultiplier = 5.0;
    private double multiplier;

    public DetectiveSkill(BattleCharacter owner) {
        super(owner);
        this.skillName = "Detective";
    }

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
    public void endRound() {
        if(multiplier < maxMultiplier) {
            multiplier += perTurn;
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
        String desc = "Attack Multiplier: " + String.format("%1$.2f", multiplier) + "x\n\n" +
                "Multiplies damage dealt by 1.00x at the start of a wave. Multiplier increases by 0.50x at the end of each turn, up to a maximum of 5.00x.";
        return desc;
    }
}
