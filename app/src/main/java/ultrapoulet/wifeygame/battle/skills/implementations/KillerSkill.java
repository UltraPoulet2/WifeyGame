package ultrapoulet.wifeygame.battle.skills.implementations;

import ultrapoulet.wifeygame.battle.BattleCharacter;
import ultrapoulet.wifeygame.battle.skills.AbsSkill;

/**
 * Created by John on 7/30/2016.
 */
public class KillerSkill extends AbsSkill {

    public KillerSkill(BattleCharacter owner){
        super(owner);
        this.skillName = "Killer";
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
        desc.append("Defeating an enemy with this wifey increases damage dealt multiplier by 1.00x for the rest of the battle.");
        return desc.toString();
    }
}
