package ultrapoulet.wifeygame.battle;

import java.util.ArrayList;
import java.util.List;

import ultrapoulet.androidgame.framework.Graphics;
import ultrapoulet.androidgame.framework.Graphics.ImageFormat;
import ultrapoulet.androidgame.framework.Image;
import ultrapoulet.wifeygame.battle.requirements.AbsRequirement;
import ultrapoulet.wifeygame.battle.requirements.RequiredCharacterRequirement;
import ultrapoulet.wifeygame.character.EnemyCharacter;
import ultrapoulet.wifeygame.character.WifeyCharacter;

/**
 * Created by John on 6/19/2016.
 */
public class BattleInfo {

    private String battleName;
    private int partyMax = 7;
    private ArrayList<EnemyCharacter> enemyList = new ArrayList<>();
    private ArrayList<AbsRequirement> restrictionList = new ArrayList<>();

    private ArrayList<WifeyCharacter> requiredList = new ArrayList<>();

    private ArrayList<WifeyDrop> dropList = new ArrayList<>();

    private String background;

    private int energyRequirement;

    private int numAttempts = 0;
    private int numComplete = 0;

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
            restrictionList.add(r);
            if(r instanceof RequiredCharacterRequirement){
                requiredList = ((RequiredCharacterRequirement) r).getRequiredList();
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
            System.out.println("Added a drop: " + input.getHashKey());
        }
    }

    public int getFoundDrops(){
        return 0;
    }

    public int getMaxDrops(){
        return dropList.size();
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

    public void incrementNumComplete(){
        numComplete++;
    }


    public ArrayList<AbsRequirement> getRequirements(){
        return restrictionList;
    }

    public ArrayList<WifeyCharacter> getRequiredList(){
        return requiredList;
    }

    public boolean allowCharacter(WifeyCharacter c){
        for(int i = 0; i < restrictionList.size(); i++){
            if(!restrictionList.get(i).validateCharacter(c)){
                return false;
            }
        }
        return true;
    }

    public boolean allowParty(List<WifeyCharacter> party){
        for(int i = 0; i < restrictionList.size(); i++){
            if(!restrictionList.get(i).validateParty(party)){
                return false;
            }
        }
        return true;
    }

    public boolean validParty(List<WifeyCharacter> party){
        for(int i = 0; i < party.size(); i++){
            if(party.get(i) != null && allowCharacter(party.get(i)) == false){
                return false;
            }
        }
        return allowParty(party);
    }
}
