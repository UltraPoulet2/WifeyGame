package ultrapoulet.wifeygame.battle.skills;

import ultrapoulet.wifeygame.battle.BattleCharacter;

/**
 * Created by John on 7/26/2016.
 */
public class PravdaSkill extends AbsSkill {

    public PravdaSkill(BattleCharacter owner) { super(owner); }

    private double multiplier = 0;

    @Override
    public void startBattle(BattleCharacter[] party){
        for(int i = 0; i < party.length; i++){
            if(party[i] != owner && party[i].hasSkill(PravdaSkill.class)){
                multiplier += 1.0;
            }
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
}
