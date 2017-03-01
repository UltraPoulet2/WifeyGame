package ultrapoulet.wifeygame.battle.skills.implementations;

import ultrapoulet.wifeygame.battle.BattleCharacter;
import ultrapoulet.wifeygame.battle.skills.AbsSkill;

/**
 * Created by John on 7/20/2016.
 */
public class GhostSkill extends AbsSkill {

    public GhostSkill(BattleCharacter owner) {
        super(owner);
        this.skillName = "Ghost";
    }

    @Override
    public double receivePhysicalAttackPercentage(BattleCharacter enemy) {
        return 0.5;
    }


    @Override
    public Multipliers getMultipliers(BattleCharacter enemy) {
        Multipliers returnValue = new Multipliers();
        returnValue.setPhysDef(0.5);
        return returnValue;
    }

    @Override
    public String getDescription(BattleCharacter enemy) {
        StringBuilder desc = new StringBuilder();
        desc.append("Physical Defense Multiplier: 0.50x\n\n");
        desc.append("Multiplies physical attack damage taken by 0.50x");
        return desc.toString();
    }
}
