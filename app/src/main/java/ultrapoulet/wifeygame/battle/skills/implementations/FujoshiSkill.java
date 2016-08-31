package ultrapoulet.wifeygame.battle.skills.implementations;

import ultrapoulet.wifeygame.battle.BattleCharacter;
import ultrapoulet.wifeygame.battle.skills.AbsSkill;

/**
 * Created by John on 8/13/2016.
 */
public class FujoshiSkill extends AbsSkill {

    public FujoshiSkill(BattleCharacter owner){
        super(owner);
        this.skillName = "Fujoshi";
    }

    private double multiplier = 1.0;
    private double perTrap = 1.0/3.0;
    private double twoTrapBonus = 1.0;

    @Override
    public void startBattle(BattleCharacter[] party) {
        int count = 0;
        for(int i = 0; i < party.length; i++){
            if(party[i] != owner && party[i].hasSkill(TrapSkill.class)){
                count++;
            }
        }
        multiplier += count * perTrap;
        if(count >= 2){
            multiplier += twoTrapBonus;
        }
    }

    @Override
    public double physicalAttackPercentage(BattleCharacter enemy) {
        return multiplier;
    }

    @Override
    public double magicalAttackPercentage(BattleCharacter enemy) {
        return multiplier;
    }

    @Override
    public double specialAttackPercentage(BattleCharacter enemy) {
        return multiplier;
    }


    @Override
    public double[] getMultipliers(BattleCharacter enemy) {
        double multipliers[] = new double[6];
        multipliers[PHYS_ATK] = multiplier;
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
        desc.append("Attack Multiplier: " + String.format("%1$.2f", multiplier) + "\n\n");
        desc.append("Increases damage dealt multiplier by 0.33x for each Trap wifey in the party. Damage increased by a bonus 1.0x if there are more than two Trap wifeys.");
        return desc.toString();
    }
}
