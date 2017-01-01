package ultrapoulet.wifeygame.character;

import java.util.ArrayList;
import java.util.Collections;

import ultrapoulet.androidgame.framework.Graphics;
import ultrapoulet.androidgame.framework.Image;
import ultrapoulet.wifeygame.battle.BattleWifey;

/**
 * Created by John on 5/5/2016.
 */
public class WifeyCharacter {

    private String name;
    private String hashKey;
    private int strength;
    private int magic;
    private ArrayList<SkillsEnum> skills;

    private int experience;
    private int level;

    private Weapon weapon;

    private Element attackElement;
    private Element strongElement;
    private Element weakElement;

    private String image;

    private ArrayList<TransformWifey> transformations;

    public WifeyCharacter(){
        skills = new ArrayList<>();
        transformations = new ArrayList<>();
    }

    public BattleWifey getBattleCharacter(Graphics g){
        return new BattleWifey(this, g);
    }

    public String getName(){
        return this.name;
    }

    public String getHashKey(){
        return this.hashKey;
    }

    public Image getImage(Graphics g){
        return g.newImage("characters/" + this.image + ".png", Graphics.ImageFormat.ARGB8888);
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

    public Element getAttackElement() { return this.attackElement; }

    public Element getStrongElement() { return this.strongElement; }

    public Element getWeakElement() { return this.weakElement; }

    public ArrayList<SkillsEnum> getSkills() { return this.skills; }

    public ArrayList<TransformWifey> getTransformations(){
        return this.transformations;
    }

    public void setHashKey(String hashKey){
        this.hashKey = hashKey;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setImage(String image){
        this.image = image;
    }

    public void setStrength(int strength){
        this.strength = strength;
    }

    public void setMagic(int magic){
        this.magic = magic;
    }

    public void setWeapon(Weapon weapon) { this.weapon = weapon; }

    public void setAttackElement(Element element){ this.attackElement = element; }

    public void setStrongElement(Element element) { this.strongElement = element; }

    public void setWeakElement(Element element) { this.weakElement = element; }

    public void addSkill(SkillsEnum skill) {
        if (!this.skills.contains(skill)) {
            this.skills.add(skill);
        }
        Collections.sort(this.skills, SkillsEnum.SKILLS_ENUM_COMPARATOR);
    }

    public void addTransformation(TransformWifey t){
        this.transformations.add(t);
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

    public int compareStrength(WifeyCharacter other){
        int result = other.getStrength() - this.getStrength();
        if(result != 0){
            return result;
        }
        else{
            return this.compareName(other);
        }
    }

    public int compareMagic(WifeyCharacter other){
        int result = other.getMagic() - this.getMagic();
        if(result != 0){
            return result;
        }
        else{
            return this.compareName(other);
        }
    }

    public boolean validate(){
        if(name.length() == 0){
            return false;
        }
        if(strength <= 0){
            return false;
        }
        if(magic <= 0){
            return false;
        }
        if(weapon == null){
            return false;
        }
        if(attackElement == null || strongElement == null | weakElement == null){
            return false;
        }
        return true;
    }
}
