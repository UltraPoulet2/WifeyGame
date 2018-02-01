package ultrapoulet.wifeygame.battle.skills.basicskills;

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
        String desc = "Physical Defense Multiplier: 0.50x\n\n" +
                "Increases physical attack defense by 0.50x.";
        return desc;
    }
}
