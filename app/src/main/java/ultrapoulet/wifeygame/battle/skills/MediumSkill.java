package ultrapoulet.wifeygame.battle.skills;

import ultrapoulet.wifeygame.battle.BattleCharacter;

/**
 * Created by John on 7/21/2016.
 */
public class MediumSkill extends AbsSkill {

    public MediumSkill(BattleCharacter owner) {
        super(owner);
        this.skillName = "Medium";
        this.description = "Desc";
    }

    @Override
    public double magicalAttackPercentage(BattleCharacter enemy){
        if(enemy.hasSkill(GhostSkill.class)){
            return 2.0;
        }
        else{
            return 1.0;
        }
    }

    @Override
    public double specialAttackPercentage(BattleCharacter enemy) {
        if(enemy.hasSkill(GhostSkill.class)){
            return 2.0;
        }
        else{
            return 1.0;
        }
    }
}
