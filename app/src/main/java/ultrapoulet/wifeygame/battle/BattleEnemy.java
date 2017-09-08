package ultrapoulet.wifeygame.battle;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ultrapoulet.androidgame.framework.Graphics;
import ultrapoulet.androidgame.framework.helpers.AnimationImages;
import ultrapoulet.wifeygame.AnimationAssets;
import ultrapoulet.wifeygame.battle.enemyai.EnemyAI;
import ultrapoulet.wifeygame.battle.enemyai.EnemyAI.EnemyAction;
import ultrapoulet.wifeygame.battle.skills.AbsSkill.Multipliers;
import ultrapoulet.wifeygame.battle.skills.SkillList;
import ultrapoulet.wifeygame.character.EnemyCharacter;
import ultrapoulet.wifeygame.character.TransformEnemy;

/**
 * Created by John on 3/5/2016.
 */
public class BattleEnemy extends BattleCharacter{

    private static final int POWERUPTURNS = 3;
    private static final int POWERDOWNTURNS = 3;
    private static final int DEFENDTURNS = 3;
    private static final int WEAKENTURNS = 3;

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

    private int gold;
    private int experience;

    private int defendTurns = 0;
    private int weakenTurns = 0;
    private int powerUpTurns = 0;
    private int powerDownTurns = 0;

    private boolean defendActive = false;
    private boolean weakenActive = false;
    private boolean powerUpActive = false;
    private boolean powerDownActive = false;

    private Map<EnemyAction, String> actionStrings;

    private EnemyAI ai;

    private ArrayList<TransformEnemy> transformations;

    private EnemyAction storedAction;

    public BattleEnemy(EnemyCharacter input, Graphics g) {
        this.name = input.getName();
        this.maxHP = input.getMaxHP();
        this.currentHP = input.getMaxHP();
        this.powerDamage = input.getPowerDamage();
        this.powerHits = input.getPowerHits();
        this.comboDamage = input.getComboDamage();
        this.comboHits = input.getComboHits();
        this.magicDamage = input.getMagicDamage();
        this.healAmount = input.getHealAmount();
        this.powerUpPercentage = input.getPowerUpPercentage();
        this.powerDownPercentage = input.getPowerDownPercentage();
        this.defendPercentage = input.getDefendPercentage();
        this.weakenPercentage = input.getWeakenPercentage();
        this.specialDamage = input.getSpecialDamage();
        this.specialHits = input.getSpecialHits();
        this.skills = new SkillList(input.getSkills(), input.getWeaponSkill(), input.getUniqueSkill(), this);
        this.attackElement = input.getAttackElement();
        this.strongElement = input.getStrongElement();
        this.weakElement = input.getWeakElement();
        this.image = input.getImage(g);
        this.ai = input.getAI();
        this.transformations = input.getTransformations();
        this.gold = input.getGold();
        this.experience = input.getExperience();

        //As clean up at some point, remove all this, put in the enum
        actionStrings = new HashMap<>();
        actionStrings.put(EnemyAction.POWER_ATTACK, "Power Attack");
        actionStrings.put(EnemyAction.COMBO_ATTACK, "Combo Attack");
        actionStrings.put(EnemyAction.MAGIC_ATTACK, "Magic Attack");
        actionStrings.put(EnemyAction.HEALING_MAGIC, "Heal");
        actionStrings.put(EnemyAction.POWER_UP, "Power Up");
        actionStrings.put(EnemyAction.POWER_DOWN, "Power Down");
        actionStrings.put(EnemyAction.DEFEND, "Defend");
        actionStrings.put(EnemyAction.WEAKEN, "Weaken");
        actionStrings.put(EnemyAction.SPECIAL_ATTACK, "Special Attack");
        actionStrings.put(EnemyAction.TRANSFORM, "Transformation");
        actionStrings.put(EnemyAction.WAIT, "Wait");
    }

