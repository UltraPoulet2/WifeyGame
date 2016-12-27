package ultrapoulet.wifeygame.battle;

import java.util.List;

import ultrapoulet.androidgame.framework.Graphics;
import ultrapoulet.androidgame.framework.Image;
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
    protected Image image;

    protected Element attackElement;
    protected Element strongElement;
    protected Element weakElement;

    protected final static double strongMultiplier = 0.5;
    protected final static double weakMultiplier = 2.0;

    protected int transformNumber = 0;

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

    public Image getImage(){
        return this.image;
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

    public abstract void startBattle(List<BattleCharacter> party);
    public abstract void startTurn();
    public abstract int PowerAttackDamage(BattleCharacter enemy);
    public abstract int ComboAttackDamage(BattleCharacter enemy);
    public abstract int MagicAttackDamage(BattleCharacter enemy);
    public abstract int HealAmount(BattleCharacter target);
    public abstract int SpecialAttackDamage(BattleCharacter enemy);
    public abstract int getNumHits();

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

    public int startRound(){ return skills.startRound(); }

    public abstract void endRound();

    public void endWave() { skills.endWave(); }

    public double getElementDamage(BattleCharacter enemy){
        if(enemy.getStrongElement() == attackElement){
            return strongMultiplier;
        }
        else if(enemy.getWeakElement() == attackElement){
            return weakMultiplier;
        }
        return 1.0;
    }

    //Return true if the attack element is the enemy's weakness
    public boolean isWeaknessAttack(BattleCharacter enemy){
        return enemy.getWeakElement() == attackElement;
    }

    //Return true if the attack element is the enemy's strength
    public boolean isStrongAttack(BattleCharacter enemy){
        return enemy.getStrongElement() == attackElement;
    }

    public abstract boolean canTransform();

    //I don't like passing in Graphics here, but quickest workaround for now
    public abstract void transform(Graphics g);

    public boolean canPreventDeath() {
        return skills.canPreventDeath();
    }

    public int preventDeath() {
        return skills.preventDeath();
    }

    public void resetSkills(){
        skills.resetSkills();
    }

    public void updateParty(List<BattleCharacter> party){
        skills.updateParty(party);
    }
}
