package ultrapoulet.wifeygame.character;

import java.util.ArrayList;
import java.util.Collections;

import ultrapoulet.androidgame.framework.Image;
import ultrapoulet.wifeygame.battle.enemyai.EnemyAI;

/**
 * Created by John on 9/20/2016.
 */

public class TransformEnemy {

    private String name;
    private String ai;
    private int maxHP = 0;
    private int powerDamage = 0;
    private int powerHits = 0;
    private int comboDamage = 0;
    private int comboHits = 0;
    private int magicDamage = 0;
    private int healAmount = 0;
    private double powerUpPercentage = 0.0;
    private double powerDownPercentage = 0.0;
    private double defendPercentage = 0.0;
    private double weakenPercentage = 0.0;
    private int specialDamage = 0;
    private int specialHits = 0;

    private Element attackElement;
    private Element strongElement;
    private Element weakElement;

    private ArrayList<SkillsEnum> addSkills;
    private ArrayList<SkillsEnum> removeSkills;

    private Image image;

    public TransformEnemy(){
        addSkills = new ArrayList();
        removeSkills = new ArrayList();
    }

    public String getName(){
        return this.name;
    }

    public String getAi(){
        return this.ai;
    }

    public Image getImage(){
        return this.image;
    }

    public int getHP(){
        return this.maxHP;
    }

    public int getPowerDamage(){
        return this.powerDamage;
    }

    public int getPowerHits(){
        return this.powerHits;
    }

    public int getComboDamage(){
        return this.comboDamage;
    }

    public int getComboHits(){
        return this.comboHits;
    }

    public int getMagicDamage(){
        return this.magicDamage;
    }

    public int getHealAmount(){
        return this.healAmount;
    }

    public double getPowerUpPercentage(){
        return this.powerUpPercentage;
    }

    public double getPowerDownPercentage(){
        return this.powerDownPercentage;
    }

    public double getDefendPercentage(){
        return this.defendPercentage;
    }

    public double getWeakenPercentage(){
        return this.weakenPercentage;
    }

    public int getSpecialDamage(){
        return this.specialDamage;
    }

    public int getSpecialHits(){
        return this.specialHits;
    }

    public Element getAttackElement(){
        return this.attackElement;
    }

    public Element getStrongElement(){
        return this.strongElement;
    }

    public Element getWeakElement(){
        return this.weakElement;
    }

    public ArrayList<SkillsEnum> getAddSkills(){
        return this.addSkills;
    }

    public ArrayList<SkillsEnum> getRemoveSkills(){
        return this.removeSkills;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setAi(String ai){
        if(EnemyAI.getAI(ai) != null){
            this.ai = ai;
        }
    }

    public void setImage(Image image){
        this.image = image;
    }

    public void setHP(int maxHP){
        this.maxHP = maxHP;
    }

    public void setPowerDamage(int powerDamage){
        this.powerDamage = powerDamage;
    }

    public void setPowerHits(int powerHits){
        this.powerHits = powerHits;
    }

    public void setComboDamage(int comboDamage){
        this.comboDamage = comboDamage;
    }

    public void setComboHits(int comboHits){
        this.comboHits = comboHits;
    }

    public void setMagicDamage(int magicDamage){
        this.magicDamage = magicDamage;
    }

    public void setHealAmount(int healAmount){
        this.healAmount = healAmount;
    }

    public void setPowerUpPercentage(double powerUpPercentage){
        this.powerUpPercentage = powerUpPercentage;
    }

    public void setPowerDownPercentage(double powerDownPercentage){
        this.powerDownPercentage = powerDownPercentage;
    }

    public void setDefendPercentage(double defendPercentage){
        this.defendPercentage = defendPercentage;
    }

    public void setWeakenPercentage(double weakenPercentage){
        this.weakenPercentage = weakenPercentage;
    }

    public void setSpecialDamage(int specialDamage){
        this.specialDamage = specialDamage;
    }

    public void setSpecialHits(int specialHits){
        this.specialHits = specialHits;
    }

    public void setAttackElement(Element attackElement){
        this.attackElement = attackElement;
    }

    public void setStrongElement(Element strongElement){
        this.strongElement = strongElement;
    }

    public void setWeakElement(Element weakElement){
        this.weakElement = weakElement;
    }

    public void addSkill(SkillsEnum skill){
        if (!this.addSkills.contains(skill)) {
            this.addSkills.add(skill);
        }
        Collections.sort(this.addSkills, SkillsEnum.SKILLS_ENUM_COMPARATOR);
    }

    public void removeSkill(SkillsEnum skill){
        if (!this.removeSkills.contains(skill)) {
            this.removeSkills.add(skill);
        }
        Collections.sort(this.removeSkills, SkillsEnum.SKILLS_ENUM_COMPARATOR);
    }

    public boolean validate(){
        if(name == null || name.length() == 0){
            return false;
        }
        if(image == null){
            return false;
        }
        if(ai == null || ai.length() == 0){
            return false;
        }
        return true;
    }
}