    public int getNumHits(){
        int hits;
        switch(getAction()){
            case POWER_ATTACK:
                hits = powerHits + this.skills.getBonusHits();
                return (hits < 10) ? hits : 10;
            case COMBO_ATTACK:
                hits = comboHits + this.skills.getBonusHits();
                return (hits < 10) ? hits: 10;
            case SPECIAL_ATTACK:
                hits = specialHits + this.skills.getBonusHits();
                return (hits < 10) ? hits : 10;
            default:
                return 1;
        }

    }

    public int getGold(){
        return this.gold;
    }

    public int getExperience(){
        return this.experience;
    }

    //This function is currently not used
    public int getBonusRecruiting() {
        return 0;
    }

    public SkillList getSkills() { return this.skills; }

    public AnimationImages getBattleAnimation(){
        return AnimationAssets.TestAnimation;
    }

    public void startBattle(List<BattleCharacter> party) {
        this.currentHP = this.maxHP;
        ai.reset();
        this.skills.startBattle(party);
    }

    //This call will likely change to get information about current state of battle
    //Might not need to though
    public void determineAction(){
        ai.selectAction();
        storedAction = ai.getAction();
    }

    public EnemyAction getAction(){
        return storedAction;
    }

    public String getActionString() {
        return actionStrings.get(storedAction);
    }

    public int PowerAttackDamage(BattleCharacter enemy){
        //Returns the damage that will be dealt for a Power Attack
        int baseDamage = this.powerDamage;
        double multiplier = skills.physicalAttackPercentage(enemy);
        if(powerUpActive){
            multiplier += powerUpPercentage;
        }
        if(powerDownActive){
            multiplier -= powerDownPercentage;
        }
        //System.out.println("Enemy's multiplying damage by: " + multiplier * getElementDamage(enemy));
        Log.i("PowerAttackDamage", "Enemy multiplying physical damage to: " + enemy.getName() + " by: " + multiplier * getElementDamage(enemy));
        int modDamage = (int) (baseDamage * multiplier * getElementDamage(enemy));
        modDamage = modDamage + (int) ((modDamage / 10) * Math.random());
        return modDamage;
    }

    public int ComboAttackDamage(BattleCharacter enemy){
        //Returns the damage that will be dealt for a Combo Attack
        int baseDamage = this.comboDamage;
        double multiplier = skills.physicalAttackPercentage(enemy);
        if(powerUpActive){
            multiplier += powerUpPercentage;
        }
        if(powerDownActive){
            multiplier -= powerDownPercentage;
        }
        //System.out.println("Enemy's multiplying damage by: " + multiplier * getElementDamage(enemy));
        Log.i("ComboAttackDamage", "Enemy multiplying physical damage to: " + enemy.getName() + " by: " + multiplier * getElementDamage(enemy));
        int modDamage = (int) (baseDamage * multiplier * getElementDamage(enemy));
        modDamage = modDamage + (int) ((modDamage / 10) * Math.random());
        return modDamage;
    }

    public int MagicAttackDamage(BattleCharacter enemy){
        //Returns the damage that will be dealt for a Magic Attack
        int baseDamage = this.magicDamage;
        double multiplier = skills.magicalAttackPercentage(enemy);
        if(powerUpActive){
            multiplier += powerUpPercentage;
        }
        if(powerDownActive){
            multiplier -= powerDownPercentage;
        }
        //System.out.println("Enemy's multiplying damage by: " + multiplier * getElementDamage(enemy));
        Log.i("MagicAttackDamage", "Enemy multiplying magic damage to: " + enemy.getName() + " by: " + multiplier * getElementDamage(enemy));
        int modDamage = (int) (baseDamage * multiplier * getElementDamage(enemy));
        modDamage = modDamage + (int) ((modDamage / 10) * Math.random());
        return modDamage;
    }

    public int HealAmount(BattleCharacter target){
        //Returns the amount that will be healed
        int baseHeal = (int) (this.healAmount * skills.healPercentage(target));
        //System.out.println("Enemy's multiplying heal by: " + skills.healPercentage(target));
        Log.i("HealAmount", "Enemy multiplying heal by: " + skills.healPercentage(target));
        int modHeal = baseHeal + (int) ((baseHeal / 10) * Math.random());
        return modHeal;
    }

