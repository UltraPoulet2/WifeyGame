package ultrapoulet.wifeygame.character;

import java.util.HashMap;

import ultrapoulet.wifeygame.battle.BattleCharacter;
import ultrapoulet.wifeygame.battle.skills.AbsSkill;
import ultrapoulet.wifeygame.battle.skills.GhostSkill;
import ultrapoulet.wifeygame.battle.skills.MasochistSkill;
import ultrapoulet.wifeygame.battle.skills.MediumSkill;
import ultrapoulet.wifeygame.battle.skills.SadistSkill;
import ultrapoulet.wifeygame.battle.skills.TsundereSkill;

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
        switch(this.skillName){
            case "Sadist":
                return new SadistSkill(owner);
            case "Masochist":
                return new MasochistSkill(owner);
            case "Tsundere":
                return new TsundereSkill(owner);
            case "Ghost":
                return new GhostSkill(owner);
            case "Medium":
                return new MediumSkill(owner);
            default:
                return null;
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
        skillsList.put("SADIST", new SkillsEnum("Sadist", "Description"));
        skillsList.put("MASOCHIST", new SkillsEnum("Masochist", "Description"));
        skillsList.put("TSUNDERE", new SkillsEnum("Tsundere", "Description"));
        skillsList.put("GHOST", new SkillsEnum("Ghost", "Description"));
        skillsList.put("MEDIUM", new SkillsEnum("Medium", "Description"));
    }
}