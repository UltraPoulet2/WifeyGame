package ultrapoulet.wifeygame.battle.skills;

import ultrapoulet.wifeygame.battle.BattleCharacter;

/**
 * Created by John on 8/3/2016.
 */
public class AthleteSkill extends AbsSkill {

    public AthleteSkill(BattleCharacter owner) {
        super(owner);
        this.skillName = "Athlete";
        this.description = "Desc";
    }

    private double multiplier = 1.5;
    private double perGirl = 0.5;

    private boolean managerFound = false;

    @Override
    public void startBattle(BattleCharacter[] party) {
        for(int i = 0; i < party.length; i++){
            if(party[i] != owner && party[i].hasSkill(AthleteSkill.class)){
                multiplier += perGirl;
            }
        }
    }

    @Override
    public void receiveBonus(double multiplier, Class givingSkill){
        if(givingSkill == SportsManagerSkill.class && !managerFound){
            this.multiplier += multiplier;
            managerFound = true;
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
