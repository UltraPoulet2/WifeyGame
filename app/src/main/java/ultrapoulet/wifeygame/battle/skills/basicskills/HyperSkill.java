package ultrapoulet.wifeygame.battle.skills.basicskills;

import ultrapoulet.wifeygame.battle.BattleCharacter;
import ultrapoulet.wifeygame.battle.skills.AbsSkill;

/**
 * Created by John on 7/31/2016.
 */
public class HyperSkill extends AbsSkill {

    public HyperSkill(BattleCharacter owner) {
        super(owner);
        this.skillName = "Hyper";
    }

    @Override
    public int getBonusHits() {
        return 1;
    }

    @Override
    public String getDescription(BattleCharacter enemy) {
        String desc = "Bonus Combo Hits: 1\n\n" +
                "Increases combo hits by 1.";
        return desc;
    }
}
