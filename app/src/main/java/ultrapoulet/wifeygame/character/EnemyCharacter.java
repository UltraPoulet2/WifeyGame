package ultrapoulet.wifeygame.character;

import java.util.ArrayList;

import ultrapoulet.androidgame.framework.Image;
import ultrapoulet.wifeygame.battle.BattleEnemy;
import ultrapoulet.wifeygame.battle.enemyai.EnemyAI;

/**
 * Created by John on 6/28/2016.
 */
public class EnemyCharacter {

    private String name;
    private int maxHP;
    private int powerDamage;
    private int powerHits;
    private int comboDamage;
    private int comboHits;
    private int magicDamage;
    private int healAmount;
    private double powerUpPercentage;
    private double powerDownPercentage;
    private double defendPercentage;
    private double weakenPercentage;
    private int specialDamage;
    private int specialHits;

    private Element attackElement;
    private Element strongElement;
    private Element weakElement;

    private ArrayList<SkillsEnum> skills;

    private int gold;
    private int experience;

    private Image image;

    //private EnemyAI ai;
    private String ai;

    public EnemyCharacter() { skills = new ArrayList<>(); }

    public String getName(){
        return this.name;
    }

    public int getMaxHP(){
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

    public int getSpecialDamage() {
        return this.specialDamage;
    }

    public int getSpecialHits() {
        return this.specialHits;
    }

    public ArrayList<SkillsEnum> getSkills(){ return this.skills; }

    public Image getImage() {
        return this.image;
    }

    public Element getAttackElement() { return this.attackElement; }

    public Element getStrongElement() { return this.strongElement; }

    public Element getWeakElement() { return this.weakElement; }


    public void setName(String name){
        this.name = name;
    }

    public void setMaxHP(int maxHP){
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

    public void addSkill(SkillsEnum skill){ this.skills.add(skill); }

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

    public void setImage(Image image){
        this.image = image;
    }

    public void setAttackElement(Element element){ this.attackElement = element; }

    public void setStrongElement(Element element) { this.strongElement = element; }

    public void setWeakElement(Element element) { this.weakElement = element; }

    public void setAI(String ai){
        if(EnemyAI.getAI(ai) != null) {
            this.ai = ai;
        }
    }

    public BattleEnemy getBattleEnemy(){
        return new BattleEnemy(
                name,
                maxHP,
                powerDamage,
                powerHits,
                comboDamage,
                comboHits,
                magicDamage,
                healAmount,
                powerUpPercentage,
                powerDownPercentage,
                defendPercentage,
                weakenPercentage,
                specialDamage,
                specialHits,
                skills,
                attackElement,
                strongElement,
                weakElement,
                image,
                EnemyAI.getAI(ai));
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
        if(maxHP == 0){
            return false;
        }
        if(attackElement == null || strongElement == null | weakElement == null){
            return false;
        }
        return true;
    }
}
