package ultrapoulet.wifeygame.character;

import java.util.Comparator;
import java.util.HashMap;

import ultrapoulet.wifeygame.battle.BattleCharacter;
import ultrapoulet.wifeygame.battle.skills.AbsWeaponSkill;
import ultrapoulet.wifeygame.battle.skills.weaponskills.KokoRoseWeaponSkill;

/**
 * Created by John on 6/4/2017.
 */

public class WeaponSkillsEnum {

    private String skillName;
    private String skillDesc;

    private WeaponSkillsEnum(String skillName, String skillDesc){
        this.skillName = skillName;
        this.skillDesc = skillDesc;
    }

    public String getSkillName(){
        return this.skillName;
    }

    public String getSkillDesc(){
        return this.skillDesc;
    }

    public AbsWeaponSkill getWeaponBattleSkill(BattleCharacter owner){
        switch(this.skillName){
            case "Koko's Roses":
                return new KokoRoseWeaponSkill(owner);
            default:
                return null;
        }
    }

    public static WeaponSkillsEnum getSkill(String skillName){
        if(skillsList == null){
            createSkillList();
        }
        return skillsList.get(skillName);
    }

    private static HashMap<String, WeaponSkillsEnum> skillsList;

    private static void createSkillList() {
        skillsList = new HashMap<>();
        skillsList.put("KOKO_ROSE", new WeaponSkillsEnum("Koko's Roses", "Decreases physical, magical, and special damage taken multiplier by 0.05x from an enemy for each round that it has been attacked, to a minimum of 0.50x."));
    }
}
