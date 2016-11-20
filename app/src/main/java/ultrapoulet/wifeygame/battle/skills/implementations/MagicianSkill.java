package ultrapoulet.wifeygame.battle.skills.implementations;

import ultrapoulet.wifeygame.battle.BattleCharacter;
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
    }

    @Override
    public double magicalAttackPercentage(BattleCharacter enemy) {
        magicUsed = true;
        return multiplier;
    }

    @Override
    public void endRound() {
        if(magicUsed && multiplier > minimumMultiplier){
            multiplier -= decrease;
        }
    }

    @Override
    public Multipliers getMultipliers(BattleCharacter enemy) {
        Multipliers returnValue = new Multipliers();
        returnValue.setMagAtk(multiplier);
        return returnValue;
    }

    @Override
    public String getDescription(BattleCharacter enemy) {
        StringBuilder desc = new StringBuilder();
        desc.append("Magical Attack Multiplier: " + multiplier + "x\n\n");
        desc.append("Multiplies magical damage dealt by 3.0x at the start of a wave. Multiplier decreases by 0.5x each time a magical attack is used, to a minimum of 1.0x.");
        return desc.toString();
    }
}
