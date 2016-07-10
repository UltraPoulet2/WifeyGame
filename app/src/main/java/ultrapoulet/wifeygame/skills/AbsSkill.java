package ultrapoulet.wifeygame.skills;

import ultrapoulet.wifeygame.battle.BattleCharacter;
import ultrapoulet.wifeygame.battle.BattleEnemy;

/**
 * Created by John on 7/10/2016.
 */
public class AbsSkill {

    //The owner of the skill
    private BattleCharacter owner;
    private String skillName;
    private String description;

    public AbsSkill(BattleCharacter owner){
        this.owner = owner;
    }

    public String getSkillName(){
        return skillName;
    }

    public String getDescription(){
        return description;
    }

    public void startBattle(){
        //Do anything that needs to be done at the start of a battle
    }

    public void startWave(){
        //Do anything that needs to be done at the start of a wave
    }

    public void startRound(){
        //Do anything that needs to be done at the start of a round
    }

    public double physicalAttackPercentage(BattleEnemy enemy){
        //The amount to increase physical damage.
        //Some skill might need to see the state of the BattleEnemy, so it is included
        //Returned multiplier is additive. It will start at 1.0, and add/subtract values from
        //all skills
        return 0.0;
    }

    public double magicalAttackPercentage(BattleEnemy enemy){
        //The amount to increase magical damage.
        //Some skills might need to see the state of the BattleEnemy, so it is included
        //Returned multiplier is additive. It will start at 1.0, and add/subtract values
        //from all skills
        return 0.0;
    }

    public double specialAttackPercentage(BattleEnemy enemy){
        //The amount to increase special damage.
        //Some skills might need to see the state of the BattleEnemy, so it is included
        //Returned multiplier is additive. It will start at 1.0, and add/subtract values
        //from all skills
        return 0.0;
    }

    public double healPercentage(BattleCharacter partyMember){
        //The amount to increase healing to a party member
        //Returned multiplier is additive.
        return 0.0;
    }

    public double receivePhysicalAttackPercentage(BattleEnemy enemy){
        //The amount to decrease physical damage.
        //This will be capped, so as not to hit 0 or below
        return 0.0;
    }

    public double receiveMagicalAttackPercentage(BattleEnemy enemy){
        //The amount to decrease magical damage.
        //This will be capped, so as not to hit 0 or below
        return 0.0;
    }

    public double receiveSpecialAttackPercentage(BattleEnemy enemy){
        //The amount to decrease special damage.
        //This will be capped, so as not to hit 0 or below
        return 0.0;
    }

    public double receiveHealPercentage(BattleCharacter partyMember){
        //The amount to increase healing from a party member
        //Returned multiplier is additive
        return 0.0;
    }

    public void onDamageDealt(int damage){
        //Do things for when damage is dealt
        //Examples: Heal, increase counter, etc
    }

    public void onEnemyDefeat(BattleEnemy enemy){
        //Do things for when an enemy is defeated
    }

    public void onDamageReceived(int damage){
        //Do things when damage is received
    }

}
