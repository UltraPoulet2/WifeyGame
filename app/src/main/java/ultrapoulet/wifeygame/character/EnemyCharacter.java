package ultrapoulet.wifeygame.character;

import java.util.ArrayList;
import java.util.Collections;

import ultrapoulet.androidgame.framework.Graphics;
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

    private Weapon weapon;
    private ArrayList<SkillsEnum> skills;
    private WeaponSkillsEnum weaponSkill;
    private UniqueSkillsEnum uniqueSkill;

    private int gold;
    private int experience;

    private String image;

    private String ai;

    private ArrayList<TransformEnemy> transformations;

    public EnemyCharacter() {
        skills = new ArrayList<>();
        transformations = new ArrayList<>();
    }

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

    public WeaponSkillsEnum getWeaponSkill() { return this.weaponSkill; }

    public UniqueSkillsEnum getUniqueSkill() { return this.uniqueSkill; }

    public Image getImage(Graphics g) {
        return g.newImage("enemies/" + this.image + ".png", Graphics.ImageFormat.ARGB8888);
    }

    public Element getAttackElement() { return this.attackElement; }

    public Element getStrongElement() { return this.strongElement; }

    public Element getWeakElement() { return this.weakElement; }

    public ArrayList<TransformEnemy> getTransformations(){
        return this.transformations;
    }

    public EnemyAI getAI(){
        return EnemyAI.getAI(ai);
    }

    public int getGold() {
        return this.gold;
    }

    public int getExperience() {
        return this.experience;
    }

    public Weapon getWeapon() {
        return this.weapon;
    }


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

    public void addSkill(SkillsEnum skill){
        if (!this.skills.contains(skill)) {
            this.skills.add(skill);
        }
        Collections.sort(this.skills, SkillsEnum.SKILLS_ENUM_COMPARATOR);
    }

    public void setWeaponSkill(WeaponSkillsEnum skill) {
        this.weaponSkill = skill;
    }

    public void setUniqueSkill(UniqueSkillsEnum skill) {
        this.uniqueSkill = skill;
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

    public void setImage(String image){
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

    public void setGold(int gold){
        this.gold = gold;
    }

    public void setExperience(int exp){
        this.experience = exp;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public void addTransformation(TransformEnemy t){
        this.transformations.add(t);
    }

    public BattleEnemy getBattleEnemy(Graphics g){
        return new BattleEnemy(this, g);
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
        //For now, make every enemy provide gold and experience
        if(gold == 0 || experience == 0){
            return false;
        }
        if(weapon == null){
            return false;
        }
        return true;
    }
}
