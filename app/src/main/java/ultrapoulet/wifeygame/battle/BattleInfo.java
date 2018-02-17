package ultrapoulet.wifeygame.battle;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ultrapoulet.androidgame.framework.Graphics;
import ultrapoulet.androidgame.framework.Graphics.ImageFormat;
import ultrapoulet.androidgame.framework.Image;
import ultrapoulet.wifeygame.battle.requirements.AbsRequirement;
import ultrapoulet.wifeygame.battle.requirements.AllowedCharacterRequirement;
import ultrapoulet.wifeygame.battle.requirements.BannedCharacterRequirement;
import ultrapoulet.wifeygame.battle.requirements.BannedSkillRequirement;
import ultrapoulet.wifeygame.battle.requirements.RequiredCharacterRequirement;
import ultrapoulet.wifeygame.battle.requirements.RequiredSkillRequirement;
import ultrapoulet.wifeygame.character.EnemyCharacter;
import ultrapoulet.wifeygame.character.WifeyCharacter;
import ultrapoulet.wifeygame.gamestate.StoryBattles;

/**
 * Created by John on 6/19/2016.
 */
public class BattleInfo {

    private String battleName;
    private String key;
    private int partyMax = 7;
    private ArrayList<EnemyCharacter> enemyList = new ArrayList<>();
    private ArrayList<AbsRequirement> restrictionList;

    private ArrayList<WifeyCharacter> requiredList = new ArrayList<>();

    private AbsRequirement characterRestrictions;
    private AbsRequirement characterRequirements;
    private AbsRequirement skillRestrictions;
    private AbsRequirement skillRequirements;

    private ArrayList<WifeyDrop> dropList = new ArrayList<>();

    private String background;

    private int energyRequirement;

    private int numAttempts = 0;
    private int numComplete = 0;

    private boolean unlocked = false;
    private ArrayList<String> unlockList = new ArrayList<>();

    private int recommendedPower = 0;

    private class WifeyDrop{
        private WifeyCharacter wifey;
        private int dropChance;

        public WifeyDrop(WifeyCharacter inChar, int drop){
            this.wifey = inChar;
            this.dropChance = drop;
        }

        public WifeyCharacter getWifey(){
            return this.wifey;
        }

        public int getDropChance(){
            return this.dropChance;
        }
    }

    public void setName(String name){
        battleName = name;
    }

    public String getName(){
        return battleName;
    }

    public void setKey(String key){
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }

    public void setPartyMax(int max){ partyMax = max; }

    public int getPartyMax() { return partyMax; }

    public void addEnemy(EnemyCharacter enemy){
        enemyList.add(enemy);
    }

    public EnemyCharacter getEnemy(int index){
        if(index >= 0 && index < enemyList.size()){
            return enemyList.get(index);
        }
        else{
            return null;
        }
    }

    //I'm not sure if this will be needed
    public List<EnemyCharacter> getCharacterEnemies(){
        return enemyList;
    }


    public List<BattleCharacter> getBattleEnemies(Graphics g) {
        List<BattleCharacter> returnValue = new ArrayList<>();
        for(int i = 0; i < enemyList.size(); i++){
            returnValue.add(enemyList.get(i).getBattleEnemy(g));
        }
        return returnValue;
    }


    public void setBackground(String input){
        this.background = input;
    }

    public String getBackgroundName(){
        return background;
    }

    public Image getBackground(Graphics g){
        return g.newImage("backgrounds/" + background + ".png", ImageFormat.RGB565);
    }

    public void setEnergyRequirement(int energy){
        this.energyRequirement = energy;
    }

    public int getEnergyRequirement(){
        return energyRequirement;
    }

    public void addRequirement(AbsRequirement r) {
        if(r != null){
            if(r instanceof AllowedCharacterRequirement || r instanceof BannedCharacterRequirement) {
                characterRestrictions = r;
            }
            else if(r instanceof RequiredCharacterRequirement){
                characterRequirements = r;
                requiredList = ((RequiredCharacterRequirement) characterRequirements).getRequiredList();
            }
            else if(r instanceof BannedSkillRequirement) {
                skillRestrictions = r;
            }
            else if(r instanceof RequiredSkillRequirement) {
                skillRequirements = r;
            }
        }
    }

