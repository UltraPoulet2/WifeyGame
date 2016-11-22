package ultrapoulet.wifeygame.battle;

import java.util.ArrayList;
import java.util.List;

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

    private ArrayList<WifeyCharacter> requiredList = null;

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
    public EnemyCharacter[] getCharacterEnemies(){
        EnemyCharacter[] temp = new EnemyCharacter[enemyList.size()];
        enemyList.toArray(temp);
        return temp;
    }


    public List<BattleCharacter> getBattleEnemies() {
        List<BattleCharacter> returnValue = new ArrayList<>();
        for(int i = 0; i < enemyList.size(); i++){
            returnValue.add(enemyList.get(i).getBattleEnemy());
        }
        return returnValue;
    }

    public void addRequirement(AbsRequirement r) {
        if(r != null){
            restrictionList.add(r);
            if(r instanceof RequiredCharacterRequirement){
                requiredList = ((RequiredCharacterRequirement) r).getRequiredList();
            }
        }
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
