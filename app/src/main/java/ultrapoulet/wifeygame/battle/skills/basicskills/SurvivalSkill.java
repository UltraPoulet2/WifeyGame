package ultrapoulet.wifeygame.battle.skills.basicskills;

import ultrapoulet.wifeygame.battle.BattleCharacter;
import ultrapoulet.wifeygame.battle.skills.AbsSkill;

/**
 * Created by John on 8/3/2016.
 */
public class SurvivalSkill extends AbsSkill {

    public SurvivalSkill(BattleCharacter owner, boolean pres){
        super(owner);
        this.isPresident = pres;
        this.skillName = "Sabagebu";
        if(pres){
            perTurn = 0.02;
            this.skillName += " President";
        }
    }

    private boolean isPresident;
    private double defense = 0.0;
    private double perTurn = 0.01;

    @Override
    public double receivePhysicalAttackPercentage(BattleCharacter enemy) {
        return defense;
    }

    @Override
    public double receiveMagicalAttackPercentage(BattleCharacter enemy) {
        return defense;
    }

    @Override
    public double receiveSpecialAttackPercentage(BattleCharacter enemy) {
        return defense;
    }

    @Override
    public void endRound() {
        double damagePercent = 1 - defense;
        defense += damagePercent * perTurn;
    }


    @Override
    public Multipliers getMultipliers(BattleCharacter enemy) {
        Multipliers returnValue = new Multipliers();
        returnValue.setPhysDef(defense);
        returnValue.setMagDef(defense);
        returnValue.setSpecDef(defense);
        return returnValue;
    }

    @Override
    public String getDescription(BattleCharacter enemy) {
        String desc = "Defense Multiplier: " + String.format("%1$.2f", defense) + "x\n\n" +
                "Increases physical, magical, and special defense by " + String.format("%1$.2f", perTurn) + "x each turn.";
        return desc;
    }
}
