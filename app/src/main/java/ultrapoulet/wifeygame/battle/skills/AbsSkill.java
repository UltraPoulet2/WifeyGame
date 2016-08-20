package ultrapoulet.wifeygame.battle.skills;

import ultrapoulet.wifeygame.battle.BattleCharacter;

/**
 * Created by John on 7/10/2016.
 */
public class AbsSkill {

    //The owner of the skill
    protected BattleCharacter owner;
    protected String skillName;
    protected String description;

    protected int PHYS_ATK = 0;
    protected int MAG_ATK = 1;
    protected int SPEC_ATK = 2;
    protected int PHYS_DEF = 3;
    protected int MAG_DEF = 4;
    protected int SPEC_DEF = 5;

    public AbsSkill(BattleCharacter owner){ this.owner = owner; }

    public void setOwner(BattleCharacter owner){ this.owner = owner; }

    public String getSkillName(){
        return this.skillName;
    }

    public String getDescription(){
        return description;
    }

    public void startBattle(BattleCharacter[] party){
        //Do anything that needs to be done at the start of a battle
    }

    public void receiveBonus(double multiplier, Class givingSkill){
        //Do anything that needs to be done for receiving a bonus from another class.
    }

    public void startWave(){
        //Do anything that needs to be done at the start of a wave
    }

    public void startRound(){
        //Do anything that needs to be done at the start of a round
    }

    public void endRound() {
        //Do anything that needs to be done at the end of a round
    }

    public double physicalAttackPercentage(BattleCharacter enemy){
        //The amount to increase physical damage.
        //Some skill might need to see the state of the BattleEnemy, so it is included
        //Returned multiplier is additive. It will start at 1.0, and multiply values from
        //all skills
        return 1.0;
    }

    public int getBonusHits(){
        //If the skill increases the number of combo hits, return it
        //These will be capped to 10 at most
        return 0;
    }

    public double magicalAttackPercentage(BattleCharacter enemy){
        //The amount to increase magical damage.
        //Some skills might need to see the state of the BattleEnemy, so it is included
        //Returned multiplier is additive. It will start at 1.0, and multiply values
        //from all skills
        return 1.0;
    }

    public double specialAttackPercentage(BattleCharacter enemy){
        //The amount to increase special damage.
        //Some skills might need to see the state of the BattleEnemy, so it is included
        //Returned multiplier is additive. It will start at 1.0, and multiply values
        //from all skills
        return 1.0;
    }

    public double healPercentage(BattleCharacter partyMember){
        //The amount to increase healing to a party member
        //Returned multiplier is multiplicative.
        return 1.0;
    }

    public double receivePhysicalAttackPercentage(BattleCharacter enemy){
        //The amount to decrease physical damage.
        //This will be capped, so as not to hit 0 or below
        return 0.0;
    }

    public double receiveMagicalAttackPercentage(BattleCharacter enemy){
        //The amount to decrease magical damage.
        //This will be capped, so as not to hit 0 or below
        return 0.0;
    }

    public double receiveSpecialAttackPercentage(BattleCharacter enemy){
        //The amount to decrease special damage.
        //This will be capped, so as not to hit 0 or below
        return 0.0;
    }

    public double receiveHealPercentage(BattleCharacter partyMember){
        //The amount to increase healing from a party member
        //Returned multiplier is additive
        return 1.0;
    }

    public void onDamageDealt(int damage){
        //Do things for when damage is dealt
        //Examples: Heal, increase counter, etc
    }

    public void onEnemyDefeat(BattleCharacter enemy){
        //Do things for when an enemy is defeated
    }

    public void onDamageReceived(int damage){
        //Do things when damage is received
    }

    //Remove the skill owner at end of battle to garbage collect
    public void endBattle(){
        this.owner = null;
    }

    public double[] getMultipliers(BattleCharacter enemy){
        double[] multipliers = new double[6];
        for(int i = 0; i < 3; i++){
            multipliers[i] = 1.0;
        }
        for(int i = 3; i < 6; i++){
            multipliers[i] = 0.0;
        }
        return multipliers;
    }

}
