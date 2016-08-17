package ultrapoulet.wifeygame.battle.skills;

import ultrapoulet.wifeygame.battle.BattleCharacter;

/**
 * Created by John on 7/31/2016.
 */
public class ChuuniSkill extends AbsSkill {

    public ChuuniSkill(BattleCharacter owner){
        super(owner);
        this.skillName = "Chuunibyou";
        this.description = "Desc";
    }

    @Override
    public double specialAttackPercentage(BattleCharacter enemy) {
        return 5.0;
    }
}
