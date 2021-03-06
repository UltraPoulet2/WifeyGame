package ultrapoulet.wifeygame.battle;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ultrapoulet.androidgame.framework.Graphics;
import ultrapoulet.wifeygame.character.TransformWifey;
import ultrapoulet.wifeygame.battle.skills.AbsSkill.Multipliers;
import ultrapoulet.wifeygame.battle.skills.SkillList;
import ultrapoulet.wifeygame.character.WifeyCharacter;

/**
 * Created by John on 3/5/2016.
 */
public class BattleWifey extends BattleCharacter{

    private int numHits;
    //private Weapon weapon;
    private int strength;
    private int magic;

    private ArrayList<TransformWifey> transformWifeys;

    private boolean isDefending = false;

    public BattleWifey(WifeyCharacter input, Graphics g) {
        this.name = input.getName();
        this.maxHP = calculateHP(input.getStrength());
        this.currentHP = this.maxHP;
        this.weapon = input.getWeapon();
        this.numHits = this.weapon.getNumHits();
        this.strength = input.getStrength();
        this.magic = input.getMagic();
        this.image = input.getImage(g);
        this.skills = new SkillList(input.getSkills(), input.getWeaponSkill(), input.getUniqueSkill(), this);
        this.attackElement = input.getAttackElement();
        this.strongElement = input.getStrongElement();
        this.weakElement = input.getWeakElement();
        this.transformWifeys = input.getTransformations();
    }

    public static int calculateHP(int strength){
        return (int) (290 * Math.pow(1.8, strength / 100.0));
    }

    public int getNumHits(){
        int temp = this.numHits + this.skills.getBonusHits();
        if(temp > 10){
            return 10;
        }
        return temp;
    }

    public int getStrength(){
        return this.strength;
    }

    public int getMagic(){
        return this.magic;
    }

    public SkillList getSkills() { return this.skills; }

    public void startBattle(List<BattleCharacter> party){
        this.currentHP = this.maxHP;
        this.isDefending = false;
        this.skills.startBattle(party);
    }

    public void endRound() { skills.endRound(); }

    public void startTurn(){
        //Function for things that happen at start of turn
        this.isDefending = false;
    }

    public int PowerAttackDamage(BattleCharacter enemy){
        //int baseDamage = this.strength * 5;
        int baseDamage = (int) (20 * Math.pow(4, this.strength / 100.0 ));
        //Do checks on skills to determine bonus damage
        double multiplier = skills.physicalAttackPercentage(enemy) * getElementDamage(enemy);
        int modDamage = (int) (baseDamage * multiplier);
        Log.i("PowerAttackDamage", "Multiplying physical damage from: " + this.getName() + " by: " + multiplier);
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
        //int baseDamage = this.magic * 5;
        int baseDamage = (int) (20 * Math.pow(4, this.magic / 100.0 ));
        //Do checks on skills to determine bonus damage
        double multiplier = skills.magicalAttackPercentage(enemy) * getElementDamage(enemy);
        int modDamage = (int) (baseDamage * multiplier);
        Log.i("MagicAttackDamage", "Multiplying magical damage from: " + this.getName() + " by: " + multiplier);
        modDamage = modDamage + (int) ((modDamage / 10) * Math.random());
        return modDamage;
    }

    public int HealAmount() {
        int baseHeal = (int) (20 * Math.pow(4, this.magic / 100.0 ));
        //Do checks on skills to determine bonus healing
        double multiplier = skills.healPercentage();
        int modHeal = (int) (baseHeal * multiplier);
        Log.i("HealAmount", "Heal to everyone from " + this.getName() + " multiplied by: " + multiplier);
        modHeal = modHeal + (int) ((modHeal / 10) * Math.random());
        return modHeal;
    }

    public int HealAmount(BattleCharacter target){
        //int baseHeal = this.magic * 2;
        int baseHeal = (int) (20 * Math.pow(4, this.magic / 100.0 ));
        //Do checks on skills to determine bonus healing
        double multiplier = skills.healPercentage(target);
        int modHeal = (int) (baseHeal * multiplier);
        Log.i("HealAmount", "Heal to " + target.getName() + " from " + this.getName() + " multiplied by: " + multiplier);
        modHeal = modHeal + (int) ((modHeal / 10) * Math.random());
        return modHeal;
    }

    public int SpecialAttackDamage(BattleCharacter enemy){
        int maxStat = (this.strength > this.magic) ? this.strength : this.magic;
        int baseDamage = (int) (50 * Math.pow(5, maxStat / 100.0));
        //Do checks on skills to determine bonus damage
        double multiplier = skills.specialAttackPercentage(enemy) * getElementDamage(enemy);
        int modDamage = (int) (baseDamage * multiplier);
        Log.i("SpecialAttackDamage", "Multiplying special damage from: " + this.getName() + " by: " + multiplier);
        modDamage = modDamage + (int) ((modDamage / 10) * Math.random());
        return modDamage;

    }

