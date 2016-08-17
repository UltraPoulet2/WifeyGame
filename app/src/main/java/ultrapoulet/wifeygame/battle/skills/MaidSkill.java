package ultrapoulet.wifeygame.battle.skills;

import ultrapoulet.wifeygame.battle.BattleCharacter;

/**
 * Created by John on 7/31/2016.
 */
public class MaidSkill extends AbsSkill {

    public MaidSkill(BattleCharacter owner) {
        super(owner);
        this.skillName = "Maid";
        this.description = "Desc";
    }

    private boolean healLastTurn = false;
    private boolean healCurrentTurn = false;

    @Override
    public void startRound() {
        healLastTurn = healCurrentTurn;
        healCurrentTurn = false;
    }

    @Override
    public double healPercentage(BattleCharacter partyMember) {
        healCurrentTurn = true;
        return 1.25;
    }

    @Override
    public double physicalAttackPercentage(BattleCharacter enemy) {
        if(healLastTurn) {
            return 2.0;
        }
        else {
            return 1.0;
        }
    }

    @Override
    public double magicalAttackPercentage(BattleCharacter enemy) {
        if(healLastTurn) {
            return 2.0;
        }
        else {
            return 1.0;
        }
    }

    @Override
    public double specialAttackPercentage(BattleCharacter enemy) {
        if(healLastTurn) {
            return 2.0;
        }
        else {
            return 1.0;
        }
    }
}
