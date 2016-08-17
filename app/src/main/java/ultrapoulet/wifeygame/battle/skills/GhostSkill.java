package ultrapoulet.wifeygame.battle.skills;

import ultrapoulet.wifeygame.battle.BattleCharacter;

/**
 * Created by John on 7/20/2016.
 */
public class GhostSkill extends AbsSkill {

    public GhostSkill(BattleCharacter owner) {
        super(owner);
        this.skillName = "Ghost";
        this.description = "Desc";
    }

    @Override
    public double receivePhysicalAttackPercentage(BattleCharacter enemy) {
        return 0.5;
    }
}
