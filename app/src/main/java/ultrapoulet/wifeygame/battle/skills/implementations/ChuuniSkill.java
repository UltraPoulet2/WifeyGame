package ultrapoulet.wifeygame.battle.skills.implementations;

import ultrapoulet.wifeygame.battle.BattleCharacter;
import ultrapoulet.wifeygame.battle.skills.AbsSkill;

/**
 * Created by John on 7/31/2016.
 */
public class ChuuniSkill extends AbsSkill {

    public ChuuniSkill(BattleCharacter owner){
        super(owner);
        this.skillName = "Chuunibyou";
    }

    @Override
    public double specialAttackPercentage(BattleCharacter enemy) {
        return 5.0;
    }

    @Override
    public Multipliers getMultipliers(BattleCharacter enemy) {
        Multipliers returnValue = new Multipliers();
        returnValue.setSpecAtk(5.0);
        return returnValue;
    }

    @Override
    public String getDescription(BattleCharacter enemy){
        StringBuilder desc = new StringBuilder();
        desc.append("Special Attack Multiplier: 5.0x\n\n");
        desc.append("Multiplies special attack damage dealt by 5.0x.");
        return desc.toString();
    }
}
