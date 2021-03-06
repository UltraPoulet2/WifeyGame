package ultrapoulet.wifeygame.battle.skills.basicskills;

import ultrapoulet.wifeygame.battle.BattleCharacter;
import ultrapoulet.wifeygame.battle.BattleCharacter.EnemyAction;
import ultrapoulet.wifeygame.battle.BattleCharacter.PlayerAction;
import ultrapoulet.wifeygame.battle.skills.AbsSkill;

/**
 * Created by John on 7/31/2016.
 */
public class MaidSkill extends AbsSkill {

    public MaidSkill(BattleCharacter owner) {
        super(owner);
        this.skillName = "Maid";
    }

    private boolean healLastTurn = false;
    private boolean healCurrentTurn = false;

    @Override
    public int startRound() {
        healLastTurn = healCurrentTurn;
        healCurrentTurn = false;
        return 0;
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

    @Override
    public void onActionSelect(PlayerAction action) {
        healCurrentTurn = action == PlayerAction.HEALING_MAGIC;
    }

    @Override
    public void onActionSelect(EnemyAction action) {
        healCurrentTurn = action == EnemyAction.HEALING_MAGIC;
    }


    @Override
    public Multipliers getMultipliers(BattleCharacter enemy) {
        double multiplier = healLastTurn ? 2.0 : 1.0;
        Multipliers returnValue = new Multipliers();
        returnValue.setPhysAtk(multiplier);
        returnValue.setMagAtk(multiplier);
        returnValue.setSpecAtk(multiplier);
        return returnValue;
    }

    @Override
    public String getDescription(BattleCharacter enemy) {
        String desc = "Attack Multiplier: " +
                (healLastTurn ? "2.00x\n\n" : "1.00x\n\n") +
                "Multiplies damage dealt by 2.00x if this wifey healed the previous turn.";
        return desc;
    }
}