    public void Defend(){
        this.isDefending = true;
    }

    public int takePhysicalDamage(int damage, BattleCharacter enemy){
        //Check skills for anything to reduce damage taken
        int displayDamage = damage;
        if(this.isDefending){ displayDamage = displayDamage/2; }
        double multiplier = skills.receivePhysicalAttackPercentage(enemy);
        displayDamage = (int) (displayDamage * multiplier);
        Log.i("takePhysicalDamage", this.getName() + " multiplying physical damage received by: " + multiplier);
        this.currentHP = this.currentHP - displayDamage;
        skills.onDamageReceived(displayDamage);
        if(this.currentHP <= 0){
            this.currentHP = 0;
        }
        return displayDamage;
    }

    public int takeMagicalDamage(int damage, BattleCharacter enemy){
        //Check skills for anything to reduce damage taken
        int displayDamage = damage;
        if(this.isDefending){ displayDamage = displayDamage/2; }
        double multiplier = skills.receiveMagicalAttackPercentage(enemy);
        displayDamage = (int) (displayDamage * multiplier);
        Log.i("takeMagicalDamage", this.getName() + " multiplying magical damage received by: " + multiplier);
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
        double multiplier = skills.receiveSpecialAttackPercentage(enemy);
        displayDamage = (int) (displayDamage * multiplier);
        Log.i("takeSpecialDamage", this.getName() + " multiplying special damage received by: " + multiplier);
        this.currentHP = this.currentHP - displayDamage;
        skills.onDamageReceived(displayDamage);
        if(this.currentHP <= 0){
            this.currentHP = 0;
        }
        return displayDamage;
    }

    public int healDamage(int heal) {
        //Check skills for anything to increase healing
        int displayHeal = heal;
        double multiplier = skills.receiveHealPercentage();
        displayHeal = (int) (displayHeal * multiplier);
        this.currentHP = this.currentHP + displayHeal;
        Log.i("healDamage", this.getName() + " multiplying heal received from party by: " + multiplier);
        if(this.currentHP > this.maxHP){
            displayHeal = displayHeal - (this.currentHP - this.maxHP);
            this.currentHP = this.maxHP;
        }
        return displayHeal;
    }

    public int healDamage(int heal, BattleCharacter healer){
        //Check skills for anything to increase healing
        int displayHeal = heal;
        double multiplier = skills.receiveHealPercentage(healer);
        displayHeal = (int) (displayHeal * multiplier);
        this.currentHP = this.currentHP + displayHeal;
        Log.i("healDamage", this.getName() + " multiplying heal received from: " + healer.getName() + " by: " + multiplier);
        if(this.currentHP > this.maxHP){
            displayHeal = displayHeal - (this.currentHP - this.maxHP);
            this.currentHP = this.maxHP;
        }
        return displayHeal;
    }

    public void transform(Graphics g){
        TransformWifey form = transformWifeys.get(transformNumber);
        transformNumber++;
        this.image = form.getImage(g);
        this.maxHP = form.getHP();
        this.strength = form.getStrength();
        this.magic = form.getMagic();
        if(form.getWeapon() != null){
            this.weapon = form.getWeapon();
            this.numHits = this.weapon.getNumHits();
        }
        if(form.getAttackElement() != null){
            this.attackElement = form.getAttackElement();
        }
        if(form.getStrongElement() != null){
            this.strongElement = form.getStrongElement();
        }
        if(form.getWeakElement() != null){
            this.weakElement = form.getWeakElement();
        }
        for(int i = 0; i < form.getAddSkills().size(); i++){
            skills.addSkill(form.getAddSkills().get(i).getBattleSkill(this));
        }
        for(int i = 0; i < form.getRemoveSkills().size(); i++){
            skills.removeSkill(form.getRemoveSkills().get(i).getBattleSkill(this));
        }
        if(form.getUniqueSkill() != null){
            skills.setUniqueSkill(form.getUniqueSkill().getUniqueBattleSkill(this));
        }
        if(form.getWeaponSkill() != null){
            skills.setWeaponSkill(form.getWeaponSkill().getWeaponBattleSkill(this));
        }
    }

    public boolean canTransform(){
        return transformWifeys.size() > transformNumber;
    }

    public int getTransformNumber() {
        return transformNumber + 1;
    }

    public int getMaxTransformNumber() {
        return transformWifeys.size() + 1;
    }

    public Multipliers getMultipliers(BattleCharacter enemy){
        return skills.getMultipliers(enemy);
    }

    public int getGold(){
        return skills.getBonusGold();
    }

    public int getExperience(){
        return skills.getBonusExp();
    }

    public int getBonusRecruiting(){
        return skills.getBonusRecruiting();
    }
}
