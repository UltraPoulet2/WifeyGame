package ultrapoulet.wifeygame.character;

import ultrapoulet.wifeygame.battle.BattleCharacter;
import ultrapoulet.wifeygame.battle.skills.AbsUniqueSkill;
import ultrapoulet.wifeygame.battle.skills.uniqueskills.RemSkill;

/**
 * Created by John on 6/7/2017.
 */

public enum UniqueSkillsEnum {
    REM("Rem", "Rem gets a special skill because she is Rem. Multiplies damage dealt and healing by 2.00x."){
        @Override
        public AbsUniqueSkill getUniqueBattleSkill(BattleCharacter owner) {
            return new RemSkill(owner);
        }
    };

    private String skillName;
    private String skillDesc;
    public abstract AbsUniqueSkill getUniqueBattleSkill(BattleCharacter owner);

    private UniqueSkillsEnum(String skillName, String skillDesc){
        this.skillName = skillName;
        this.skillDesc = skillDesc;
    }

    public String getSkillName(){
        return this.skillName;
    }

    public String getSkillDesc(){
        return this.skillDesc;
    }
}
