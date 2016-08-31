package ultrapoulet.wifeygame.battle.skills.implementations;

import ultrapoulet.wifeygame.battle.BattleCharacter;
import ultrapoulet.wifeygame.battle.skills.AbsSkill;
import ultrapoulet.wifeygame.battle.skills.implementations.GhostSkill;

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
    public double[] getMultipliers(BattleCharacter enemy) {
        double multiplier = enemy.hasSkill(GhostSkill.class) ? 2.0 : 1.0;

        double multipliers[] = new double[6];
        multipliers[PHYS_ATK] = 1.0;
        multipliers[MAG_ATK] = multiplier;
        multipliers[SPEC_ATK] = multiplier;
        multipliers[PHYS_DEF] = 0.0;
        multipliers[MAG_DEF] = 0.0;
        multipliers[SPEC_DEF] = 0.0;

        return multipliers;
    }

    @Override
    public String getDescription(BattleCharacter enemy) {
        StringBuilder desc = new StringBuilder();
        double multiplier = enemy.hasSkill(GhostSkill.class) ? 2.0 : 1.0;
        desc.append("Magical Attack Multiplier: " + multiplier + "x\n");
        desc.append("Special Attack Multiplier: " + multiplier + "x\n\n");
        desc.append("Multiplies magical and special damage dealt by 2.0x against Ghosts.");
        return desc.toString();
    }
}
