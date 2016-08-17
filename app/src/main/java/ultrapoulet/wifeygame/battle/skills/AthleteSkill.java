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
}
