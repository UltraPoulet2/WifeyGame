package ultrapoulet.wifeygame.battle;

import ultrapoulet.wifeygame.battle.skills.SkillList;
import ultrapoulet.wifeygame.character.Element;

/**
 * Created by John on 7/13/2016.
 */
public abstract class BattleCharacter {
    protected String name;
    protected int maxHP;
    protected int currentHP;
    protected SkillList skills;

    protected Element attackElement;
    protected Element strongElement;
    protected Element weakElement;

    protected final static double strongMultiplier = 0.5;
    protected final static double weakMultiplier = 2.0;

    public String getName(){ return this.name; }

    public int getMaxHP(){ return this.maxHP; }

    public int getCurrentHP(){ return this.currentHP; }

    public void setCurrentHP(int hp){
        if(hp < this.maxHP){
            this.currentHP = hp;
        }
        else{
            this.currentHP = this.maxHP;
        }
    }

    public boolean hasSkill(Class skillClass){
        return skills.hasSkill(skillClass);
    }

    public void giveSkillBonus(double multiplier, Class givingSkill, Class receivingSkill){
        skills.giveSkillBonus(multiplier, givingSkill, receivingSkill);
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

    public abstract void startBattle(BattleCharacter[] party);
    public abstract int PowerAttackDamage(BattleCharacter enemy);
    public abstract int ComboAttackDamage(BattleCharacter enemy);
    public abstract int MagicAttackDamage(BattleCharacter enemy);
    public abstract int HealAmount(BattleCharacter target);
    public abstract int SpecialAttackDamage(BattleCharacter enemy);

    public void onDamageDealt(int damage){
        skills.onDamageDealt(damage);
    }

    public void onEnemyDefeat(BattleCharacter enemy){
        skills.onEnemyDefeat(enemy);
    }

    public abstract int takePhysicalDamage(int damage, BattleCharacter enemy);
    public abstract int takeMagicalDamage(int damage, BattleCharacter enemy);
    public abstract int takeSpecialDamage(int damage, BattleCharacter enemy);
    public abstract int healDamage(int heal, BattleCharacter healer);

    public void startWave() { skills.startWave(); }

    public void startRound(){ skills.startRound(); }

    public abstract void endRound();

    public double getElementDamage(BattleCharacter enemy){
        if(enemy.getStrongElement() == attackElement){
            return strongMultiplier;
        }
        else if(enemy.getWeakElement() == attackElement){
            return weakMultiplier;
        }
        return 1.0;
    }

    public abstract boolean canTransform();

    public abstract void transform();

    public abstract void resetSkills();

    public abstract void updateParty(BattleCharacter[] party);
}
