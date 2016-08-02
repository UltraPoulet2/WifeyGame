package ultrapoulet.wifeygame.battle.skills;

import ultrapoulet.wifeygame.battle.BattleCharacter;

/**
 * Created by John on 8/1/2016.
 */
public class MagicalGirlSkill extends AbsSkill {

    public MagicalGirlSkill(BattleCharacter owner){ super(owner); }

    private double baseMultiplier = 2.0;
    private double perGirl = 0.25;

    private double multiplier;

    @Override
    public void startBattle(BattleCharacter[] party) {
        multiplier = baseMultiplier;
        for(int i = 0; i < party.length; i++){
            if(party[i] != owner && party[i].hasSkill(MagicalGirlSkill.class)){
                multiplier += perGirl;
            }
        }
    }

    @Override
    public double magicalAttackPercentage(BattleCharacter enemy) {
        return multiplier;
    }

    @Override
    public double healPercentage(BattleCharacter partyMember) {
        return multiplier;
    }

    @Override
    public double specialAttackPercentage(BattleCharacter enemy) {
        return multiplier;
    }
}
