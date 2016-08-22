package ultrapoulet.wifeygame.battle.skills;

import ultrapoulet.wifeygame.battle.BattleCharacter;

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
        StringBuilder desc = new StringBuilder();
        desc.append("Bonus Combo Hits: 1\n\n");
        desc.append("Increases combo hits by 1.");
        return desc.toString();
    }
}
