package ultrapoulet.wifeygame.battle.skills.uniqueskills;

import ultrapoulet.wifeygame.battle.BattleCharacter;
import ultrapoulet.wifeygame.battle.skills.AbsUniqueSkill;

/**
 * Created by John on 8/29/2016.
 */
public class RemSkill extends AbsUniqueSkill {

    private double multiplier = 2.0;

    public RemSkill(BattleCharacter owner){
        super(owner);
        this.skillName = "Rem";
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
    public double healPercentage(BattleCharacter partyMember) {
        return multiplier;
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
        String desc = "Attack Multiplier: " + String.format("%1$.2f", multiplier) + "x\n" +
                "Healing Multiplier: " + String.format("%1$.2f", multiplier) + "x\n\n" +
                "Rem gets a special skill because she is Rem. Multiplies damage dealt and healing by 2.00x.";
        return desc;
    }
}
