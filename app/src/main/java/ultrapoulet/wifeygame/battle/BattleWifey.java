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
        int temp = this.numHits + this.skills.getBonusHits();
        if(temp > 10){
            return 10;
        }
        return temp;
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

    public SkillList getSkills() { return this.skills; }

    public boolean hasSkill(Class skillClass){
        return skills.hasSkill(skillClass);
    }

    public void giveSkillBonus(double multiplier, Class givingSkill, Class receivingSkill){
        skills.giveSkillBonus(multiplier, givingSkill, receivingSkill);
    }

    public void setCurrentHP(int hp){
        this.currentHP = hp;
    }

    public void startBattle(BattleCharacter[] party){
        this.currentHP = this.maxHP;
        this.isDefending = false;
        this.skills.startBattle(party);
    }

    public void startWave() { skills.startWave(); }

    public void startRound(){
        skills.startRound();
    }

    public void endRound() { skills.endRound(); }

    public void turnStart(){
        //Function for things that happen at start of turn
        this.isDefending = false;
    }

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
        System.out.println("Multiplying damage by: " + skills.magicalAttackPercentage(enemy));
        modDamage = modDamage + (int) ((modDamage / 10) * Math.random());
        return modDamage;
    }

    public int HealAmount(BattleCharacter target){
        int baseHeal = this.magic * 2;
        //Do checks on skills to determine bonus healing
        int modHeal = (int) (baseHeal * skills.healPercentage(target));
        System.out.println("Base heal multiplied by: " + skills.healPercentage(target));
        modHeal = modHeal + (int) ((modHeal / 10) * Math.random());
        return modHeal;
    }

    public int SpecialAttackDamage(BattleCharacter enemy){
        int baseDamage = this.strength * 10 + this.magic * 10;
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

    public int takePhysicalDamage(int damage, BattleCharacter enemy){
        //Check skills for anything to reduce damage taken
        int displayDamage = damage;
        if(this.isDefending){ displayDamage = displayDamage/2; }
        displayDamage = (int) (displayDamage * skills.receivePhysicalAttackPercentage(enemy));
        System.out.println("Multiplying damage taken by: " + skills.receivePhysicalAttackPercentage(enemy));
        this.currentHP = this.currentHP - displayDamage;
        skills.onDamageReceived(displayDamage);
        if(this.currentHP <= 0){
            this.currentHP = 0;
        }
        //Check if there's a skill to prevent lethal damage
        return displayDamage;
    }

    public int takeMagicalDamage(int damage, BattleCharacter enemy){
        //Check skills for anything to reduce damage taken
        int displayDamage = damage;
        if(this.isDefending){ displayDamage = displayDamage/2; }
        displayDamage = (int) (displayDamage * skills.receiveMagicalAttackPercentage(enemy));
        System.out.println("Multiplying damage taken by: " + skills.receiveMagicalAttackPercentage(enemy));
        this.currentHP = this.currentHP - displayDamage;
        skills.onDamageReceived(displayDamage);
        if(this.currentHP <= 0){
            this.currentHP = 0;
        }
        return displayDamage;
    }


    public int takeSpecialDamage(int damage, BattleCharacter enemy){
        //Check skills for anything to reduce damage taken
        int displayDamage = damage;
        if(this.isDefending){ displayDamage = displayDamage/2; }
        displayDamage = (int) (displayDamage * skills.receiveSpecialAttackPercentage(enemy));
        System.out.println("Multiplying damage taken by: " + skills.receiveSpecialAttackPercentage(enemy));
        this.currentHP = this.currentHP - displayDamage;
        skills.onDamageReceived(displayDamage);
        if(this.currentHP <= 0){
            this.currentHP = 0;
        }
        return displayDamage;
    }


    public int healDamage(int heal, BattleCharacter healer){
        //Check skills for anything to increase healing
        int displayHeal = heal;
        displayHeal = (int) (displayHeal * skills.receiveHealPercentage(healer));
        this.currentHP = this.currentHP + displayHeal;
        System.out.println("Heal multiplied by: " + skills.receiveHealPercentage(healer));
        if(this.currentHP > this.maxHP){
            displayHeal = displayHeal - (this.currentHP - this.maxHP);
            this.currentHP = this.maxHP;
        }
        return displayHeal;
    }

    public double[] getMultipliers(BattleEnemy enemy){
        return skills.getMultipliers(enemy);
    }
}
