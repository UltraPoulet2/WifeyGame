package ultrapoulet.wifeygame.battle;

import java.util.ArrayList;

import ultrapoulet.androidgame.framework.Image;
import ultrapoulet.wifeygame.character.SkillsEnum;
import ultrapoulet.wifeygame.character.Weapon;
import ultrapoulet.wifeygame.battle.skills.SkillList;

/**
 * Created by John on 3/5/2016.
 */
public class BattleWifey implements BattleCharacter{

    private String name;
    private int maxHP;
    private int currentHP;
    private int numHits;
    private Weapon weapon;
    private SkillList skills;
    private int strength;
    private int magic;

    private Image image;

    private boolean isDefending = false;

    //Section for skill flags

    public BattleWifey(String name, Weapon weapon, int strength, int magic, Image image, ArrayList<SkillsEnum> skills){
        this.name = name;
        this.maxHP = calculateHP(strength);
        this.currentHP = this.maxHP;
        this.weapon = weapon;
        this.numHits = weapon.getNumHits();
        this.strength = strength;
        this.magic = magic;
        this.image = image;
        this.skills = new SkillList(skills, this);
    }

    public static int calculateHP(int strength){
        return 10 * strength;
    }

    public String getName(){
        return this.name;
    }

    public Weapon getWeapon() { return this.weapon; }

    public int getNumHits(){
        return this.numHits;
    }

    public int getMaxHP(){
        return this.maxHP;
    }

    public int getCurrentHP(){
        return this.currentHP;
    }

    public int getStrength(){
        return this.strength;
    }

    public int getMagic(){
        return this.magic;
    }

    public Image getImage(){
        return this.image;
    }

    //For testing only
    public void setCurrentHP(int hp){
        this.currentHP = hp;
    }

    public void battleStart(){
        this.currentHP = this.maxHP;
        this.isDefending = false;
    }

    public void turnStart(){
        //Function for things that happen at start of turn
        this.isDefending = false;
    }

    //Temp functions
    public int PowerAttackDamage(){return 0;}
    public int ComboAttackDamage(){return 0;}
    public int MagicAttackDamage(){return 0;}
    public int SpecialAttackDamage(){return 0;}

    public int PowerAttackDamage(BattleCharacter enemy){
        int baseDamage = this.strength * 5;
        //Do checks on skills to determine bonus damage
        int modDamage = (int) (baseDamage * skills.physicalAttackPercentage(enemy));
        System.out.println("Increasing damage by: " + skills.physicalAttackPercentage(enemy));
        modDamage = modDamage + (int) ((modDamage / 10) * Math.random());
        return modDamage;
    }

    public int ComboAttackDamage(BattleCharacter enemy){
        int powerDamage = PowerAttackDamage(enemy);
        int totalHits = this.numHits + this.skills.getBonusHits();
        if(totalHits > 10){
            totalHits =  10;
        }
        int divider = (90 - 4*(totalHits - 2)) / totalHits;
        return (powerDamage * divider) / 100;
    }

    public int MagicAttackDamage(BattleCharacter enemy){
        int baseDamage = this.magic * 5;
        //Do checks on skills to determine bonus damage
        int modDamage = (int) (baseDamage * skills.magicalAttackPercentage(enemy));
        modDamage = modDamage + (int) ((modDamage / 10) * Math.random());
        return modDamage;
    }

    public int HealAmount(){
        int baseHeal = this.magic * 2;
        //Do checks on skills to determine bonus healing
        //int modHeal = (int) (baseHeal * skills.healPercentage(member);
        int modHeal = baseHeal + (int) ((baseHeal / 10) * Math.random());
        return baseHeal;
    }

    public int SpecialAttackDamage(BattleCharacter enemy){
        int baseDamage = this.strength * 4 + this.magic * 4;
        //Do checks on skills to determine bonus damage
        int modDamage = (int) (baseDamage * skills.specialAttackPercentage(enemy));
        modDamage = modDamage + (int) ((modDamage / 10) * Math.random());
        return modDamage;

    }

    public void onDamageDealt(int damage){
        skills.onDamageDealt(damage);
    }

    public void onEnemyDefeat(BattleCharacter enemy){
        skills.onEnemyDefeat(enemy);
    }

    public void Defend(){
        this.isDefending = true;
    }

    public int takePhysicalDamage(int damage){
        //Check skills for anything to reduce damage taken
        int displayDamage = damage;
        if(this.isDefending){ displayDamage = displayDamage/2; }
        //displayDamage = (int) (displayDamage * skills.receivePhysicalAttackPercentage(enemy));
        //skills.onDamageReceived(displayDamage);
        this.currentHP = this.currentHP - displayDamage;
        if(this.currentHP <= 0){
            this.currentHP = 0;
        }
        //Check if there's a skill to prevent lethal damage
        return displayDamage;
    }

    public int takeMagicalDamage(int damage){
        //Check skills for anything to reduce damage taken
        int displayDamage = damage;
        if(this.isDefending){ displayDamage = displayDamage/2; }
        //displayDamage = (int) (displayDamage * skills.receiveMagicalAttackPercentage(enemy));
        //skills.onDamageReceived(displayDamage);
        this.currentHP = this.currentHP - displayDamage;
        if(this.currentHP <= 0){
            this.currentHP = 0;
        }
        return displayDamage;
    }


    public int takeSpecialDamage(int damage){
        //Check skills for anything to reduce damage taken
        int displayDamage = damage;
        if(this.isDefending){ displayDamage = displayDamage/2; }
        //displayDamage = (int) (displayDamage * skills.receiveSpecialAttackPercentage(enemy));
        //skills.onDamageReceived(displayDamage);
        this.currentHP = this.currentHP - displayDamage;
        if(this.currentHP <= 0){
            this.currentHP = 0;
        }
        return displayDamage;
    }


    public int healDamage(int heal){
        //Check skills for anything to increase healing
        int displayHeal = heal;
        this.currentHP = this.currentHP + displayHeal;
        //displayHeal = (int) (displayHeal * skills.receiveHealPercentage(healer);
        if(this.currentHP > this.maxHP){
            displayHeal = displayHeal - (this.currentHP - this.maxHP);
            this.currentHP = this.maxHP;
        }
        return displayHeal;
    }
}
