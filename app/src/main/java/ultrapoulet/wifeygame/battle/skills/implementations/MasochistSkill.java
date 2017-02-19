package ultrapoulet.wifeygame.battle.skills.implementations;

import ultrapoulet.wifeygame.battle.BattleCharacter;
import ultrapoulet.wifeygame.battle.skills.AbsSkill;

/**
 * Created by John on 7/18/2016.
 */
public class MasochistSkill extends AbsSkill {

    public double damageMultiplier = 4.00;

    public MasochistSkill(BattleCharacter owner){
        super(owner);
        this.skillName = "Masochist";
    }

    @Override
    public double physicalAttackPercentage(BattleCharacter enemy) {
        return getMult();
    }

    @Override
    public double magicalAttackPercentage(BattleCharacter enemy) {
        return getMult();
    }

    @Override
    public double specialAttackPercentage(BattleCharacter enemy) {
        return getMult();
    }

    @Override
    public double receiveHealPercentage(BattleCharacter partyMember) {
        //Lose 50% of heal
        return 0.5;
    }

    private double getMult(){
        return 1.0 + (damageMultiplier * (owner.getMaxHP() - owner.getCurrentHP()))/(owner.getMaxHP());
    }


    @Override
    public Multipliers getMultipliers(BattleCharacter enemy) {
        Multipliers returnValue = new Multipliers();
        returnValue.setPhysAtk(getMult());
        returnValue.setMagAtk(getMult());
        returnValue.setSpecAtk(getMult());
        return returnValue;
    }

    @Override
    public String getDescription(BattleCharacter enemy) {
        StringBuilder desc = new StringBuilder();
        desc.append("Attack Multiplier: " + String.format("%1$.2f", getMult()) + "x\n\n");
        desc.append("Multiplies damage dealt up to 4.00x the lower this wifey's health is. Healing received multiplied by 0.50x.");
        return desc.toString();
    }
}
