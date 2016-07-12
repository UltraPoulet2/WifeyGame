package ultrapoulet.wifeygame.skills;

import java.util.ArrayList;

import ultrapoulet.wifeygame.battle.BattleCharacter;
import ultrapoulet.wifeygame.battle.BattleEnemy;

/**
 * Created by John on 7/11/2016.
 */
public class SkillList {

    private ArrayList<AbsSkill> skills;
    private BattleCharacter owner;

    public SkillList(BattleCharacter owner){
        this.owner = owner;
        skills = new ArrayList<>();
    }

    public void addSkill(AbsSkill newSkill){
        if(newSkill != null && !isDuplicate(newSkill)){
            skills.add(newSkill);
        }
    }

    public void startBattle(){
        //Do anything that needs to be done at the start of a battle
        for(int i = 0; i < skills.size(); i++){
            skills.get(i).startBattle();
        }
    }

    public void startWave(){
        //Do anything that needs to be done at the start of a wave
        for(int i = 0; i < skills.size(); i++){
            skills.get(i).startWave();
        }
    }

    public void startRound(){
        //Do anything that needs to be done at the start of a round
        for(int i = 0; i < skills.size(); i++){
            skills.get(i).startRound();
        }
    }

    public double physicalAttackPercentage(BattleEnemy enemy){
        //The amount to increase physical damage.
        //Some skill might need to see the state of the BattleEnemy, so it is included
        //Returned multiplier is additive. It will start at 1.0, and add/subtract values from
        //all skills
        double multiplier = 1.0;
        for(int i = 0; i < skills.size(); i++){
            multiplier += skills.get(i).physicalAttackPercentage(enemy);
        }
        return multiplier;
    }

    public int getBonusHits(){
        //If the skill increases the number of combo hits, return it
        //These will be capped to 10 at most
        int hits = 0;
        for(int i = 0; i < skills.size(); i++){
            hits += skills.get(i).getBonusHits();
        }
        return 0;
    }

    public double magicalAttackPercentage(BattleEnemy enemy){
        //The amount to increase magical damage.
        //Some skills might need to see the state of the BattleEnemy, so it is included
        //Returned multiplier is additive. It will start at 1.0, and add/subtract values
        //from all skills
        double multiplier = 1.0;
        for(int i = 0; i < skills.size(); i++){
            multiplier += skills.get(i).magicalAttackPercentage(enemy);
        }
        return multiplier;
    }

    public double specialAttackPercentage(BattleEnemy enemy){
        //The amount to increase special damage.
        //Some skills might need to see the state of the BattleEnemy, so it is included
        //Returned multiplier is additive. It will start at 1.0, and add/subtract values
        //from all skills
        double multiplier = 1.0;
        for(int i = 0; i < skills.size(); i++){
            multiplier += skills.get(i).specialAttackPercentage(enemy);
        }
        return multiplier;
    }

    public double healPercentage(BattleCharacter partyMember){
        //The amount to increase healing to a party member
        //Returned multiplier is additive.
        double multiplier = 1.0;
        for(int i = 0; i < skills.size(); i++){
            multiplier += skills.get(i).healPercentage(partyMember);
        }
        return multiplier;
    }

    public double receivePhysicalAttackPercentage(BattleEnemy enemy){
        //The amount to decrease physical damage.
        //This will be capped, so as not to hit 0 or below
        double multiplier = 1.0;
        for(int i = 0; i < skills.size(); i++){
            multiplier -= skills.get(i).receivePhysicalAttackPercentage(enemy);
        }
        if(multiplier <= 0.10){
            multiplier = 0.10;
        }
        return multiplier;
    }

    public double receiveMagicalAttackPercentage(BattleEnemy enemy){
        //The amount to decrease magical damage.
        //This will be capped, so as not to hit 0 or below
        double multiplier = 1.0;
        for(int i = 0; i < skills.size(); i++){
            multiplier -= skills.get(i).receiveMagicalAttackPercentage(enemy);
        }
        if(multiplier <= 0.10){
            multiplier = 0.10;
        }
        return multiplier;
    }

    public double receiveSpecialAttackPercentage(BattleEnemy enemy){
        //The amount to decrease special damage.
        //This will be capped, so as not to hit 0 or below
        double multiplier = 1.0;
        for(int i = 0; i < skills.size(); i++){
            multiplier -= skills.get(i).receiveSpecialAttackPercentage(enemy);
        }
        if(multiplier <= 0.10){
            multiplier = 0.10;
        }
        return multiplier;
    }

    public double receiveHealPercentage(BattleCharacter partyMember){
        //The amount to increase healing from a party member
        //Returned multiplier is additive
        double multiplier = 1.0;
        for(int i = 0; i < skills.size(); i++){
            multiplier += skills.get(i).receiveHealPercentage(partyMember);
        }
        return multiplier;
    }

    public void onDamageDealt(int damage){
        //Do things for when damage is dealt
        //Examples: Heal, increase counter, etc
        for(int i = 0; i < skills.size(); i++){
            skills.get(i).onDamageDealt(damage);
        }
    }

    public void onEnemyDefeat(BattleEnemy enemy){
        //Do things for when an enemy is defeated
        for(int i = 0; i < skills.size(); i++){
            skills.get(i).onEnemyDefeat(enemy);
        }
    }

    public void onDamageReceived(int damage){
        //Do things when damage is received
        for(int i = 0; i < skills.size(); i++){
            skills.get(i).onDamageReceived(damage);
        }
    }

    public boolean isDuplicate(AbsSkill newSkill){
        for(int i = 0; i < skills.size(); i++){
            if(skills.get(i).getClass() == newSkill.getClass()){
                return true;
            }
        }
        return false;
    }
}
