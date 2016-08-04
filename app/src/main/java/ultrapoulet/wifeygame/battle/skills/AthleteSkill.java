package ultrapoulet.wifeygame.battle.skills;

import ultrapoulet.wifeygame.battle.BattleCharacter;

/**
 * Created by John on 8/3/2016.
 */
public class AthleteSkill extends AbsSkill {

    public AthleteSkill(BattleCharacter owner){ super(owner); }

    private double multiplier = 1.0;
    private double perGirl = 0.5;
    private double manager = 1.0;

    @Override
    public void startBattle(BattleCharacter[] party) {
        boolean managerFound = false;
        for(int i = 0; i < party.length; i++){
            if(party[i] != owner && party[i].hasSkill(AthleteSkill.class)){
                multiplier += perGirl;
            }
            if(party[i] != owner && !managerFound && party[i].hasSkill(SportsManagerSkill.class)){
                multiplier += manager;
                managerFound = true;
            }
        }
    }

    @Override
    public double physicalAttackPercentage(BattleCharacter enemy) {
        return multiplier;
    }
}
