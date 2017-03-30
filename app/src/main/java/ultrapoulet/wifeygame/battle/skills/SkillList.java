package ultrapoulet.wifeygame.battle.skills;

import java.util.ArrayList;
import java.util.List;

import ultrapoulet.wifeygame.battle.BattleCharacter;
import ultrapoulet.wifeygame.battle.skills.AbsSkill.Multipliers;
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

    public void removeSkill(AbsSkill removeSkill){
        if(removeSkill != null && hasSkill(removeSkill.getClass())){
            int i = 0;
            while(i < skills.size() && skills.get(i).getClass() != removeSkill.getClass()){
                i++;
            }
            skills.remove(i);
        }
    }

    public void giveSkillBonus(double multiplier, Class givingSkill, Class receivingSkill){
        if(!AbsSkill.class.isAssignableFrom(givingSkill) || !AbsSkill.class.isAssignableFrom(receivingSkill)){
            System.out.println("SkillList:giveSkillBonus: input " + givingSkill + " and/or " + receivingSkill + " are not AbsSkills");
            return;
        }
        for(int i = 0; i < skills.size(); i++){
            if(skills.get(i).getClass() == receivingSkill){
                skills.get(i).receiveBonus(multiplier, givingSkill);
            }
        }
    }

    public AbsSkill get(int index){
        return skills.get(index);
    }

    public int size(){
        return skills.size();
    }

    public void setOwner(BattleCharacter owner){
        for(int i = 0; i < skills.size(); i++){
            skills.get(i).setOwner(owner);
        }
    }

    public void startBattle(List<BattleCharacter> party) {
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

    public int startRound(){
        //Do anything that needs to be done at the start of a round
        int displayDamage = 0;
        for(int i = 0; i < skills.size(); i++){
            displayDamage += skills.get(i).startRound();
        }
        return displayDamage;
    }

    public void endRound() {
        //Do anything that needs to be done at the end of a round
        for(int i = 0; i < skills.size(); i++){
            skills.get(i).endRound();
        }
    }

    public void endWave(BattleCharacter enemy) {
        //Do anything that needs to be done at the end of a wave
        //This is currently only relevant for BattleWifeys
        for(int i = 0; i < skills.size(); i++){
            skills.get(i).endWave(enemy);
        }
    }

    public void resetSkills(){
        for(int i = 0; i < skills.size(); i++){
            skills.get(i).resetValues();
        }
    }

    public void updateParty(List<BattleCharacter> party){
        for(int i = 0; i < skills.size(); i++){
            skills.get(i).updateParty(party);
        }
    }

    public double physicalAttackPercentage(BattleCharacter enemy){
        //The amount to increase physical damage.
        //Some skill might need to see the state of the BattleEnemy, so it is included
        //Returned multiplier is multiplicative. It will start at 1.0, and multiply values from
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
        //Returned multiplier is multiplicative. It will start at 1.0, and multiply values
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
        //Returned multiplier is multiplicative. It will start at 1.0, and multiply values
        //from all skills
        double multiplier = 1.0;
        for(int i = 0; i < skills.size(); i++){
            multiplier *= skills.get(i).specialAttackPercentage(enemy);
        }
        return multiplier;
    }

    public double healPercentage(BattleCharacter partyMember){
        //The amount to increase healing to a party member
        //Returned multiplier is multiplicative.
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

    public boolean canPreventDeath(){
        boolean returnValue = false;
        for(int i = 0; i < skills.size(); i++){
            returnValue = returnValue || skills.get(i).canPreventDeath();
        }
        return returnValue;
    }

    public int preventDeath() {
        int returnValue = 0;
        for(int i = 0; i < skills.size(); i++){
            returnValue += skills.get(i).preventDeath();
        }
        return returnValue;
    }

    public int getBonusExp() {
        int returnValue = 0;
        for(int i = 0; i < skills.size(); i++){
            returnValue += skills.get(i).getBonusExp();
        }
        return returnValue;
    }

    public int getBonusGold() {
        int returnValue = 0;
        for(int i = 0; i < skills.size(); i++){
            returnValue += skills.get(i).getBonusGold();
        }
        return returnValue;
    }

    public boolean hasSkill(Class skillClass){
        for(int i = 0; i < skills.size(); i++){
            if(skills.get(i).getClass() == skillClass){
                return true;
            }
        }
        return false;
    }


    public Multipliers getMultipliers(BattleCharacter enemy){
        Multipliers returnValue = new Multipliers();
        for(int i = 0; i < skills.size(); i++){
            Multipliers skillMult = skills.get(i).getMultipliers(enemy);
            returnValue.setPhysAtk(returnValue.getPhysAtk() * skillMult.getPhysAtk());
            returnValue.setMagAtk(returnValue.getMagAtk() * skillMult.getMagAtk());
            returnValue.setSpecAtk(returnValue.getSpecAtk() * skillMult.getSpecAtk());
            returnValue.setPhysDef(returnValue.getPhysDef() + (skillMult.getPhysDef() * (1.0 - returnValue.getPhysDef())));
            returnValue.setMagDef(returnValue.getMagDef() + (skillMult.getMagDef() * (1.0 - returnValue.getMagDef())));
            returnValue.setSpecDef(returnValue.getSpecDef() + (skillMult.getSpecDef() * (1.0 - returnValue.getSpecDef())));
        }
        returnValue.setPhysDef(1.0 - returnValue.getPhysDef() > 0.10 ? 1.0 - returnValue.getPhysDef() : 0.10);
        returnValue.setMagDef(1.0 - returnValue.getMagDef() > 0.10 ? 1.0 - returnValue.getMagDef() : 0.10);
        returnValue.setSpecDef(1.0 - returnValue.getSpecDef() > 0.10 ? 1.0 - returnValue.getSpecDef() : 0.10);
        return returnValue;
    }
}
