package ultrapoulet.wifeygame.battle.skills.basicskills;

import ultrapoulet.wifeygame.battle.BattleCharacter;
import ultrapoulet.wifeygame.battle.skills.AbsSkill;

/**
 * Created by John on 6/23/2017.
 */

public class IdolSkill extends AbsSkill {

    public IdolSkill(BattleCharacter owner){
        super(owner);
        this.skillName = "Idol";
    }

    private int bonusRecruiting = 0;
    private static final int RECRUIT_PER_ENEMY = 2;

    @Override
    public void onEnemyDefeat(BattleCharacter enemy) {
        bonusRecruiting += RECRUIT_PER_ENEMY;
    }

    @Override
    public int getBonusRecruiting() {
        return bonusRecruiting;
    }

    @Override
    public String getDescription(BattleCharacter enemy) {
        String desc = "Bonus Recruiting Percentage: " + bonusRecruiting + "%\n\n" +
                "Defeating an enemy with this wifey increases chance to find a wifey after the battle by 2%.";
        return desc;
    }
}
