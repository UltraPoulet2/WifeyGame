package ultrapoulet.wifeygame.battle.skills;

import ultrapoulet.wifeygame.battle.BattleCharacter;

/**
 * Created by John on 7/19/2016.
 */
public class TsundereSkill extends AbsSkill {

    private double bonusDamage;
    private double maxMultiplier = 4.0;

    public TsundereSkill(BattleCharacter owner){
        super(owner);
        this.skillName = "Tsundere";
        this.description = "Desc";
    }

    @Override
    public void startBattle(BattleCharacter[] party) {
        bonusDamage = maxMultiplier - (party.length - 1);
        if(bonusDamage < 1.0){
            bonusDamage = 1.0;
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
