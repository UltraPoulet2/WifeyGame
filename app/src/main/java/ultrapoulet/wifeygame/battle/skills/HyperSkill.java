package ultrapoulet.wifeygame.battle.skills;

import ultrapoulet.wifeygame.battle.BattleCharacter;

/**
 * Created by John on 7/31/2016.
 */
public class HyperSkill extends AbsSkill {

    public HyperSkill(BattleCharacter owner){ super(owner); }

    @Override
    public int getBonusHits() {
        return 1;
    }
}