package ultrapoulet.wifeygame.battle.skills.implementations;

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
        if(enemy.getAttackElement() == Element.getElement("AIR")) {
            return multiplier;
        }
        else {
            return 0.0;
        }
    }

    @Override
    public double receiveMagicalAttackPercentage(BattleCharacter enemy) {
        if(enemy.getAttackElement() == Element.getElement("AIR")) {
            return multiplier;
        }
        else {
            return 0.0;
        }
    }

    @Override
    public double receiveSpecialAttackPercentage(BattleCharacter enemy) {
        if(enemy.getAttackElement() == Element.getElement("AIR")) {
            return multiplier;
        }
        else {
            return 0.0;
        }
    }

    @Override
    public double[] getMultipliers(BattleCharacter enemy){
        double defense;
        if(enemy.getAttackElement() == Element.getElement("AIR")) {
            defense = multiplier;
        }
        else {
            defense = 0.0;
        }
        double multipliers[] = new double[6];
        multipliers[PHYS_ATK] = 1.0;
        multipliers[MAG_ATK] = 1.0;
        multipliers[SPEC_ATK] = 1.0;
        multipliers[PHYS_DEF] = defense;
        multipliers[MAG_DEF] = defense;
        multipliers[SPEC_DEF] = defense;

        return multipliers;
    }

    @Override
    public String getDescription(BattleCharacter enemy) {
        double defense;
        if(enemy.getAttackElement() == Element.getElement("AIR")) {
            defense = multiplier;
        }
        else {
            defense = 1.0;
        }
        StringBuilder desc = new StringBuilder();
        desc.append("Magical Defense Multiplier: " + defense + "x\n");
        desc.append("Special Defense Multiplier: " + defense  + "x\n\n");
        desc.append("Multiplies magical and special damage taken from AIR sources by 0.5x");
        return desc.toString();
    }


}
