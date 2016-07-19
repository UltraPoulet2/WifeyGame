package ultrapoulet.wifeygame.battle.skills;

import ultrapoulet.wifeygame.battle.BattleCharacter;

/**
 * Created by John on 7/18/2016.
 */
public class MasochistSkill extends AbsSkill {

    public double maxMultiplier = 4.00;

    public MasochistSkill(BattleCharacter owner){ super(owner); }

    @Override
    public double physicalAttackPercentage(BattleCharacter enemy) {
        return (maxMultiplier * (owner.getMaxHP() - owner.getCurrentHP()))/(owner.getMaxHP());
    }

    @Override
    public double magicalAttackPercentage(BattleCharacter enemy) {
        return (maxMultiplier * (owner.getMaxHP() - owner.getCurrentHP()))/(owner.getMaxHP());
    }

    @Override
    public double specialAttackPercentage(BattleCharacter enemy) {
        return (maxMultiplier * (owner.getMaxHP() - owner.getCurrentHP()))/(owner.getMaxHP());
    }

    @Override
    public double receiveHealPercentage(BattleCharacter partyMember) {
        //Lose 50% of heal
        return -0.5;
    }
}