    public int SpecialAttackDamage(BattleCharacter enemy){
        //Returns the amount that will be dealt for a Special Attack
        int baseDamage = this.specialDamage;
        double multiplier = skills.specialAttackPercentage(enemy);
        if(powerUpActive){
            multiplier += powerUpPercentage;
        }
        if(powerDownActive){
            multiplier -= powerDownPercentage;
        }
        //System.out.println("Enemy's multiplying damage by: " + multiplier * getElementDamage(enemy));
        Log.i("SpecialAttackDamage", "Enemy multiplying special damage to: " + enemy.getName() + " by: " + multiplier * getElementDamage(enemy));
        int modDamage = (int) (baseDamage * multiplier * getElementDamage(enemy));
        modDamage = modDamage + (int) ((modDamage / 10) * Math.random());
        return modDamage;
    }

    public int takePhysicalDamage(int damage, BattleCharacter enemy){
        //Damage modifiers
        int displayDamage = damage;
        double multiplier = skills.receivePhysicalAttackPercentage(enemy);
        if(defendActive){
            multiplier -= defendPercentage;
        }
        if(weakenActive){
            multiplier += weakenPercentage;
        }
        displayDamage = (int) (displayDamage * multiplier);
        //System.out.println("Enemy's multiplying damage taken by: " + multiplier);
        Log.i("takePhysicalDamage", "Enemy multiplying physical damage taken from: " + enemy.getName() + " by: " + multiplier);
        this.currentHP = this.currentHP - displayDamage;
        skills.onDamageReceived(displayDamage);
        if(this.currentHP <= 0){
            this.currentHP = 0;
        }
        return displayDamage;
    }

    public int takeMagicalDamage(int damage, BattleCharacter enemy){
        //Damage modifiers
        int displayDamage = damage;
        double multiplier = skills.receiveMagicalAttackPercentage(enemy);
        if(defendActive){
            multiplier -= defendPercentage;
        }
        if(weakenActive){
            multiplier += weakenPercentage;
        }
        displayDamage = (int) (displayDamage * multiplier);
        //System.out.println("Enemy's multiplying damage taken by: " + multiplier);
        Log.i("takeMagicalDamage", "Enemy multiplying magical damage taken from: " + enemy.getName() + " by: " + multiplier);
        this.currentHP = this.currentHP - displayDamage;
        skills.onDamageReceived(displayDamage);
        if(this.currentHP <= 0){
            this.currentHP = 0;
        }
        return displayDamage;
    }

    public int takeSpecialDamage(int damage, BattleCharacter enemy){
        //Damage modifiers
        int displayDamage = damage;
        double multiplier = skills.receiveSpecialAttackPercentage(enemy);
        if(defendActive){
            multiplier -= defendPercentage;
        }
        if(weakenActive){
            multiplier += weakenPercentage;
        }
        displayDamage = (int) (displayDamage * multiplier);
        //System.out.println("Enemy's multiplying damage taken by: " + multiplier);
        Log.i("takeSpecialDamage", "Enemy multiplying special damage from: " + enemy.getName() + " by: " + multiplier);
        this.currentHP = this.currentHP - displayDamage;
        skills.onDamageReceived(displayDamage);
        if(this.currentHP <= 0){
            this.currentHP = 0;
        }
        return displayDamage;
    }

    public int healDamage(int heal, BattleCharacter healer){
        //Heal modifiers
        int displayHeal = heal;
        double multiplier = skills.receiveHealPercentage(healer);
        displayHeal = (int) (displayHeal * multiplier);
        //System.out.println("Enemy's multiplying heal received by: " + skills.receiveHealPercentage(healer));
        Log.i("healDamage", "Enemy multiplying heal received by: " + multiplier);
        this.currentHP = this.currentHP + displayHeal;
        if(this.currentHP > this.maxHP){
            displayHeal = displayHeal - (this.currentHP - this.maxHP);
            this.currentHP = this.maxHP;
        }
        return displayHeal;
    }

