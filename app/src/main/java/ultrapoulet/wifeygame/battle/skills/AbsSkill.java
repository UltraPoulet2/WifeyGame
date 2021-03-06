package ultrapoulet.wifeygame.battle.skills;

import android.util.Log;

import java.util.List;

import ultrapoulet.wifeygame.battle.BattleCharacter;
import ultrapoulet.wifeygame.battle.BattleCharacter.EnemyAction;
import ultrapoulet.wifeygame.battle.BattleCharacter.PlayerAction;

/**
 * Created by John on 7/10/2016.
 */
public abstract class AbsSkill {

    //The owner of the skill
    protected BattleCharacter owner;
    protected String skillName;

    public static class Multipliers{
        private double physAtk = 1.0;
        private double magAtk = 1.0;
        private double specAtk = 1.0;
        private double physDef = 0.0;
        private double magDef = 0.0;
        private double specDef = 0.0;

        public void setPhysAtk(double input){
            this.physAtk = input;
        }

        public double getPhysAtk(){
            return this.physAtk;
        }

        public void setMagAtk(double input){
            this.magAtk = input;
        }

        public double getMagAtk(){
            return this.magAtk;
        }

        public void setSpecAtk(double input){
            this.specAtk = input;
        }

        public double getSpecAtk(){
            return this.specAtk;
        }

        public void setPhysDef(double input){
            this.physDef = input;
        }

        public double getPhysDef(){
            return this.physDef;
        }

        public void setMagDef(double input){
            this.magDef = input;
        }

        public double getMagDef(){
            return this.magDef;
        }

        public void setSpecDef(double input){
            this.specDef = input;
        }

        public double getSpecDef(){
            return this.specDef;
        }
    }

    public AbsSkill(BattleCharacter owner){ this.owner = owner; }

    public void setOwner(BattleCharacter owner){ this.owner = owner; }

    public String getSkillName(){
        if(this.skillName == null){
            Log.e("AbsSkill", "Skill name has not been set");
            return "";
        }
        return this.skillName;
    }

    public String getDescription(BattleCharacter enemy){
        return "";
    }

    public void startBattle(List<BattleCharacter> party){
        //Do anything that needs to be done at the start of a battle
    }

    public void receiveBonus(double multiplier, Class givingSkill){
        //Do anything that needs to be done for receiving a bonus from another class.
    }

    public void startWave(){
        //Do anything that needs to be done at the start of a wave
    }

    public int startRound(){
        //Do anything that needs to be done at the start of a round
        return 0;
    }

    public double startRoundPartyRegenPercentage(){
        //The percentage of healing to be performed to the entire party at the start of a round
        //Most skills will not party regen
        return 0.0;
    }

    public void endRound() {
        //Do anything that needs to be done at the end of a round
    }

    public void endWave(BattleCharacter enemy) {
        //Do anything that needs to be done at the end of a wave
        //Pass in the enemy that was defeated that round
    }

    public void resetValues(){
        //This function is used to reset multipliers before updateParty is called
    }

    public void updateParty(List<BattleCharacter> party){
        //Do anything that needs to be done if the party is modified (Transformation)
    }

    public double physicalAttackPercentage(BattleCharacter enemy){
        //The amount to increase physical damage.
        //Some skill might need to see the state of the BattleEnemy, so it is included
        //Returned multiplier is multiplicative. It will start at 1.0, and multiply values from
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
        //Returned multiplier is multiplicative. It will start at 1.0, and multiply values
        //from all skills
        return 1.0;
    }

    public double specialAttackPercentage(BattleCharacter enemy){
        //The amount to increase special damage.
        //Some skills might need to see the state of the BattleEnemy, so it is included
        //Returned multiplier is multiplicative. It will start at 1.0, and multiply values
        //from all skills
        return 1.0;
    }

    public double healPercentage() {
        //Return the amount to increase healing where the party member does not matter
        //Returned multiplier is multiplicative.
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

    public double receiveHealPercentage() {
        //The amount to increase healing where the party member does not matter
        //Returned multiplier is additive
        return 1.0;
    }

    public double receiveHealPercentage(BattleCharacter partyMember){
        //The amount to increase healing from a party member
        //Returned multiplier is additive
        return 1.0;
    }

    public void onActionSelect(PlayerAction playerAction) {
        //Do things when a player selects an action
    }

    public void onActionSelect(EnemyAction enemyAction) {
        //Do things when an enemy selects an action
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

    public boolean canPreventDeath() {
        //Return true if it is possible for death to be prevented
        return false;
    }

    public int preventDeath() {
        //Return the amount of heal if death is prevented
        return 0;
    }

    public int getBonusExp() {
        //Return the amount of bonus experience received from battle
        return 0;
    }

    public int getBonusGold(){
        //Return the amount of bonus gold received from battle
        return 0;
    }

    public int getBonusRecruiting() {
        //Return the amount of bonus recruiting percentage
        return 0;
    }

    //Remove the skill owner at end of battle to garbage collect
    public void endBattle(){
        this.owner = null;
    }

    public Multipliers getMultipliers(BattleCharacter enemy) {
        return new Multipliers();
    }

}
