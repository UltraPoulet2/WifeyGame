package ultrapoulet.wifeygame.battle.skills;

import ultrapoulet.wifeygame.battle.BattleCharacter;

/**
 * Created by John on 8/3/2016.
 */
public class SportsManagerSkill extends AbsSkill {

    public SportsManagerSkill(BattleCharacter owner){
        super(owner);
        this.skillName = "Sports Manager";
        this.description = "Desc";
    }

    private double multiplier = 1.0;
    private double perGirl = 0.05;

    @Override
    public void startBattle(BattleCharacter[] party) {
        for(int i = 0; i < party.length; i++){
            if(party[i] != owner && party[i].hasSkill(AthleteSkill.class)){
                multiplier += perGirl;
                party[i].giveSkillBonus(1.0, SportsManagerSkill.class, AthleteSkill.class);
            }
        }
    }

    @Override
    public double physicalAttackPercentage(BattleCharacter enemy) {
        return multiplier;
    }


    @Override
    public double[] getMultipliers(BattleCharacter enemy) {
        double multipliers[] = new double[6];
        multipliers[PHYS_ATK] = multiplier;
        multipliers[MAG_ATK] = 1.0;
        multipliers[SPEC_ATK] = 1.0;
        multipliers[PHYS_DEF] = 0.0;
        multipliers[MAG_DEF] = 0.0;
        multipliers[SPEC_DEF] = 0.0;

        return multipliers;
    }
}
