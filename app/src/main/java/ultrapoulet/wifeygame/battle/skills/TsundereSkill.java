package ultrapoulet.wifeygame.battle.skills;

import ultrapoulet.wifeygame.battle.BattleCharacter;

/**
 * Created by John on 7/19/2016.
 */
public class TsundereSkill extends AbsSkill {

    private double bonusDamage;
    private double maxMultiplier = 5.0;

    public TsundereSkill(BattleCharacter owner){ super(owner); }

    @Override
    public void startBattle(BattleCharacter[] party) {
        bonusDamage = maxMultiplier - ((party.length - 1) * 1.25);
        if(bonusDamage < 0.0){
            bonusDamage = 0.0;
        }
    }

    @Override
    public double physicalAttackPercentage(BattleCharacter enemy) {
        return bonusDamage;
    }

    @Override
    public double magicalAttackPercentage(BattleCharacter enemy){
        return bonusDamage;
    }

    @Override
    public double specialAttackPercentage(BattleCharacter enemy) {
        return bonusDamage;
    }
}
