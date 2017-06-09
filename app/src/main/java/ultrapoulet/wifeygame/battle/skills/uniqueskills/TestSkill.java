package ultrapoulet.wifeygame.battle.skills.uniqueskills;

import ultrapoulet.wifeygame.battle.BattleCharacter;
import ultrapoulet.wifeygame.battle.skills.AbsUniqueSkill;

/**
 * Created by John on 6/8/2017.
 */

public class TestSkill extends AbsUniqueSkill {

    public TestSkill(BattleCharacter owner){
        super(owner);
        this.skillName = "Test";
    }

    @Override
    public String getDescription(BattleCharacter enemy) {
        return "I am a testing skill";
    }
}
