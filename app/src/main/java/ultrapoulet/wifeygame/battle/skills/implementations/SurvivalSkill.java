package ultrapoulet.wifeygame.battle.skills.implementations;

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
    public double[] getMultipliers(BattleCharacter enemy) {
        double multipliers[] = new double[6];
        multipliers[PHYS_ATK] = 1.0;
        multipliers[MAG_ATK] = 1.0;
        multipliers[SPEC_ATK] = 1.0;
        multipliers[PHYS_DEF] = defense;
        multipliers[MAG_DEF] = defense;
        multipliers[SPEC_DEF] = defense;

        return multipliers;
    }

    @Override
    public String getDescription(BattleCharacter enemy) {
        StringBuilder desc = new StringBuilder();
        desc.append("Defense Multiplier: " +  String.format("%1$.2f", defense) + "x\n\n");
        desc.append("Decreases physical, magical, and special damage taken multiplier by " + perTurn + "x each turn.");
        return desc.toString();
    }
}
