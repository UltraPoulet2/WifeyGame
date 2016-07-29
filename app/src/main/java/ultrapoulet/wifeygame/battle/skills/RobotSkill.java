package ultrapoulet.wifeygame.battle.skills;

import ultrapoulet.wifeygame.battle.BattleCharacter;

/**
 * Created by John on 7/22/2016.
 */
public class RobotSkill extends AbsSkill {

    public RobotSkill(BattleCharacter owner) { super(owner); }

    @Override
    public double receivePhysicalAttackPercentage(BattleCharacter enemy) {
        return 1.2;
    }

    @Override
    public double receiveMagicalAttackPercentage(BattleCharacter enemy) {
        return 0.8;
    }
}
