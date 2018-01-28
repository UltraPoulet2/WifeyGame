package ultrapoulet.wifeygame.battle.skills.basicskills;

import ultrapoulet.wifeygame.battle.BattleCharacter;
import ultrapoulet.wifeygame.battle.BattleCharacter.EnemyAction;
import ultrapoulet.wifeygame.battle.BattleCharacter.PlayerAction;
import ultrapoulet.wifeygame.battle.skills.AbsSkill;

/**
 * Created by John on 9/23/2016.
 */

public class MagicianSkill extends AbsSkill {

    private double defaultMultiplier = 3.0;
    private double decrease = 0.5;
    private double minimumMultiplier = 1.0;

    private double multiplier;

    private boolean magicUsed = false;

    public MagicianSkill(BattleCharacter owner) {
        super(owner);
        this.skillName = "Magician";
    }

    @Override
    public void startWave() {
        multiplier = defaultMultiplier;
        magicUsed = false;
    }

    @Override
    public int startRound() {
        if(magicUsed && multiplier > minimumMultiplier){
            multiplier -= decrease;
        }
        magicUsed = false;
        return 0;
    }

    @Override
    public void onActionSelect(PlayerAction action) {
        magicUsed = action == PlayerAction.MAGIC_ATTACK;
    }

    @Override
    public void onActionSelect(EnemyAction action) {
        magicUsed = action == EnemyAction.MAGIC_ATTACK;
    }

    @Override
    public double magicalAttackPercentage(BattleCharacter enemy) {
        return multiplier;
    }

    @Override
    public Multipliers getMultipliers(BattleCharacter enemy) {
        Multipliers returnValue = new Multipliers();
        returnValue.setMagAtk(multiplier);
        return returnValue;
    }

    @Override
    public String getDescription(BattleCharacter enemy) {
        String desc = "Magical Attack Multiplier: " + String.format("%1$.2f", multiplier) + "x\n\n" +
                "Multiplies magical damage dealt by 3.00x at the start of a wave. Multiplier decreases by 0.50x each time a magical attack is used, to a minimum of 1.00x.";
        return desc;
    }
}
