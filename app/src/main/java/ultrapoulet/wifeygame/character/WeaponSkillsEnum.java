package ultrapoulet.wifeygame.character;

import ultrapoulet.wifeygame.battle.BattleCharacter;
import ultrapoulet.wifeygame.battle.skills.AbsWeaponSkill;
import ultrapoulet.wifeygame.battle.skills.weaponskills.KokoRoseWeaponSkill;

/**
 * Created by John on 6/4/2017.
 */

public enum WeaponSkillsEnum {
    KOKOROSE("Koko's Roses", "Decreases physical, magical, and special damage taken multiplier by 0.05x from an enemy for each round that it has been attacked, to a minimum of 0.50x."){
        @Override
        public AbsWeaponSkill getWeaponBattleSkill(BattleCharacter owner) {
            return new KokoRoseWeaponSkill(owner);
        }
    };

    private String skillName;
    private String skillDesc;
    public abstract AbsWeaponSkill getWeaponBattleSkill(BattleCharacter owner);

    WeaponSkillsEnum(String skillName, String skillDesc){
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
