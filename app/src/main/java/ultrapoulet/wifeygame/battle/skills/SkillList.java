package ultrapoulet.wifeygame.battle.skills;

import java.util.ArrayList;

import ultrapoulet.wifeygame.battle.BattleCharacter;
import ultrapoulet.wifeygame.character.SkillsEnum;

/**
 * Created by John on 7/11/2016.
 */
public class SkillList {

    private ArrayList<AbsSkill> skills;

    public SkillList(ArrayList<SkillsEnum> inSkills, BattleCharacter owner){
        skills = new ArrayList<>();
        for(int i = 0; i < inSkills.size(); i++){
            addSkill(inSkills.get(i).getBattleSkill(owner));
        }
    }

    public void addSkill(AbsSkill newSkill){
        if(newSkill != null && !hasSkill(newSkill.getClass())){
            skills.add(newSkill);
        }
    }

    public void setOwner(BattleCharacter owner){
        for(int i = 0; i < skills.size(); i++){
            skills.get(i).setOwner(owner);
        }
    }

    public void startBattle(BattleCharacter[] party){
        //Do anything that needs to be done at the start of a battle
        for(int i = 0; i < skills.size(); i++){
            skills.get(i).startBattle(party);
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

    public void endRound() {
        //Do anything that needs to be done at the end of a round
        for(int i = 0; i < skills.size(); i++){
            skills.get(i).endRound();
        }
    }

    public double physicalAttackPercentage(BattleCharacter enemy){
        //The amount to increase physical damage.
        //Some skill might need to see the state of the BattleEnemy, so it is included
        //Returned multiplier is additive. It will start at 1.0, and multiply values from
        //all skills
        double multiplier = 1.0;
        for(int i = 0; i < skills.size(); i++){
            multiplier *= skills.get(i).physicalAttackPercentage(enemy);
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
        return hits;
    }

    public double magicalAttackPercentage(BattleCharacter enemy){
        //The amount to increase magical damage.
        //Some skills might need to see the state of the BattleEnemy, so it is included
        //Returned multiplier is additive. It will start at 1.0, and multiply values
        //from all skills
        double multiplier = 1.0;
        for(int i = 0; i < skills.size(); i++){
            multiplier *= skills.get(i).magicalAttackPercentage(enemy);
        }
        return multiplier;
    }

    public double specialAttackPercentage(BattleCharacter enemy){
        //The amount to increase special damage.
        //Some skills might need to see the state of the BattleEnemy, so it is included
        //Returned multiplier is additive. It will start at 1.0, and multiply values
        //from all skills
        double multiplier = 1.0;
        for(int i = 0; i < skills.size(); i++){
            multiplier *= skills.get(i).specialAttackPercentage(enemy);
        }
        return multiplier;
    }

    public double healPercentage(BattleCharacter partyMember){
        //The amount to increase healing to a party member
        //Returned multiplier is additive.
        double multiplier = 1.0;
        for(int i = 0; i < skills.size(); i++){
            multiplier *= skills.get(i).healPercentage(partyMember);
        }
        return multiplier;
    }

    public double receivePhysicalAttackPercentage(BattleCharacter enemy){
        //The amount to decrease physical damage.
        //This will be capped, so as not to hit 0 or below
        double resistance = 0.0;
        for(int i = 0; i < skills.size(); i++){
            resistance += skills.get(i).receivePhysicalAttackPercentage(enemy) * (1.0 - resistance);
        }
        double multiplier = 1.0 - resistance;
        if(multiplier <= 0.10){
            multiplier = 0.10;
        }
        return multiplier;
    }

    public double receiveMagicalAttackPercentage(BattleCharacter enemy){
        //The amount to decrease magical damage.
        //This will be capped, so as not to hit 0 or below
        double resistance = 0.0;
        for(int i = 0; i < skills.size(); i++){
            resistance += skills.get(i).receiveMagicalAttackPercentage(enemy) * (1.0 - resistance);
        }
        double multiplier = 1.0 - resistance;
        if(multiplier <= 0.10){
            multiplier = 0.10;
        }
        return multiplier;
    }

    public double receiveSpecialAttackPercentage(BattleCharacter enemy){
        //The amount to decrease special damage.
        //This will be capped, so as not to hit 0 or below
        double resistance = 0.0;
        for(int i = 0; i < skills.size(); i++){
            resistance += skills.get(i).receiveSpecialAttackPercentage(enemy) * (1.0 - resistance);
        }
        double multiplier = 1.0 - resistance;
        if(multiplier <= 0.10){
            multiplier = 0.10;
        }
        return multiplier;
    }

    public double receiveHealPercentage(BattleCharacter partyMember){
        //The amount to increase healing from a party member
        //Returned multiplier is multiplicative
        double multiplier = 1.0;
        for(int i = 0; i < skills.size(); i++){
            multiplier *= skills.get(i).receiveHealPercentage(partyMember);
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

    public void onEnemyDefeat(BattleCharacter enemy){
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

    public boolean hasSkill(Class skillClass){
        for(int i = 0; i < skills.size(); i++){
            if(skills.get(i).getClass() == skillClass){
                return true;
            }
        }
        return false;
    }

    public void endBattle(){
        //We remove the owner of the skill to free memory
        for(int i = 0; i < skills.size(); i++){
            skills.get(i).endBattle();
        }
    }
}
