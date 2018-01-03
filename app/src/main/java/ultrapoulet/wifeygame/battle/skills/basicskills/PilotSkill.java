package ultrapoulet.wifeygame.battle.skills.basicskills;

import ultrapoulet.wifeygame.battle.BattleCharacter;
import ultrapoulet.wifeygame.battle.skills.AbsSkill;
import ultrapoulet.wifeygame.character.Element;

/**
 * Created by John on 9/23/2016.
 */

public class PilotSkill extends AbsSkill {

    private double multiplier = 0.5;

    public PilotSkill(BattleCharacter owner){
        super(owner);
        this.skillName = "Pilot";
    }

    @Override
    public double receivePhysicalAttackPercentage(BattleCharacter enemy) {
        if(enemy.getAttackElement() == Element.AIR) {
            return multiplier;
        }
        else {
            return 0.0;
        }
    }

    @Override
    public double receiveMagicalAttackPercentage(BattleCharacter enemy) {
        if(enemy.getAttackElement() == Element.AIR) {
            return multiplier;
        }
        else {
            return 0.0;
        }
    }

    @Override
    public double receiveSpecialAttackPercentage(BattleCharacter enemy) {
        if(enemy.getAttackElement() == Element.AIR) {
            return multiplier;
        }
        else {
            return 0.0;
        }
    }

    @Override
    public Multipliers getMultipliers(BattleCharacter enemy) {
        double defense = enemy.getAttackElement() == Element.AIR ? multiplier : 0.0;
        Multipliers returnValue = new Multipliers();
        returnValue.setPhysDef(defense);
        returnValue.setMagDef(defense);
        returnValue.setSpecDef(defense);
        return returnValue;
    }

    @Override
    public String getDescription(BattleCharacter enemy) {
        double defense;
        if(enemy.getAttackElement() == Element.AIR) {
            defense = multiplier;
        }
        else {
            defense = 1.0;
        }
        String desc = "Magical Defense Multiplier: " + String.format("%1$.2f", defense) + "x\n" +
                "Special Defense Multiplier: " + String.format("%1$.2f", defense) + "x\n\n" +
                "Increases magical and special defense against AIR sources by 0.50x.";
        return desc;
    }


}