    public void defend(){
        defendActive = true;
        defendTurns = 0;
    }

    public boolean isDefendActive() {
        return defendActive;
    }

    public void weaken(){
        weakenActive = true;
        weakenTurns = 0;
    }

    public boolean isWeakenActive() {
        return weakenActive;
    }

    public void powerUp(){
        powerUpActive = true;
        powerUpTurns = 0;
    }

    public boolean isPowerUpActive() {
        return powerUpActive;
    }

    public void powerDown(){
        powerDownActive = true;
        powerDownTurns = 0;
    }

    public boolean isPowerDownActive() {
        return powerDownActive;
    }

    public void startTurn(){
        if(defendActive){
            defendTurns++;
            if(defendTurns > DEFENDTURNS){
                defendActive = false;
                defendTurns = 0;
            }
        }
        if(weakenActive){
            weakenTurns++;
            if(weakenTurns > WEAKENTURNS){
                weakenActive = false;
                weakenTurns = 0;
            }
        }
    }

    public void endRound(){
        skills.endRound();

        if(powerUpActive){
            powerUpTurns++;
            if(powerUpTurns > POWERUPTURNS){
                powerUpActive = false;
                powerUpTurns = 0;
            }
        }
        if(powerDownActive){
            powerDownTurns++;
            if(powerDownTurns > POWERDOWNTURNS){
                powerDownActive = false;
                powerDownTurns = 0;
            }
        }
    }

    public boolean canTransform(){
        return transformations.size() > transformNumber;
    }

    public void transform(Graphics g){
        TransformEnemy form = transformations.get(transformNumber);
        transformNumber++;
        this.name = form.getName();
        this.image = form.getImage(g);
        this.ai = EnemyAI.getAI(form.getAi());
        if(form.getHP() > 0) {
            this.maxHP = form.getHP();
            if(this.maxHP < this.currentHP){
                this.currentHP = this.maxHP;
            }
        }
        if(form.getPowerDamage() > 0){
            this.powerDamage = form.getPowerDamage();
        }
        if(form.getPowerHits() > 0){
            this.powerHits = form.getPowerHits();
        }
        if(form.getComboDamage() > 0){
            this.comboDamage = form.getComboDamage();
        }
        if(form.getComboHits() > 0){
            this.comboHits = form.getComboHits();
        }
        if(form.getMagicDamage() > 0){
            this.comboHits = form.getMagicDamage();
        }
        if(form.getHealAmount() > 0){
            this.healAmount = form.getHealAmount();
        }
        if(form.getPowerUpPercentage() > 0.0){
            this.powerUpPercentage = form.getPowerUpPercentage();
        }
        if(form.getPowerDownPercentage() > 0.0){
            this.powerDownPercentage = form.getPowerDownPercentage();
        }
        if(form.getDefendPercentage() > 0.0){
            this.defendPercentage = form.getDefendPercentage();
        }
        if(form.getWeakenPercentage() > 0.0){
            this.weakenPercentage = form.getWeakenPercentage();
        }
        if(form.getSpecialDamage() > 0){
            this.specialDamage = form.getSpecialDamage();
        }
        if(form.getSpecialHits() > 0){
            this.specialDamage = form.getSpecialHits();
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
        if(form.getUniqueSkill() != null){
            skills.setUniqueSkill(form.getUniqueSkill().getUniqueBattleSkill(this));
        }
        if(form.getWeaponSkill() != null){
            skills.setWeaponSkill(form.getWeaponSkill().getWeaponBattleSkill(this));
        }
        for(int i = 0; i < form.getAddSkills().size(); i++){
            skills.addSkill(form.getAddSkills().get(i).getBattleSkill(this));
        }
        for(int i = 0; i < form.getRemoveSkills().size(); i++){
            skills.removeSkill(form.getRemoveSkills().get(i).getBattleSkill(this));
        }
    }

    public Multipliers getMultipliers(BattleCharacter enemy){
        return skills.getMultipliers(enemy);
    }

}
