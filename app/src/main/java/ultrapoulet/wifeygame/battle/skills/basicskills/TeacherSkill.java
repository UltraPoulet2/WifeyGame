package ultrapoulet.wifeygame.battle.skills.basicskills;

import ultrapoulet.wifeygame.battle.BattleCharacter;
import  ultrapoulet.wifeygame.battle.skills.AbsSkill;

/**
 * Created by John on 3/6/2017.
 */

public class TeacherSkill extends AbsSkill {

    private int totalBonusExp;
    private static final double bonusExpPercentage = 0.10;

    public TeacherSkill(BattleCharacter owner){
        super(owner);
        this.skillName = "Teacher";
    }

    @Override
    public void endWave(BattleCharacter enemy) {
        totalBonusExp += (int) (enemy.getExperience() * bonusExpPercentage);
    }

    @Override
    public int getBonusExp() {
        return totalBonusExp;
    }

    @Override
    public String getDescription(BattleCharacter enemy) {
        String desc = "Current Bonus EXP: " + totalBonusExp + "\n\n" +
                "Increases experience received by party by 10%";
        return desc;
    }
}
