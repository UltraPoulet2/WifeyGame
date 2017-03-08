package ultrapoulet.wifeygame.battle.skills.implementations;

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
        StringBuilder desc = new StringBuilder();
        desc.append("Current Bonus EXP: " + totalBonusExp + "\n\n");
        desc.append("Increases experience received by party by 10%");
        return desc.toString();
    }
}
