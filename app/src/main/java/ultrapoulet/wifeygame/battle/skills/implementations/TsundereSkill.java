package ultrapoulet.wifeygame.battle.skills.implementations;

import java.util.List;

import ultrapoulet.wifeygame.battle.BattleCharacter;
import ultrapoulet.wifeygame.battle.skills.AbsSkill;

/**
 * Created by John on 7/19/2016.
 */
public class TsundereSkill extends AbsSkill {

    private double multiplier;
    private double maxMultiplier = 4.0;

    public TsundereSkill(BattleCharacter owner){
        super(owner);
        this.skillName = "Tsundere";
    }

    @Override
    public void startBattle(List<BattleCharacter> party) {
        multiplier = maxMultiplier - (party.size() - 1);
        if(multiplier < 1.0){
            multiplier = 1.0;
        }
    }

    @Override
    public double physicalAttackPercentage(BattleCharacter enemy) {
        return multiplier;
    }

    @Override
    public double magicalAttackPercentage(BattleCharacter enemy){
        return multiplier;
    }

    @Override
    public double specialAttackPercentage(BattleCharacter enemy) {
        return multiplier;
    }


    @Override
    public Multipliers getMultipliers(BattleCharacter enemy) {
        Multipliers returnValue = new Multipliers();
        returnValue.setPhysAtk(multiplier);
        returnValue.setMagAtk(multiplier);
        returnValue.setSpecAtk(multiplier);
        return returnValue;
    }

    @Override
    public String getDescription(BattleCharacter enemy) {
        StringBuilder desc = new StringBuilder();
        desc.append("Damage Multiplier: " + String.format("%1$.2f", multiplier) + "x\n\n");
        desc.append("Multiplies damage dealt by 2.00x if there are only 3 party members, 3.00x if there are only 2, 4.00x if this is the only wifey.");
        return desc.toString();
    }
}
