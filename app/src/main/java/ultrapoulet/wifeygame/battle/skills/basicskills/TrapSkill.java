package ultrapoulet.wifeygame.battle.skills.basicskills;

import ultrapoulet.wifeygame.battle.BattleCharacter;
import ultrapoulet.wifeygame.battle.skills.AbsSkill;

/**
 * Created by John on 8/13/2016.
 */
public class TrapSkill extends AbsSkill {

    public TrapSkill(BattleCharacter owner){
        super(owner);
        this.skillName = "Trap";
    }

    @Override
    public double receivePhysicalAttackPercentage(BattleCharacter enemy) {
        return 0.15;
    }

    @Override
    public double receiveMagicalAttackPercentage(BattleCharacter enemy) {
        return 0.15;
    }

    @Override
    public double receiveSpecialAttackPercentage(BattleCharacter enemy) {
        return 0.15;
    }

    @Override
    public Multipliers getMultipliers(BattleCharacter enemy) {
        Multipliers returnValue = new Multipliers();
        returnValue.setPhysDef(0.15);
        returnValue.setMagDef(0.15);
        returnValue.setSpecDef(0.15);
        return returnValue;
    }

    @Override
    public String getDescription(BattleCharacter enemy) {
        StringBuilder desc = new StringBuilder();
        desc.append("Defense Multiplier: 0.15x\n\n");
        desc.append("Multiplies physical, magical, and special damage taken by 0.15x.");
        return desc.toString();
    }
}
