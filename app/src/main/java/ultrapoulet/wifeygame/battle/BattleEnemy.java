package ultrapoulet.wifeygame.battle;

import java.util.HashMap;
import java.util.Map;

import ultrapoulet.androidgame.framework.Image;
import ultrapoulet.wifeygame.battle.enemyai.EnemyAI;
import ultrapoulet.wifeygame.battle.enemyai.EnemyAI.EnemyAction;

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

    //private EnemySkills[] skills;

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

    public int getCurrentHP(){ return this.currentHP ;}

    public int getNumHits(){
        switch(getAction()){
            case POWER_ATTACK:
                return powerHits;
            case COMBO_ATTACK:
                return comboHits;
            case SPECIAL_ATTACK:
                return specialHits;
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

    public void battleStart(){
        this.currentHP = this.maxHP;
        ai.reset();
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

    public int PowerAttackDamage(){
        //Returns the damage that will be dealt for a Power Attack
        int baseDamage = this.powerDamage + (int) ((this.powerDamage / 10) * Math.random());
        if(powerUpActive){
            baseDamage = (int) (baseDamage * powerUpPercentage);
        }
        if(powerDownActive){
            baseDamage = (int) (baseDamage * powerDownPercentage);
        }
        return baseDamage;
    }

    public int ComboAttackDamage(){
        //Returns the damage that will be dealt for a Combo Attack
        int baseDamage = this.comboDamage + (int) ((this.comboDamage / 10) * Math.random());
        if(powerUpActive){
            baseDamage = (int) (baseDamage * powerUpPercentage);
        }
        if(powerDownActive){
            baseDamage = (int) (baseDamage * powerDownPercentage);
        }
        return baseDamage;
    }

    public int MagicAttackDamage(){
        //Returns the damage that will be dealt for a Magic Attack
        int baseDamage = this.magicDamage + (int) ((this.magicDamage / 10) * Math.random());
        if(powerUpActive){
            baseDamage = (int) (baseDamage * powerUpPercentage);
        }
        if(powerDownActive){
            baseDamage = (int) (baseDamage * powerDownPercentage);
        }
        return baseDamage;
    }

    public int HealAmount(){
        //Returns the amount that will be healed
        int baseHeal = this.healAmount + (int) ((this.healAmount / 10) * Math.random());
        return baseHeal;
    }

    public int SpecialAttackDamage(){
        //Returns the amount that will be dealt for a Special Attack
        int baseDamage = this.specialDamage + (int) ((this.specialDamage / 10) * Math.random());
        if(powerUpActive){
            baseDamage = (int) (baseDamage * powerUpPercentage);
        }
        if(powerDownActive){
            baseDamage = (int) (baseDamage * powerDownPercentage);
        }
        return baseDamage;
    }

    public int takePhysicalDamage(int damage){
        //Damage modifiers
        int displayDamage = damage;
        if(defendActive){
            displayDamage = (int) (displayDamage * defendPercentage);
        }
        if(weakenActive){
            displayDamage = (int) (displayDamage * weakenPercentage);
        }
        this.currentHP = this.currentHP - displayDamage;
        if(this.currentHP <= 0){
            this.currentHP = 0;
        }
        return displayDamage;
    }

    public int takeMagicalDamage(int damage){
        //Damage modifiers
        int displayDamage = damage;
        if(defendActive){
            displayDamage = (int) (displayDamage * defendPercentage);
        }
        if(weakenActive){
            displayDamage = (int) (displayDamage * weakenPercentage);
        }
        this.currentHP = this.currentHP - displayDamage;
        if(this.currentHP <= 0){
            this.currentHP = 0;
        }
        return displayDamage;
    }

    public int takeSpecialDamage(int damage){
        //Damage modifiers
        int displayDamage = damage;
        if(defendActive){
            displayDamage = (int) (displayDamage * defendPercentage);
        }
        if(weakenActive){
            displayDamage = (int) (displayDamage * weakenPercentage);
        }
        this.currentHP = this.currentHP - displayDamage;
        if(this.currentHP <= 0){
            this.currentHP = 0;
        }
        return displayDamage;
    }

    public int healDamage(int heal){
        //Heal modifiers
        int displayHeal = heal;
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

    public void startRound(){
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
        //totalRoundMultiplier *= roundMultiplier;

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
