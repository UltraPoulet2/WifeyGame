package ultrapoulet.wifeygame.battle.skills.basicskills;

import ultrapoulet.wifeygame.battle.BattleCharacter;
import ultrapoulet.wifeygame.battle.skills.AbsSkill;

/**
 * Created by John on 7/21/2016.
 */
public class MediumSkill extends AbsSkill {

    public MediumSkill(BattleCharacter owner) {
        super(owner);
        this.skillName = "Medium";
    }

    @Override
    public double magicalAttackPercentage(BattleCharacter enemy){
        if(enemy.hasSkill(GhostSkill.class)){
            return 2.0;
        }
        else{
            return 1.0;
        }
    }

    @Override
    public double specialAttackPercentage(BattleCharacter enemy) {
        if(enemy.hasSkill(GhostSkill.class)){
            return 2.0;
        }
        else{
            return 1.0;
        }
    }

    @Override
    public Multipliers getMultipliers(BattleCharacter enemy) {
        double multiplier = enemy.hasSkill(GhostSkill.class) ? 2.0 : 1.0;
        Multipliers returnValue = new Multipliers();
        returnValue.setMagAtk(multiplier);
        returnValue.setSpecAtk(multiplier);
        return returnValue;
    }

    @Override
    public String getDescription(BattleCharacter enemy) {

        double multiplier = enemy.hasSkill(GhostSkill.class) ? 2.0 : 1.0;
        String desc = "Magical Attack Multiplier: " + String.format("%1$.2f",multiplier) + "x\n" +
                "Special Attack Multiplier: " + String.format("%1$.2f",multiplier) + "x\n\n" +
                "Multiplies magical and special damage dealt to Ghosts by 2.00x.";
        return desc.toString();
    }
}
