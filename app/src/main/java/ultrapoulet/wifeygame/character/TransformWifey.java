package ultrapoulet.wifeygame.character;

import java.util.ArrayList;
import java.util.Collections;

import ultrapoulet.androidgame.framework.Image;
import ultrapoulet.wifeygame.battle.BattleWifey;

/**
 * Created by John on 9/14/2016.
 */
public class TransformWifey {

    private String name;
    private int strength;
    private int magic;
    private ArrayList<SkillsEnum> addSkills;
    private ArrayList<SkillsEnum> removeSkills;
    private Weapon weapon;
    private Element attackElement;
    private Element strongElement;
    private Element weakElement;
    private Image image;

    public TransformWifey(){
        addSkills = new ArrayList();
        removeSkills = new ArrayList();
    }

    public String getName(){
        return this.name;
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

    public Element getAttackElement() { return this.attackElement; }

    public Element getStrongElement() { return this.strongElement; }

    public Element getWeakElement() { return this.weakElement; }

    public ArrayList<SkillsEnum> getAddSkills() { return this.addSkills; }

    public ArrayList<SkillsEnum> getRemoveSkills() { return this.removeSkills; }

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

    public void setAttackElement(Element element){ this.attackElement = element; }

    public void setStrongElement(Element element) { this.strongElement = element; }

    public void setWeakElement(Element element) { this.weakElement = element; }

    public void addSkill(SkillsEnum skill) {
        if (!this.addSkills.contains(skill)) {
            this.addSkills.add(skill);
        }
        Collections.sort(this.addSkills, SkillsEnum.SKILLS_ENUM_COMPARATOR);
    }

    public void removeSkill(SkillsEnum skill) {
        if (!this.removeSkills.contains(skill)) {
            this.removeSkills.add(skill);
        }
        Collections.sort(this.removeSkills, SkillsEnum.SKILLS_ENUM_COMPARATOR);
    }

    public boolean validate(){
        if(name.length() == 0){
            return false;
        }
        if(image == null){
            return false;
        }
        if(strength <= 0){
            return false;
        }
        if(magic <= 0){
            return false;
        }
        return true;
    }
}
