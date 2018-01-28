package ultrapoulet.wifeygame.battle.skills.basicskills;

import ultrapoulet.wifeygame.battle.BattleCharacter;
import ultrapoulet.wifeygame.battle.BattleCharacter.EnemyAction;
import ultrapoulet.wifeygame.battle.BattleCharacter.PlayerAction;
import ultrapoulet.wifeygame.battle.skills.AbsSkill;

/**
 * Created by John on 8/1/2016.
 */
public class WitchSkill extends AbsSkill {

    private double increase = 0.2;
    private int numUses = 0;
    private boolean magicCurrentTurn = false;

    public WitchSkill(BattleCharacter owner){
        super(owner);
        this.skillName = "Witch";
    }

    @Override
    public double magicalAttackPercentage(BattleCharacter enemy) {
        magicCurrentTurn = true;
        return getMult();
    }

    @Override
    public int startRound() {
        if(magicCurrentTurn){
            numUses++;
        }
        magicCurrentTurn = false;
        return 0;
    }

    @Override
    public void onActionSelect(PlayerAction action) {
        magicCurrentTurn = action == PlayerAction.MAGIC_ATTACK;
    }

    @Override
    public void onActionSelect(EnemyAction action) {
        magicCurrentTurn = action == EnemyAction.MAGIC_ATTACK;
    }

    @Override
    public Multipliers getMultipliers(BattleCharacter enemy) {
        Multipliers returnValue = new Multipliers();
        returnValue.setMagAtk(getMult());
        return returnValue;
    }

    private double getMult(){
        return 1.0 + increase * numUses;
    }

    @Override
    public String getDescription(BattleCharacter enemy) {
        String desc = "Magical Attack Multiplier: " + String.format("%1$.2f", getMult()) + "x\n\n" +
                "Increases magical damage dealt multiplier by 0.20x each time a magical attack is used.";
        return desc;
    }
}
