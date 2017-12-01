package ultrapoulet.wifeygame.battle.skills.basicskills;

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
        String desc = "Special Attack Multiplier: 5.00x\n\n" +
                "Multiplies special attack damage dealt by 5.00x.";
        return desc;
    }
}
