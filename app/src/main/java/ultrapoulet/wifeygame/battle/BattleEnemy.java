package ultrapoulet.wifeygame.battle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ultrapoulet.androidgame.framework.Image;
import ultrapoulet.wifeygame.battle.enemyai.EnemyAI;
import ultrapoulet.wifeygame.battle.enemyai.EnemyAI.EnemyAction;
import ultrapoulet.wifeygame.battle.skills.SkillList;
import ultrapoulet.wifeygame.character.SkillsEnum;

/**
 * Created by John on 3/5/2016.
 */
public class BattleEnemy implements BattleCharacter{

    private static final int POWERUPTURNS = 3;
    private static final int POWERDOWNTURNS = 3;
    private static final int DEFENDTURNS = 3;
    private static final int WEAKENTURNS = 3;

    private String name;
    private int maxHP;
    private int currentHP;
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
    private SkillList skills;

    private int gold;
    private int experience;

    private Image image;

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

    public BattleEnemy(
            String name,
            int maxHP,
            int powerDamage,
            int powerHits,
            int comboDamage,
            int comboHits,
            int magicDamage,
            int healAmount,
            double powerUpPercentage,
            double powerDownPercentage,
            double defendPercentage,
            double weakenPercentage,
            int specialDamage,
            int specialHits,
            ArrayList<SkillsEnum> skills,
            Image image,
            EnemyAI ai){
        this.name = name;
        this.maxHP = maxHP;
        this.currentHP = maxHP;
        this.powerDamage = powerDamage;
        this.powerHits = powerHits;
        this.comboDamage = comboDamage;
        this.comboHits = comboHits;
        this.magicDamage = magicDamage;
        this.healAmount = healAmount;
        this.powerUpPercentage = powerUpPercentage;
        this.powerDownPercentage = powerDownPercentage;
        this.defendPercentage = defendPercentage;
        this.weakenPercentage = weakenPercentage;
        this.specialDamage = specialDamage;
        this.specialHits = specialHits;
        this.skills = new SkillList(skills, this);
        this.image = image;
        this.ai = ai;

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

    public String getName(){ return this.name; }

    public int getMaxHP(){
        return this.maxHP;
    }

    public int getCurrentHP(){ return this.currentHP; }

    public void setCurrentHP(int hp){ if(hp < maxHP) {this.currentHP = hp;} }

    public boolean hasSkill(Class skillClass){
        return skills.hasSkill(skillClass);
    }

    public void giveSkillBonus(double multiplier, Class givingSkill, Class receivingSkill){
        skills.giveSkillBonus(multiplier, givingSkill, receivingSkill);
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
                //Just do 1 for now
                return 1;
        }

    }

    public int getGold(){
        return this.gold;
    }

    public int getExperience(){
        return this.experience;
    }

    public Image getImage() { return this.image; }

    public void startBattle(BattleCharacter[] party){
        this.currentHP = this.maxHP;
        ai.reset();
        this.skills.startBattle(party);
    }

    //This call will likely change to get information about current state of battle
    //Might not need to though
    public void determineAction(){
        ai.selectAction();
    }

    public EnemyAction getAction(){
        return ai.getAction();
    }

    public String getActionString() {
        return actionStrings.get(ai.getAction());
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
        System.out.println("Enemy's increasing damage by: " + multiplier);
        int modDamage = (int) (baseDamage * multiplier);
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
        System.out.println("Enemy's increasing damage by: " + multiplier);
        int modDamage = (int) (baseDamage * multiplier);
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
        System.out.println("Enemy's increasing damage by: " + multiplier);
        int modDamage = (int) (baseDamage * multiplier);
        modDamage = modDamage + (int) ((modDamage / 10) * Math.random());
        return modDamage;
    }

    public int HealAmount(BattleCharacter target){
        //Returns the amount that will be healed
        int baseHeal = (int) (this.healAmount * skills.healPercentage(target));
        System.out.println("Enemy's multiplying heal by: " + skills.healPercentage(target));
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
        System.out.println("Enemy's increasing damage by: " + multiplier);
        int modDamage = (int) (baseDamage * multiplier);
        modDamage = modDamage + (int) ((modDamage / 10) * Math.random());
        return modDamage;
    }

    public void onDamageDealt(int damage){
        skills.onDamageDealt(damage);
    }

    public void onEnemyDefeat(BattleCharacter enemy){
        skills.onEnemyDefeat(enemy);
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
        System.out.println("Enemy's multiplying damage taken by: " + multiplier);
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
        System.out.println("Enemy's multiplying damage taken by: " + multiplier);
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
        System.out.println("Enemy's multiplying damage taken by: " + multiplier);
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
        displayHeal = (int) (displayHeal * skills.receiveHealPercentage(healer));
        System.out.println("Enemy's multiplying heal received by: " + skills.receiveHealPercentage(healer));
        this.currentHP = this.currentHP + displayHeal;
        if(this.currentHP > this.maxHP){
            this.currentHP = this.maxHP;
        }
        return displayHeal;
    }

    public void defend(){
        defendActive = true;
        defendTurns = 0;
    }
    public void weaken(){
        weakenActive = true;
        weakenTurns = 0;
    }

    public void powerUp(){
        powerUpActive = true;
        powerUpTurns = 0;
    }

    public void powerDown(){
        powerDownActive = true;
        powerDownTurns = 0;
    }

    public void startWave(){
        skills.startWave();
    }

    public void startRound(){
        skills.startRound();
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
}
