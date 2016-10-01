package ultrapoulet.wifeygame.battle.skills.implementations;

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
        if(enemy.getAttackElement() == Element.getElement("WATER")) {
            return -0.5;
        }
        else {
            return 0.0;
        }
    }

    @Override
    public double receiveSpecialAttackPercentage(BattleCharacter enemy) {
        if(enemy.getAttackElement() == Element.getElement("WATER")) {
            return -0.5;
        }
        else {
            return 0.0;
        }
    }

    @Override
    public double[] getMultipliers(BattleCharacter enemy){
        double defense;
        if(enemy.getAttackElement() == Element.getElement("WATER")) {
            defense = -0.5;
        }
        else {
            defense = 0.0;
        }
        double multipliers[] = new double[6];
        multipliers[PHYS_ATK] = 1.0;
        multipliers[MAG_ATK] = 1.0;
        multipliers[SPEC_ATK] = 1.0;
        multipliers[PHYS_DEF] = 0.2;
        multipliers[MAG_DEF] = defense;
        multipliers[SPEC_DEF] = defense;

        return multipliers;
    }

    @Override
    public String getDescription(BattleCharacter enemy) {
        double defense;
        if(enemy.getAttackElement() == Element.getElement("WATER")) {
            defense = 0.5;
        }
        else {
            defense = 1.0;
        }
        StringBuilder desc = new StringBuilder();
        desc.append("Physical Defense Multiplier: 0.8x\n");
        desc.append("Magical Defense Multiplier: " + defense + "x\n");
        desc.append("Special Defense Multiplier: " + defense  + "x\n\n");
        desc.append("Multiplies physical damage taken by 0.8x. Multiplies magical and special damage taken from WATER sources by 1.5x.");
        return desc.toString();
    }
}
