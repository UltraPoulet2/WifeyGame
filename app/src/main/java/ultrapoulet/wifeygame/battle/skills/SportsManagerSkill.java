package ultrapoulet.wifeygame.battle.skills;

import ultrapoulet.wifeygame.battle.BattleCharacter;

/**
 * Created by John on 8/3/2016.
 */
public class SportsManagerSkill extends AbsSkill {

    public SportsManagerSkill(BattleCharacter owner){ super(owner); }

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
}
