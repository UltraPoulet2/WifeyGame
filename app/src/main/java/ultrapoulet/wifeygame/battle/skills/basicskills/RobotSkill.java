package ultrapoulet.wifeygame.battle.skills.basicskills;

import ultrapoulet.wifeygame.battle.BattleCharacter;
import ultrapoulet.wifeygame.battle.skills.AbsSkill;
import ultrapoulet.wifeygame.character.Element;

/**
 * Created by John on 7/22/2016.
 */
public class RobotSkill extends AbsSkill {

    public RobotSkill(BattleCharacter owner) {
        super(owner);
        this.skillName = "Robot";
    }

    @Override
    public double receivePhysicalAttackPercentage(BattleCharacter enemy) {
        return 0.2;
    }

    @Override
    public double receiveMagicalAttackPercentage(BattleCharacter enemy) {
        if(enemy.getAttackElement() == Element.WATER) {
            return -0.5;
        }
        else {
            return 0.0;
        }
    }

    @Override
    public double receiveSpecialAttackPercentage(BattleCharacter enemy) {
        if(enemy.getAttackElement() == Element.WATER) {
            return -0.5;
        }
        else {
            return 0.0;
        }
    }

    @Override
    public Multipliers getMultipliers(BattleCharacter enemy) {
        double defense = enemy.getAttackElement() == Element.WATER ? -0.5 : 0.0;
        Multipliers returnValue = new Multipliers();
        returnValue.setPhysDef(0.2);
        returnValue.setMagDef(defense);
        returnValue.setSpecDef(defense);
        return returnValue;
    }

    @Override
    public String getDescription(BattleCharacter enemy) {
        double defense;
        if(enemy.getAttackElement() == Element.WATER) {
            defense = 0.5;
        }
        else {
            defense = 1.0;
        }
        StringBuilder desc = new StringBuilder();
        desc.append("Physical Defense Multiplier: 0.80x\n");
        desc.append("Magical Defense Multiplier: " + String.format("%1$.2f", defense) + "x\n");
        desc.append("Special Defense Multiplier: " + String.format("%1$.2f", defense)  + "x\n\n");
        desc.append("Multiplies physical damage taken by 0.80x. Multiplies magical and special damage taken from WATER sources by 1.50x.");
        return desc.toString();
    }
}
