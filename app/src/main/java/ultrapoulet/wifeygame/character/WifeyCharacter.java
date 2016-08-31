package ultrapoulet.wifeygame.character;

import java.util.ArrayList;

import ultrapoulet.androidgame.framework.Image;
import ultrapoulet.wifeygame.battle.BattleWifey;
import ultrapoulet.wifeygame.battle.skills.AbsSkill;
import ultrapoulet.wifeygame.battle.skills.SkillList;

/**
 * Created by John on 5/5/2016.
 */
public class WifeyCharacter {

    private String name;
    private String hashKey;
    private int strength;
    private int magic;
    //private SkillList skills;
    private ArrayList<SkillsEnum> skills;

    private int experience;
    private int level;

    private Weapon weapon;

    private Image image;

    public WifeyCharacter(){
        skills = new ArrayList<>();
    }

    public BattleWifey getBattleCharacter(){
        return new BattleWifey(name, weapon, strength, magic, image, skills);
    }

    public String getName(){
        return this.name;
    }

    public String getHashKey(){
        return this.hashKey;
    }

    public Image getImage(){
        return this.image;
    }

    public int getHP(){
        return BattleWifey.calculateHP(this.strength);
    }

    public int getStrength(){
        return this.strength;
    }

    public int getMagic(){
        return this.magic;
    }

    public Weapon getWeapon() { return this.weapon; }

    public ArrayList<SkillsEnum> getSkills() { return this.skills; }

    public void setHashKey(String hashKey){
        this.hashKey = hashKey;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setImage(Image image){
        this.image = image;
    }

    public void setStrength(int strength){
        this.strength = strength;
    }

    public void setMagic(int magic){
        this.magic = magic;
    }

    public void setWeapon(Weapon weapon) { this.weapon = weapon; }

    public void addSkill(SkillsEnum skill) {
        if (!this.skills.contains(skill)) {
            this.skills.add(skill);
        }
    }

    public int compareName(WifeyCharacter other){
        int result = this.getName().compareTo(other.getName());
        if(result != 0){
            return result;
        }
        else{
            return this.getHashKey().compareTo(other.getHashKey());
        }
    }
}