    public void addDrop(WifeyCharacter input, int dropChance){
        boolean addWifey = true;
        for(int i = 0; i < dropList.size(); i++){
            if(dropList.get(i).getWifey() == input){
                addWifey = false;
            }
        }
        if(addWifey) {
            dropList.add(new WifeyDrop(input, dropChance));
            Log.i("BattleInfo", "Added a drop: " + input.getHashKey() + " to battle: " + this.getName());
        }
    }

    public int getFoundDrops(){
        int result = 0;
        for(int i = 0; i < dropList.size(); i++){
            if(dropList.get(i).getWifey().isDropped()){
                result++;
            }
        }
        return result;
    }

    public int getMaxDrops(){
        return dropList.size();
    }

    //Does the calculations for the drops and returns the list of characters dropped
    public ArrayList<WifeyCharacter> performDrops(int bonusRecruiting){
        ArrayList<WifeyCharacter> results = new ArrayList<>();
        for(WifeyDrop drop : dropList){
            if(drop.getWifey().isDropped()){
                continue;
            }
            Random rng = new Random();
            if(rng.nextInt(100) < drop.getDropChance() + bonusRecruiting){
                drop.getWifey().drop();
                results.add(drop.getWifey());
            }
        }
        return results;
    }

    public void addUnlock(String value){
        this.unlockList.add(value);
    }

    public void validateUnlocks(){
        ArrayList<String> validList = new ArrayList<>();
        for(String value : unlockList){
            if(StoryBattles.getBattle(value) != null){
                validList.add(value);
                //System.out.println("Battle found: " + value);
                Log.i("BattleInfo", "Battle found: " + value);
            }
            else {
                //System.out.println("BattleInfo::validateUnlocks(): " + "Could not find battle: " + value);
                Log.e("BattleInfo", "Could not find battle: " + value);
            }
        }
        unlockList = validList;
    }

    public void performUnlocks(){
        for(String value : unlockList){
            StoryBattles.getBattle(value).unlock();
        }
    }

    public void unlock(){
        this.unlocked = true;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public int getNumAttempts(){
        return numAttempts;
    }

    public void setNumAttempts(int input){
        numAttempts = input;
    }

    public void incrementNumAttempts(){
        numAttempts++;
    }

    public int getNumComplete(){
        return numComplete;
    }

    public void setNumComplete(int input){
        numComplete = input;
    }

    public void setRecommendedPower(int input) {
        this.recommendedPower = input;
    }

    public int getRecommendedPower() {
        return recommendedPower;
    }

    public void incrementNumComplete(){
        numComplete++;
        if(numComplete == 1){
            performUnlocks();
        }
    }

    private void generateRestrictionList() {
        if(restrictionList == null) {
            restrictionList = new ArrayList<>();
            restrictionList.add(characterRestrictions);
            restrictionList.add(characterRequirements);
            restrictionList.add(skillRestrictions);
            restrictionList.add(skillRequirements);
        }
    }

    public ArrayList<AbsRequirement> getRequirements(){
        generateRestrictionList();
        return restrictionList;
    }

    public AbsRequirement getCharacterRestrictions() {
        return characterRestrictions;
    }

    public AbsRequirement getCharacterRequirements() {
        return characterRequirements;
    }

    public AbsRequirement getSkillRestrictions() {
        return skillRestrictions;
    }

    public AbsRequirement getSkillRequirements() {
        return skillRequirements;
    }

    public ArrayList<WifeyCharacter> getRequiredList(){
        return requiredList;
    }

    public boolean allowCharacter(WifeyCharacter c){
        generateRestrictionList();
        for(AbsRequirement r : restrictionList){
            if(r != null && !r.validateCharacter(c)){
                return false;
            }
        }
        return true;
    }

    public boolean validParty(List<WifeyCharacter> party){
        generateRestrictionList();
        for(AbsRequirement r : restrictionList){
            if(r != null && !r.validateParty(party)){
                return false;
            }
        }
        return true;
    }
}
