package ultrapoulet.wifeygame.character;

import java.util.HashMap;

import ultrapoulet.wifeygame.battle.BattleCharacter;
import ultrapoulet.wifeygame.battle.skills.AbsSkill;

/**
 * Created by John on 7/14/2016.
 */
public class SkillsEnum {

    private String skillName;
    private String skillDesc;

    private SkillsEnum(String skillName, String skillDesc){
        this.skillName = skillName;
        this.skillDesc = skillDesc;
    }

    public String getSkillName(){
        return this.skillName;
    }

    public String getSkillDesc(){
        return this.skillDesc;
    }

    public AbsSkill getBattleSkill(BattleCharacter owner){
        if(this.skillName.equals("")){
            return new AbsSkill(owner);
        }
        else{
            return new AbsSkill(owner);
        }
    }

    public static SkillsEnum getSkill(String skillName){
        if(skillsList == null){
            createSkillList();
        }
        return skillsList.get(skillName);
    }

    private static HashMap<String, SkillsEnum> skillsList;

    private static void createSkillList(){
        skillsList = new HashMap<>();
    }
}
