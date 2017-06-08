package ultrapoulet.wifeygame.character;

import java.util.HashMap;

import ultrapoulet.wifeygame.battle.BattleCharacter;
import ultrapoulet.wifeygame.battle.skills.AbsUniqueSkill;

/**
 * Created by John on 6/7/2017.
 */

public class UniqueSkillsEnum {

    private String skillName;
    private String skillDesc;

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

    public AbsUniqueSkill getUniqueBattleSkill(BattleCharacter owner){
        switch(this.skillName){
            default:
                return null;
        }
    }

    public static UniqueSkillsEnum getSkill(String skillName){
        if(skillsList == null){
            createSkillList();
        }
        return skillsList.get(skillName);
    }

    private static HashMap<String, UniqueSkillsEnum> skillsList;

    private static void createSkillList() {
        skillsList = new HashMap<>();
    }
}
